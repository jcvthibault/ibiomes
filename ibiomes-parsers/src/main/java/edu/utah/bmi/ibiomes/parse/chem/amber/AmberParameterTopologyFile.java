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
import edu.utah.bmi.ibiomes.conf.IBIOMESExecutionTimeSummary;
import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;
import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Bond;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.MolecularSystemFactory;
import edu.utah.bmi.ibiomes.topo.bio.Residue;
import edu.utah.bmi.ibiomes.topo.comp.AmberAtomTypeDictionary;

/**
 * AMBER topology file (parmtop)
 * @author Julien Thibault
 *
 */
public class AmberParameterTopologyFile extends AbstractTopologyFile
{
	private static final long serialVersionUID = -8329761308485275213L;
	private float AMBER_CHARGE_CONSTANT = 18.2223f;

	private static final String PARMTOP_SECTION_POINTERS 				= "POINTERS";
	private static final String PARMTOP_SECTION_ATOM_NAME 				= "ATOM_NAME";
	private static final String PARMTOP_SECTION_CHARGE 					= "CHARGE";
	private static final String PARMTOP_SECTION_MASS 					= "MASS";
	private static final String PARMTOP_SECTION_ATOM_TYPE				= "AMBER_ATOM_TYPE";
	private static final String PARMTOP_SECTION_BOND_FORCE_CONSTANT		= "BOND_FORCE_CONSTANT";
	private static final String PARMTOP_SECTION_BOND_EQUIL_VALUE		= "BOND_EQUIL_VALUE";
	private static final String PARMTOP_SECTION_BONDS_INC_HYDROGEN		= "BONDS_INC_HYDROGEN";
	private static final String PARMTOP_SECTION_BONDS_WITHOUT_HYDROGEN	= "BONDS_WITHOUT_HYDROGEN";
	private static final String PARMTOP_SECTION_RESIDUE_POINTER			= "RESIDUE_POINTER";
	private static final String PARMTOP_SECTION_RESIDUE_LABEL			= "RESIDUE_LABEL";
	private static final String PARMTOP_SECTION_TITLE					= "TITLE";
	
	private static final String REGEX_SECTIONS_OF_INTEREST	= 
				  "("+PARMTOP_SECTION_ATOM_NAME+")|"
				+ "("+PARMTOP_SECTION_CHARGE+")|"
				+ "("+PARMTOP_SECTION_MASS+")|"
				+ "("+PARMTOP_SECTION_ATOM_TYPE+")|"
				+ "("+PARMTOP_SECTION_POINTERS+")|"
				+ "("+PARMTOP_SECTION_BOND_FORCE_CONSTANT+")|"
				+ "("+PARMTOP_SECTION_BOND_EQUIL_VALUE+")|"
				+ "("+PARMTOP_SECTION_BONDS_INC_HYDROGEN+")|"
				+ "("+PARMTOP_SECTION_BONDS_WITHOUT_HYDROGEN+")|"
				+ "("+PARMTOP_SECTION_RESIDUE_POINTER+")|"
				+ "("+PARMTOP_SECTION_RESIDUE_LABEL+")|"
				+ "("+PARMTOP_SECTION_TITLE+")";
	
	/**
	 * Default constructor. Load molecule topology.
	 * @param localPath Topology file path.
	 * @throws Exception
	 */
	public AmberParameterTopologyFile(String localPath) throws Exception {
		super(localPath, FORMAT_AMBER_PARMTOP);
		
		boolean timingsOn = (IBIOMESConfiguration.getInstance().hasCollectTimingsOn());
		long startTime = 0;
		if (timingsOn)
			startTime = System.currentTimeMillis();
		
		boolean success = parseFile();
		
		if (timingsOn){
			long endTime = System.currentTimeMillis();
			IBIOMESExecutionTimeSummary.getInstance().addExecutionTimingRecord(
					"AMBER parmtop", localPath, success, endTime - startTime);
		}
	}

