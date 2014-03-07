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

package edu.utah.bmi.ibiomes.parse.chem.amber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
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
 * Collection of AMBER simulation files (topology, trajectory and MD output files)
 * @author Julien Thibault, Universiyt of Utah
 *
 */
@XmlRootElement(name="experiment")
public class AmberCollection extends ExperimentFolder
{
	private final static String[] structureFormats = {LocalFile.FORMAT_PDB };
	private final static String[] parameterFormats = {LocalFile.FORMAT_AMBER_MDOUT, LocalFile.FORMAT_AMBER_MDIN};
		
	@SuppressWarnings("unused")
	private AmberCollection(){
	}
	
	/**
	 * New AMBER file collection
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public AmberCollection(LocalDirectory fileDirectory) throws Exception{
		super(fileDirectory, Software.AMBER, null, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_AMBER);
		initializeProcesses();
	}
	
	/**
	 * New AMBER file collection
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public AmberCollection(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception{
		super(fileDirectory, Software.AMBER, desc, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_AMBER);
		initializeProcesses();
	}
	
	/**
	 * Initialize process groups
	 * @throws Exception
	 */
	private void initializeProcesses() throws Exception
	{
		MolecularSystem system = this.initializeMolecularSystem();
		List<ExperimentProcess> processes = initializeProcesses(parameterFormats);
		
		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
		processGroup.organizeProcessesForMD();
		
		this.processGroups = new ArrayList<ExperimentProcessGroup>();
		this.processGroups.add(processGroup);
	}
	
	/**
	 * Set Amber collection metadata based on process group
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
		
		//default software is AMBER
		if (!metadata.containsAttribute(PlatformMetadata.SOFTWARE_NAME))
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, Software.AMBER));
		
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
			e.printStackTrace();
		}
		
		return metadata;
	}
}
