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

package edu.utah.bmi.ibiomes.parse.chem.gromacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Compound;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;
import edu.utah.bmi.ibiomes.topo.bio.DNA;
import edu.utah.bmi.ibiomes.topo.bio.NucleicAcid;
import edu.utah.bmi.ibiomes.topo.bio.Protein;
import edu.utah.bmi.ibiomes.topo.bio.RNA;
import edu.utah.bmi.ibiomes.topo.bio.Residue;
import edu.utah.bmi.ibiomes.topo.bio.ResidueChainParser;

/**
 * GROMACS topology reader
 * @author Julien Thibault, University of Utah
 *
 */
public class GROMACSTopologyReader {

	private final static String ION_REGEX = "NA\\+?|CL\\-?|MG|K\\+?|CA|CU1?|ZN";
	
	/**
	 * Load sections from GROMACS topology file
	 * @param fr File reader
	 * @return List of sections
	 * @throws Exception 
	 */
	public static List<GROMACSTopologySection> readSections(IBIOMESFileReader fr) throws Exception{
		
		String line = null;
	    List<GROMACSTopologySection> sections = new ArrayList<GROMACSTopologySection>();
	    GROMACSTopologySection section = null;
	    List<String> content = null;
		    
		try{	   
	        while (( line = fr.readLine()) != null)
	        {
	        	line = line.trim();
	        	
	        	//if section title
	        	if (line.matches("\\[ .* \\]"))
	        	{	
	        		if (section != null){
	        			section.setLines(content);
	        			sections.add(section);
	        		}
	        		String sectionTitle = line.substring(1, line.length()-1).trim() ;
	        		section = new GROMACSTopologySection(sectionTitle);
	        		content = new ArrayList<String>();
		        }
	        	//if regular line
	        	else if (line.length()>0 
	        			&& !line.startsWith(";") 
	        			&& !line.startsWith("#") ){
	        		content.add(line);
	        	}
	        }
	        if (section != null){
				section.setLines(content);
				sections.add(section);
			}
	        fr.close();
	        return sections;
		}
		catch (Exception e){
			if (fr!=null)
				try {
					fr.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}
	
	/**
	 * Load atom definitions from GROMACS topology file sections
	 * @param sections List of sections
	 * @return List of atoms
	 * @throws IOException
	 */
	public static List<Atom> readAtomDefintions(List<GROMACSTopologySection> sections) throws IOException 
	{
		List<Atom> atoms = new ArrayList<Atom>();
		int s = 0;
		while (s<sections.size())
		{
			GROMACSTopologySection section = sections.get(s);
			if (section.getName().toLowerCase().equals("atomtypes"))
			{
				//TODO
			}
		}
		return atoms;
	}
	
	/**
	 * Load molecules from GROMACS topology file sections
	 * @param sections List of sections
	 * @return List of molecules
	 * @throws IOException
	 * @throws CompressorException 
	 */
	public static List<Molecule> readMolecules(List<GROMACSTopologySection> sections) throws IOException, CompressorException 
	{
		List<Molecule> molecules = new ArrayList<Molecule>();
		int s = 0;
		while (s<sections.size())
		{
			GROMACSTopologySection section = sections.get(s);
			if (section.getName().toLowerCase().equals("moleculetype"))
			{
				//get molecule name
				String header = section.getLines().get(0);
				String[] headerValues = header.split("\\s+");
				String moleculeName = headerValues[0];
				
				//if not ion or water
				if (!moleculeName.matches(ResidueChainParser.WATER_REGEX)
					&& !moleculeName.toUpperCase().matches(ION_REGEX) )
				{
					//get next section (atoms)
					s++;
					GROMACSTopologySection atomSection = sections.get(s);
					if (!atomSection.getName().toLowerCase().equals("atoms")){
						throw new IOException("[ atoms ] section expected after [ moleculetype ] section.");
					}
					else
					{						
						List<Residue> residues = new ArrayList<Residue>();
						List<Atom> atoms = new ArrayList<Atom>();
						Residue currResidue = null;
						int currResId = -1;
						
						for (String line : atomSection.getLines())
						{
							String[] values = line.split("\\s+");
	        				int id = Integer.parseInt(values[0]);
	        				String atomtype = values[1];
	        				int resId = Integer.parseInt(values[2]);
	        				String resName = values[3];
	        				String atomName = values[4];
	        				String cgnr = values[5];
	        				float charge = 0.0f;
	        				double mass = 0.0;
	        				try{
	        					if (values.length>6)
	        						charge = Float.parseFloat(values[6]);
	        					if (values.length>7)
	        						mass = Double.parseDouble(values[7]);
	        				}
	        				catch (NumberFormatException e){
	        				}
	    					//create atom
	        				//TODO dictionary of GROMACS atom types? 
	        				atomtype = atomtype.substring(0,1);
	    					Atom atom = new Atom(id, atomName, charge, mass, atomtype);
	    					atoms.add(atom);
	    					
	    					//create new residue and/or add atom to residue
	    					if (resId != currResId){
	    						currResidue = new Residue(resName);
	    						residues.add(currResidue);
	    						currResId = resId;
	    						//currResidue.setIsStandard();
	    					}
	    					currResidue.addAtom(atom);
						}
						//detect molecule type from residue chain
						ResidueChainParser chainParser = new ResidueChainParser(residues);
						List<String> types = chainParser.getMoleculeTypes();
						Molecule m = null;
						if (types!=null && types.size()>0)
						{
							String molType = types.get(0);
							if(molType.equals(MolecularSystem.TYPE_NUCLEIC_ACID)){
								m = new NucleicAcid(moleculeName, residues);
							}
							else if(molType.equals(MolecularSystem.TYPE_RNA)){
								m = new RNA(moleculeName, residues);
							}
							else if(molType.equals(MolecularSystem.TYPE_DNA)){
								m = new DNA(moleculeName, residues);
							}
							else if(molType.equals(MolecularSystem.TYPE_PROTEIN)){
								m = new Protein(moleculeName, residues);
							}
							else {
								m = new Compound(moleculeName, atoms);
							}
						}
						else{
							m = new Compound(moleculeName, atoms);
						}
						
						//create molecule
				    	molecules.add(m);
					}
				}
				else //water or ion
				{
				}
			}
			s++;
		}
		return molecules;
	}
	
	/**
	 * Get GROMACS topology file section related to system information
	 * @param sections List of sections
	 * @return System section
	 */
	public static GROMACSTopologySection getSystemSection(List<GROMACSTopologySection> sections){
		int s = 0;
		while (s<sections.size())
		{
			if (sections.get(s).getName().toLowerCase().equals("system"))
				return sections.get(s);
			s++;
		}
		return null;
	}
	
	/**
	 * Get GROMACS topology file section related to molecule information
	 * @param sections List of sections
	 * @return Molecules section
	 */
	public static GROMACSTopologySection getMoleculeListSection(List<GROMACSTopologySection> sections){
		int s = 0;
		while (s<sections.size())
		{
			if (sections.get(s).getName().toLowerCase().equals("molecules"))
				return sections.get(s);
			s++;
		}
		return null;
	}
}