	/**
	 * Parse file to load molecule topology
	 * @throws Exception
	 */
	private boolean parseFile()
	{
		try{
			ArrayList<AmberFileSection> topSections = loadAmberTopologyFile(this.getAbsolutePath());
			//load list of atoms
			ArrayList<Atom> atomList = loadAtoms(topSections);
			//add bond info (set list of bonded atoms for each atom)
			loadBonds(atomList, topSections);
			//load residue info and assign atoms
			ArrayList<Residue> residueList = loadResidues(atomList, topSections);
			
			//identify molecules
	        MolecularSystemFactory moleculeFactory = new MolecularSystemFactory();
	        MolecularSystem molecularSystem = moleculeFactory.identifyMoleculesUsingResidues(residueList, false);
	        
	        //set system description
			String title = getTopologyTitle(topSections);
			molecularSystem.setDescription(title);
			molecularSystem.setDefinitionFiles(this.getCanonicalPath());
				        
			this.molecularSystems = new ArrayList<MolecularSystem>();
			this.molecularSystems.add(molecularSystem);
			
			return true;
		}
		catch (Exception e){
			this.format = LocalFile.FORMAT_UNKNOWN;
			try {
				if (IBIOMESConfiguration.getInstance().isOutputToConsole())
					System.out.println("WARNING: cannot parse '"+this.getAbsolutePath()+"' as an AMBER parameter/topology file.");
				if (IBIOMESConfiguration.getInstance().isOutputErrorStackToConsole())
					e.printStackTrace();
			} catch (Exception e1) {
			}
			return false;
		}
	}
	
	/**
	 * Load Amber topology file
	 * @param topFilePath
	 * @return List of sections in the topology file
	 * @throws Exception
	 */
	private ArrayList<AmberFileSection> loadAmberTopologyFile(String topFilePath) throws Exception 
	{
		ArrayList<AmberFileSection> sections = new ArrayList<AmberFileSection>();
		StringBuilder contents = null;
	    String line = null;
	    String title = null;
	    String format = null;
	    
	    IBIOMESFileReader br = new IBIOMESFileReader(this);
		try{
			
		    line = br.readLine();
		    //System.out.println("Version: " + line.substring(9));
		    boolean isSectionOfInterest = false;
		    
	        while (( line = br.readLine()) != null){
	        	
	        	if (line.startsWith("%FLAG")) {
	        		
	        		if (contents != null) 
	        		{
	        			//store previous section
	        			AmberFileSection section = new AmberFileSection();
	            		section.setContent(contents.toString());
	            		section.setFormat(format.split("a|I|E"));
	            		if (format.indexOf('a')>0)
	            			section.setType("char");
	            		else if (format.indexOf('I')>0)
	            			section.setType("int");
	            		else if (format.indexOf('E')>0)
	            			section.setType("exp");
	            		else {
	            	        br.close();
	            			throw new Exception("Unknown type in format '"+ format+"'");
	            		}            		
	            		section.setValues(getValues(section.getContent(), section.getFormat()[1]));
	            		section.setTitle(title);
	            		sections.add(section);
	        		}
	        		
	        		//get section title
	        		title = line.substring(6).trim();
	        		//if section of interest initialize new section structure
	        		if (title.matches(REGEX_SECTIONS_OF_INTEREST))
	        		{
	        			isSectionOfInterest = true;
		        		contents = new StringBuilder();
		        		//get format line
		        		line = br.readLine();
		        		format = line.substring(7).trim();
		        		format = format.substring(1, format.length()-1);
	        		}
	        		else isSectionOfInterest = false;
	        	}
	        	else if (isSectionOfInterest){
		        	contents.append(line);
	        	}
	        }
	        br.close();
	        
	        //add last section
	        AmberFileSection section = new AmberFileSection();
	        section.setContent(contents.toString());
			section.setFormat(format.split("a|I|E"));
			if (format.indexOf('a')>0)
				section.setType("char");
			else if (format.indexOf('I')>0)
				section.setType("int");
			else if (format.indexOf('E')>0)
				section.setType("exp");
			else throw new Exception("Unknown type in format '"+ format+"'");
			
			section.setValues(getValues(section.getContent(), section.getFormat()[1]));
			section.setTitle(title);
			sections.add(section);	
			
			return sections;
		}
		catch (Exception e){
			try {
				if (br!=null)
					br.close();
			} catch (Exception e1) {
			}
			throw e;
		}
	}
	
	private List<String> getValues(String line, String nCharPerValue)
	{
		int i = 0;
		int indCom = nCharPerValue.indexOf('.');
		if (indCom > 0)
			nCharPerValue = nCharPerValue.substring(0,indCom);
		int shift = Integer.parseInt(nCharPerValue);
		
		ArrayList<String> values = new ArrayList<String>();
		while ((i + shift) <= line.length())
		{
			String value = line.substring(i, i + shift).trim();
			values.add(value);
			i = i + shift;
		}
		return values;
	}
	
