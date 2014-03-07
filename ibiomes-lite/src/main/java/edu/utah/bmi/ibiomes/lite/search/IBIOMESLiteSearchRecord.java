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

package edu.utah.bmi.ibiomes.lite.search;

/**
 * iBIOMES Lite search result record 
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class IBIOMESLiteSearchRecord {

	private String experimentId;
	private String localDirPath;
	private String liteWebDirPath;
	
	/**
	 * New search record
	 * @param experimentId Experiment ID
	 * @param localDirPath Absolute path to local experiment directory
	 * @param liteWebDirPath Relative path to experiment directory in iBIOMES Lite web folder
	 */
	public IBIOMESLiteSearchRecord(String experimentId, String localDirPath, String liteWebDirPath){
		this.experimentId = experimentId;
		this.localDirPath = localDirPath;
		this.liteWebDirPath = liteWebDirPath;
	}

	/**
	 * Get experiment ID in iBIOMES Lite
	 * @return Experiment ID in iBIOMES Lite
	 */
	public String getExperimentId() {
		return experimentId;
	}
	
	/**
	 * Get absolute path to local directory 
	 * @return Absolute path to local directory
	 */
	public String getLocalDirPath() {
		return localDirPath;
	}
	
	/**
	 * Get relative path to experiment directory in iBIOMES Lite web folder
	 * @return Relative path to experiment directory in iBIOMES Lite web folder
	 */
	public String getLiteWebDirPath() {
		return liteWebDirPath;
	}

}
