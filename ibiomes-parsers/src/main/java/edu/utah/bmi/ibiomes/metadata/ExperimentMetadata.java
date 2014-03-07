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
public class ExperimentMetadata extends IbiomesDictionary {

	/**
	 * Relative path to the main topology file for this collection
	 */
	public static final String TOPOLOGY_FILE_PATH = "TOPOLOGY_FILE_PATH";
	/**
	 * Relative path to the main parameter file for this collection
	 */
	public static final String PARAMETER_FILE_PATH = "PARAMETER_FILE_PATH";
	/**
	 * Relative path to the main 3D structure file (used to load into JMol)
	 */
	public static final String MAIN_3D_STRUCTURE_FILE = "MAIN_3D_STRUCTURE_FILE";
	/**
	 * Publication reference ID. Format: 'database:ID' (e.g., pubmed:22276203).
	 */
	public static final String PUBLICATION_REF_ID = "PUBLICATION_REF_ID";
	/**
	 * Literature database.(e.g., PubMed).
	 */
	public static final String PUBLICATION_DB = "PUBLICATION_DB";
	/**
	 * Log entry (for experiment results)
	 */
	public static final String LOG_ENTRY = "LOG_ENTRY";
	/**
	 * External resources (links)
	 */
	public static final String EXTERNAL_LINK = "EXTERNAL_LINK";
	
	
	
	/**
	 * Get list of general metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return ExperimentMetadata.getMetadataAttributes(ExperimentMetadata.class);
	}
}
