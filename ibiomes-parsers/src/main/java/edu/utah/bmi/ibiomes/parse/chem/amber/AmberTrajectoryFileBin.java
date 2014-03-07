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
import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTrajectoryFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.Trajectory;

/**
 * AMBER binary trajectory file
 * @author Julien Thibault
 *
 */
public class AmberTrajectoryFileBin extends AbstractTrajectoryFile
{
	private static final long serialVersionUID = 7539188574632577402L;
	
	/**
	 * Load an AMBER trajectory file
	 * @param path
	 * @throws IOException 
	 */
	public AmberTrajectoryFileBin(String path) throws IOException
	{
		super(path, FORMAT_AMBER_TRAJ_BIN);
	}
	
	/**
	 * Retrieve atom trajectories
	 * @param atomList
	 * @return Atom trajectories
	 * @throws Exception
	 */
	public List<Trajectory> getTrajectories(ArrayList<Atom> atomList) throws Exception 
	{
		//ArrayList<AMBERFileSection> frames = new ArrayList<AMBERFileSection>();
	    IBIOMESFileReader br = null;
	    
	    /*System.out.println("================================================");
		System.out.println("TRAJECTORIES");
		System.out.println("================================================");*/
		
	    int coordLength = 8; 
	    int shift = 0;
	    int nAtoms = atomList.size();
	    
	    ArrayList<Trajectory> trajectories = new ArrayList<Trajectory>();
	    
	    try{
		    //for each atom
		    for (int a=0;a<nAtoms;a++)
		    {
		    	br = new IBIOMESFileReader(this);
		    	//skip title line and mark
			    br.readLine();
			    
			    //skip to access coordinates of current atom
			    br.skip(shift);
			    
			    boolean atomDone = false;
			    int f = 0;
			    
			    //System.out.println("Atom #" + (a+1) + "...");
			    
			    int shiftForNl = 0;
			    int shiftForNlSkip = 0;
			    
			    //create new trajectory for current atom
			    Trajectory traj = new Trajectory();
			    
			    //while all the frames for the current atom have not been collected
			    while (!atomDone){
				    	
			    	shiftForNl = 0;
			    	if ((3*a + 1) % 10 == 0){
			    		shiftForNl = 1;
			    		shiftForNlSkip = 1;
			    	}
			    	char[] cbufX = new char[coordLength + shiftForNl];
			    	if (br.read(cbufX) < 0)
			    		break;
			    	String sbufX = new String(cbufX);
			    	double x = Double.parseDouble(sbufX.substring(0,coordLength).trim());
			    	
			    	shiftForNl = 0;
			    	if ((3*a + 2) % 10 == 0){
			    		shiftForNl = 1;
			    		shiftForNlSkip = 1;
			    	}
			    	char[] cbufY = new char[coordLength + shiftForNl];
			    	br.read(cbufY);
			    	String sbufY = new String(cbufY);
			    	double y = Double.parseDouble(sbufY.substring(0,coordLength).trim());
			    	
			    	shiftForNl = 0;
			    	if ((3*a + 3) % 10 == 0){
			    		shiftForNl = 1;
			    		shiftForNlSkip = 1;
			    	}
			    	char[] cbufZ = new char[coordLength + shiftForNl];
			    	br.read(cbufZ);
			    	String sbufZ = new String(cbufZ);
			    	double z = Double.parseDouble(sbufZ.substring(0,coordLength).trim());
			    	
			    	Coordinate3D coord = new Coordinate3D(x, y, z);
			    	traj.addCoordinates(coord);
			    	//System.out.println(x + "," + y + "," + z);
			    	
			    	int nSkip = coordLength*(nAtoms-1)*3;
			    	int nNewLineChars = (int)Math.ceil((double)nSkip/((double)(10*coordLength))) + 1;
			    	nSkip = nSkip + nNewLineChars - shiftForNlSkip;
			    	br.skip(nSkip);
		        	
			    	
			    	f++;
		    	}
			    br.close();
			    
			    //save trajectory file for current atom
			    //traj.store("trajectories/test_" + a + ".traj");
			    //traj.storeXML("trajectories/test_" + a + ".xml");
			    
			    trajectories.add(traj);
			    
		    	shift += coordLength*3 + shiftForNlSkip;
			}
	        return trajectories;
	    }
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			System.out.println("ERROR: cannot parse '"+this.getAbsolutePath()+"' as an AMBER trajectory file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
			if (br!=null){
				try{
					br.close();
				} catch(Exception e2){
				}
			}
			throw e;
		}
	}

	public static boolean checkFormat(String path) {
		
		/*char buffer2[BUF_SIZE];
		  // File must already be set up for read
		  if (OpenFile()) return false;
		  IO->Gets(buffer2, BUF_SIZE); // Title
		  IO->Gets(buffer2, BUF_SIZE); // REMD header/coords
		  CloseFile();
		  // Check if second line contains REMD/HREMD, Amber Traj with REMD header
		  if ( IsRemdHeader( buffer2 ) ) {
		    if (debug_>0) mprintf("  AMBER TRAJECTORY with (H)REMD header.\n");
		    hasREMD_ = REMD_HEADER_SIZE;
		    hasTemperature_ = true;
		    return true;
		  }
		  // Check if we can read at least 3 coords of width 8, Amber trajectory
		  float TrajCoord[3];
		  if ( sscanf(buffer2, "%8f%8f%8f", TrajCoord, TrajCoord+1, TrajCoord+2) == 3 )
		  {
		    if (debug_>0) mprintf("  AMBER TRAJECTORY file\n");
		    return true;
		  }*/

		return false;
	}
}
