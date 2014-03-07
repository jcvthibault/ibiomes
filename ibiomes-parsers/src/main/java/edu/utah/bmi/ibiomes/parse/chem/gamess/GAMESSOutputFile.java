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

package edu.utah.bmi.ibiomes.parse.chem.gamess;

import java.io.IOException;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractProcessGroupSetFile;

/**
 * GAMESS output file (.out).
 * @author Julien Thibault, University of Utah
 *
 */
public class GAMESSOutputFile extends AbstractProcessGroupSetFile
{
	private static final long serialVersionUID = 1532971664069121615L;

	/**
	 * Default constructor for GAMESS output files.
	 * @param localPath Path to the file
	 * @throws Exception 
	 */
	public GAMESSOutputFile(String localPath) throws Exception
	{
		super(localPath, LocalFile.FORMAT_GAMESS_OUTPUT);
		parseFile();
	}
	
	/**
	 * Get file metadata and GAMESS-specific metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		
		//get file metadata
		MetadataAVUList metadata = super.getMetadata();
		
		//get GAMESS-specific metadata
		//metadata.add(new MetadataAVU(BiosimMetadata.TOTAL_MOLECULE_CHARGE, _charge));
		
		return metadata;
	}
	
	private void parseFile() throws Exception{

	/*	
	      BASIS OPTIONS
	     -------------
	     GBASIS=N31          IGAUSS=       6      POLAR=POPN31  
	     NDFUNC=       1     NFFUNC=       0     DIFFSP=       F
	     NPFUNC=       1      DIFFS=       F     BASNAM=        
     
	     RUN TITLE
	     ---------
	     S3 anion ROHF with MP2 and the HW (plus d and diffuse) basis and ECP
	 
		 TOTAL NUMBER OF BASIS SET SHELLS             =   18
		 NUMBER OF CARTESIAN GAUSSIAN BASIS FUNCTIONS =   54
		 NUMBER OF ELECTRONS                          =   49
		 CHARGE OF MOLECULE                           =   -1
		 SPIN MULTIPLICITY                            =    2
		 NUMBER OF OCCUPIED ORBITALS (ALPHA)          =   25
		 NUMBER OF OCCUPIED ORBITALS (BETA )          =   24
		 TOTAL NUMBER OF ATOMS                        =    3
		 
	*/
		IBIOMESFileReader br = null;
	    try{
			br = new IBIOMESFileReader(this);
		
			
			br.close(); 

			//build process groups

    		/*ExperimentProcess process = new ExperimentProcess(null, null, tasks);
    		ExperimentProcessGroup processGroup = new ExperimentProcessGroup(null, null, molecularSystem, process);
    		this.processGroups = new ArrayList<ExperimentProcessGroup>();
    		this.processGroups.add(processGroup);*/
		}
	    catch (Exception e){
	    	this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a GAMESS output file.");
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
