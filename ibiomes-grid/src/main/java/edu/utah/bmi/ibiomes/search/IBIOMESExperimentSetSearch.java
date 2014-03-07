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

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.JargonQueryException;

import edu.utah.bmi.ibiomes.pub.set.ExperimentSetSqlConnector;

/**
 * Utility class to search experiment sets
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESExperimentSetSearch implements IBIOMESSearch {

	private final Logger logger = Logger.getLogger(IBIOMESExperimentSetSearch.class);
	
	public enum ExperimentSetAttributeEnum {DESCRIPTION, NAME, ID, OWNER};
	
	private final static int MAX_NUMBER_RECORDS_DEFAULT = 100;
	
	protected int currentIndex;
	
	protected int numberOfRowsRequested;
	private int totalNumberOfResults = -1;
	protected ExperimentSetAttributeEnum orderBy;
	protected boolean isAscendant;
	
	protected String ownerUsername;
	protected String description;
	protected String name;
	protected long creationDateMin;
	protected long creationDateMax;
	protected List<AVUQueryElement> avuConditions;
	
	private ExperimentSetSqlConnector sql;
	private IRODSAccount irodsAccount;
	private List<String> results;
	
	protected boolean isCaseInsensitive = true;

	/**
	 * Constructor
	 * @param ds Data source
	 */
	public IBIOMESExperimentSetSearch(IRODSAccount irodsAccount, DataSource ds){
		this.sql = new ExperimentSetSqlConnector(ds);
		this.numberOfRowsRequested = MAX_NUMBER_RECORDS_DEFAULT;
		this.currentIndex = 0;
		this.isCaseInsensitive = true;
		this.orderBy = ExperimentSetAttributeEnum.ID;
		this.isAscendant = true;
		this.creationDateMin = 0;
		this.creationDateMax = 0;
		this.irodsAccount = irodsAccount;
	}
	
	/**
	 * Start search with offset
	 */
	public List<String> executeWithStart(int continueIndex)
			throws JargonException, JargonQueryException, Exception {
		this.currentIndex = continueIndex;
		this.results = sql.searchExperimentSets(irodsAccount.getUserName(), ownerUsername, name, description, creationDateMin, creationDateMax, avuConditions, numberOfRowsRequested, continueIndex, orderBy.name(), isAscendant);
		return results;
	}

	/**
	 * Execute query
	 */
	public List<String> execute() throws JargonException, JargonQueryException,
			Exception {
		this.results = sql.searchExperimentSets(irodsAccount.getUserName(), ownerUsername, name, description, creationDateMin, creationDateMax, avuConditions, numberOfRowsRequested, 0, orderBy.name(), isAscendant);
		return results;
	}

	/**
	 * Execute query and close
	 */
	public List<String> executeAndClose() throws JargonException, JargonQueryException, Exception {
		try{
			List<String> tempResults = sql.searchExperimentSets(irodsAccount.getUserName(), ownerUsername, name, description, creationDateMin, creationDateMax, avuConditions, numberOfRowsRequested, 0, orderBy.name(), isAscendant);
			return tempResults;
		}
		finally{
			this.closeSearch();
		}
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
	 * Get results (experiment set IDs)
	 */
	public List<String> getResults() throws JargonException {
		return this.results;
	}

	public boolean hasMoreResults() throws Exception {
		int nResults = this.getTotalNumberOfRecords();
		if (nResults > this.currentIndex + this.numberOfRowsRequested)
			return true;
		else return false;
	}

	public int getTotalNumberOfRecords() throws Exception {
		if (this.totalNumberOfResults < 0)
			this.totalNumberOfResults = sql.getNumberOfResultsForSearch(irodsAccount.getUserName(), ownerUsername, name, description, creationDateMin, creationDateMax, avuConditions);
		return this.totalNumberOfResults;
	}

	public void closeSearch() throws JargonException {
		this.currentIndex = 0;
		this.avuConditions = null;
		this.creationDateMin = 0;
		this.creationDateMax = 0;
		this.description = null;
		this.ownerUsername = null;
		this.name = null;
		this.results = null;
		this.sql = null;
		this.totalNumberOfResults = -1;
	}
	
	public IBIOMESSearchStatus getStatus() throws Exception {
		return new IBIOMESSearchStatus(this.currentIndex, this.numberOfRowsRequested, getTotalNumberOfRecords(), hasMoreResults());
	}

	public int getNumberOfRowsRequested() {
		return this.numberOfRowsRequested;
	}
	
	public void setNumberOfRowsRequested(int numberOfRecords) {
		this.numberOfRowsRequested = numberOfRecords;
	}

	public List<AVUQueryElement> getAvuConditions() {
		return this.avuConditions;
	}

	public void setAvuConditions(List<AVUQueryElement> avuConditions) {
		this.avuConditions = avuConditions;
	}

	public long getCreationDateMin() {
		return creationDateMin;
	}

	public void setCreationDateMin(long creationDateMin) {
		this.creationDateMin = creationDateMin;
	}

	public long getCreationDateMax() {
		return this.creationDateMax;
	}

	public void setCreationDateMax(long creationDateMax) {
		this.creationDateMax = creationDateMax;
	}

	public ExperimentSetAttributeEnum getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(ExperimentSetAttributeEnum orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAscendant() {
		return isAscendant;
	}

	public void setAscendant(boolean isAscendant) {
		this.isAscendant = isAscendant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(String ownerUsername) {
		this.ownerUsername = ownerUsername;;
	}
}
