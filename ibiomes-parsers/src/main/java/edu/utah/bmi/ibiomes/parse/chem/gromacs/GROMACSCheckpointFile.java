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

import java.io.IOException;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;

/**
 * GROMACS checkpoint file (.cpt). A .cpt file is produced by mdrun at 
 * specified intervals (mdrun -cpt n, where n is the interval in minutes), 
 * and contains information on all the state variables in a simulated 
 * system.  In the case of a crash (hardware failure, power outage, etc), 
 * a checkpoint file can be used to resume the simulation exactly as it 
 * was before the failure.  Simulations can also be extended using a 
 * checkpoint file.
 * 
 * @author Julien Thibault
 *
 */
public class GROMACSCheckpointFile extends ChemicalFile {

	private static final long serialVersionUID = 8599479410825940047L;

	public GROMACSCheckpointFile(String localPath) throws IOException {
		super(localPath, FORMAT_GROMACS_RESTART);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get general metadata and GROMACS topology-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
}
