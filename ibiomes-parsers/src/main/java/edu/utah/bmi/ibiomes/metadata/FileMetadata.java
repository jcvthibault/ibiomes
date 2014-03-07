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
 * File metadata (e.g. format, type)
 * @author Julien Thibault
 *
 */
public class FileMetadata extends IbiomesDictionary {

	/**
	 * Format of the file (e.g. ASCII Amber, NetCDF, CSV).
	 */
	public static final String FILE_FORMAT = "FILE_FORMAT";
	/**
	 * File description.
	 */
	public static final String FILE_DESCRIPTION = "FILE_DESCRIPTION";
	/**
	 * Type of the file (e.g. image, document, chemical file).
	 */
	public static final String MEDIA_TYPE = "MEDIA_TYPE";
	/**
	 * Whether the file is hidden or not
	 */
	public static final String FILE_IS_HIDDEN = "FILE_IS_HIDDEN";
	/**
	 * File class/annotation (e.g. 'main topology', 'result')
	 */
	public static final String FILE_CLASS = "FILE_CLASS";
	
	public static final String FILE_CLASS_TOPOLOGY_MAIN = "TOPOLOGY";
	public static final String FILE_CLASS_STRUCTURE_MAIN= "STRUCTURE";
	public static final String FILE_CLASS_PARAMETER_MAIN = "PARAMETERS";
	public static final String FILE_CLASS_ANALYSIS = "ANALYSIS";
	public static final String FILE_CLASS_PRESENTATION = "PRESENTATION";
	
	/**
	 * Get list of file metadata attributes
	 * @return list of topology metadata attributes
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static List<String> getMetadataAttributes() throws IllegalArgumentException, IllegalAccessException {
		return FileMetadata.getMetadataAttributes(FileMetadata.class);
	}
}
