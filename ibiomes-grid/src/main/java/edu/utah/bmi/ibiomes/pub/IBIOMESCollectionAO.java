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
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.Collection;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;

import edu.utah.bmi.ibiomes.search.IBIOMESCollectionSearch;

/**
 * iBIOMES collection (iRODS directory) access object
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESCollectionAO {

	public static final int MAX_AVU_VALUE_LENGTH = 27000;
	
	private IRODSAccessObjectFactory irodsAccessObjectFactory = null;
	private IRODSAccount account = null;
	private static Logger logger = Logger.getLogger(IBIOMESCollectionAO.class);
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param account
	 */
	public IBIOMESCollectionAO(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount account){
		this.account = account;
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	}
	
	/**
	 * Get collection object (system and user metadata) from iRODS collection
	 * @return Collection info
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getCollection(IRODSFile coll) throws JargonException, JargonQueryException
	{	
		CollectionAO coFactory = irodsAccessObjectFactory.getCollectionAO(account);
		return this.getCollection(coll, coFactory);
	}
	
	/**
	 * Get collection object (system and user metadata)
	 * @return Collection
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getCollection(IRODSFile coll, CollectionAO coFactory) throws JargonException, JargonQueryException
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
	 * Get collection (system and user metadata) from its path
	 * @param path Path to the collection
	 * @return iBIOMES collection
	 * @throws JargonException
	 * @throws JargonQueryException 
	 */
	public IBIOMESCollection getCollectionFromPath(String path) throws JargonException, JargonQueryException
	{
		CollectionAO coFactory = irodsAccessObjectFactory.getCollectionAO(account);
		IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		
		IRODSFile coll = ifactory.instanceIRODSFile(path);
		return this.getCollection(coll, coFactory);
	}

	/**
	 * Get collection (system and user metadata) by ID
	 * @param id iRODS collection ID
	 * @return iBOMES collection
	 * @throws JargonException
	 * @throws JargonQueryException 
	 * @throws FileNotFoundException 
	 */
	public IBIOMESCollection getCollectionByID(int id) throws JargonException, JargonQueryException
	{
		logger.info("[iBIOMES] Get collection by ID: " + id);
		
		CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(account);
		IRODSFileFactory irodsFactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
		
		Collection collection = collAO.findById(id);
		IRODSFile ifile = irodsFactory.instanceIRODSFile(collection.getAbsolutePath());
		List<MetaDataAndDomainData> meta = collAO.findMetadataValuesForCollection(collection.getAbsolutePath());
		return new IBIOMESCollection(ifile, collection, meta);
	}
	
	/**
	 * Retrieve list of collections from their path
	 * @param collectionPaths Collections paths
	 * @return List of iBIOMES collections
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESCollectionList getCollectionsFromPath(List<String> collectionPaths) throws JargonException, JargonQueryException
	{
		IBIOMESCollectionList ibiomesFiles = new IBIOMESCollectionList();
		for (String path : collectionPaths){
			ibiomesFiles.add(this.getCollectionFromPath(path));
		}
		return ibiomesFiles;
	}
	
	/**
	 * Get list of collections under a given parent collection (recursive)
	 * @return list of collections under a given parent collection
	 * @throws Exception 
	 */
	public IBIOMESCollectionList getCollectionsUnderParent(String path) throws Exception{
		return this.getCollectionsUnderParent(path, true);
	}
	
	/**
	 * Get list of collections under a given parent collection
	 * @param recursive Recursive flag
	 * @return list of collections under a given parent collection
	 * @throws Exception 
	 */
	public IBIOMESCollectionList getCollectionsUnderParent(String path, boolean recursive) throws Exception
	{
		if (path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		if (recursive)
			path = path + "/%";
			
		IBIOMESCollectionSearch search = new IBIOMESCollectionSearch(irodsAccessObjectFactory, account);
		search.setParentCollection(path);
		List<String> collectionPaths = search.executeAndClose();

		return this.getCollectionsFromPath(collectionPaths);
	}
	
	/**
	 * Retrieve list of collections
	 * @param collections
	 * @return List of iBOMES collections
	 * @throws JargonException
	 * @throws JargonQueryException
	 */
	public IBIOMESCollectionList getCollections(List<IRODSFile> collections) throws JargonException, JargonQueryException
	{
		IBIOMESCollectionList ibiomesColl = new IBIOMESCollectionList();
		for (IRODSFile c : collections)
		{
			ibiomesColl.add(this.getCollection(c));
		}
		return ibiomesColl;
	}
	
	/**
	 * Check read permissions
	 * @param path Path to the collection
	 * @return True if readable
	 * @throws JargonException
	 */
	public boolean isReadable(String path) throws JargonException{
		try{ 
			IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
			IRODSFile file = ifactory.instanceIRODSFile(path);
			boolean canRead = file.canRead();
			return canRead;
		} catch(Exception e){
			//TODO remove this hint when once specific queries are made part of the iRODS release
			logger.error("Cannot get READ permissions. Make sure that specific queries are loaded in IRODS (jargon_specquery.sh)");
			throw e;
		}
	}
	
	/**
	 * Check write permissions
	 * @param path Path to the collection
	 * @return True if writable
	 * @throws JargonException
	 */
	public boolean isWritable(String path) throws JargonException{
		try{
			IRODSFileFactory ifactory = irodsAccessObjectFactory.getIRODSFileFactory(account);
			IRODSFile file = ifactory.instanceIRODSFile(path);
			boolean canWrite = file.canWrite();
			return canWrite;
		} catch(Exception e){
			//TODO remove this hint once these specific queries are made part of the iRODS release
			logger.error("Cannot get WRITE permissions. Make sure that specific queries are loaded in IRODS (jargon_specquery.sh)");
			throw e;
		}
	}
}
