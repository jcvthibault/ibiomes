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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Interface to handle local file collections that need to be published into iRODS. 
 * @author Julien Thibault
 *
 */
public interface LocalDirectory {
	
	/**
	 * Get local path to the directory
	 * @return Path to the directory
	 */
	public String getAbsolutePath();
	
	/**
	 * Get directory name
	 * @return directory name
	 */
	public String getName();
	
	/**
	 * Get publisher username
	 * @return publisher username
	 */
	public String getPublisher();

	/**
	 * Set publisher username
	 * @param publisher Publisher username
	 */
	public void setPublisher(String publisher);

	/**
	 * Get publication date
	 * @return Publication date
	 */
	public String getPublicationDate() ;

	/**
	 * Set external URL from which the data an be directly accessed
	 * @param externalURL URL
	 */
	public void setExternalURL(String externalURL);
	
	/**
	 * Get external URL from which the data an be directly accessed
	 * @return URL
	 */
	public String getExternalURL() ;
	
	/**
	 * Get files
	 * @return List of files
	 */
	public Collection<ArrayList<LocalFile>> getFiles();
	
	/**
	 * Get files by format
	 * @return List of files
	 */
	public HashMap<String, ArrayList<LocalFile>> getFilesByFormat();
	
	/**
	 * Get list of subdirectories
	 * @return List of subdirectories
	 */
	public ArrayList<LocalDirectory> getSubdirectories();
	
	/**
	 * Find subdirectory by path
	 * @param string Path to subdirectory
	 * @return Subdirectory
	 */
	public LocalDirectory findSubdirectoryByPath(String string);
	
	/**
	 * List recursively all the files in this collection and the sub-collections
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getFilesByFormatRecursive();

	/**
	 * List recursively all the files in this collection and the sub-collections, using a directory exclusion list
	 * @param directoryExclusionList List of directories to exclude from the listing
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getFilesByFormatRecursive(List<String> directoryExclusionList);
	
	/**
	 * Get collection metadata
	 * @throws Exception 
	 */
	public MetadataAVUList getMetadata() throws Exception;
	
	/**
	 * Set metadata for this directory
	 * @param metadata List of metadata
	 */
	public void setMetadata(MetadataAVUList metadata);
	
	/**
	 * Get relative path from the top directory
	 * @return Relative path from the top directory
	 */
	public String getRelativePathFromTop();

	/**
	 * Set relative path from the top directory
	 * @param relativePath
	 */
	public void setRelativePathFromTop(String relativePath);
	
	/**
	 * Get path to parent directory
	 * @return path to parent directory
	 */
	public String getParent();
	
	/**
	 * Dump list of files/subdirectories and associated metadata
	 * @throws Exception
	 */
	public void dumpToText() throws Exception;

	/**
	 * Dump list of files/subdirectories and associated metadata into XML file
	 * @param outputPath Path to output file
	 * @throws Exception
	 */
	public void storeToXML(String outputPath) throws Exception;
	
	/**
	 * Dump list of files/subdirectories and associated metadata into XML output
	 * @throws Exception
	 */
	public void storeToXML() throws Exception;
	
	/**
	 * Dump list of files/subdirectories and associated metadata into HTML file
	 * @param outputPath Path to output file
	 * @throws Exception
	 */
	public void storeToHTML(String outputPath) throws Exception;
	
	/**
	 * Dump list of files/subdirectories and associated metadata into HTML output
	 * @throws Exception
	 */
	public void storeToHTML() throws Exception;

}
