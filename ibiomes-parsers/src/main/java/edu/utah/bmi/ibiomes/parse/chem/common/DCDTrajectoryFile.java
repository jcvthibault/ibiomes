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

package edu.utah.bmi.ibiomes.parse.chem.common;

import java.io.IOException;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;

/**
 * DCD trajectory file (used in CHARMM and NAMD).
 * @author Julien Thibault
 *
 */
public class DCDTrajectoryFile extends ChemicalFile 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1302934223068675594L;

	/**
	 * Default constructor for DCD trajectory files.
	 * @param localPath Path to the file
	 * @throws Exception 
	 */
	public DCDTrajectoryFile(String localPath) throws Exception
	{
		super(localPath, FORMAT_DCD_TRAJECTORY);
	}
	
	/**
	 * Default constructor for DCD trajectory files.
	 * @param localPath Path to the file
	 * @param parse Parse flag
	 * 
	 * @throws Exception 
	 */
	public DCDTrajectoryFile(String localPath, boolean parse) throws Exception
	{
		super(localPath, FORMAT_DCD_TRAJECTORY);
		if (parse)
			parseFile();
	}
	
	/**
	 * Get file metadata and DCD-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		//get analysis file metadata
		MetadataAVUList metadata = super.getMetadata();
		
		//get GROMACS-specific metadata
		//metadata.add(new MetadataAVU(BiosimMetadata.TOTAL_MOLECULE_CHARGE, _charge));
		
		return metadata;
	}
	
	private void parseFile() throws Exception{
		IBIOMESFileReader br = null;
	    try{
	    	br = new IBIOMESFileReader(this);
			//TODO
			br.close(); 
		}
	    catch (Exception e){
	    	this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a DCD trajectory file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}

}
