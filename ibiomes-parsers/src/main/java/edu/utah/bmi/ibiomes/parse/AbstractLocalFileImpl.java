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

package edu.utah.bmi.ibiomes.parse;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.log4j.Logger;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Abstract local file with associated metadata
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="file")
public abstract class AbstractLocalFileImpl extends File implements LocalFile
{
	private static final long serialVersionUID = -2439275914850871968L;

	protected final Logger logger = Logger.getLogger(AbstractLocalFileImpl.class);
	
	protected String description;
	protected String format;
	protected String fileType;
	protected String software;
	protected String relativePathFromProjectRoot;
	protected MetadataAVUList extendedAttributes;
	protected List<String> assignedClasses;
	
	private AbstractLocalFileImpl(){
		super(".");
	}
	
	/**
	 * Constructor
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public AbstractLocalFileImpl(String localPath) throws IOException{
		super(localPath);
		if (this.exists()){
			this.setFormat(LocalFile.FORMAT_UNKNOWN);
		}
		else throw new IOException("Local file \""+ localPath +"\" does not exist!");
	}
	
	/**
	 * Constructor
	 * @param fileFormat File format
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public AbstractLocalFileImpl(String localPath, String fileFormat) throws IOException {
		super(localPath);
		if (this.exists()){
			this.setFormat(fileFormat);
		}
		else throw new IOException("Local file \""+ localPath +"\" does not exist!");
	}
	
	/**
	 * Constructor
	 * @param fileFormat File format
	 * @param fileType File type
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public AbstractLocalFileImpl(String localPath, String fileFormat, String fileType) throws IOException {
		super(localPath);
		if (this.exists()){
			this.setFormat(fileFormat);
			this.setFileType(fileType);
		}
		else throw new IOException("Local file \""+ localPath +"\" does not exist!");
	}

	/**
	 * Get relative path to file from project root directory
	 * @return Relative path
	 */
	public String getRelativePathFromProjectRoot() {
		return relativePathFromProjectRoot;
	}

	/**
	 * Set relative path to file from project root directory
	 * @param relativePathFromProjectRoot Relative path
	 */
	public void setRelativePathFromProjectRoot(String relativePathFromProjectRoot) {
		this.relativePathFromProjectRoot = relativePathFromProjectRoot;
	}
	
	/**
	 * Set software
	 * @param software Software package name
	 */
	public void setSoftware(String software){
		this.software = software;
	}
	
	/**
	 * Get software 
	 * @return Software package name
	 */
	public String getSoftware(){
		return this.software;
	}
	
	/**
	 * Set the file format (e.g. PDB, AMBER, CSV)
	 * @param format
	 */
	public void setFormat(String format){
		this.format = format;
	}
	
	/**
	 * Get file format  (e.g. PDB, AMBER, CSV)
	 * @return File format
	 */
	public String getFormat(){
		return this.format;
	}
	
	/**
	 * Set the file type (e.g. image, audio)
	 * @param type File type
	 */
	public void setFileType(String type){
		this.fileType = type;
	}
	
	/**
	 * Get file type   (e.g. image, audio)
	 * @return File type
	 */
	public String getFileType(){
		return this.fileType;
	}
	
	/**
	 * Get description
	 * @return File description
	 */
	public String getDescription(){
		return this.description;
	}
	/**
	 * Add textual description to the file
	 * @param desc File description
	 */
	public void setDescription(String desc){
		if (desc != null) 
			this.description = desc.trim();
	}
	
	/**
	 * Get file MIME type
	 * @return MIME type
	 */
	public String getMimeType(){
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String mimeType = fileNameMap.getContentTypeFor(this.getAbsolutePath());
		return mimeType;
	}
	
	/**
	 * Get input reader
	 * @return Input reader
	 * @throws CompressorException 
	 * @throws IOException 
	 */
	public IBIOMESFileReader getInputReader() throws CompressorException, IOException {
		return new IBIOMESFileReader(this);
	}

	/**
	 * Get assigned classes
	 */
	public List<String> getAssignedClasses() {
		return assignedClasses;
	}

	/**
	 * Set assigned classes
	 * @param assignedClasses Assigned classes
	 */
	public void setAssignedClasses(List<String> assignedClasses) {
		this.assignedClasses = assignedClasses;
	}
	
	/**
	 * Get extended attributes
	 */
	public MetadataAVUList getExtendedAttributes() {
		return extendedAttributes;
	}
	
	/**
	 * Set extended attributes
	 */
	public void setExtendedAttributes(MetadataAVUList extendedAttributes) {
		this.extendedAttributes = extendedAttributes;
	}
	
	/**
	 * Return the set of metadata defined for this file.
	 * @return Set of metadata defined for this file
	 * @throws Exception 
	 */
	@XmlElementWrapper(name="metadata")
	@XmlElement(name="AVU")
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.format != null && this.format.length()>0)
			metadata.add(new MetadataAVU(FileMetadata.FILE_FORMAT, this.format));
		
		if (this.description != null && this.description.length()>0)
			metadata.add(new MetadataAVU(FileMetadata.FILE_DESCRIPTION, this.description));
		
		if (this.software != null && this.software.length()>0)
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, this.software));
		
		if (extendedAttributes!=null){
			for (MetadataAVU overrideAVU : extendedAttributes){
				metadata.updatePair(overrideAVU.getAttribute(), overrideAVU.getValue());
			}
		}
		if (assignedClasses!=null){
			for (String assignedClass : assignedClasses){
				metadata.updatePair(FileMetadata.FILE_CLASS, assignedClass.toUpperCase());
			}
		}
		return metadata;
	}
}
