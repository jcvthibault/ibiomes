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
 * AMBER Leap log (leap.log). This file contains information about the force field 
 * parameter sets that were used to generate the parmtop file.
 * @author Julien Thibault
 *
 */
public class AmberLeapLogFile extends ChemicalFile {

	private static final long serialVersionUID = 8539362376601626297L;
	
	public AmberLeapLogFile(String localPath) throws Exception {
		super(localPath, FORMAT_AMBER_LEAP_LOG);
		try {
			parseFile();
		} catch (Exception e) {
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an AMBER Leap log file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * Parse AMBER Leap log file
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
		IBIOMESFileReader inputReader = new IBIOMESFileReader(this);
		try{
		    String line = null;
		    line = inputReader.readLine();
		    if (line == null || line.trim().length()==0 
		    		|| !line.trim().startsWith("log started:"))
		    {
		        inputReader.close();
		    	throw new Exception("Header for AMBER Leap log files not found in '"+this.getAbsolutePath()+"'.");
		    }
		    else
		    {
		        /*while (( line = inputReader.readLine()) != null)
		        {
		        	line = line.trim();
		        	if (!line.startsWith("!") && line.indexOf('=')>0)
		        	{
		        		
		    		}
		        }*/
		        inputReader.close();
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
	
	@Override
	public MetadataAVUList getMetadata() throws Exception {
		
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
}
