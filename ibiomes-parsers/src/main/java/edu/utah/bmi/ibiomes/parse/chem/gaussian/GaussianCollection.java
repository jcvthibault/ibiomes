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

package edu.utah.bmi.ibiomes.parse.chem.gaussian;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.parse.chem.common.TopologyFileCollection;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;

/**
 * Collection of GAUSSIAN files (input/com and output/log files)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="experiment")
public class GaussianCollection extends ExperimentFolder
{	
	private final static String[] structureFormats = {LocalFile.FORMAT_GAUSSIAN_LOG, LocalFile.FORMAT_PDB };
	private final static String[] parameterAndTopologyFormats = {LocalFile.FORMAT_GAUSSIAN_LOG, LocalFile.FORMAT_GAUSSIAN_COM };
	
	@SuppressWarnings(value = { "unused" })
	private GaussianCollection(){	
	}
	
	/**
	 * New Gaussian collection
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public GaussianCollection(LocalDirectory fileDirectory) throws Exception{
		super(fileDirectory, 
				Software.GAUSSIAN, 
				null, 
				structureFormats, 
				TopologyFileCollection.TOPOLOGY_FORMATS_GAUSSIAN);
		this.initializeProcessGroups(parameterAndTopologyFormats);
	}
	
	/**
	 * New Gaussian collection
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public GaussianCollection(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception{
		super(fileDirectory, 
				Software.GAUSSIAN, 
				desc, 
				structureFormats, 
				TopologyFileCollection.TOPOLOGY_FORMATS_GAUSSIAN);
		this.initializeProcessGroups(parameterAndTopologyFormats);
	}
	
	/**
	 * Get experiment metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
				
		//add Gaussian calculation metadata
		ArrayList<LocalFile> inputFiles = allFiles.get(LocalFile.FORMAT_GAUSSIAN_COM);
		if (inputFiles!=null){
			//for each of the input file
			for (int f=0;f<inputFiles.size();f++)
			{
				GaussianInputFile inpFile = (GaussianInputFile)inputFiles.get(f);
				//method metadata
				List<ExperimentTask> tasks = inpFile.getTasks();
				if (tasks != null & tasks.size()>0){
					ExperimentTask task = tasks.get(0);
					metadata.addAll(task.getMetadata());
				}
			}
		}
		
		//add aggregated metadata
		SummaryExperimentTasks tasksSummary = this.getExperimentTasksSummary();
		if (tasksSummary != null){
			metadata.addAll(tasksSummary.getMetadata());
		}
		
		// default to QM if no method found
		if (!metadata.containsAttribute(MethodMetadata.COMPUTATIONAL_METHOD_NAME)){
			metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, ParameterSet.METHOD_QM));
		}
		
		//if no task found, default software name to GAUSSIAN
		if (!metadata.containsAttribute(PlatformMetadata.SOFTWARE_NAME)){
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, Software.GAUSSIAN));
		}
				
		if (mainTopologyFile != null){
			List<MolecularSystem> systems = mainTopologyFile.getMolecularSystems();
			if (systems != null && systems.size()>0){
				MolecularSystem system = systems.get(0);
				metadata.addAll(system.getMetadata());
			}
		}
		return metadata;
	}
}
