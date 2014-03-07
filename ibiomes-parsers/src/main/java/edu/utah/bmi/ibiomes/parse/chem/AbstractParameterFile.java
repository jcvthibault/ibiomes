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
import java.util.List;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Abstract file that contains computational method details and parameters
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class AbstractParameterFile extends ChemicalFile implements MethodParameterFile {

	private static final long serialVersionUID = 8771509990461411364L;
	protected List<ExperimentTask> tasks;
	
	/**
	 * 
	 * @param localPath
	 * @throws IOException
	 */
	public AbstractParameterFile(String localPath) throws IOException {
		super(localPath);
	}
	
	/**
	 * 
	 * @param localPath
	 * @param fileFormat
	 * @throws IOException
	 */
	public AbstractParameterFile(String localPath, String fileFormat) throws IOException {
		super(localPath,fileFormat);
	}
	
	/**
	 * Get tasks
	 */
	public List<ExperimentTask> getTasks() {
		return this.tasks;
	}
	
	/**
	 * Set tasks
	 */
	public void setTasks(List<ExperimentTask> tasks) {
		this.tasks = tasks;
	}
	
	/**
	 * Get metadata
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = new MetadataAVUList();
		if (super.getMetadata()!=null)
			metadata.addAll(super.getMetadata());
		
		if (this.tasks != null && this.tasks.size()>0){
			for (ExperimentTask task : this.tasks){
				metadata.addAll(task.getMetadata());
			}
		}
		return metadata;
	}

}
