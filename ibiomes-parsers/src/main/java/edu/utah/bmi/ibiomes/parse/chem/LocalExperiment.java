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

package edu.utah.bmi.ibiomes.parse.chem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryMolecularSystems;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Interface to handle local file collections that need to be published into iRODS. 
 * @author Julien Thibault
 *
 */
public interface LocalExperiment extends LocalDirectory
{
	/**
	 * Get software
	 * @return software
	 */
	public String getSoftware();
	
	/**
	 * Get description
	 * @return description
	 */
	public String getDescription();

	/**
	 * Get list of experiment process groups
	 * @return main structure file
	 */
	public List<ExperimentProcessGroup> getProcessGroups();
	
	/**
	 * Get main structure file
	 * @return main structure file
	 */
	public LocalFile getMainStructureFile();

	/**
	 * Get main topology file
	 * @return main topology file
	 */
	public TopologyFile getMainTopologyFile();

	/**
	 * Get main parameter file
	 * @return main parameter file
	 */
	public MethodParameterFile getMainParameterFile();
	
	/**
	 * Retrieve all the files in this collection and the sub-collections
	 * @return List of files
	 */
	public HashMap<String,ArrayList<LocalFile>> getFilesByFormatRecursive();
	
	/**
	 * Retrieve all the analysis files in this collection and the sub-collections
	 * @return List of analysis files
	 * @throws Exception 
	 */
	public HashMap<String, List<LocalFile>> getAnalysisFiles() throws Exception;
	
	/**
	 * Set software
	 * @param software Software name
	 */
	public void setSoftware(String software);
	
	/**
	 * Store collection and file metadata to text file. 
	 * @param outputFilePath
	 * @throws IOException
	 */
	public void storeToText(String outputFilePath) throws Exception;
	
	/**
	 * Store collection and file metadata to XML file. 
	 * @param outputFilePath
	 * @throws IOException
	 */
	public void storeToXML(String outputFilePath) throws Exception;

	/**
	 * Get summary of computational tasks
	 * @return Summary of computational tasks
	 */
	public SummaryExperimentTasks getTasksSummary();

	/**
	 * Get summary of simulated molecular systems
	 * @return Summary of simulated molecular systems
	 */
	public SummaryMolecularSystems getMolecularSystemsSummary();

}
