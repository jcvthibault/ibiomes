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

package edu.utah.bmi.ibiomes.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.GenQueryOrderByField.OrderByType;
import org.irods.jargon.core.query.IRODSGenQueryBuilder;
import org.irods.jargon.core.query.IRODSGenQueryFromBuilder;
import org.irods.jargon.core.query.IRODSQueryResultRow;
import org.irods.jargon.core.query.QueryConditionOperators;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.irods.jargon.core.query.TranslatedIRODSGenQuery;

/**
 * Query builder for iBIOMES data objects (iRODS files)
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESFileSearch extends IBIOMESAbstractSearch {

	private String directory;
	private String filename;
	private long sizeMin;
	private long sizeMax;
	private final Logger logger = Logger.getLogger(IBIOMESExperimentSearch.class);
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param acc iRODS account
	 * @throws JargonException
	 */
	public IBIOMESFileSearch(
			IRODSAccessObjectFactory irodsAccessObjectFactory, 
			IRODSAccount acc) throws JargonException
	{
		super(irodsAccessObjectFactory, acc);
	}

	/**
	 * Constructor
	 * @param caseInsensitive Case-insensitive flag
	 * @param irodsAccessObjectFactory
	 * @param acc iRODS account
	 * @throws JargonException
	 */
	public IBIOMESFileSearch(
			boolean caseInsensitive,
			boolean needCount,
			IRODSAccessObjectFactory irodsAccessObjectFactory, 
			IRODSAccount acc) throws JargonException
	{
		super(caseInsensitive, needCount, irodsAccessObjectFactory, acc);
	}
	
	/**
	 * Compile search query
	 */
	protected void compileQuery() throws Exception
	{
		logger.debug("[SEARCH] Compile FILE search query");
		
		//TODO THIS IS JUST TO HIDE IRODS BUG
		if (sizeMin>0 || sizeMax>0){
			isCaseInsensitive = false;
		}
		
		logger.debug("[SEARCH] Case-insensitive: " + isCaseInsensitive);
		
		IRODSGenQueryBuilder queryBuilder = new IRODSGenQueryBuilder(true, isCaseInsensitive, needCount, null);
		
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_D_DATA_ID);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_D_COLL_ID);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_NAME);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_DATA_NAME);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_D_DATA_PATH);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_D_OWNER_NAME);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_DATA_SIZE);
		queryBuilder = queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_D_CREATE_TIME);
		
		
		//name of the parent collection
		if (directory!=null && directory.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_NAME, QueryConditionOperators.LIKE, directory);
			logger.debug("[SEARCH] Criteria: PARENT DIR=" + directory);
		}
		
		//name of the file
		if (filename!=null && filename.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_NAME, QueryConditionOperators.LIKE, filename);
			logger.debug("[SEARCH] Criteria: NAME=" + filename);
		}
		
		//owner name
		if (ownerUsername!=null && ownerUsername.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_OWNER_NAME, QueryConditionOperators.LIKE, ownerUsername);
			logger.debug("[SEARCH] Criteria: OWNER=" + ownerUsername);
		}
		
		//creation date range
		if ((creationDateMax>0 || creationDateMin>0))
		{
			logger.debug("[SEARCH] Criteria: TIMESTAMP=[" +creationDateMin+","+ creationDateMax+"]");
			if (creationDateMin != creationDateMax){
				if (creationDateMin>0 && creationDateMax==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, creationDateMin);
				else if (creationDateMax>0 && creationDateMin==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, creationDateMax);
				else {
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, creationDateMin);
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, creationDateMax);
					//queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.BETWEEN, creationDateMin, creationDateMax);
				}
			}
			else {
				//System.out.println(creationDateMin);
				queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_D_CREATE_TIME, QueryConditionOperators.EQUAL, creationDateMin);
			}
		}
		
		//file size range
		if (sizeMin>0 || sizeMax>0)
		{
			logger.debug("[SEARCH] Criteria: SIZE=[" +sizeMin+","+ sizeMax+"]");
			if (sizeMin != sizeMax){
				if (sizeMin>0 && sizeMax==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, String.valueOf(sizeMin));
				else if (sizeMax>0 && sizeMin==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, String.valueOf(sizeMax));
				else {
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, String.valueOf(sizeMin));
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, String.valueOf(sizeMax));
					//queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.BETWEEN, sizeMin, sizeMax);
				}
			}
			else {
				queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_DATA_SIZE, QueryConditionOperators.EQUAL, String.valueOf(sizeMin));
			}
		}
		
		//AVU conditions
		if (avuConditions != null)
		{
			for (AVUQueryElement avu : avuConditions)
			{
				QueryConditionOperators rodsOp = getOperatorFromAVUEnum(avu.getOperator());
				if (avu.getAvuQueryPart().equals(AVUQueryPart.ATTRIBUTE)){
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_DATA_ATTR_NAME, rodsOp, avu.getValue());
					logger.debug("[SEARCH] AVU ATTRIBUTE " + rodsOp.name() + " " + avu.getValue());
				}
				else if (avu.getAvuQueryPart().equals(AVUQueryPart.VALUE)){
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_DATA_ATTR_VALUE, rodsOp, avu.getValue());
					logger.debug("[SEARCH] AVU VALUE " + rodsOp.name() + " " + avu.getValue());
				}
			}
		}

		//ordering (ascending or descending)
		if (orderBy != null){
			logger.debug("[SEARCH] Order by '" + orderBy + "'");
			OrderByType order = OrderByType.ASC;
			if (!isAscendant)
				order = OrderByType.DESC;
			queryBuilder.addOrderByGenQueryField(orderBy, order);
		}
		
		//generate query
		logger.debug("[SEARCH] Number of records wanted: " + this.numberOfRowsRequested);
		IRODSGenQueryFromBuilder query = queryBuilder.exportIRODSQueryFromBuilder(this.numberOfRowsRequested);
		TranslatedIRODSGenQuery trQuery = query.convertToTranslatedIRODSGenQuery();

		irodsQuery = trQuery.getIrodsQuery();
		logger.debug("[SEARCH] Query: " + queryBuilder.toString());
		System.out.println("[SEARCH] Query: " + queryBuilder.toString());
	}
	
	/**
	 * Get list of results (file paths)
	 */
	public List<String> getResults() throws JargonException
	{
		List<IRODSQueryResultRow> rows = irodsResults.getResults();
		List<String> results = new ArrayList<String>();
		for (IRODSQueryResultRow row : rows){
			String path = row.getColumn(RodsGenQueryEnum.COL_COLL_NAME.getName());
			path += "/" + row.getColumn(RodsGenQueryEnum.COL_DATA_NAME.getName());
			results.add(path);
		}
		return results;
	}
	

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public long getSizeMin() {
		return sizeMin;
	}

	public void setSizeMin(long sizeMin) {
		this.sizeMin = sizeMin;
	}

	public long getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}	
}
