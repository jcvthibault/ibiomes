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

package edu.utah.bmi.ibiomes.local.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorException;
import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;

/**
 * Test suite for Z-matrices and conversion to Cartesian coordinates
 * @author Julien Thibault, University of Utah
 *
 */
public class TestZMatrix
{
	private final Logger logger = Logger.getLogger(TestZMatrix.class);
	private final String[] filePaths = {
			 "acetaldehyde", "waterX2"
			};
	
	public TestZMatrix() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void testZMatrixToCartesian() throws Exception
	{
		for (int f=0; f<filePaths.length; f++)
		{
			String zmatFilePath = TestCommon.TEST_DATA_DIR + "/zmatrix/" + filePaths[f] + ".zmat";
			String coordFilePath = TestCommon.TEST_DATA_DIR + "/zmatrix/" + filePaths[f] + ".xyz";
			
			ArrayList<Atom> atomList = new ArrayList<Atom>();
			List<String[]> zMatrix = new ArrayList<String[]>();
			int a = 0;
			String line = null;
			String[] lineSplit = null;
			IBIOMESFileReader br = null;
        	
			br = new IBIOMESFileReader(new File(zmatFilePath));
			
	        while (( line = br.readLine()) != null)
	        {
	        	if (line.trim().length()>0){
	        		a++;
	        		lineSplit = line.trim().split("[ ]+");
	        		Atom atom = new Atom(a, lineSplit[0], lineSplit[0]);
	        		atomList.add(atom);
        			zMatrix.add(lineSplit);
	        	}
	        	else break;
	        }
	        br.close();
	        

			int first_atoms[] = new int[atomList.size()];
        	double distances[] = new double[atomList.size()];
        	int second_atoms[] = new int[atomList.size()];
        	double angles[] = new double[atomList.size()];
        	int third_atoms[] = new int[atomList.size()];
        	double dihedrals[] = new double[atomList.size()];
        	
        	for (a=1;a<atomList.size();a++)
        	{
        		lineSplit = zMatrix.get(a);
	        	first_atoms[a] = Integer.parseInt(lineSplit[1])-1;
        		//parse distances
        		double d = Double.parseDouble(lineSplit[2]);
        		distances[a] = d;
        		//parse angles
        		if (lineSplit.length>4){
        			second_atoms[a] = Integer.parseInt(lineSplit[3])-1;
        			double angle = Double.parseDouble(lineSplit[4]);
	        		angles[a] = angle;
        			//parse dihedrals
	        		if (lineSplit.length>6){
	        			third_atoms[a] = Integer.parseInt(lineSplit[5])-1;
	        			double dihedral = Double.parseDouble(lineSplit[6]);
	        			dihedrals[a] = dihedral;
	        		}
        		}
	        }
        	
			//get coordinates
        	Coordinate3D[] coords = Coordinate3D.loadFromZMatrix(distances, first_atoms, angles, second_atoms, dihedrals, third_atoms);
        	for (a=0;a<atomList.size();a++){
        		atomList.get(a).setCoordinates(coords[a]);
        		//System.out.println(atomList.get(a).getName() + " " + (float)coords[a].getX() + " " + (float)coords[a].getY() + " " + (float)coords[a].getZ());
        	}
		}
	}
}
