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

package edu.utah.bmi.ibiomes.parse.chem.gromacs;

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
import edu.utah.bmi.ibiomes.topo.Molecule;

/**
 * Collection of GROMACS files
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="experiment")
public class GROMACSCollection extends ExperimentFolder
{
	private final static String[] structureFormats = { LocalFile.FORMAT_PDB, LocalFile.FORMAT_GROMACS_GRO };
	private final static String[] parameterFileFormats = {LocalFile.FORMAT_GROMACS_MDP };
	
	@SuppressWarnings(value = { "unused" })
	private GROMACSCollection(){	
	}
	
	/**
	 * New GROMACS collection
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public GROMACSCollection(LocalDirectory fileDirectory) throws Exception
	{
		super(fileDirectory, Software.GROMACS, null, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_GROMACS);
		initializeProcesses();
	}
	
	/**
	 * New GROMACS collection
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public GROMACSCollection(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception{
		super(fileDirectory, Software.GROMACS, desc, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_GROMACS);
		initializeProcesses();
	}
	
	private void initializeProcesses() throws Exception
	{
		MolecularSystem system = null;
		if (mainTopologyFile != null)
		{
			List<MolecularSystem> systems = mainTopologyFile.getMolecularSystems();
			if (systems != null && systems.size()>0){
				system = systems.get(0);
				List<Molecule> molecules = system.getSoluteMolecules();
				//get list of molecules defined in ITP files
				if (mainTopologyFile.getFormat().equals(LocalFile.FORMAT_GROMACS_TOP)){
					List<LocalFile> filesInclude = allFiles.get(LocalFile.FORMAT_GROMACS_ITP);
					if (filesInclude != null && molecules!=null)
					{
						for (LocalFile fileItp : filesInclude){
							if (fileItp instanceof GROMACSIncludeTopologyFile){
								List<Molecule> localMoleculeDefs = ((GROMACSIncludeTopologyFile) fileItp).getMolecules();
								if (localMoleculeDefs != null){
									//for each molecule in the ITP file, replace in the TOP file
									for (Molecule molDef : localMoleculeDefs){
										for (int m=0; m<molecules.size();m++){
											Molecule mol = molecules.get(m);
											if (molDef.getName()!=null && 
												mol.getName()!=null && 
												molDef.getName().toUpperCase().equals(mol.getName().toUpperCase())){
												molecules.set(m, molDef);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		List<ExperimentProcess> processes = this.initializeProcesses(parameterFileFormats);
		
		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
		processGroup.organizeProcessesForMD();
		this.processGroups = new ArrayList<ExperimentProcessGroup>();
		this.processGroups.add(processGroup);
	}
	
	/**
	 * Get Gromacs collection metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{	
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
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, Software.GROMACS));
		
		//add topology info
		if (mainTopologyFile != null 
				&& mainTopologyFile.getMolecularSystems()!=null
				&& mainTopologyFile.getMolecularSystems().size()>0)
		{
			MolecularSystem system = mainTopologyFile.getMolecularSystems().get(0);
			metadata.addAll(system.getMetadata());
			
			if (!metadata.containsAttribute(MethodMetadata.SOLVENT_TYPE)) {
				//if water or ions are present, assume solvated system
				if (system.getIonCount()>0 || system.getSolventMoleculeCount()>0)
					metadata.updatePair(MethodMetadata.SOLVENT_TYPE, ParameterSet.SOLVENT_EXPLICIT);
				else metadata.add(new MetadataAVU(MethodMetadata.SOLVENT_TYPE, ParameterSet.SOLVENT_IN_VACUO));
			}
		}
		return metadata;
	}
}
