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

package edu.utah.bmi.ibiomes.parse.chem.charmm;

import java.io.IOException;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;

/**
 * CHARMM output file
 * @author Julien Thibault, University of Utah
 *
 */
public class CHARMMOutputFile extends ChemicalFile 
{
	private static final long serialVersionUID = -324534556122138484L;

	/**
	 * Default constructor for CHARMM log files.
	 * @param localPath Path to the file
	 * @throws Exception 
	 */
	public CHARMMOutputFile(String localPath) throws Exception
	{
		super(localPath, LocalFile.FORMAT_CHARMM_OUT);
		parseFile();
	}
	
	/**
	 * Get file metadata and CHARMM-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		MetadataAVUList metadata = super.getMetadata();
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
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a CHARMM output file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
		}
	}

}
