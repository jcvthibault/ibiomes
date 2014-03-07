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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.domain.Collection;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;

import edu.utah.bmi.ibiomes.metadata.BiosimMetadata;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.search.IBIOMESCollectionSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESFileSearch;

/**
 * IBIOMES experiment access object.
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESExperimentAO {

	private static final int N_RECORDS_SEARCH = 100;
	
	private IRODSAccessObjectFactory irodsAccessObjectFactory = null;
	private IRODSAccount account = null;
	private static Logger logger = Logger.getLogger(IBIOMESExperimentAO.class);
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param account
	 */
	public IBIOMESExperimentAO(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount account){
		this.account = account;
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}
	
	/**
	 * Get experiment object (system and user metadata) from iRODS collection
	 * @return Experiment info
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getExperiment(IRODSFile coll) throws JargonException, JargonQueryException
	{	
		CollectionAO coFactory = irodsAccessObjectFactory.getCollectionAO(account);
		return this.getExperiment(coll, coFactory);
	}
	
	/**
	 * Get experiment object (system and user metadata)
	 * @return Experiment
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getExperiment(IRODSFile coll, CollectionAO coFactory) throws JargonException, JargonQueryException
	{		
		if (coll != null)
		{
			List<MetaDataAndDomainData> metadata = coFactory.findMetadataValuesForCollection(coll.getAbsolutePath());
			Collection collInfo = coFactory.findByAbsolutePath(coll.getAbsolutePath());
			IBIOMESCollection collection = new IBIOMESCollection(coll, collInfo, metadata);			
			return collection;
		}
		return null;
	}
	
	/**
	 * Get experiment (system and user metadata) by ID
	 * @param id iRODS collection ID
	 * @return iBOMES experiment
	 * @throws JargonException
	 * @throws JargonQueryException 
	 * @throws FileNotFoundException 
	 */
	public IBIOMESCollection getExperimentByID(int id) throws JargonException, JargonQueryException
	{
		logger.info("[iBIOMES] Get Experiment by ID: " + id);
		
		CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(account);
		IRODSFileFactory irodsFactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		
		Collection coll = collAO.findById(id);
		IRODSFile ifile = irodsFactory.instanceIRODSFile(coll.getAbsolutePath());
		List<MetaDataAndDomainData> meta = collAO.findMetadataValuesForCollection(coll.getAbsolutePath());
		return new IBIOMESCollection(ifile, coll, meta);
	}
	
	/**
	 * Get experiment (system and user metadata)
	 * @param path Path to the experiment
	 * @return iBIOMES experiment
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getExperimentFromPath(String path) throws JargonException, JargonQueryException
	{
		CollectionAO coFactory = irodsAccessObjectFactory.getCollectionAO(account);
		IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		
		IRODSFile coll = ifactory.instanceIRODSFile(path);
		return this.getExperiment(coll, coFactory);
	}

	/**
	 * Retrieve list of collections from their path
	 * @param collectionPaths Collections paths
	 * @return List of iBIOMES collections
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESCollectionList getExperimentsFromPath(List<String> collectionPaths) throws JargonException, JargonQueryException
	{
		IBIOMESCollectionList ibiomesFiles = new IBIOMESCollectionList();
		for (String path : collectionPaths){
			ibiomesFiles.add(this.getExperimentFromPath(path));
		}
		return ibiomesFiles;
	}
	
	/**
	 * Get list of experiments under a given parent collection
	 * @return list of experiments under a given parent collection
	 * @throws Exception 
	 */
	public IBIOMESCollectionList getExperimentsUnderParent(String path) throws Exception
	{
		if (path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		IBIOMESCollectionSearch search = new IBIOMESCollectionSearch(irodsAccessObjectFactory, account);
		search.setCollectionName(path + "/%");
		List<String> collectionPaths = search.executeAndClose();
		return this.getExperimentsFromPath(collectionPaths);
	}
	
	/**
	 * Finder the actual experiment which contains the given directory
	 * @param uri Collection URI (regular directory)
	 * @return Root experiment 
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 */
	public IBIOMESCollection getRootExperiment(String uri) throws JargonException, JargonQueryException
	{	
		IBIOMESCollectionAO factory = new IBIOMESCollectionAO(irodsAccessObjectFactory, account);

		if (uri.endsWith("/"))
			uri.substring(0, uri.length()-1);
		
		boolean found = false;
		int i = uri.lastIndexOf('/');
		while (!found && i>0)
		{
			uri = uri.substring(0, i);
			IBIOMESCollection coll = factory.getCollectionFromPath(uri);
			if (coll.getMetadata().hasPair(new MetadataAVU(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT))){
				return coll;
			}
			i = uri.lastIndexOf('/');
		}
		
		return null;
	}
	
	/**
	 * Retrieve list of collections
	 * @param collections
	 * @return List of iBOMES collections
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESCollectionList getExperiments(List<IRODSFile> collections) throws JargonException, JargonQueryException
	{
		IBIOMESCollectionList ibiomesColl = new IBIOMESCollectionList();
		for (IRODSFile c : collections)
		{
			ibiomesColl.add(this.getExperiment(c));
		}
		return ibiomesColl;
	}
	
	/**
	 * 
	 * @param experimentUri Experiment URI
	 * @param methodFileUri URI of the file to use to generate the method metadata
	 * @return List of metadata
	 * @throws JargonException 
	 * @throws JargonQueryException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public MetadataAVUList updateMethodMetadataFromFile(String experimentUri, String methodFileUri) throws JargonException, JargonQueryException, IllegalArgumentException, IllegalAccessException{
		
		CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(account);
		IBIOMESFileAO fileAO = new IBIOMESFileAO(irodsAccessObjectFactory, account);
		
		//get reference to collection
		IBIOMESCollection collection = this.getExperimentFromPath(experimentUri);
		MetadataAVUList existingMetadata = collection.getMetadata();
		//get reference to parameter file
		IBIOMESFile paramfile = fileAO.getFileByPath(experimentUri + "/" + methodFileUri);
		//get method-specific metadata only
		MetadataAVUList newMetadata = paramfile.getMetadata().getMethodMetadata();
		
		List<String> keys = newMetadata.getAttributes();
		for (String attribute : keys)
		{
			List<String> newValues = newMetadata.getValues(attribute);
			
			boolean exists = false;
			
			//get old value (if any)
			List<String> oldValues = null;
			if (existingMetadata.containsAttribute(attribute)){
				exists = true;
				oldValues = existingMetadata.getValues(attribute);
			}
			
			//if the old metadata need to be removed
			if (exists){
				for (String oldValue : oldValues){
					collAO.deleteAVUMetadata(experimentUri, AvuData.instance(attribute, oldValue, ""));
				}
			}
			//if need to add new value
			for (String newValue : newValues){
				collAO.addAVUMetadata(experimentUri, AvuData.instance(attribute, newValue, ""));
			}
		}
		return newMetadata;
	}
	
	/**
	 * Update experiment topology metadata based on a particular file.
	 * @param experimentUri Path to experiment
	 * @param topologyFileUri Path to topology file relative to experiment root
	 * @return List of metadata
	 * @throws JargonException 
	 * @throws JargonQueryException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public MetadataAVUList updateTopologyMetadataFromFile(String experimentUri, String topologyFileUri) throws JargonException, JargonQueryException, IllegalArgumentException, IllegalAccessException{
		
		CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(account);
		
		IBIOMESFileAO fileAO = new IBIOMESFileAO(irodsAccessObjectFactory, account);
		//get reference to collection
		IBIOMESCollection exp = this.getExperimentFromPath(experimentUri);
		MetadataAVUList existingMetadata = exp.getMetadata();
		//get reference to topology file
		IBIOMESFile topofile = fileAO.getFileByPath(experimentUri + "/" + topologyFileUri);
		
		//get topology-specific metadata only
		MetadataAVUList newMetadata = topofile.getMetadata().getTopologyMetadata();
		
		List<String> keys = newMetadata.getAttributes();
		for (String attribute : keys)
		{
			List<String> newValues = newMetadata.getValues(attribute);
			
			boolean exists = false;
			
			//get old value (if any)
			List<String> oldValues = null;
			if (existingMetadata.containsAttribute(attribute)){
				exists = true;
				oldValues = existingMetadata.getValues(attribute);
			}
			
			//if the old metadata need to be removed
			if (exists){
				for (String oldValue : oldValues){
					collAO.deleteAVUMetadata(experimentUri, AvuData.instance(attribute, oldValue, ""));
				}
			}
			//if need to add new value
			for (String newValue : newValues){
				collAO.addAVUMetadata(experimentUri, AvuData.instance(attribute, newValue, ""));
			}
		}
		return newMetadata;
	}
	
	/**
	 * Get list of analysis files in experiment
	 * @param experimentUri Experiment URI
	 * @return List of matching files
	 * @throws Exception
	 */
	public List<String> getAnalysisFilesInExperiment(String experimentUri) throws Exception
	{	
		//create AVU query to find files in this collection that have an analysis data flag
      	List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS));
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS_ANALYSIS));
		
		//find files at first level
		IBIOMESFileSearch search = new IBIOMESFileSearch(true, false, irodsAccessObjectFactory, account);
      	search.setAvuConditions(avuQuery);
      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
      	search.setDirectory(experimentUri);
      	List<String> analysisFileNames = search.executeAndClose();
      	
      	//find files recursively
      	search = new IBIOMESFileSearch(true, false, irodsAccessObjectFactory, account);
      	search.setAvuConditions(avuQuery);
      	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
      	search.setDirectory(experimentUri + "/%");
      	analysisFileNames.addAll(search.executeAndClose());
      	
		return analysisFileNames;
	}
	

	/**
	 * Get list of topology files in experiment dataset
	 * @param experimentUri Experiment URI
	 * @return List of matching files
	 * @throws Exception
	 */
	public List<String> getTopologyFilesInExperiment(String experimentUri) throws Exception{
		return this.getSpecificFilesInExperiment(experimentUri, IBIOMESFileGroup.topologyFileFormats);
	}

	/**
	 * Get list of MD/QM parameter files in experiment dataset
	 * @param experimentUri Experiment URI
	 * @return List of matching files
	 * @throws Exception
	 */
	public List<String> getParameterFilesInExperiment(String experimentUri) throws Exception{
		return this.getSpecificFilesInExperiment(experimentUri, IBIOMESFileGroup.parameterFileFormats);
	}

	/**
	 * Get list of Jmol-supported files in experiment dataset
	 * @param experimentUri Experiment URI
	 * @return List of matching files
	 * @throws Exception
	 */
	public List<String> getJmolFilesInExperiment(String experimentUri) throws Exception{
		return this.getSpecificFilesInExperiment(experimentUri, IBIOMESFileGroup.jmolFileFormats);
	}

	/**
	 * Get list of analysis files in experiment dataset
	 * @param experimentUri Experiment URI
	 * @return List of matching files
	 * @throws Exception
	 */
	public List<String> getPossibleAnalysisFilesInExperiment(String experimentUri) throws Exception{
		return this.getSpecificFilesInExperiment(experimentUri, IBIOMESFileGroup.analysisFileFormats);
	}

	/**
	 * Get list of format-specified files in experiment dataset
	 * @param experimentUri Experiment URI
	 * @return List of matching files (relative paths)
	 * @throws Exception
	 */
	public List<String> getSpecificFilesInExperiment(String experimentUri, String[] formats) throws Exception {
     	
		List<String> fileAbsPaths = new ArrayList<String>();

		//repeat search for each analysis file format
     	for (int f=0;f<formats.length; f++)
     	{
     		String format = formats[f];
     		
	     	//create AVU query to find files in this collection that have an analysis data flag
	     	List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
			avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_FORMAT));
			avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, format));
			
			//find files at first level
			IBIOMESFileSearch search = new IBIOMESFileSearch( irodsAccessObjectFactory, account);
	     	search.setAvuConditions(avuQuery);
	     	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
	     	search.setDirectory(experimentUri);
	     	fileAbsPaths.addAll(search.executeAndClose());
	     	
	     	//find files recursively
	     	search = new IBIOMESFileSearch( irodsAccessObjectFactory, account);
	     	search.setAvuConditions(avuQuery);
	     	search.setNumberOfRowsRequested(N_RECORDS_SEARCH);
	     	search.setDirectory(experimentUri + "/%");
	     	fileAbsPaths.addAll(search.executeAndClose());
     	}
     	return fileAbsPaths;
	}
}
