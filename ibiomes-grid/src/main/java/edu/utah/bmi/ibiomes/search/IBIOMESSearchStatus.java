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

/**
 * Store status of a search with paging
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESSearchStatus {

	private int continueIndex;
	private int numberOfRowsRequested;
	private boolean hasMoreResults;
	private int totalNumberOfResults;
	private String searchType;

	/**
	 * Constructor
	 * @param index Current search result offset
	 * @param nRows Number of rows requested
	 * @param totalNumberOfResults Total number of results
	 * @param hasMore Whether there are more results after the current page
	 */
	public IBIOMESSearchStatus(int index, int nRows, int totalNumberOfResults, boolean hasMore){
		this.continueIndex = index;
		this.numberOfRowsRequested = nRows;
		this.hasMoreResults = hasMore;
		this.totalNumberOfResults = totalNumberOfResults;
	}
	
	/**
	 * Get current index of the search result
	 * @return Current index of the search result
	 */
	public int getContinueIndex() {
		return continueIndex;
	}
	
	/**
	 * Get numbers of results returned by each result set (page)
	 * @return Numbers of results returned by each result set (page)
	 */
	public int getNumberOfRowsRequested() {
		return numberOfRowsRequested;
	}
	
	/**
	 * Whether there are more results after the current search page
	 * @return True if there are more results after the current search page
	 */
	public boolean isHasMoreResults() {
		return hasMoreResults;
	}

	/**
	 * Get total number of results returned by the search
	 * @return Total number of results
	 */
	public int getTotalNumberOfResults() {
		return totalNumberOfResults;
	}
	
	/**
	 * Get the search type (e.g. 'files', 'qm', 'md')
	 * @return Search type
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * Set the search type (e.g. 'files', 'qm', 'md')
	 * @param searchType Search type
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
}