	/**
	 * Create list of atoms and their properties
	 * @param topSections Topology-related sections
	 * @return List of atom objects
	 * @throws IOException 
	 */
	private ArrayList<Atom> loadAtoms(ArrayList<AmberFileSection> topSections) throws IOException
	{
		ArrayList<Atom> atomList = new ArrayList<Atom>();
		PeriodicTable table = PeriodicTable.getInstance(); 
		AmberAtomTypeDictionary atomTypeDefs = AmberAtomTypeDictionary.getInstance();
		
		AmberFileSection section = getSection(topSections, PARMTOP_SECTION_POINTERS);
		
		/*
		NATOM  : total number of atoms 
		NTYPES : total number of distinct atom types
		NBONH  : number of bonds containing hydrogen
		MBONA  : number of bonds not containing hydrogen
		NTHETH : number of angles containing hydrogen
		MTHETA : number of angles not containing hydrogen
		NPHIH  : number of dihedrals containing hydrogen
		MPHIA  : number of dihedrals not containing hydrogen
		NHPARM : currently not used
		NPARM  : currently not used
		NEXT   : number of excluded atoms
		NRES   : number of residues
		 */
		
		int nAtoms = Integer.parseInt(section.getValue(0));
		/*System.out.print("Number of atoms: ");
		System.out.println(nAtoms);
		System.out.print("Number of distinct atom types: ");
		System.out.println(section.values.get(1));
		System.out.print("Number of bonds containing hydrogen: ");
		System.out.println(section.values.get(2));
		System.out.print("Number of bonds not containing hydrogen: ");
		System.out.println(section.values.get(3));*/
		
		AmberFileSection sectionAtomNames = getSection(topSections, PARMTOP_SECTION_ATOM_NAME);
		AmberFileSection sectionAtomCharges = getSection(topSections,PARMTOP_SECTION_CHARGE);
		AmberFileSection sectionAtomMasses = getSection(topSections, PARMTOP_SECTION_MASS);
		AmberFileSection sectionAtomTypes = getSection(topSections, PARMTOP_SECTION_ATOM_TYPE);
		
		//check that number of atoms match
		if (nAtoms != sectionAtomNames.getValues().size()){
			System.out.println("Expected number of atoms: " + nAtoms + ". Actual: " + sectionAtomNames.getValues().size());
			throw new IOException("Expected number of atoms: " + nAtoms + ". Actual: " + sectionAtomNames.getValues().size());
		}
		
		for (int a=0; a<nAtoms; a++){
			String atomName = sectionAtomNames.getValue(a);
			float atomCharge = Float.parseFloat(sectionAtomCharges.getValue(a)) / AMBER_CHARGE_CONSTANT;
			double atomMass = Double.parseDouble(sectionAtomMasses.getValue(a));
			String atomType = sectionAtomTypes.getValue(a);
			AtomicElement element = null;
			//try to find element based on AMBER dictionary
			element = atomTypeDefs.getElementForAtomType(atomType);
			if (element == null)
				//try to find element based on name and mass
				element = table.getElementByMass(atomType, atomMass);
			//create atom object
			Atom atom = new Atom(a, atomName, 
					atomCharge,
					atomMass,
					atomType,
					element);
			atomList.add(atom);
		}
		return atomList;
	}
	
