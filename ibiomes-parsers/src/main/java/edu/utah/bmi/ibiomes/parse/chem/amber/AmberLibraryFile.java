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

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;

/**
 * AMBER OFF library file (.lib/.off)
 * @author Julien Thibault
 *
 */
public class AmberLibraryFile extends ChemicalFile {

	private static final long serialVersionUID = 8539362376601626297L;
	
	public AmberLibraryFile(String localPath) throws Exception {
		super(localPath, FORMAT_AMBER_OFF);
		try {
			parseFile();
		} catch (Exception e) {
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an AMBER library file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Parse AMBER library file
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader inputReader = new IBIOMESFileReader(this);
		try{
		    String line = null;
		    line = inputReader.readLine();
	        inputReader.close();
	        
		    if (line == null || line.trim().length()==0 
		    		|| !line.trim().startsWith("!!index array str"))
		    {
		    	throw new Exception("Header for AMBER library files not found in '"+this.getAbsolutePath()+"'.");
		    }
		}
		catch (Exception e){
			try {
				if (inputReader!=null)
					inputReader.close();
			} catch (Exception e1) {
			}
			throw e;
		}
	}
	
	/**
	 * Get general metadata and Amber topology-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
}
