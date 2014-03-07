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
import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Compound;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;

/**
 * Structure Data Format (SDF) file
 * @author Julien Thibault, University of Utah
 *
 */
public class SDFFile extends AbstractTopologyFile {

	private static final long serialVersionUID = 4307917862849926777L;
	private String version = "";
	private String creator = null;
	private String comments = null;
	
	/**
	 * Load SDF file
	 * @param pathname File path
	 * @throws Exception 
	 */
	public SDFFile(String pathname) throws Exception {
		super(pathname, FORMAT_SDF);
		this.parseFile();
	}
	
	/**
	 * Get version
	 * @return Version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Get creator
	 * @return Creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * Get comments
	 * @return Comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Parse SDF file
	 * @throws Exception 
	 */
	private void parseFile() throws Exception
	{
		boolean isV3000 = false;
		String comments = "";
		String name = "";
		IBIOMESFileReader br = null;
		try{
			br = new IBIOMESFileReader(this);
			
			//get molecule name (line 1)
			name = br.readLine();
			
			//get creator/application (line 2)
			creator = br.readLine();
			
			//get comments (line 3)
			comments = br.readLine();
			
			//get counts line (line 4)
			String countsLine = br.readLine();
			String[] counts = countsLine.split("\\s+");
			//check version in last column
			version = counts[counts.length-1];
			if (version.toLowerCase().equals("v3000")){
				isV3000 = true;
			}
			
			List<Atom> atomList = null;
			if (isV3000){
				atomList = parseV3000File(br);
			}
			else {
				atomList = parseV2000File(br);
			}
			
			br.close();
			
			//create molecular system
			Compound molecule = new Compound(this.getName());
			molecule.addAtoms(atomList);
			molecule.setDescription(comments);
			molecule.setName(name);
			List<Molecule> molecules = new ArrayList<Molecule>();
			molecules.add(molecule);
			MolecularSystem system = new MolecularSystem(this.getName(), comments);
			system.setSoluteMolecules(molecules);
			system.setDefinitionFiles(this.getCanonicalPath());
			
			this.molecularSystems = new ArrayList<MolecularSystem>();
			this.molecularSystems.add(system);
			
			//set file description
			String description = name;
			if (comments.length()>0)
				description = description + ": " + comments;
			description = description.trim();
			this.setDescription(description);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an SDF file.");
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
	
	/**
	 * @throws IOException 
	 * 
	 */
	private List<Atom> parseV3000File(IBIOMESFileReader br) throws IOException
	{
		String line;
		boolean endFound = false;
		boolean startFound = false;
		List<Atom> atomList = new ArrayList<Atom>();
		
		//find start of atom list
		while (!startFound) {
			line = br.readLine().trim();
			if (line.endsWith("BEGIN ATOM"))
				startFound = true;
		}
		
		//read each atom line
		int i = 1;
		while ((line = br.readLine()) != null && !endFound)
		{
			line = line.trim();
			if (line.endsWith("END ATOM"))
				endFound = true;
			else {				
				String[] atom = line.split("\\s+");
				String atomName = atom[3];
				double x = Double.parseDouble(atom[4]);
				double y = Double.parseDouble(atom[5]);
				double z = Double.parseDouble(atom[6]);
				
				Atom a = new Atom(i, atomName, atomName);
				Coordinate3D coord = new Coordinate3D(x,y,z);
				a.setCoordinates(coord);
				
				atomList.add(a);
			}
			i++;
		}
		return atomList;
	}
	
	/**
	 * @throws IOException 
	 * 
	 */
	private List<Atom> parseV2000File(IBIOMESFileReader br) throws IOException
	{
		String line;
		boolean endFound = false;
		List<Atom> atomList = new ArrayList<Atom>();
		
		//read each atom line
		int i = 1;
		while ((line = br.readLine()) != null && !endFound)
		{
			line = line.trim();
			if (line.matches("[\\d\\s]+"))
				endFound = true;
			else 
			{
				String[] atom = line.split("\\s+");
				
				double x = Double.parseDouble(atom[0]);
				double y = Double.parseDouble(atom[1]);
				double z = Double.parseDouble(atom[2]);
				String atomName = atom[3];
				
				Atom a = new Atom(i, atomName, atomName);
				Coordinate3D coord = new Coordinate3D(x,y,z);
				a.setCoordinates(coord);
				
				atomList.add(a);
			}
			i++;
		}
		return atomList;
	}
	
	@Override
	public MetadataAVUList getMetadata() throws Exception{
		MetadataAVUList metadata = super.getMetadata();
		return metadata;
	}
}
