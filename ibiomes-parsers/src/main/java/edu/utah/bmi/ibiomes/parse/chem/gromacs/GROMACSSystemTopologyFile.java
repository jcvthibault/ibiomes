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

import java.util.ArrayList;
import java.util.List;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;
import edu.utah.bmi.ibiomes.topo.Ion;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;
import edu.utah.bmi.ibiomes.topo.Water;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;
import edu.utah.bmi.ibiomes.topo.bio.ResidueChainParser;

/**
 * GROMACS system topology file (.top). 
 * This topology file defines the entire system topology, 
 * either directly or by including .itp files (using the include file mechanism). 
 * It is an ASCII file which is read by grompp, which processes it and creates a 
 * binary topology (.tpr).
 * 
 * @author Julien Thibault, University of Utah
 *
 */
public class GROMACSSystemTopologyFile extends AbstractTopologyFile
{
	private static final long serialVersionUID = -799913500449118780L;
	
	/**
	 * Default constructor.
	 * @param pathname Topology file path.
	 * @throws Exception
	 */
	public GROMACSSystemTopologyFile(String pathname) throws Exception
	{
		super(pathname, LocalFile.FORMAT_GROMACS_TOP);
		parseFile();
	}
	
	/**
	 * Load GROMACS topology file
	 * @param filePath
	 * @throws Exception
	 */
	private void parseFile() throws Exception 
	{
	    MolecularSystem molecularSystem = new MolecularSystem();
	    List<Molecule> waters = new ArrayList<Molecule>();
	    List<Ion> ions = new ArrayList<Ion>();
	    
	    try{
		    List<GROMACSTopologySection> sections = GROMACSTopologyReader.readSections(new IBIOMESFileReader(this));
			List<Molecule> definedMolecules = GROMACSTopologyReader.readMolecules(sections);
			List<Molecule> molecules = new ArrayList<Molecule>();
		    
			//system information
			GROMACSTopologySection systemSection = GROMACSTopologyReader.getSystemSection(sections);
	    	if (systemSection!=null)
	    	{	
	    		if (systemSection.getLines().size()>0){
	    			String description = systemSection.getLines().get(0);
	    			this.setDescription(description);
	    			molecularSystem.setDescription(description);
	    		}
	    	}
	        
	    	//list of molecules and count
	    	GROMACSTopologySection moleculeListSection = GROMACSTopologyReader.getMoleculeListSection(sections);
	    	for (String line : moleculeListSection.getLines())
	    	{
	    		String[] values = line.split("\\s+");
				String moleculeName = values[0];
				int moleculeCount = Integer.parseInt(values[1]);
	
				//if water
				if (moleculeName.matches(ResidueChainParser.WATER_REGEX)){
					for (int i=0;i<moleculeCount;i++) {
						waters.add(new Water());
					}
				}
				//if ion
				else if (moleculeName.toUpperCase().matches("NA\\+?|CL\\-?|MG|K\\+?|CA|CU1?|ZN") )
				{
					for (int i=0;i<moleculeCount;i++) {
						//TODO pass the list of atoms instead of null
						ions.add(new Ion(moleculeName, null));
					}
				}
				//else check included molecule
				else {
					Molecule molecule = null;
					//check if molecule is defined
					for (Molecule defMolecule : definedMolecules){
						if (defMolecule.getName().toUpperCase().equals(moleculeName.toUpperCase())){
							molecule = defMolecule;
							break;
						}
					}
					//if no definition just use the name (empty topology)
					if (molecule==null){
						molecule = new Biomolecule(moleculeName);
					}
					for (int i=0;i<moleculeCount;i++) {
						molecules.add(molecule);
					}
				}
	    	}
	        molecularSystem.setIons(ions);
	        molecularSystem.setSolventMolecules(waters);
	        molecularSystem.setSoluteMolecules(molecules);
			molecularSystem.setDefinitionFiles(this.getCanonicalPath());
	        this.molecularSystems = new ArrayList<MolecularSystem>();
			this.molecularSystems.add(molecularSystem);
	    }
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			if (IBIOMESConfiguration.getInstance().isOutputToConsole())
				System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as a GROMACS System Topology file.");
			if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
				e.printStackTrace();
		}
	}
}
