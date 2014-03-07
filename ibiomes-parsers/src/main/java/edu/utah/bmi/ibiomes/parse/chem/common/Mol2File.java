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
import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Coordinate3D;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.bio.Residue;
import edu.utah.bmi.ibiomes.topo.comp.AmberAtomTypeDictionary;
import edu.utah.bmi.ibiomes.topo.comp.CharmmAtomTypeDictionary;

/**
 * Mol2 (Sybyl, Tripos) file
 * @author Julien Thibault, University of Utah
 *
 */
public class Mol2File extends AbstractTopologyFile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 44269446853646598L;
	
	private String moleculeType = null;
	private String program = null;
	private String[] counts = null;
	
	/**
	 * Load Mol2 file
	 * @param pathname File path
	 * @throws Exception 
	 * @throws CDKException 
	 */
	public Mol2File(String pathname) throws Exception {
		super(pathname, FORMAT_MOL2);
		this.parseFile();
	}
	
	/**
	 * Parse Mol2 file
	 * @throws Exception 
	 * @throws IOException
	 */
	private void parseFile() throws Exception
	{
		String comments = "";
		String name = "";
		IBIOMESFileReader br = null;
	    try{
	    	br = new IBIOMESFileReader(this);
		
			String line = "";
			boolean found = false;
			while (!found){
				line = br.readLine();
				if (line == null)
					return;
				else if (line.trim().matches("@<TRIPOS>MOLECULE"))
					found = true;
				else if (line.startsWith("#")){
					comments += line + "\n";
				}
			}
			
			//get molecule name (line 1)
			name = br.readLine();
			//get counts line (line 2)
			String countsLine = br.readLine();
			this.counts = countsLine.split("\\s+");
			//get creator/application (line 3)
			this.moleculeType = br.readLine();
			//get creator/application (line 4)
			this.program = br.readLine();
			
			//read atom info
			List<Residue> residues = parseRecordData(br);
			
			br.close();
			
			//create molecule
			MolecularSystem molecularSystem = new MolecularSystem(residues);
			molecularSystem.setDescription(comments);
			molecularSystem.setName(name);
			molecularSystem.setDefinitionFiles(this.getCanonicalPath());
			this.molecularSystems = new ArrayList<MolecularSystem>();
			this.molecularSystems.add(molecularSystem);
			
			//set file description
			if (comments.length()>0)
				this.setDescription(name + " - " + comments.trim());
			else 			
				this.setDescription(name);
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a Mol2 file.");
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
	private List<Residue> parseRecordData(IBIOMESFileReader br) throws IOException
	{
		String line;
		boolean endFound = false;
		boolean startFound = false;
		
		//find start of atom list
		while (!startFound) {
			line = br.readLine().trim();
			if (line.matches("@<TRIPOS>ATOM"))
				startFound = true;
		}
		
		int i = 1;
		int currResId = -1;
		ArrayList<Residue> residues = new ArrayList<Residue>();
		Residue currResidue = null;

		//read each atom line
		while ((line = br.readLine()) != null && !endFound)
		{
			line = line.trim();
			if (line.matches("@<TRIPOS>BOND"))
				endFound = true;
			else {				
				String[] atomRecord = line.split("\\s+");
				String atomName = atomRecord[1];
				String atomType = atomRecord[5];
				double x = Double.parseDouble(atomRecord[2]);
				double y = Double.parseDouble(atomRecord[3]);
				double z = Double.parseDouble(atomRecord[4]);
				int resId = Integer.parseInt(atomRecord[6]);
				String resName = atomRecord[7];
				float charge = Float.parseFloat(atomRecord[8]);
				
				//try to find element based on AMBER dictionary
				AmberAtomTypeDictionary amberAtomTypeDefs = AmberAtomTypeDictionary.getInstance();
				AtomicElement element = amberAtomTypeDefs.getElementForAtomType(atomType);
				//try to find element based on CHARMM dictionary
				if (element == null){
					CharmmAtomTypeDictionary charmmAtomTypeDefs = CharmmAtomTypeDictionary.getInstance();
					element = charmmAtomTypeDefs.getElementForAtomType(atomType);
				}
				if (element == null){
					//TODO check other dictionaries
				}
				
				Atom a = new Atom(i, atomName, charge, 0, atomType, element);
				Coordinate3D coord = new Coordinate3D(x,y,z);
				a.setCoordinates(coord);
				
				if (resId != currResId){
					currResidue = new Residue(resName);
					residues.add(currResidue);
					currResId = resId;
				}
				currResidue.addAtom(a);
			}
			i++;
		}
		
		
		//find start of residue list
		startFound = false;
		while (!startFound) {
			line = br.readLine().trim();
			if (line.matches("@<TRIPOS>SUBSTRUCTURE"))
				startFound = true;
		}
		if (startFound)
		{
			//read each residue line (substructure)
			i=0;
			endFound = false;
			try {
				while ((line = br.readLine()) != null && !endFound)
				{
					line = line.trim();
					if (line.startsWith("@<TRIPOS>") || line.length()==0)
						endFound = true;
					else {				
						String[] residueRecord = line.split("\\s+");
						String resId = residueRecord[1];
						String resCode = residueRecord[6];
		
						//update residue names
						if (!resCode.equals("****")){
							for (Residue res : residues){
								if (res.getCode().equals(resId)){
									res.setCode(resCode);
								}
							}
						}
					}
					i++;
				}
			} catch (Exception e){
				System.out.println("ERROR: an error occured when parsing file '" + this.getAbsolutePath() + "'");
			}
		}
		return residues;
	}

	/**
	 * Get molecule type field
	 * @return Molecule type field
	 */
	public String getMoleculeType() {
		return moleculeType;
	}

	/**
	 * Set molecule type field
	 * @param moleculeType Molecule type field
	 */
	public void setMoleculeType(String moleculeType) {
		this.moleculeType = moleculeType;
	}

	/**
	 * Get program
	 * @return Program
	 */
	public String getProgram() {
		return program;
	}

	/**
	 * Set program
	 * @param program Program
	 */
	public void setProgram(String program) {
		this.program = program;
	}
	
	/**
	 * Get counts
	 * @return Counts
	 */
	public String[] getCounts(){
		return this.counts;
	}
}
