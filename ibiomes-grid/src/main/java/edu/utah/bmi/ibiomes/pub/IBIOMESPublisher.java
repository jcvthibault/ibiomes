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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.DuplicateDataException;
import org.irods.jargon.core.exception.OverwriteException;
import org.irods.jargon.core.packinstr.TransferOptions;
import org.irods.jargon.core.packinstr.TransferOptions.ForceOption;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.DataTransferOperations;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSRegistrationOfFilesAO;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.query.MetaDataAndDomainData;
import org.irods.jargon.core.transfer.DefaultTransferControlBlock;
import org.irods.jargon.core.transfer.TransferControlBlock;

import edu.utah.bmi.ibiomes.metadata.BiosimMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.IBIOMESListener;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Used to publish local data to iBIOMES (copy or in-place registration).
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESPublisher {

	private final Logger logger = Logger.getLogger(IBIOMESPublisher.class);
	
	private IRODSAccount irodsAccount = null;
	private IRODSAccessObjectFactory irodsFactory = null;

	/**
	 * Constructor
	 * @param account IRODS user account
	 * @param factory IRODS access object factory
	 */
	public IBIOMESPublisher(IRODSAccount account, IRODSAccessObjectFactory factory){
		this.irodsAccount = account;
		this.irodsFactory = factory;
	}
	
	/**
	 * Publish regular directory to iBIOMES
	 * @param directory Directory
	 * @param ibiomesPath Path to directory copy in iBIOMES
	 * @param overwrite Overwrite flag
	 * @throws Exception 
	 */
	public void publishDirectory(LocalDirectory directory, String ibiomesPath, boolean overwrite) throws Exception{
		directory.getMetadata().removePairs(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT);
		publishExperiment(directory, ibiomesPath, overwrite, null);
	}
	
	/**
	 * Publish regular directory to iBIOMES
	 * @param directory Directory
	 * @param ibiomesPath Path to directory copy in iBIOMES
	 * @param overwrite Overwrite flag
	 * @param listener Listener for progress bar
	 * @throws Exception 
	 */
	public void publishDirectory(
			LocalDirectory directory, 
			String ibiomesPath, 
			boolean overwrite, 
			IBIOMESListener listener) throws Exception{
		directory.getMetadata().removePairs(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT);
		publishExperiment(directory, ibiomesPath, overwrite, listener);
	}
	
	/**
	 * Publish experiment directory to iBIOMES
	 * @param directory Experiment
	 * @param ibiomesPath Path to directory copy in iBIOMES
	 * @throws Exception 
	 */
	public void publishExperiment(
			LocalDirectory directory, 
			String ibiomesPath, 
			boolean overwrite) throws Exception{
		publishExperiment(directory, ibiomesPath, overwrite, null);
	}
	/**
	 * Publish experiment directory to iBIOMES
	 * @param directory Experiment
	 * @param ibiomesPath Path to directory copy in iBIOMES
	 * @param listener Listener for progress bar
	 * @throws Exception 
	 */
	public void publishExperiment(
			LocalDirectory directory, 
			String ibiomesPath, 
			boolean overwrite, 
			IBIOMESListener listener) throws Exception{
		CollectionAO cAO = null;
		//connection
		cAO = irodsFactory.getCollectionAO(irodsAccount);
		
		//if subdirectory
		IRODSFile irodsCollection =  null;
		irodsCollection = cAO.instanceIRODSFileForCollectionPath(ibiomesPath);
		
		logger.info("Registering collection '"+directory.getAbsolutePath()+"' into iRODS...");
		
		//if the collection does not exist
		if (!irodsCollection.exists())
		{
			logger.info("[iRODS] Creating directory '"+ibiomesPath+"'");
			//create new IRODS collection
			irodsCollection.mkdir();
			
			//add metadata
			this.addCollectionMetadataInIRODS(directory, cAO, ibiomesPath);
		}
		irodsCollection.close();
		
		//register files into the new iRODS collection
		this.publishFilesInDirectory(directory.getFilesByFormat(), ibiomesPath, overwrite, listener);
		
		//register subdirectories and children files (recursive)
		logger.info("Registering subdirectories into iRODS...");
		if (directory.getSubdirectories() != null)
		{
			for (LocalDirectory subdir : directory.getSubdirectories()){
				//get relative file path from top directory
				String virtualFilePath = ibiomesPath + "/" + subdir.getName();
				//register into iRODS
				logger.info("\tRegistering subdirectory "+ subdir.getName() +"...");
				this.publishExperiment(subdir, virtualFilePath, overwrite, listener);
			}
		}
	}
	
	/**
	 * Publish file to iBIOMES
	 * @param locaFile Local file
	 * @param ibiomesPath Path to file copy in iBIOMES
	 * @param overwrite Overwrite flag
	 * @throws Exception 
	 */
	public boolean publishFile(LocalFile locaFile, String ibiomesPath, boolean overwrite) throws Exception{
		
		boolean fileExists = false;
		DataObjectAO dataAO = null;
		boolean newFile = false;
		
		dataAO = irodsFactory.getDataObjectAO(irodsAccount);
		DataTransferOperations dataTransfer = irodsFactory.getDataTransferOperations(irodsAccount);
		TransferOptions transferOptions = new TransferOptions();
		TransferControlBlock transferCtrl = DefaultTransferControlBlock.instance();
		
		IRODSFile irodsFile = dataAO.instanceIRODSFileForPath(ibiomesPath);
		if (!irodsFile.exists()){
		
			//create IRODS file
			logger.info("[iRODS] Create file in iRODS: '"+ irodsFile.getAbsolutePath() + "'");
			newFile = true;
		}
		else {
			//overwrite IRODS file?
			if (overwrite)
			{
				logger.info("[iRODS] Overwrite file in iRODS: '"+ irodsFile.getAbsolutePath() + "'");
				
				//delete existing metadata
				List<MetaDataAndDomainData> metadata = dataAO.findMetadataValuesForDataObject(irodsFile);
				for (MetaDataAndDomainData m : metadata){
					dataAO.deleteAVUMetadata(irodsFile.getAbsolutePath(), AvuData.instance(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
				}
				transferOptions.setForceOption(ForceOption.USE_FORCE);
			}
			else {
				logger.info("[iRODS] File already exists in iRODS: '"+ irodsFile.getAbsolutePath() + "'");
				transferOptions.setForceOption(ForceOption.NO_FORCE);
			}
		}
		transferCtrl.setTransferOptions(transferOptions);

		//copy local file to iRODS ('put' operation)
		try{
			dataTransfer.putOperation((File)locaFile, irodsFile, null, transferCtrl);
		}
		catch(OverwriteException oe){
			newFile = false;
			if (overwrite)
				logger.error("Could not overwrite file '"+ irodsFile.getAbsolutePath() + "'");
			else {
				//TODO just skip?
			}
		}

		//if created
		if (irodsFile.exists())
		{
			fileExists = true;
			if (newFile || overwrite)
				//add metadata
				this.addFileMetadataInIRODS(locaFile, dataAO, irodsFile.getAbsolutePath());
		}
		
		irodsFile.close();
		
		return fileExists;
	}
	
	/**
	 * Register regular directory into iBIOMES (in-place registration)
	 * @param directory Directory
	 * @param ibiomesPath Path to directory in iBIOMES
	 * @param force Force registration
	 * @throws Exception 
	 */
	public void registerDirectory(LocalDirectory directory, String ibiomesPath, boolean force) throws Exception{
		directory.getMetadata().removePairs(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT);
		registerExperiment(directory, ibiomesPath, force);
	}
	
	/**
	 * Register experiment directory into iBIOMES (in-place registration)
	 * @param directory Experiment
	 * @param ibiomesPath Path to directory in iBIOMES
	 * @param force Force registration
	 * @throws Exception 
	 */
	public void registerExperiment(LocalDirectory directory, String ibiomesPath, boolean force) throws Exception{
		registerExperiment(directory, ibiomesPath, force, null);
	}
	

	/**
	 * Register experiment directory into iBIOMES (in-place registration)
	 * @param directory Experiment
	 * @param ibiomesPath Path to directory in iBIOMES
	 * @param force Force registration
	 * @throws Exception 
	 */
	public void registerExperiment(
			LocalDirectory directory,
			String ibiomesPath, 
			boolean force, 
			IBIOMESListener listener) throws Exception {
		CollectionAO cAO = null;
		IRODSRegistrationOfFilesAO ireg= null;
		
		//connection
		cAO = irodsFactory.getCollectionAO(irodsAccount);
		ireg = irodsFactory.getIRODSRegistrationOfFilesAO(irodsAccount);
		
		//if subdirectory
		IRODSFile irodsCollection =  null;
		irodsCollection = cAO.instanceIRODSFileForCollectionPath(ibiomesPath);
		
		logger.info("Registering collection '"+directory.getAbsolutePath()+"' into iRODS...");
		
		//if the collection does not exist
		if (!irodsCollection.exists())
		{
			logger.info("[iRODS] Creating collection '"+ibiomesPath+"'");
			
			//register new IRODS collection recursively
			ireg.registerPhysicalCollectionRecursivelyToIRODS(directory.getAbsolutePath(), ibiomesPath, force, irodsAccount.getDefaultStorageResource(), "");
		}
		
		//add metadata
		this.addCollectionMetadataInIRODS(directory, cAO, ibiomesPath);
		//close connection to ifile
		irodsCollection.close();
		
		//register files into the new iRODS collection
		this.registerFilesInDirectory(directory.getFilesByFormat(), ibiomesPath, listener);
		
		//register subdirectories and children files (recursive)
		logger.info("Registering subdirectories into iRODS...");
		if (directory.getSubdirectories() != null)
		{
			for (LocalDirectory subdir : directory.getSubdirectories()){
				//get relative file path from top directory
				String virtualFilePath = ibiomesPath + "/" + subdir.getName();
				//register into iRODS
				logger.info("\tRegistering subdirectory "+ subdir.getName() +"...");
				this.registerExperiment(subdir, virtualFilePath, true, listener);
			}
		}
	}
	
	/**
	 * Register file into iBIOMES (in-place registration)
	 * @param locaFile Local file
	 * @param ibiomesPath Path to file in iBIOMES
	 * @throws Exception 
	 */
	public void registerFile(LocalFile locaFile, String ibiomesPath) throws Exception
	{
		IRODSRegistrationOfFilesAO ireg = null;
		DataObjectAO dataAO = null;
		
		ireg = irodsFactory.getIRODSRegistrationOfFilesAO(irodsAccount);
		dataAO = irodsFactory.getDataObjectAO(irodsAccount);

		IRODSFile irodsFile = dataAO.instanceIRODSFileForPath(ibiomesPath);
		//irodsFile.setResource(DEFAULT_RESOURCE);
		
		logger.info("[iRODS] Registering file: "+ irodsFile.toString());
		
		boolean exists = irodsFile.exists();
		//if the file does not exist in iRODS (not registered yet)
		if (!exists){
			//register
			ireg.registerPhysicalDataFileToIRODS(locaFile.getAbsolutePath(), ibiomesPath, null, "", true);
		}
		
		//add metadata
		this.addFileMetadataInIRODS(locaFile, dataAO, ibiomesPath);
		
		irodsFile.close();
	}
	
	/**
	 * Add metadata to file in iRODS
	 * @param dataAO Data access object
	 * @param irodsFilePath Path to iRODS file
	 * @throws Exception
	 */
	private void addFileMetadataInIRODS(LocalFile localFile, DataObjectAO dataAO, String irodsFilePath) throws Exception
	{
		//add metadata
		MetadataAVUList metadata = localFile.getMetadata();
		
		for (MetadataAVU pair : metadata)
		{
			String key = pair.getAttribute();
			String value = pair.getValue();
			if (value != null && value.length()>0)
			{
				if (value.length() > IBIOMESFileAO.MAX_AVU_VALUE_LENGTH)
					value = value.substring(0, IBIOMESFileAO.MAX_AVU_VALUE_LENGTH-4) + "...";
					logger.info("[iRODS] \tAdding metadata ["+ pair.toString() + "]");
				try{
					dataAO.addAVUMetadata(irodsFilePath, AvuData.instance(key, value, ""));
				}
				catch(DuplicateDataException de){
					logger.warn("Skipping duplicate AVU '"+key+"="+value+"'");
				}
			}
		}
	}
	
	/**
	 * Add metadata to collection in iRODS
	 * @param cAO Collection access object
	 * @param irodsCollectionPath Path to iRODS collection
	 * @throws Exception
	 */
	private void addCollectionMetadataInIRODS(LocalDirectory dir, CollectionAO cAO, String ibiomesPath) throws Exception
	{
		MetadataAVUList collectionMetadata = dir.getMetadata();
		if (collectionMetadata != null){
			for (MetadataAVU pair : collectionMetadata)
			{
				String key = pair.getAttribute();
				String value = pair.getValue();
				if (value != null && value.length() > 0){
					if (value.length() > IBIOMESCollectionAO.MAX_AVU_VALUE_LENGTH)
						value = value.substring(0, IBIOMESCollectionAO.MAX_AVU_VALUE_LENGTH-4) + "...";
					logger.info("[iRODS] \tAdding metadata ["+ key +" = "+ value + "]");
					try{
						cAO.addAVUMetadata(ibiomesPath, AvuData.instance(key, value, ""));
					} catch(DuplicateDataException de){
						logger.warn("Skipping duplicate AVU '"+key+"="+value+"'");
					}
				}
			}
		}
	}
	
	/**
	 * Upload multiple files to iRODS into the new collection
	 * @param virtualCollectionPath Virtual path to the collection
	 * @param overwrite Overwrite flag
	 * @throws Exception 
	 */
	private void publishFilesInDirectory(
			HashMap<String,ArrayList<LocalFile>> files, 
			String ibiomesPath, 
			boolean overwrite, 
			IBIOMESListener listener) throws Exception
	{
		if (files != null)
		{
			Iterator<String> formatIt = files.keySet().iterator();
			while (formatIt.hasNext())
			{
				String format = formatIt.next();
				logger.info("Registering '"+format +"' files into iRODS...");
				ArrayList<LocalFile> fileList = files.get(format);
				for (LocalFile file : fileList){
					//get relative file path from top directory
					String virtualFilePath = ibiomesPath + "/" + file.getName();
					//register into iRODS
					this.publishFile(file, virtualFilePath, overwrite);
					//update progress bar
					if (listener!=null)
						listener.update();
				}
			}
		}
	}
	
	/**
	 * Register multiple files to iBIOMES into the new collection (keep physical files in place)
	 * @param ibiomesPath Virtual path to the collection
	 * @param files
	 * @param listener Listener for progress bar
	 * @throws Exception 
	 */
	private void registerFilesInDirectory(
			HashMap<String,ArrayList<LocalFile>> files, 
			String ibiomesPath, 
			IBIOMESListener listener) throws Exception
	{
		logger.info("Registering files into iRODS...");
		if (files != null)
		{
			Iterator<String> formatIt = files.keySet().iterator();
			while (formatIt.hasNext())
			{
				String format = formatIt.next();
				ArrayList<LocalFile> fileList = files.get(format);
				for (LocalFile file : fileList){
					//get relative file path from top directory
					String virtualFilePath = ibiomesPath + "/" + file.getName();
					//register into iRODS
					this.registerFile(file, virtualFilePath);
					//update progress bar
					if (listener!=null)
						listener.update();
				}
			}
		}
	}

}
