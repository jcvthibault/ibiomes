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

package edu.utah.bmi.ibiomes.pub;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;

import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;

/**
 * iBIOMES file access object
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileAO {
	
	public static final int MAX_AVU_VALUE_LENGTH = 27000;

	private IRODSAccessObjectFactory irodsAccessObjectFactory = null;
	private IRODSAccount account = null;
	private static Logger logger = Logger.getLogger(IBIOMESFileAO.class);
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param account
	 */
	public IBIOMESFileAO(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount account){
		this.account = account;
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}
	
	/**
	 * Get file object (system and user metadata) from iRODS data object
	 * @return File info
	 * @throws JargonException
	 * @throws JargonQueryException 
	 * @throws FileNotFoundException 
	 */
	public IBIOMESFile getFile(IRODSFile file) throws JargonException, JargonQueryException, FileNotFoundException
	{	
		DataObjectAO dataFactory = irodsAccessObjectFactory.getDataObjectAO(account);
		return this.getFile(file, dataFactory);
	}
	
	/**
	 * Get file object (system and user metadata)
	 * @return File IBIOMES file
	 * @throws JargonException
	 * @throws JargonQueryException 
	 * @throws FileNotFoundException 
	 */
	public IBIOMESFile getFile(IRODSFile file, DataObjectAO dAO) throws JargonException, JargonQueryException, FileNotFoundException
	{		
		if (file != null){
			List<MetaDataAndDomainData> metadata = dAO.findMetadataValuesForDataObject(file.getAbsolutePath());
			IBIOMESFile ibiomesFile = new IBIOMESFile(file, metadata);			
			return ibiomesFile;
		}
		return null;
	}
	
	/**
	 * Get details on file (system and user metadata)
	 * @param path Path to the file
	 * @return iBOMES file
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESFile getFileByPath(String path) throws JargonException, JargonQueryException
	{
		logger.info("[iBIOMES] Get file by path: " + path);
		
		DataObjectAO doFactory = irodsAccessObjectFactory.getDataObjectAO(account);
		IRODSFileFactory irodsFactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		
		IRODSFile ifile = irodsFactory.instanceIRODSFile(path);
		List<MetaDataAndDomainData> meta = doFactory.findMetadataValuesForDataObject(path);
		
		return new IBIOMESFile(ifile, meta);
	}
	
	/**
	 * Get details on file (system and user metadata)
	 * @param id iRODS file ID
	 * @return iBOMES file
	 * @throws JargonException
	 * @throws JargonQueryException 
	 * @throws FileNotFoundException 
	 */
	public IBIOMESFile getFileByID(int id) throws JargonException, JargonQueryException, FileNotFoundException
	{
		logger.info("[iBIOMES] Get file by ID: " + id);
		
		DataObjectAO doFactory = irodsAccessObjectFactory.getDataObjectAO(account);
		IRODSFileFactory irodsFactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		DataObject dataObject = doFactory.findById(id);
		IRODSFile ifile = irodsFactory.instanceIRODSFile(dataObject.getAbsolutePath());
		List<MetaDataAndDomainData> meta = doFactory.findMetadataValuesForDataObject(ifile);
		return new IBIOMESFile(ifile, meta);
	}
	
	/**
	 * Retrieve all the files underneath a collection
	 * @param path Path to the collection
	 * @param indexedOnly Whether non-indexed files should be returned.
	 * @return List of iBOMES files
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESFileList getFilesUnderCollection(String path, boolean indexedOnly) throws JargonException, JargonQueryException
	{
		return this.getFilesUnderCollection(path, indexedOnly, false);
	}
	
	/**
	 * Retrieve all the files underneath a collection
	 * @param path Path to the collection
	 * @param excludeHiddenFiles Whether hidden files should be excluded from the results.
	 * @param recursive Whether files in sub-directories should be returned
	 * @return List of iBOMES files
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESFileList getFilesUnderCollection(String path, boolean excludeHiddenFiles, boolean recursive) throws JargonException, JargonQueryException
	{
		if (path.endsWith("/")){
			path = path.substring(0,path.length()-1);
		}

		IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		IRODSFile coll = fileFactory.instanceIRODSFile(path);
		File[] matchingFiles = coll.listFiles();
		
		IBIOMESFileList files = new IBIOMESFileList();
		for (int f=0; f<matchingFiles.length; f++)
		{
			File file = matchingFiles[f];
			//if its a regular file
			if (file.isFile())
			{
				IBIOMESFile ibiomesFile = this.getFileByPath(file.getAbsolutePath());
				if (!excludeHiddenFiles || !ibiomesFile.getMetadata().hasPair(new MetadataAVU(FileMetadata.FILE_IS_HIDDEN, "true")))
					files.add(ibiomesFile);
			}
			//its a collection
			else if (recursive){
				IBIOMESFileList filesInSubDir = this.getFilesUnderCollection(file.getAbsolutePath(), excludeHiddenFiles, recursive);
				files.addAll(filesInSubDir);
			}
		}
		return files;
	}
	
	/**
	 * Retrieve list of files based on their path
	 * @param files List of file paths
	 * @param excludeHiddenFiles Exclude hidden files or not
	 * @return list of files
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESFileList getFilesFromPath(List<String> files, boolean excludeHiddenFiles) throws JargonException, JargonQueryException
	{
		IBIOMESFileList ibiomesFiles = new IBIOMESFileList();
		for (String path : files){
			IBIOMESFile ibiomesFile = this.getFileByPath(path);
			if (!excludeHiddenFiles || !ibiomesFile.getMetadata().hasPair(new MetadataAVU(FileMetadata.FILE_IS_HIDDEN, "true")))
				ibiomesFiles.add(ibiomesFile);
		}
		return ibiomesFiles;
	}
	
	/**
	 * Retrieve list of files based on their path (exclude hidden files)
	 * @param files List of file paths
	 * @return list of files
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESFileList getFilesFromPath(List<String> files) throws JargonException, JargonQueryException
	{
		return this.getFilesFromPath(files,true);
	}
	
	/**
	 * Retrieve list of files
	 * @param files
	 * @return List of iBOMES files
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws FileNotFoundException 
	 */
	public IBIOMESFileList getFiles(List<IRODSFile> files) throws JargonException, JargonQueryException, FileNotFoundException
	{
		IBIOMESFileList ibiomesFiles = new IBIOMESFileList();
		for (IRODSFile f : files){
			ibiomesFiles.add(this.getFile(f));
		}
		return ibiomesFiles;
	}
	
	/**
	 * Check read permissions
	 * @param path Path to the file
	 * @return True if readable
	 * @throws JargonException
	 */
	public boolean isReadable(String path) throws JargonException{
		IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		IRODSFile file = ifactory.instanceIRODSFile(path);
		return file.canRead();
	}
	
	/**
	 * Check write permissions
	 * @param path Path to the file
	 * @return True if writable
	 * @throws JargonException
	 */
	public boolean isWritable(String path) throws JargonException{
		IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		IRODSFile file = ifactory.instanceIRODSFile(path);
		return file.canWrite();
	}
}
