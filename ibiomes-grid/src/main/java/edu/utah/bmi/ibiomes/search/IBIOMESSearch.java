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

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.RodsGenQueryEnum;

/**
 * Interface for generic iBIOMES search
 * @author Julien Thibault, University of Utah
 *
 */
public interface IBIOMESSearch {
	
	/**
	 * Execute search query from offset
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeWithStart(int continueIndex) throws JargonException, JargonQueryException, Exception;
	
	/**
	 * Execute search query
	 * @return List of file/collections that match the search criteria
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws Exception 
	 */
	public List<String> execute() throws JargonException, JargonQueryException, Exception;
	
	/**
	 * Execute search query and close connection
	 * @return List of file/collections that match the search criteria
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws Exception 
	 */
	public List<String> executeAndClose() throws JargonException, JargonQueryException, Exception;

	/**
	 * Execute search query to get next set of results
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeForNextPage() throws Exception;
	
	/**
	 * Execute search query to get previous set of results
	 * @return List of file/collections that match the search criteria
	 * @throws Exception 
	 */
	public List<String> executeForPreviousPage() throws Exception;
	
	/**
	 * Get search results (paths)
	 * @return List of paths
	 * @throws JargonException
	 */
	public List<String> getResults() throws JargonException;
	
	/**
	 * Check if the search has more results.
	 * @return True if it does.
	 * @throws Exception 
	 */
	public boolean hasMoreResults() throws Exception;
	
	/**
	 * Get the number of rows requested for this search.
	 * @return number of rows requested for this search.
	 */
	public int getNumberOfRowsRequested();
	
	
	/**
	 * Close search
	 * @throws JargonException
	 */
	public void closeSearch() throws JargonException;
	
	/**
	 * Get status of the search (when paging results)
	 * @return Search status
	 * @throws Exception 
	 */
	public IBIOMESSearchStatus getStatus() throws Exception;

	/**
	 * Set the number of rows requested
	 * @param numberOfRecords Number of rows requested
	 */
	public void setNumberOfRowsRequested(int numberOfRecords);

	/**
	 * Get list of AVU conditions
	 * @return List of AVU conditions
	 */
	public List<AVUQueryElement> getAvuConditions();

	/**
	 * Set list of AVU conditions
	 * @param avuConditions List of AVU conditions
	 */
	public void setAvuConditions(List<AVUQueryElement> avuConditions);

	/**
	 * Get creation date lower end criteria
	 * @return Creation date lower end criteria
	 */
	public long getCreationDateMin();

	/**
	 * Set creation date lower end criteria
	 * @param creationDateMin Creation date lower end criteria
	 */
	public void setCreationDateMin(long creationDateMin);

	/**
	 * Get creation date upper end criteria
	 * @return creation date upper end criteria
	 */
	public long getCreationDateMax();

	/**
	 * Set creation date upper end criteria
	 * @param creationDateMax Creation date upper end criteria
	 */
	public void setCreationDateMax(long creationDateMax);

	/**
	 * Get owner username
	 * @return Owner username
	 */
	public String getOwnerUsername();

	/**
	 * Set owner username
	 * @param ownerUsername Owner's username
	 */
	public void setOwnerUsername(String ownerUsername);
	
	/**
	 * Get total number of records matching the query
	 * @return Number of records.
	 * @throws Exception 
	 */
	public int getTotalNumberOfRecords() throws Exception;

}
