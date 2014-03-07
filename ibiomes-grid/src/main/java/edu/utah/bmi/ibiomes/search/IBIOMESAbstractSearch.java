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

import java.util.List;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSGenQueryExecutor;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.AbstractIRODSGenQuery;
import org.irods.jargon.core.query.IRODSQueryResultSet;
import org.irods.jargon.core.query.QueryConditionOperators;
import org.irods.jargon.core.query.RodsGenQueryEnum;

/**
 * Abstract query builder for iBIOMES data (experiments/collections or files)
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class IBIOMESAbstractSearch implements IBIOMESFileSystemSearch {

	private final static int MAX_NUMBER_RECORDS_DEFAULT = 100;
	
	protected IRODSGenQueryExecutor irodsQueryExecutor;
	protected AbstractIRODSGenQuery irodsQuery;
	protected IRODSQueryResultSet irodsResults;
	
	protected IRODSAccessObjectFactory irodsAccessObjectFactory = null;
	protected IRODSAccount account = null;
	protected int currentIndex;
	
	protected int numberOfRowsRequested;
	protected List<AVUQueryElement> avuConditions;
	protected long creationDateMin;
	protected long creationDateMax;
	protected RodsGenQueryEnum orderBy;
	protected boolean isAscendant;
	protected String ownerUsername;
	
	protected boolean isCaseInsensitive = true;
	protected boolean needCount = false;
	
	/**
	 * Constructor
	 * @param irodsAccessObjectFactory
	 * @param acc iRODS account
	 * @throws JargonException
	 */
	public IBIOMESAbstractSearch(IRODSAccessObjectFactory irodsAccessObjectFactory, IRODSAccount acc) throws JargonException
	{
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
		this.account = acc;
		irodsQuery = null;
		currentIndex = 0;
		numberOfRowsRequested = MAX_NUMBER_RECORDS_DEFAULT;
		irodsQueryExecutor = this.irodsAccessObjectFactory.getIRODSGenQueryExecutor(this.account);
		isCaseInsensitive = true;
	}
	
	/**
	 * Constructor
	 * @param caseInsensitive Case insensitive
	 * @param irodsAccessObjectFactory
	 * @param acc iRODS account
	 * @throws JargonException
	 */
	public IBIOMESAbstractSearch(
			boolean caseInsensitive,
			boolean requestCount,
			IRODSAccessObjectFactory irodsAccessObjectFactory, 
			IRODSAccount acc) 
					throws JargonException
	{
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
		this.account = acc;
		this.irodsQuery = null;
		this.currentIndex = 0;
		this.numberOfRowsRequested = MAX_NUMBER_RECORDS_DEFAULT;
		this.irodsQueryExecutor = this.irodsAccessObjectFactory.getIRODSGenQueryExecutor(this.account);
		this.isCaseInsensitive = caseInsensitive;
		this.needCount = requestCount;
	}
	
	/**
	 * Compile query based on provided search criteria
	 * @throws Exception
	 */
	protected abstract void compileQuery() throws Exception;
	
	/**
	 * Execute search query from offset
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeWithStart(int continueIndex) throws Exception{
		
		if (irodsQuery == null)
			compileQuery();
		irodsResults = irodsQueryExecutor.executeIRODSQueryWithPaging(irodsQuery, continueIndex);
		this.currentIndex = continueIndex;
		return getResults();
	}
	
	/**
	 * Execute search query to get next set of results
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeForNextPage() throws Exception{
		int continueIndex = this.currentIndex + this.numberOfRowsRequested;
		return executeWithStart(continueIndex);
	}
	/**
	 * Execute search query to get previous set of results
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeForPreviousPage() throws Exception{
		int continueIndex = this.currentIndex - this.numberOfRowsRequested;
		return executeWithStart(continueIndex);
	}
	
	/**
	 * Execute search query
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> execute() throws Exception{
		if (irodsQuery == null)
			compileQuery();
		irodsResults = irodsQueryExecutor.executeIRODSQuery(irodsQuery, 0);
		this.currentIndex = 0;
		return getResults();
	}
	
	/**
	 * Execute search query and close connection
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeAndClose() throws Exception{
		if (irodsQuery == null)
			compileQuery();
		irodsResults = irodsQueryExecutor.executeIRODSQuery(irodsQuery, 0);
		this.currentIndex = 0;
		List<String> results = getResults();
		this.closeSearch();
		return results;
	}

	/**
	 * Get search results (paths)
	 * @return List of paths
	 * @throws JargonException
	 */
	public abstract List<String> getResults() throws JargonException;
	
	/**
	 * Check if the search has more results.
	 * @return True if it does.
	 */
	public boolean hasMoreResults(){
		return irodsResults.isHasMoreRecords();
	}

	/**
	 * Get total number of records matching the query
	 * @return Number of records of -1 if the field wasn't specifically requested when building the query.
	 */
	public int getTotalNumberOfRecords(){
		if (needCount)
			return irodsResults.getTotalRecords();
		else return -1;
	}
	
	/**
	 * Close search
	 * @throws JargonException
	 */
	public void closeSearch() throws JargonException{
		if (irodsResults != null)
			irodsQueryExecutor.closeResults(irodsResults);
	}
	
	/**
	 * 
	 */
	protected void finalize() throws Throwable{
		closeSearch();
	}
	
	/**
	 * Get status of the search (when paging results)
	 * @return Search status object
	 */
	public IBIOMESSearchStatus getStatus(){
		return new IBIOMESSearchStatus(
				this.currentIndex, 
				this.getNumberOfRowsRequested(), 
				this.getTotalNumberOfRecords(), 
				this.irodsResults.isHasMoreRecords()
			);
	}
	
	protected static QueryConditionOperators getOperatorFromAVUEnum(AVUQueryOperatorEnum avuOp) throws Exception
	{
		QueryConditionOperators op;
		
		if (avuOp.equals(AVUQueryOperatorEnum.BETWEEN)){
			op = QueryConditionOperators.BETWEEN;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.EQUAL)){
			op = QueryConditionOperators.EQUAL;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.LIKE)){
			op = QueryConditionOperators.LIKE;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.GREATER_OR_EQUAL)){
			op = QueryConditionOperators.GREATER_THAN_OR_EQUAL_TO;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.GREATER_THAN)){
			op = QueryConditionOperators.GREATER_THAN;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.LESS_OR_EQUAL)){
			op = QueryConditionOperators.LESS_THAN_OR_EQUAL_TO;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.LESS_THAN)){
			op = QueryConditionOperators.LESS_THAN;
		}
		
		else if (avuOp.equals(AVUQueryOperatorEnum.NUM_GREATER_OR_EQUAL)){
			op = QueryConditionOperators.NUMERIC_GREATER_THAN_OR_EQUAL_TO;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.NUM_LESS_OR_EQUAL)){
			op = QueryConditionOperators.NUMERIC_LESS_THAN_OR_EQUAL_TO;
		}
		else if (avuOp.equals(AVUQueryOperatorEnum.NUM_LESS_THAN)){
			op = QueryConditionOperators.NUMERIC_LESS_THAN;
		}
		else throw new Exception("Operation '"+avuOp+"' not supported!");
		
		return op;
	}

	public int getNumberOfRowsRequested() {
		return numberOfRowsRequested;
	}

	public void setNumberOfRowsRequested(int numberOfRows) {
		this.numberOfRowsRequested = numberOfRows;
	}

	public List<AVUQueryElement> getAvuConditions() {
		return avuConditions;
	}

	public void setAvuConditions(List<AVUQueryElement> avuConditions) {
		this.avuConditions = avuConditions;
	}
	
	/**
	 * Go through given AVU conditions and add inferred criteria based on ontology
	 */
	public void enrichAvuConditionsWithOntology() {
		if (this.avuConditions!=null){
			String prevAttr = null;
			for (AVUQueryElement cond : this.avuConditions){
				if(cond.getAvuQueryPart()==AVUQueryPart.ATTRIBUTE){
					prevAttr = cond.getValue();
				}
				else {
					if (prevAttr==null){
						//logger.warn();
						break;
					}
				}
			}
		}
	}

	public long getCreationDateMin() {
		return creationDateMin;
	}

	public void setCreationDateMin(long creationDateMin) {
		this.creationDateMin = creationDateMin;
	}

	public long getCreationDateMax() {
		return creationDateMax;
	}

	public void setCreationDateMax(long creationDateMax) {
		this.creationDateMax = creationDateMax;
	}

	public RodsGenQueryEnum getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(RodsGenQueryEnum orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAscendant() {
		return isAscendant;
	}

	public void setAscendant(boolean isAscendant) {
		this.isAscendant = isAscendant;
	}

	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		if (ownerUsername!=null)
			ownerUsername = ownerUsername.replaceAll("[\"']", "");
		this.ownerUsername = ownerUsername;
	}
}
