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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.irods.jargon.core.query.AVUQueryElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * SQL interface for experiment sets
 * @author Julien Thibault
 *
 */
public class ExperimentSetSqlConnector {

	private final Logger logger = Logger.getLogger(ExperimentSetSqlConnector.class);
	
	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public ExperimentSetSqlConnector(){
	}

	/**
	 * Constructor
	 * @param dataSource Data source
	 */
	public ExperimentSetSqlConnector(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	/**
	 * Retrieve experiment set from its code
	 * @param id Set ID
	 * @return Experiment set
	 * @throws SQLException
	 */
	public IBIOMESExperimentSet getExperimentSetById(long id, String username) throws SQLException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_experiment_set( ?, ?) }");
		proc.setLong(1, id);
		proc.setString(2, username);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		if (results.next())
		{
			IBIOMESExperimentSet set = new IBIOMESExperimentSet(
					results.getLong("id"), 
					results.getString("owner"),
					results.getLong("timestamp"), 
					results.getString("name"), 
					results.getString("description"),
					results.getBoolean("is_public"));
			con.close();
			return set;
		}
		else {
			con.close();
			return null;
		}
	}
	
	/**
	 * Retrieve all experiment sets readable by given user
	 * @param username Username
	 * @return List of experiment sets.
	 * @throws SQLException
	 */
	public IBIOMESExperimentSetList getExperimentSetsForUser(String username) throws SQLException
	{
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_experiment_sets_read( ? ) }");
		proc.setString(1, username);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		IBIOMESExperimentSetList setList = new IBIOMESExperimentSetList();
		
		if (results!=null){
			while (results.next())
			{
				IBIOMESExperimentSet set = new IBIOMESExperimentSet(
						results.getLong("id"), 
						results.getString("owner"),
						results.getLong("timestamp"), 
						results.getString("name"), 
						results.getString("description"),
						results.getBoolean("is_public"));
				setList.add(set);
			}
		}
		
		con.close();
		return setList;
	}
	
	/**
	 * Retrieve all experiment sets owned by given user
	 * @param username Owner's username
	 * @return List of experiment sets.
	 * @throws SQLException
	 */
	public IBIOMESExperimentSetList getExperimentSetsForOwner(String username) throws SQLException
	{
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_experiment_sets_owned( ? ) }");
		proc.setString(1, username);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		IBIOMESExperimentSetList setList = new IBIOMESExperimentSetList();
		
		if (results!=null){
			while (results.next())
			{
				IBIOMESExperimentSet set = new IBIOMESExperimentSet(
						results.getLong("id"), 
						results.getString("owner"),
						results.getLong("timestamp")*1000, 
						results.getString("name"), 
						results.getString("description"),
						results.getBoolean("is_public"));
				setList.add(set);
			}
		}
		con.close();
		return setList;
	}
	
	/**
	 * Create new experiment set
	 * @param name Set name
	 * @param description Set description
	 * @param isPublic Public flag
	 * @throws SQLException
	 */
	public long createExperimentSet(String username, String name, String description, boolean isPublic) throws SQLException
	{
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call create_experiment_set( ?, ?, ?, ?, ? ) }");
		proc.setString(1, username);
		proc.setString(2, name);
		proc.setString(3, description);
		proc.setBoolean(4, isPublic);
	    proc.registerOutParameter(5, java.sql.Types.INTEGER);
		proc.execute();
		int id = proc.getInt(5);
		con.close();
		return id;
	}
	
	/**
	 * Update experiment set description
	 * @param id Set ID
	 * @param name Set name
	 * @param description Set description
	 * @return True if deletion is successful.
	 * @throws SQLException
	 */
	public boolean updateExperimentSet(long id, String username, String name, String description, boolean isPublic) throws SQLException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call update_experiment_set( ?, ?, ?, ?, ? ) }");
		proc.setLong(1, id);
		proc.setString(2, username);
		proc.setString(3, name);
		proc.setString(4, description);
		proc.setBoolean(5, isPublic);
		proc.execute();
		con.close();
		return true;
	}
	
	/**
	 * Delete experiment set
	 * @param id Set ID
	 * @return True
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean deleteExperimentSet(long id, String username) throws SQLException
	{
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call delete_experiment_set( ?, ? ) }");
		proc.setLong(1, id);
		proc.setString(2, username);
		System.out.println("[" + id + "]" + username);
		proc.execute();
		con.close();
		return true;
	}

	/**
	 * Get list of metadata for experiment set
	 * @param setId Experiment set ID
	 * @return List of metadata
	 * @throws SQLException
	 */
	public MetadataAVUList getExperimentSetMetadata(long setId, String username) throws SQLException 
	{
		MetadataAVUList metadata = new MetadataAVUList();
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_experiment_set_metadata( ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		if (results!=null){
			while (results.next())
			{
				MetadataAVU avu = new MetadataAVU(
						results.getString("attribute"),
						results.getString("value"), 
						results.getString("unit"));
				metadata.add(avu);
			}
		}
		con.close();
		return metadata;
	}

	/**
	 * Delete experiment set AVU
	 * @param setId
	 * @param username
	 * @param avu
	 * @throws SQLException 
	 */
	public void deleteExperimentSetMetadata(long setId, String username, MetadataAVU avu) throws SQLException {
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call delete_experiment_set_avu( ?, ?, ?, ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.setString(3, avu.getAttribute());
		proc.setString(4, avu.getValue());
		proc.setString(5, avu.getUnit());
		proc.execute();		
		con.close();
	}

	/**
	 * Clear all experiment set metadata
	 * @param setId Set ID
	 * @param username Username
	 * @throws SQLException 
	 */
	public void clearExperimentSetMetadata(long setId, String username) throws SQLException {
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call clear_experiment_set_metadata( ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.execute();		
		con.close();
	}
	
	/**
	 * Add metadata to experiment set
	 * @param setId
	 * @param username
	 * @param avu
	 * @throws SQLException
	 */
	public void addExperimentSetMetadata(long setId, String username, MetadataAVU avu) throws SQLException {
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call add_experiment_set_avu( ?, ?, ?, ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.setString(3, avu.getAttribute());
		proc.setString(4, avu.getValue());
		String unit = avu.getUnit();
		if (unit==null)
			unit = "";
		proc.setString(5, unit);
		proc.execute();		
		con.close();
	}

	/**
	 * Set experiment set metadata. Delete all existing metadata and add new ones.
	 * @param setId
	 * @param username
	 * @param metadata
	 * @throws SQLException 
	 */
	public void setExperimentSetMetadata(long setId, String username, MetadataAVUList metadata) throws SQLException {
		//clear existing metadata
		this.clearExperimentSetMetadata(setId, username);
		//add new metadata
		for (MetadataAVU avu : metadata){
			this.addExperimentSetMetadata(setId, username, avu);
		}
	}
	
	/**
	 * Get list of analysis file IDs for experiment set
	 * @param setId Experiment set ID
	 * @return List of IDs
	 * @throws SQLException
	 */
	public List<Integer> getExperimentSetAnalysisFileIds(long setId, String username) throws SQLException 
	{
		ArrayList<Integer> fileIds = new ArrayList<Integer>();
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_experiment_set_analysis_data( ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		if (results!=null){
			while (results.next()){
				Integer id = results.getInt("file_id");
				fileIds.add(id);
			}
		}
		con.close();
		return fileIds;
	}

	/**
	 * Remove file from the list of analysis files for this experiment set
	 * @param setId Experiment set ID
	 * @param username Username
	 * @param fileId iRODS data object ID
	 * @throws SQLException 
	 */
	public void removeExperimentSetAnalysisFile(long setId, String username, long fileId) throws SQLException {
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call remove_experiment_set_analysis_data( ?, ?, ? ) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.setLong(3, fileId);
		proc.execute();		
		con.close();
	}
	
	/**
	 * Add metadata to experiment set
	 * @param setId Experiment set ID
	 * @param username Username
	 * @param fileId iRODS data object ID
	 * @throws SQLException
	 */
	public void addExperimentSetAnalysisFile(long setId, String username, long fileId) throws SQLException {
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call add_experiment_set_analysis_data( ?, ?, ?) }");
		proc.setLong(1, setId);
		proc.setString(2, username);
		proc.setLong(3, fileId);
		proc.execute();		
		con.close();
	}
	
	/**
	 * Search experiment sets
	 * @param owner Owner username
	 * @param name Set name
	 * @param description Set description
	 * @param createDateMin
	 * @param createDateMax
	 * @param avuConditions
	 * @param numberOfRecordsRequested
	 * @param offset
	 * @param orderBy
	 * @param ascendant
	 * @throws Exception 
	 */
	public List<String> searchExperimentSets(
			String username,
			String owner, 
			String name, 
			String description, 
			long createDateMin, 
			long createDateMax, 
			List<AVUQueryElement> avuConditions, 
			int numberOfRecordsRequested, 
			int offset, 
			String orderBy, 
			boolean ascendant) throws Exception {
			
		String sql = 
				"select distinct S.ID as ID " +
				"from EXPERIMENT_SET S ";
		sql += buildQueryString(username, owner, name, description, createDateMin, createDateMax, avuConditions);
		
		if (orderBy != null && orderBy.length()>0){
			sql += "ORDER BY " + orderBy +" ";
			if (!ascendant)
				sql += sql += "DESC ";
		}
		sql += "LIMIT " + numberOfRecordsRequested + " ";
		sql += "OFFSET " + offset + " ";
		
		System.out.println("Preparing SQL query:\n" + sql);

		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery(sql);
		List<String> ids = new ArrayList<String>();
		while (rs.next()) {
			long id = rs.getLong("ID");
			ids.add(String.valueOf(id));
		}
		con.close();
		
		return ids;
	}
	
	/**
	 * Search experiment sets
	 * @param username Username
	 * @param owner Owner username
	 * @param name Set name
	 * @param description Set description
	 * @param createDateMin Min date criteria
	 * @param createDateMax Max date criteria
	 * @param avuConditions AVU criteria
	 * @throws Exception 
	 */
	public int getNumberOfResultsForSearch(
			String username,
			String owner, 
			String name, 
			String description, 
			long createDateMin, 
			long createDateMax, 
			List<AVUQueryElement> avuConditions) throws Exception {
		
		int nResults = -1;
		String sql = 
				"select count(distinct S.ID) as N " +
				"from EXPERIMENT_SET S ";
		sql += buildQueryString(username, owner, name, description, createDateMin, createDateMax, avuConditions);
		
		logger.info("Preparing SQL query:\n" + sql);

		Connection con = dataSource.getConnection();
		Statement stmt = con.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery(sql);

		if (rs.next()) {
			nResults = rs.getInt("N");
		}
		con.close();
		
		return nResults;
	}
	
	private String buildQueryString(
			String username,
			String owner, 
			String name, 
			String description, 
			long createDateMin, 
			long createDateMax, 
			List<AVUQueryElement> avuConditions) throws Exception {
		
		boolean isFirstCondition = true;
		
		String sql = " ";
		
		if (avuConditions != null && avuConditions.size()>0){
			sql += "where ID in (";
			sql += "select SET_ID ";
			sql += "from EXPERIMENT_SET_METADATA ";
			sql += "where ";
			String conditionsStr = "";
			try{
				int a = 0;
				while (a < avuConditions.size()){
					AVUQueryElement avuCondition = avuConditions.get(a);
					//TODO escape SQL characters such as "'" to avoid injections
					//TODO dont allow partial attributes
					conditionsStr += "(ATTRIBUTE like '" + avuCondition.getValue() + "'";
					conditionsStr += " and ";
					avuCondition = avuConditions.get(a+1);
					conditionsStr += "VALUE " + avuCondition.getOperator().getOperatorValue() + " '" + avuCondition.getValue() + "')";
					a+=2;
					if (a<avuConditions.size())
						conditionsStr += " and ";
				}
			}
			catch (Exception e){
				throw new Exception("The AVU condition list must include pairs of Attribute-Value conditions.");
			}
			sql += conditionsStr + " ";
			sql += "group by SET_ID ";
			sql += "having count(SET_ID) >= " + avuConditions.size()/2;
			sql += ") ";
			isFirstCondition = false;
		}
		
		if (owner != null && owner.length()>0){
			owner = owner.replaceAll("[\"']", "");
			if (!isFirstCondition)
				sql += "and  ";
			else sql += "where ";
			sql += "S.OWNER like '"+owner+"' ";
			isFirstCondition = false;
		}
		
		if (name != null && name.length()>0){
			name = name.replaceAll("[\"']", "");
			if (!isFirstCondition)
				sql += "and  ";
			else sql += "where ";
			sql += "S.NAME like '"+name+"' ";
			isFirstCondition = false;
		}
		
		if (description != null && description.length()>0){
			description = description.replaceAll("[\"']", "");
			if (!isFirstCondition)
				sql += "and  ";
			else sql += "where ";
			sql += "S.DESCRIPTION like '"+description+"' ";
			isFirstCondition = false;
		}
		
		if (createDateMin>0){
			if (!isFirstCondition)
				sql += "and  ";
			else sql += "where ";
			sql += "S.TIMESTAMP > "+createDateMin+" ";
			isFirstCondition = false;
		}
		
		if (createDateMax>0){
			if (!isFirstCondition)
				sql += "and  ";
			else sql += "where ";
			sql += "S.TIMESTAMP < "+createDateMax+" ";
			isFirstCondition = false;
		}
		
		//search only public sets or sets that are owned by current user
		if (!isFirstCondition)
			sql += "and  ";
		else sql += "where ";
		sql += "((IS_PUBLIC is true) or (OWNER like '"+username+"')) "; 
		
		return sql;
	}
}
