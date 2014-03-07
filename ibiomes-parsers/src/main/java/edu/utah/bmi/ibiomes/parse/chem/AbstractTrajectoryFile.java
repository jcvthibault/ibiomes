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

import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.metadata.TrajectoryMetadata;

/**
 * Abstract trajectory file
 * @author Julien Thibault, University of Utah
 *
 */
public class AbstractTrajectoryFile extends ChemicalFile implements TrajectoryFile 
{
	private static final long serialVersionUID = 5207864145615655898L;
	private int numberOfFrames;
	
	/**
	 * 
	 * @param localPath
	 * @throws IOException
	 */
	public AbstractTrajectoryFile(String localPath) throws IOException {
		super(localPath);
	}
	
	/**
	 * 
	 * @param localPath
	 * @param format
	 * @throws IOException
	 */
	public AbstractTrajectoryFile(String localPath, String format) throws IOException {
		super(localPath,format);
	}

	/**
	 * Get number of frames
	 */
	public int getNumberOfFrames() {
		return this.numberOfFrames;
	}
	
	/**
	 * Get metadata
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = new MetadataAVUList();
		if (super.getMetadata()!=null)
			metadata.addAll(super.getMetadata());
		if (numberOfFrames>0)
			metadata.add(new MetadataAVU(TrajectoryMetadata.TIME_STEP_COUNT, String.valueOf(numberOfFrames)));
		return metadata;
	}
}
