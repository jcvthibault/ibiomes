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

import java.io.IOException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ChemicalFile;
import edu.utah.bmi.ibiomes.parse.chem.TrajectoryFile;

/**
 * AMBER restart file (atom coordinates + velocity)
 * @author Julien Thibault
 *
 */
public class AmberRestartFile extends ChemicalFile implements TrajectoryFile {

	private static final long serialVersionUID = -5577978145409702898L;

	public AmberRestartFile(String localPath) throws IOException {
		super(localPath, FORMAT_AMBER_RESTART);
		// TODO Auto-generated constructor stub
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
	

	/**
	 * Get the number of frames stored in this file
	 */
	public int getNumberOfFrames() {
		return 1;
	}

	public static boolean checkFormat(String path) {
	/*char buffer2[BUF_SIZE];
	  // Assume file set up for read
	  if (OpenFile()) return false;
	  IO->Gets(buffer2, BUF_SIZE); // Title
	  IO->Gets(buffer2, BUF_SIZE); // natom, [time, temp]
	  CloseFile();
	  // Check for an integer (I5) followed by 0-2 scientific floats (E15.7)
	  if (strlen(buffer2)<=36) {
	    //mprintf("DEBUG: Checking restart.\n");
	    //mprintf("DEBUG: buffer2=[%s]\n",buffer2);
	    int i=0;
	    for (; i<5; i++) {
	      if (!isspace(buffer2[i]) && !isdigit(buffer2[i])) break;
	      //mprintf("DEBUG:    %c is a digit/space.\n",buffer2[i]);
	    }
	    //mprintf("DEBUG: i=%i\n");
	    //if ( i==5 && strchr(buffer2,'E')!=NULL ) {
	    if ( i==5 ) {
	      if (debug_>0) mprintf("  AMBER RESTART file\n");
	      return true;
	    }
	  }*/
	  return false;
	}
	
	private void parse()
	{
		IBIOMESFileReader br = null;
		try{
			
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			System.out.println("ERROR: cannot parse '"+this.getAbsolutePath()+"' as an AMBER restart file.");
			e.printStackTrace();
			if (br!=null){
				try{
					br.close();
				} catch(Exception e2){
					e.printStackTrace();
				}
			}
		}
	}
}
