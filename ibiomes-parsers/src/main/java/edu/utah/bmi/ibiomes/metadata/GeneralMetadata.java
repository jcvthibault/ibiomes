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

package edu.utah.bmi.ibiomes.metadata;

import java.util.List;

import edu.utah.bmi.ibiomes.dictionaries.IbiomesDictionary;

/**
 * General metadata
 * @author Julien Thibault
 *
 */
public class GeneralMetadata extends IbiomesDictionary {

	/**
	 * Simulation title
	 */
	public static final String EXPERIMENT_TITLE = "TITLE";
	/**
	 * Collection of file description
	 */
	public static final String EXPERIMENT_DESCRIPTION = "EXPERIMENT_DESCRIPTION";
	/**
	 * Task description
	 */
	public static final String TASK_DESCRIPTION = "TASK_DESCRIPTION";
	/**
	 * Author / experimentator
	 */
	public static final String EXPERIMENT_AUTHOR = "AUTHOR";
	/**
	 * Type of file (collection, regular file...)
	 */
	public static final String IBIOMES_FILE_TYPE = "FILE_TYPE";
	
	/**
	 * Experiment set ID
	 */
	public static final String EXPERIMENT_SET_ID = "EXPERIMENT_SET_ID";
	
	
	
	/**
	 * Get list of general metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return GeneralMetadata.getMetadataAttributes(GeneralMetadata.class);
	}
}
