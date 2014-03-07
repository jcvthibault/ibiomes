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
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentProcess;
import edu.utah.bmi.ibiomes.experiment.ExperimentProcessGroup;
import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;

/**
 * Abstract file that contains list of experiment process groups. 
 * Each experiment process is a set of calculations on the same system/molecule.
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class AbstractProcessGroupSetFile extends ChemicalFile implements TopologyFile, MethodParameterFile {

	private static final long serialVersionUID = 606846891534164024L;
	protected List<ExperimentProcessGroup> processGroups;
	
	/**
	 * New file that contains experiment process groups
	 * @param localPath Path to local file
	 * @throws IOException
	 */
	public AbstractProcessGroupSetFile(String localPath) throws IOException {
		super(localPath);
	}
	
	/**
	 * New file that contains experiment process groups
	 * @param localPath Path to local file
	 * @param fileFormat File format
	 * @throws IOException
	 */
	public AbstractProcessGroupSetFile(String localPath, String fileFormat) throws IOException {
		super(localPath,fileFormat);
	}

	/**
	 * Get list of process groups
	 * @return list of process groups
	 */
	public List<ExperimentProcessGroup> getProcessGroups() {
		return processGroups;
	}
	
	/**
	 * Set list of process groups
	 * @param processGroups List of process groups
	 */
	public void setProcessGroups(List<ExperimentProcessGroup> processGroups) {
		this.processGroups = processGroups;
	}
	
	/**
	 * Get list of molecular systems
	 */
	public List<MolecularSystem> getMolecularSystems() {
		List<MolecularSystem> systems = new ArrayList<MolecularSystem>();
		if (this.processGroups!=null){
			for (ExperimentProcessGroup processGroup : this.processGroups){
				if (processGroup.getMolecularSystem()!=null)
					systems.add(processGroup.getMolecularSystem());
			}
		}
		return systems;
	}

	/**
	 * Get list of tasks
	 */
	public List<ExperimentTask> getTasks() {
		ArrayList<ExperimentTask> tasks = new ArrayList<ExperimentTask>();
		if (this.processGroups!=null){
			for (ExperimentProcessGroup processGroup : this.processGroups){
				List<ExperimentProcess> processes = processGroup.getProcesses();
				if (processes!=null){
					for (ExperimentProcess process : processes){
						tasks.addAll(process.getTasks());
					}
				}
			}
		}
		return tasks;
	}
	
	/**
	 * Get metadata
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = new MetadataAVUList();
		if (super.getMetadata()!=null)
			metadata.addAll(super.getMetadata());
		
		List<MolecularSystem> molecularSystems = this.getMolecularSystems();
		if (molecularSystems != null && molecularSystems.size()>0){
			metadata.addAll(molecularSystems.get(0).getMetadata());
		}

		List<ExperimentTask> tasks = getTasks();
		if (tasks != null && tasks.size()>0){
			for (ExperimentTask task : tasks){
				metadata.addAll(task.getMetadata());
			}
		}
		return metadata;
	}
}