	/**
	 * Build list of bonds from amber file
	 * @param atomList List of atoms
	 * @param topSections Topology-related sections
	 * @return List of bond objects
	 */
	private ArrayList<Bond> loadBonds(ArrayList<Atom> atomList, ArrayList<AmberFileSection> topSections)
	{
		ArrayList<Bond> bondList = new ArrayList<Bond>();
		
		/*
		 	the atom numbers in the following arrays that describe bonds, angles, 
		 	and dihedrals are coordinate array indexes for runtime speed. The true 
		 	atom number equals the absolute value of the number divided by three, 
		 	plus one.
		 */

		AmberFileSection sectionBondForceCst = getSection(topSections, PARMTOP_SECTION_BOND_FORCE_CONSTANT);
		AmberFileSection sectionBondEquilVal = getSection(topSections, PARMTOP_SECTION_BOND_EQUIL_VALUE);
		
		AmberFileSection sectionBondsH = getSection(topSections, PARMTOP_SECTION_BONDS_INC_HYDROGEN);
		for (int b=0; b<sectionBondsH.getValues().size(); b=b+3)
		{
			int atom1Id = Integer.parseInt(sectionBondsH.getValue(b));
			int atom2Id = Integer.parseInt(sectionBondsH.getValue(b+1));
			
			Atom atom1 = atomList.get((atom1Id / 3 ) );
			Atom atom2 = atomList.get((atom2Id / 3 ) );
			int bondType = Integer.parseInt(sectionBondsH.getValue(b+2));
			double forceCst = Double.parseDouble(sectionBondForceCst.getValue(bondType - 1));
			double equilVal = Double.parseDouble(sectionBondEquilVal.getValue(bondType - 1));
			Bond bond = new Bond(atom1,atom2,bondType,forceCst,equilVal,true);
			bondList.add(bond);
			
			if (atom1.getBondedAtoms()==null)
				atom1.setBondedAtoms(new ArrayList<Atom>());
			if (atom2.getBondedAtoms()==null)
				atom2.setBondedAtoms(new ArrayList<Atom>());
			atom1.getBondedAtoms().add(atom2);
			atom2.getBondedAtoms().add(atom1);
		}
		
		AmberFileSection sectionBondsNoH = getSection(topSections, PARMTOP_SECTION_BONDS_WITHOUT_HYDROGEN);
		for (int b=0; b<sectionBondsNoH.getValues().size(); b=b+3)
		{
			int atom1Id = Integer.parseInt(sectionBondsNoH.getValue(b));
			int atom2Id = Integer.parseInt(sectionBondsNoH.getValue(b+1));
			
			Atom atom1 = atomList.get((atom1Id / 3 ) );
			Atom atom2 = atomList.get((atom2Id / 3 ) );
			int bondType = Integer.parseInt(sectionBondsNoH.getValue(b+2));
			double forceCst = Double.parseDouble(sectionBondForceCst.getValue(bondType - 1));
			double equilVal = Double.parseDouble(sectionBondEquilVal.getValue(bondType - 1));
			Bond bond = new Bond(atom1,atom2,bondType,forceCst,equilVal,true);
			bondList.add(bond);

			if (atom1.getBondedAtoms()==null)
				atom1.setBondedAtoms(new ArrayList<Atom>());
			if (atom2.getBondedAtoms()==null)
				atom2.setBondedAtoms(new ArrayList<Atom>());
			atom1.getBondedAtoms().add(atom2);
			atom2.getBondedAtoms().add(atom1);
		}
		
		/*System.out.println("================================================");
		System.out.println("BONDS");
		System.out.println("================================================");
		for (int b=0; b<bondList.size(); b++){
			
			Bond bond = bondList.get(b);
			Atom atom1 = bond.getAtoms().get(0);
			Atom atom2 = bond.getAtoms().get(1);
			System.out.println("BOND " + (b+1) + ": " + bond.toString());
		}*/
		
		return bondList;
	}
	
	/**
	 * Load list of residues
	 * @param atomList Atom list
	 * @param sections Sections
	 * @return List of residues
	 * @throws IOException 
	 */
	private ArrayList<Residue> loadResidues(ArrayList<Atom> atomList, ArrayList<AmberFileSection> sections) throws IOException
	{
		ArrayList<Residue> residueList = new ArrayList<Residue>();
		AmberFileSection sectionResiduePointers = getSection(sections, PARMTOP_SECTION_RESIDUE_POINTER);
		AmberFileSection sectionResidueLabels = getSection(sections, PARMTOP_SECTION_RESIDUE_LABEL);
		
		//check that number of residues match
		AmberFileSection section = getSection(sections, PARMTOP_SECTION_POINTERS);
		int nResidues = Integer.parseInt(section.getValue(11));
		if (nResidues != sectionResiduePointers.getValues().size()){
			System.out.println("Expected number of residues: " + nResidues + ". Actual: " + sectionResiduePointers.getValues().size());
			throw new IOException("Expected number of residues: " + nResidues + ". Actual: " + sectionResiduePointers.getValues().size());
		}
		
		for (int r=0; r<sectionResidueLabels.getValues().size(); r++)
		{
			//create new Residue
			String residueLabel = sectionResidueLabels.getValue(r);
			Residue residue = new Residue(residueLabel);

			int start = Integer.parseInt(sectionResiduePointers.getValue(r)) - 1;
			int end = atomList.size();
			if (r < sectionResidueLabels.getValues().size() - 1) 
				end = Integer.parseInt(sectionResiduePointers.getValue(r+1)) - 1;
			//add atoms
			for (int a=start; a<end; a++){
				residue.addAtom(atomList.get(a));
			}
			residueList.add(residue);
		}
		return residueList;
	}
		
	private String getTopologyTitle(ArrayList<AmberFileSection> sections){
		AmberFileSection sectionTitle = getSection(sections, PARMTOP_SECTION_TITLE);
		List<String> vals = sectionTitle.getValues();
		String title = "";
		for (String val : vals){
			title += val;
		}
		return title.trim();
	}
	
	/**
	 * Get section in the file
	 * @param sections List of sections
	 * @param title Section title
	 * @return Section
	 */
	private AmberFileSection getSection(ArrayList<AmberFileSection> sections, String title)
	{
		int i = 0;
		AmberFileSection section = null;
		while (i < sections.size())
		{
			if (sections.get(i).getTitle().compareTo(title)==0){
				return sections.get(i);
			}
			i++;
		}
		return section;
	}
	
}
