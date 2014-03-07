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
import org.irods.jargon.core.query.GenQueryOrderByField.OrderByType;
import org.irods.jargon.core.query.IRODSGenQueryBuilder;
import org.irods.jargon.core.query.IRODSGenQueryFromBuilder;
import org.irods.jargon.core.query.IRODSQueryResultRow;
import org.irods.jargon.core.query.QueryConditionOperators;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.TranslatedIRODSGenQuery;

/**
 * Query builder for iBIOMES collections
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESCollectionSearch extends IBIOMESAbstractSearch {

	private String collectionName = null;
	private final Logger logger = Logger.getLogger(IBIOMESCollectionSearch.class);
	private String parentCollection = null;
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param acc iRODS account
	 * @throws JargonException
	 */
	public IBIOMESCollectionSearch(
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
	public IBIOMESCollectionSearch(
			boolean caseInsensitive,
			boolean needCount,
			IRODSAccessObjectFactory irodsAccessObjectFactory, 
			IRODSAccount acc) throws JargonException
	{
		super(caseInsensitive, needCount, irodsAccessObjectFactory, acc);
	}
	
	/**
	 * Compile query
	 * @throws Exception 
	 */
	protected void compileQuery() throws Exception
	{
		logger.debug("[SEARCH] Compile collection search query");
		logger.debug("[SEARCH] Case-insensitive: " + isCaseInsensitive);
		
		IRODSGenQueryBuilder queryBuilder = new IRODSGenQueryBuilder(true, isCaseInsensitive, needCount, null);

		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_NAME);
		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_OWNER_NAME);
		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
		
		//name of the collection
		if (collectionName!=null && collectionName.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_NAME, QueryConditionOperators.LIKE, collectionName);
			logger.debug("[SEARCH] Criteria: NAME=" + collectionName);
		}
		//parent collection
		if (parentCollection!=null && parentCollection.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_PARENT_NAME, QueryConditionOperators.LIKE, parentCollection);
			logger.debug("[SEARCH] Criteria: PARENT=" + collectionName);
		}		
		//owner name
		if (ownerUsername!=null && ownerUsername.length()>0){
			queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_OWNER_NAME, QueryConditionOperators.LIKE, ownerUsername);
			logger.debug("[SEARCH] Criteria: OWNER=" + ownerUsername);
		}

		//creation date range
		if ((creationDateMax>0 || creationDateMin>0)){
			logger.debug("[SEARCH] Criteria: TIMESTAMP=[" +creationDateMin+","+ creationDateMax+"]");
			if (creationDateMin != creationDateMax){
				if (creationDateMin>0 && creationDateMax==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, String.valueOf(creationDateMin));
				else if (creationDateMax>0 && creationDateMin==0)
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, String.valueOf(creationDateMax));
				else {
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO, creationDateMin);
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO, creationDateMax);
					//queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.BETWEEN, String.valueOf(creationDateMin), String.valueOf(creationDateMax));
				}
			}
			else {
				queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_CREATE_TIME, QueryConditionOperators.EQUAL, String.valueOf(creationDateMin));
			}
		}
				
		//AVU conditions
		if (avuConditions != null)
		{
			for (AVUQueryElement avu : avuConditions)
			{
				QueryConditionOperators rodsOp = getOperatorFromAVUEnum(avu.getOperator());
				if (avu.getAvuQueryPart().equals(AVUQueryPart.ATTRIBUTE)){
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_COLL_ATTR_NAME, rodsOp, avu.getValue());
					logger.debug("[SEARCH] AVU ATTRIBUTE " + rodsOp.name() + " " + avu.getValue());
				}
				else if (avu.getAvuQueryPart().equals(AVUQueryPart.VALUE)){
					queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_COLL_ATTR_VALUE, rodsOp, avu.getValue());
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
	 * Get search results
	 * @return list of collection paths
	 */
	public List<String> getResults() throws JargonException
	{
		List<IRODSQueryResultRow> rows = irodsResults.getResults();
		List<String> results = new ArrayList<String>();
		for (IRODSQueryResultRow row : rows){
			String path = row.getColumn(RodsGenQueryEnum.COL_COLL_NAME.getName());
			results.add(path);
		}
		return results;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public String getParentCollection() {
		return this.parentCollection;
	}
	
	public void setParentCollection(String parentCollection) {
		this.parentCollection   = parentCollection;
	}
}
