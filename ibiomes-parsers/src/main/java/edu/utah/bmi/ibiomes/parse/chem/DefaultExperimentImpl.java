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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryMolecularSystems;
import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.IBIOMESFileGroup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Default implementation for experiment file collection
 * @author Julien Thibault, University of Utah
 *
 */
public class DefaultExperimentImpl extends ExperimentFolder {

	private final static String[] topologyFormats = {
		LocalFile.FORMAT_PDB,
		LocalFile.FORMAT_PSF, 
		LocalFile.FORMAT_MOL2, 
		LocalFile.FORMAT_SDF 
	};
	private final static String[] structureFormats = {
		LocalFile.FORMAT_PDB,
		LocalFile.FORMAT_PSF, 
		LocalFile.FORMAT_MOL2, 
		LocalFile.FORMAT_SDF };
	
	/**
	 * New collection without software context
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public DefaultExperimentImpl(LocalDirectory fileDirectory) throws Exception
	{
		super(fileDirectory, null, null, structureFormats, topologyFormats);
		initializeProcesses();
	}
	
	/**
	 * New collection without software context
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public DefaultExperimentImpl(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception
	{
		super(fileDirectory, null, desc, structureFormats, topologyFormats);
		initializeProcesses();
	}

	/**
	 * 
	 * @throws Exception
	 */
	private void initializeProcesses() throws Exception
	{
		MolecularSystem system = null;
		if (mainTopologyFile != null){
			List<MolecularSystem> systems = mainTopologyFile.getMolecularSystems();
			if (systems != null && systems.size()>0){
				system = systems.get(0);
			}
		}
		else{
			Iterator<String> formatIter = allFiles.keySet().iterator();
			boolean found = false;
			while (formatIter.hasNext() && !found)
			{
				String format = formatIter.next();
				if (IBIOMESFileGroup.isTopologyFile(format))
				{
					int f = 0;
					while (f < allFiles.get(format).size() && !found)
					{
						TopologyFile topologyFile = (TopologyFile) allFiles.get(format).get(f);
						List<MolecularSystem> systems = topologyFile.getMolecularSystems();
						if (systems != null && systems.size()>0){
							system = systems.get(0);
							found = true;
						}
						f++;
					}
				}
			}
		}
		
		
		Iterator<String> formatIter = allFiles.keySet().iterator();
				
		//find parameter (e.g. MD/QM input) files that define tasks
		List<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		while (formatIter.hasNext()){
			String format = formatIter.next();
			if (IBIOMESFileGroup.isParameterFile(format)){
				for (LocalFile file : allFiles.get(format)){
					try{
						MethodParameterFile parameterFile = (MethodParameterFile)file;
						List<ExperimentTask> fileTasks = parameterFile.getTasks();
						if (fileTasks!=null)
							tasks.addAll(fileTasks);
					} catch(Exception e){
						throw new Exception(format +" files do not implement the MethodParameterFile interface!", e);
					}
				}
			}
		}
		
		ExperimentProcess process = new ExperimentProcess(null, description, tasks);
		List<ExperimentProcess> processes = new ArrayList<ExperimentProcess>();
		processes.add(process);
		
		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, description, system, processes);
		
		this.processGroups = new ArrayList<ExperimentProcessGroup>();
		this.processGroups.add(processGroup);
	}
	
	/**
	 * Get Amber collection metadata
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{	
		MetadataAVUList metadata = super.getMetadata();
		
		//method metadata
		SummaryExperimentTasks tasksSummary = this.getExperimentTasksSummary();
		if (tasksSummary != null)
			metadata.addAll(tasksSummary.getMetadata());
		
		//topology metadata
		SummaryMolecularSystems summarySystems = this.getMolecularSystemsSummary();
		if (summarySystems != null)
			metadata.addAll(summarySystems.getMetadata());
		
		//find Jmol-supported file for display
		boolean found = false;
		Iterator<String> formatIter = allFiles.keySet().iterator();
		while (formatIter.hasNext() && !found){
			String format = formatIter.next();
			if (IBIOMESFileGroup.isJmolFile(format)){
				found = true;
				metadata.add(new MetadataAVU(ExperimentMetadata.MAIN_3D_STRUCTURE_FILE, allFiles.get(format).get(0).getAbsolutePath().substring(fileDirectory.getAbsolutePath().length()+1)));
			}
		}
		
		return metadata;
	}
}
