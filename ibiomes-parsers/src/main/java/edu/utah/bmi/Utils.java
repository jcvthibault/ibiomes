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

package edu.utah.bmi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class Utils {

	private static final String HEXES = "0123456789ABCDEF";
	
	/**
	 * Finder the last occurrence of a number in a string
	 * @param str String
	 * @return Index of the last occurrence of a number
	 */
	public static int findLastNumberInString(String str){
		
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(str);
		String match = "";
		boolean found = false;
		while (m.find()){
			match=str.substring(m.start(),m.end());
			found = true;
		}
		if (!found)
			return -1;
		else
			return Integer.parseInt(match);
	}
	
	/**
	 * Get current Java version
	 * @return Java version
	 */
	public static String getJavaVersion(){
		return System.getProperty("java.version");
	}
	
	/**
	 * Sort file names
	 * @param files List of file names
	 */
	public static void sortFileNames(String[] files)
	{	
		ArrayList<FileNameStructure> list = new ArrayList<FileNameStructure>();
		for (int f=0;f<files.length;f++)
		{
			FileNameStructure fileStruct = new FileNameStructure(files[f]);
			list.add(fileStruct);
		}
		Collections.sort(list);
		for (int f=0;f<files.length;f++){
			files[f] = list.get(f).getFileName();
		}
	}
	
	/**
	 * Read N first bytes of a file
	 * @param nBytes Number of bytes to read
	 * @return Byte array
	 * @throws IOException
	 */
	public static byte[] readFirstBytes(int nBytes, File file) throws IOException{
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
	 * Read N first bytes of a file stream
	 * @param nBytes Number of bytes to read
	 * @return Byte array
	 * @throws IOException
	 */
	public static byte[] readFirstBytes(int nBytes, FileInputStream fin) throws IOException{
	    try {
	        byte[] bytes = new byte[nBytes];
	        fin.read(bytes);
	        return bytes;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * Convert byte array to hexadecimal string
	 * @param bytes Byte array
	 * @return String representation
	 */
	public static String getHex(byte[] bytes) {
	    if ( bytes == null ) {
	        return null;
	    }
	    final StringBuilder hex = new StringBuilder( 2 * bytes.length );
	    for ( final byte b : bytes ) {
	        hex.append(HEXES.charAt((b & 0xF0) >> 4))
	            .append(HEXES.charAt((b & 0x0F)));
	    }
	    return hex.toString();
	}
	
	/**
	 * Normalize XML document (remove unnecessary spaces)
	 * @param doc XML document
	 * @return Normalized XML document
	 * @throws XPathExpressionException
	 */
	public static Document normalizeXmlDoc(Document doc) throws XPathExpressionException
	{
		doc.getDocumentElement().normalize();
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// XPath to find empty text nodes.
		XPathExpression xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");  
		NodeList emptyTextNodes = (NodeList) xpathExp.evaluate(doc, XPathConstants.NODESET);

		// Remove each empty text node from document.
		for (int i = 0; i < emptyTextNodes.getLength(); i++) {
		    Node emptyTextNode = emptyTextNodes.item(i);
		    emptyTextNode.getParentNode().removeChild(emptyTextNode);
		}
		return doc;
	}
	
	/**
	 * Delete non-empty directory recursively
	 * @param path Directory path
	 * @throws IOException
	 */
	public static void removeDirectoryRecursive(Path path) throws IOException
	{
	    Files.walkFileTree(path, new SimpleFileVisitor<Path>()
	    {
	        @Override
	        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	                throws IOException
	        {
	            Files.delete(file);
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
	        {
	            // try to delete the file anyway, even if its attributes
	            // could not be read, since delete-only access is
	            // theoretically possible
	            Files.delete(file);
	            return FileVisitResult.CONTINUE;
	        }

	        @Override
	        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
	        {
	            if (exc == null)
	            {
	                Files.delete(dir);
	                return FileVisitResult.CONTINUE;
	            }
	            else
	            {
	                // directory iteration failed; propagate exception
	                throw exc;
	            }
	        }
	    });
	}
	
	/**
	 * Count number of files in directory (recursive)
	 * @param dirPath Path to directory
	 * @return Number of files
	 */
	public static int countNumberOfFilesInDirectory(String dirPath){
		
		int nFiles = 0;
		File coll = new File(dirPath);
		if (coll.canRead()){
			String[] fileList = coll.list();
			for (int f=0; f<fileList.length; f++)
			{
				String path = dirPath + "/" + fileList[f];
				File file = new File(path);
				if (file.isFile() && file.canRead()){
					nFiles++;
				}
				else {
					nFiles += countNumberOfFilesInDirectory(path);
				}
			}
		}
		return nFiles;
	}
	
	/**
	 * Check if the host is running a Windows operating system
	 * @return True if it does
	 */
	public static boolean isWindows(){
		String OS = getOperatingSystem();
		return (OS.toLowerCase().indexOf("win") >= 0);
	}
	
	/**
	 * Get operating system name
	 * @return operating system name
	 */
	public static String getOperatingSystem(){
		return System.getProperty("os.name");
	}
}
