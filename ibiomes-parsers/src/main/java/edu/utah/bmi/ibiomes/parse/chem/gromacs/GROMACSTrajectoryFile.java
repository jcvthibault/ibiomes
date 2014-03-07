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
 * GROMACS output file (log).
 * @author Julien Thibault
 *
 */
public class GROMACSTrajectoryFile extends ChemicalFile 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1302934223068675594L;

	/**
	 * Default constructor for GROMACS log files.
	 * @param localPath Path to the file
	 * @throws IOException
	 */
	public GROMACSTrajectoryFile(String localPath) throws IOException
	{
		super(localPath, FORMAT_GROMACS_TRAJ);
		parseFile();
	}

	/**
	 * Get file metadata and GROMACS-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		MetadataAVUList metadata = super.getMetadata();
		
		//get GROMACS-specific metadata
		//metadata.add(new MetadataAVU(BiosimMetadata.TOTAL_MOLECULE_CHARGE, _charge));
		
		return metadata;
	}
	
	private void parseFile(){
		
	}

}
