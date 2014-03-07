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

import org.irods.jargon.core.query.RodsGenQueryEnum;

/**
 * Interface for iBIOMES search on distributed file system
 * @author Julien Thibault, University of Utah
 *
 */
public interface IBIOMESFileSystemSearch extends IBIOMESSearch {
	
	/**
	 * Get the field used to order the results
	 * @return Field used to order the results
	 */
	public RodsGenQueryEnum getOrderBy();

	/**
	 * Set the field used to order the results
	 * @param orderBy Field used to order the results
	 */
	public void setOrderBy(RodsGenQueryEnum orderBy);

	/**
	 * Check whether result ordering is ascendant
	 * @return True if ascendant, false if descendant
	 */
	public boolean isAscendant();

	/**
	 * Set whether result ordering is ascendant
	 * @param isAscendant True if ascendant, false if descendant
	 */
	public void setAscendant(boolean isAscendant);

}
