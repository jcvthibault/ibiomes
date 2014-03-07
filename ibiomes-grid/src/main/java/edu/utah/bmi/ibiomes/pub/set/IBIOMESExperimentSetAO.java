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

package edu.utah.bmi.ibiomes.pub.set;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.auth.AuthResponse;
import org.irods.jargon.core.exception.AuthenticationException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.domain.DataObject;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.MetaDataAndDomainData;

import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSearch;

/**
 * Access object for experiment sets
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESExperimentSetAO {

	private IRODSAccessObjectFactory irodsAccessObjectFactory = null;
	private IRODSAccount account = null;
	private ExperimentSetSqlConnector sql = null;
	
	private static Logger logger = Logger.getLogger(IBIOMESExperimentSetAO.class);
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param account
	 * @param ds Data source
	 * @throws JargonException 
	 */
	public IBIOMESExperimentSetAO(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount account, DataSource ds) throws JargonException{
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
		this.sql = new ExperimentSetSqlConnector();
		if (ds!=null)
			this.sql.setDataSource(ds);
		//authenticate against iRODS
		AuthResponse authentication = this.irodsAccessObjectFactory.authenticateIRODSAccount(account);
		if (!authentication.isSuccessful())
			throw new AuthenticationException("Authentication for '"+account.getUserName()+"' failed");
		else this.account = authentication.getAuthenticatedIRODSAccount();
	}
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param account
	 * @throws JargonException 
	 */
	public IBIOMESExperimentSetAO(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount account) throws JargonException{
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
		this.sql = new ExperimentSetSqlConnector();
		//authenticate against iRODS
		AuthResponse authentication = this.irodsAccessObjectFactory.authenticateIRODSAccount(account);
		if (!authentication.isSuccessful())
			throw new AuthenticationException("Authentication for '"+account.getUserName()+"' failed");
		else this.account = authentication.getAuthenticatedIRODSAccount();
	}
	
	/**
	 * Get experiment set from its ID
	 * @param setId Set ID
	 * @return Experiment set
	 * @throws SQLException
	 */
	public IBIOMESExperimentSet getExperimentSet(long setId) throws SQLException{
		return sql.getExperimentSetById(setId, account.getUserName());
	}
	
	/**
	 * List experiment sets readable (owned or public) by authenticated user
	 * @return List of experiment sets
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws SQLException
	 */
	public IBIOMESExperimentSetList listExperimentSetsWithRead() throws JargonException, JargonQueryException, SQLException {
		return sql.getExperimentSetsForUser(account.getUserName());
	}
	
	/**
	 * List experiment sets owned by authenticated user
	 * @return List of experiment sets
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws SQLException
	 */
	public IBIOMESExperimentSetList listExperimentSetsWithOwn() throws JargonException, JargonQueryException, SQLException {
		return sql.getExperimentSetsForOwner(account.getUserName());
	}
	
	/**
	 * List all the experiments in the given set
	 * @param setId Experiment set ID
	 * @return List of experiments
	 * @throws Exception 
	 */
	public List<String> listExperimentsInSet(long setId) throws Exception
	{		
		IBIOMESExperimentSearch search = new IBIOMESExperimentSearch(irodsAccessObjectFactory, account);
		List<AVUQueryElement> avuConditions = new ArrayList<AVUQueryElement>();
		avuConditions.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, GeneralMetadata.EXPERIMENT_SET_ID));
		avuConditions.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.EQUAL, String.valueOf(setId)));
		search.setAvuConditions(avuConditions);
		return search.executeAndClose();
	}
	
	/**
	 * Add experiment to set
	 * @param experimentSetId Experiment set ID
	 * @param experimentPath Experiment path
	 * @throws JargonException
	 */
	public void addExperimentToSet(long experimentSetId, String experimentPath) throws JargonException{
		CollectionAO cAO = this.irodsAccessObjectFactory.getCollectionAO(this.account);
		cAO.addAVUMetadata(experimentPath, AvuData.instance(GeneralMetadata.EXPERIMENT_SET_ID, String.valueOf(experimentSetId), ""));
	}
	
	/**
	 * Remove experiment from set
	 * @param experimentSetId Experiment set ID
	 * @param experimentPath Experiment path
	 * @throws JargonException 
	 */
	public void removeExperimentFromSet(long experimentSetId, String experimentPath) throws JargonException {
		CollectionAO cAO = this.irodsAccessObjectFactory.getCollectionAO(this.account);
		cAO.deleteAVUMetadata(experimentPath, AvuData.instance(GeneralMetadata.EXPERIMENT_SET_ID, String.valueOf(experimentSetId), ""));
	}

	/**
	 * Create experiment set
	 * @param name Set name
	 * @param description Set description
	 * @param isPublic Public flag
	 * @throws SQLException
	 */
	public IBIOMESExperimentSet createExperimentSet(String name, String description, boolean isPublic) throws SQLException{
		long id = this.sql.createExperimentSet(this.account.getUserName(), name, description, isPublic);
		return sql.getExperimentSetById(id, this.account.getUserName());
	}
	
	/**
	 * Update experiment set
	 * @param id Set ID
	 * @param name Set name
	 * @param description Set description
	 * @param isPublic Public flag
	 * @throws SQLException
	 */
	public IBIOMESExperimentSet updateExperimentSet(long id, String name, String description, boolean isPublic) throws SQLException{
		this.sql.updateExperimentSet(id, this.account.getUserName(), name, description, isPublic);
		return sql.getExperimentSetById(id, this.account.getUserName());
	}
	
	/**
	 * Delete experiment set.
	 * @param setId Experiment set ID
	 * @throws Exception 
	 */
	public void deleteExperimentSet(long setId) throws Exception {
		List<String> experiments = listExperimentsInSet(setId);
		//remove all references to this experiment set
		CollectionAO cAO = this.irodsAccessObjectFactory.getCollectionAO(this.account);
		for (String experimentPath : experiments){
			cAO.deleteAVUMetadata(experimentPath, AvuData.instance(GeneralMetadata.EXPERIMENT_SET_ID, String.valueOf(setId), ""));
		}
		//delete set
		sql.deleteExperimentSet(setId, this.account.getUserName());
	}
	
	/**
	 * Get metadata (list of AVUs)
	 * @param setId Experiment set ID
	 * @return metadata List of AVUs
	 * @throws SQLException 
	 */
	public MetadataAVUList getMetadata(long setId) throws SQLException{
		MetadataAVUList metadata = sql.getExperimentSetMetadata(setId, this.account.getUserName());
		return metadata;
	}
	/**
	 * Set metadata (list of AVUs)
	 * @param setId Experiment set ID
	 * @param metadata List of AVUs
	 * @throws SQLException 
	 */
	public void setMetadata(long setId, MetadataAVUList metadata) throws SQLException{
		sql.setExperimentSetMetadata(setId, this.account.getUserName(), metadata);
	}

	/**
	 * Add AVU
	 * @param setId Experiment set ID
	 * @param avu AVU
	 * @throws SQLException 
	 */
	public void addMetadata(long setId, MetadataAVU avu) throws SQLException{
		sql.addExperimentSetMetadata(setId, this.account.getUserName(), avu);
	}
	
	/**
	 * Add AVUs
	 * @param setId Experiment set ID
	 * @param avus AVU list
	 * @throws SQLException 
	 */
	public void addMetadata(long setId, List<MetadataAVU> avus) throws SQLException{
		for (MetadataAVU avu : avus){
			sql.addExperimentSetMetadata(setId, this.account.getUserName(), avu);
		}
	}
	
	/**
	 * Delete AVU
	 * @param setId Experiment set ID
	 * @param avu AVU
	 * @throws SQLException 
	 */
	public void deleteMetadata(long setId, MetadataAVU avu) throws SQLException{
		sql.deleteExperimentSetMetadata(setId, this.account.getUserName(), avu);
	}
	
	/**
	 * Delete AVUs
	 * @param setId Experiment set ID
	 * @param avus AVU list
	 * @throws SQLException 
	 */
	public void deleteMetadata(long setId, List<MetadataAVU> avus) throws SQLException{
		for (MetadataAVU avu : avus){
			sql.deleteExperimentSetMetadata(setId, this.account.getUserName(), avu);
		}
	}
	
	/**
	 * Delete all AVUs
	 * @param setId Experiment set ID
	 * @throws SQLException 
	 */
	public void deleteAllMetadata(long setId) throws SQLException{
		sql.clearExperimentSetMetadata(setId, this.account.getUserName());
	}
	
	/**
	 * Add reference to analysis file
	 * @param setId Experiment set ID
	 * @param fileUri File URI
	 * @throws SQLException
	 * @throws JargonException 
	 * @throws FileNotFoundException 
	 */
	public void addAnalysisFileReference(long setId, String fileUri) throws SQLException, FileNotFoundException, JargonException{
		DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(account);
		DataObject data = dao.findByAbsolutePath(fileUri);
		sql.addExperimentSetAnalysisFile(setId, this.account.getUserName(), data.getId());
	}
	
	/**
	 * Get IDs of analysis files referenced by the experiment
	 * @param setId Experiment set ID
	 * @return List of file IDs
	 * @throws SQLException 
	 */
	public List<Integer> getAnalysisFileReferences(long setId) throws SQLException{
		List<Integer> ids = sql.getExperimentSetAnalysisFileIds(setId, this.account.getUserName());
		return ids;
	}
	
	/**
	 * Add reference to analysis file
	 * @param setId Experiment set ID
	 * @param fileUris File URI list
	 * @throws SQLException 
	 * @throws JargonException 
	 * @throws FileNotFoundException 
	 */
	public void addAnalysisFileReferences(long setId, List<String> fileUris) throws SQLException, JargonException, FileNotFoundException{
		DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(account);
		for (String uri : fileUris){
			DataObject data = dao.findByAbsolutePath(uri);
			sql.addExperimentSetAnalysisFile(setId, this.account.getUserName(), data.getId());
		}
	}
	
	/**
	 * Remove reference to analysis file
	 * @param setId Experiment set ID
	 * @param fileUri File URI
	 * @throws SQLException
	 * @throws JargonException 
	 * @throws FileNotFoundException 
	 */
	public void removeAnalysisFileReference(long setId, String fileUri) throws SQLException, FileNotFoundException, JargonException{
		DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(account);
		DataObject data = dao.findByAbsolutePath(fileUri);
		sql.removeExperimentSetAnalysisFile(setId, this.account.getUserName(), data.getId());
	}
	
	/**
	 * Remove references to analysis files
	 * @param setId Experiment set ID
	 * @param fileUris File URI list
	 * @throws SQLException 
	 * @throws JargonException 
	 * @throws FileNotFoundException 
	 */
	public void removeAnalysisFileReferences(long setId, List<String> fileUris) throws SQLException, JargonException, FileNotFoundException{
		DataObjectAO dao = irodsAccessObjectFactory.getDataObjectAO(account);
		for (String uri : fileUris){
			DataObject data = dao.findByAbsolutePath(uri);
			sql.removeExperimentSetAnalysisFile(setId, this.account.getUserName(), data.getId());
		}
	}
	
	/**
	 * Get list of sets that reference a given experiment
	 * @param path Path to experiment
	 * @return List of experiment sets
	 * @throws JargonException
	 * @throws JargonQueryException
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
	public IBIOMESExperimentSetList getReferencingExperimentSets(String path) throws JargonException, JargonQueryException, NumberFormatException, SQLException{
		
		CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(account);		
		if (path.endsWith("/")){
			path = path.substring(0, path.length()-1);
		}
		List<AVUQueryElement> avus = new ArrayList<AVUQueryElement>();
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, GeneralMetadata.EXPERIMENT_SET_ID));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "%"));
		List<MetaDataAndDomainData> metadata = cAO.findMetadataValuesByMetadataQueryForCollection(avus, path);

		IBIOMESExperimentSetList sets = new IBIOMESExperimentSetList();
		if (metadata!=null && metadata.size()>0)
		{
			for (MetaDataAndDomainData m : metadata){
				String setId = m.getAvuValue();
				IBIOMESExperimentSet set = this.getExperimentSet(Long.parseLong(setId));
				if (set != null){
					sets.add(set);
				}
			}
			return sets;
		}
		else return null;
	}
}
