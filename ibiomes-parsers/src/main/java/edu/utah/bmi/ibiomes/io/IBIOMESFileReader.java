/*
 * iBIOMES - Integrated Biomolecular Simulations
 * Copyright (C) 2014  Julien Thibault, University of Utah
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.utah.bmi.ibiomes.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.CompressorStreamFactory;

import edu.utah.bmi.Utils;

/**
 * File input reader (handles compressed files)
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileReader {
	
	public static final String HEX_HEADER_BZIP 		= "425A";
	public static final String HEX_HEADER_COMPRESS 	= "1F9D";
	public static final String HEX_HEADER_GZIP		= "1F8B";
	public static final String HEX_HEADER_PKZIP		= "504B0304";

	private static final int BUFFER_SIZE = 16384;
	
	private BufferedReader br;
	private boolean isCompressed = false;
	private boolean isArchived = false;
	private String compressionScheme = null;
	private File file;
	private String hex;
	
	/**
	 * Create new file input reader
	 * @param file File
	 * @throws CompressorException
	 * @throws IOException 
	 */
	public IBIOMESFileReader(File file) throws CompressorException, IOException
	{
		this.file = file;
		
		//check if compressed
		byte[] firstBytes = this.readFirstBytes(4);
		hex = Utils.getHex(firstBytes);
		if (isCompressed(hex))
		{
			isCompressed  = true;
			br = new BufferedReader(new InputStreamReader(getInputStreamForCompressedFile(hex)), BUFFER_SIZE);
		}
		else //default file input stream 
		{
			isCompressed = false;
			br = new BufferedReader(new FileReader(file), BUFFER_SIZE);
		}
	}
	
	/**
	 * Check whether mark and reset are supported for this input stream
	 * @return True if mark and reset are supported for this input stream
	 */
	public boolean markSupported(){
		return br.markSupported();
	}
	
	/**
	 * Check if the file is compressed based on magik numbers
	 * @param hex Hexadecimal representation of first bytes
	 * @return True if compressed
	 */
	private boolean isCompressed(String hex){
		return (hex.startsWith(HEX_HEADER_BZIP) || 
				hex.startsWith(HEX_HEADER_COMPRESS) || 
				hex.startsWith(HEX_HEADER_GZIP) ||  
				hex.startsWith(HEX_HEADER_PKZIP));
	}
	
	/**
	 * Get input stream reader
	 * @param hex Hexadecimal representation of first bits 
	 * @return Input stream
	 * @throws CompressorException
	 * @throws IOException
	 */
	private CompressorInputStream getInputStreamForCompressedFile(String hex) throws CompressorException, IOException
	{
		if (hex.startsWith(HEX_HEADER_BZIP)) compressionScheme = CompressorStreamFactory.BZIP2;
		else if (hex.startsWith(HEX_HEADER_GZIP)) compressionScheme = CompressorStreamFactory.GZIP;
		
		if (compressionScheme!=null){
			CompressorStreamFactory factory = new CompressorStreamFactory();
			CompressorInputStream inputStream = factory.createCompressorInputStream(compressionScheme, new FileInputStream(file));
			return inputStream;
		}
		else //unsupported
		{
			throw new IOException("Cannot read file "+file.getAbsolutePath()+". Unsupported compression scheme.");
		}
	}

	/**
	 * Get compression scheme
	 * @return Compression scheme name or null if not compressed
	 */
	public String getCompressionScheme(){
		return this.compressionScheme;
	}
	
	/**
	 * Is compressed
	 * @return True if compressed
	 */
	public boolean isCompressed() {
		return isCompressed;
	}

	/**
	 * Is archived
	 * @return True if archived
	 */
	public boolean isArchived() {
		return isArchived;
	}
	
	/**
	 * Reads a single character
	 * @throws IOException 
	 */
	public int read() throws IOException {
		return br.read();
	}
	
	/**
	 * Read char array
	 * @param buffer Buffer
	 * @throws IOException 
	 */
	public int read(char[] buffer) throws IOException {
		return br.read(buffer);
	}
	
	/**
	 * Read line
	 * @return String
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return this.br.readLine();
	}
	
	/**
	 * Skip characters
	 * @param shift Offset
	 * @throws IOException 
	 */
	public void skip(int shift) throws IOException {
		br.skip(shift);
	}
		
	/**
	 * Close input stream
	 * @throws IOException 
	 */
	public void close() throws IOException {
		if (br!=null)
			br.close();
	}
	
	/**
	 * Read first bytes of a file (for format identification)
	 * @param nBytes NUmber of bytes to read
	 * @return nBytes first bytes
	 * @throws IOException
	 */
	public byte[] readFirstBytes(int nBytes) throws IOException{
		FileInputStream fin = null;
	    try {
	        fin = new FileInputStream(file);
	        byte[] bytes = new byte[nBytes];
	        fin.read(bytes);
	        return bytes;

	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    } finally {
	        try {
	            if (fin != null) {
	                fin.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	/**
	 * Read first bytes of a file (for format identification) after decompression (if applicable)
	 * @param nBytes NUmber of bytes to read
	 * @return nBytes first bytes as a hexadecimal String
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public String readFirstBytesDecompressed(int nBytes) throws IOException, CompressorException{
		FileInputStream fin = null;
		if (!isCompressed)
			return this.hex;
		else{
		    try {
		    	CompressorInputStream stream = getInputStreamForCompressedFile(hex);
		        byte[] bytes = new byte[nBytes];
		        stream.read(bytes);
		        return Utils.getHex(bytes);
	
		    } catch (IOException e) {
		        e.printStackTrace();
		        return null;
		    } finally {
		        try {
		            if (fin != null) {
		                fin.close();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}


}
