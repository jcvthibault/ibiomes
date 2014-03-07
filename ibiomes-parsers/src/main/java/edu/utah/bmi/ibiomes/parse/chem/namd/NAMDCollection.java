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

package edu.utah.bmi.ibiomes.parse.chem.namd;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDParameterSet;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.parse.chem.common.TopologyFileCollection;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Collection of NAMD simulation files (topology, trajectory and MD output files)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="experiment")
public class NAMDCollection extends ExperimentFolder
{
	private final static String[] structureFormats = {LocalFile.FORMAT_PDB };
	private final static String[] parameterFormats = {LocalFile.FORMAT_NAMD_LOG, LocalFile.FORMAT_NAMD_CONFIGURATION};
	
	@SuppressWarnings(value = { "unused" })
	private NAMDCollection(){	
	}
	
	/**
	 * New NAMD collection
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public NAMDCollection(LocalDirectory fileDirectory) throws Exception
	{
		super(fileDirectory, Software.NAMD, null, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_NAMD);
		initializeProcesses();
	}
	
	/**
	 * New NAMD collection
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public NAMDCollection(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception
	{
		super(fileDirectory, Software.NAMD, desc, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_NAMD);
		initializeProcesses();
	}
	
	private void initializeProcesses() throws Exception
	{
		MolecularSystem system = this.initializeMolecularSystem();
		List<ExperimentProcess> processes = initializeProcesses(parameterFormats);
		
		//if no task found default to MD
		if (processes==null || processes.size()==0) 
		{
			MDTask md = new MDTask(new MDParameterSet());
			md.setSoftware(new Software(Software.NAMD));
			ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
			tasks.add(md);
			processes = new ArrayList<ExperimentProcess>();
			processes.add(new ExperimentProcess(null, description, tasks));
		}
		
		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
		//organize processes using common MD protocol
		processGroup.organizeProcessesForMD();
		this.processGroups = new ArrayList<ExperimentProcessGroup>();
		this.processGroups.add(processGroup);
	}
	
	/**
	 * Get NAMD collection metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = super.getMetadata();

		//add aggregated metadata
		SummaryExperimentTasks tasksSummary = this.getExperimentTasksSummary();
		if (tasksSummary != null){
			metadata.addAll(tasksSummary.getMetadata());
		}
		
		// default to MD if no method found
		if (!metadata.containsAttribute(MethodMetadata.COMPUTATIONAL_METHOD_NAME)){
			metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, ParameterSet.METHOD_MD));
		}

		//if no task found, default software name to AMBER
		if (!metadata.containsAttribute(PlatformMetadata.SOFTWARE_NAME))
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, Software.NAMD));
		
		//topology information
		try{
			if (mainTopologyFile != null){
				List<MolecularSystem> systems = mainTopologyFile.getMolecularSystems();
				if (systems != null && systems.size()>0){
					MolecularSystem system = systems.get(0);
					metadata.addAll(system.getMetadata());
					//if water or ions are present, assume solvated system
					if (system.getSolventMoleculeCount()>0){
						metadata.removeAVUWithAttribute(MethodMetadata.IMPLICIT_SOLVENT_MODEL);
						metadata.updatePair(MethodMetadata.SOLVENT_TYPE, ParameterSet.SOLVENT_EXPLICIT);
					} 
				}
			}
		}
		catch (Exception e){
			//TODO
		}
				
		return metadata;
	}
}
