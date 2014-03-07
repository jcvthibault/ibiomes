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

package edu.utah.bmi.ibiomes.topo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.topo.bio.BiomoleculeFactory;
import edu.utah.bmi.ibiomes.topo.bio.Residue;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule.BiomoleculeType;
import edu.utah.bmi.ibiomes.topo.bio.Residue.ResidueType;

/**
 * Molecular system (structural information)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="molecularSystem")
public class MolecularSystem implements MetadataMappable
{
	/**
	 * Nucleic acid (DNA or RNA)
	 */
	public static final String TYPE_NUCLEIC_ACID = "Nucleic acid";
	/**
	 * DNA molecule
	 */
	public static final String TYPE_DNA = "DNA";
	/**
	 * RNA molecule
	 */
	public static final String TYPE_RNA = "RNA";
	/**
	 * Protein
	 */
	public static final String TYPE_PROTEIN = "Protein";
	/**
	 * Protein
	 */
	public static final String TYPE_COMPOUND = "Compound";
	
	private List<Molecule> solventMolecules;
	private List<Ion> ions;
	private List<Molecule> soluteMolecules;
	protected String name = null;
	protected String description = null;
	protected double apparentPH = 0.0;
	private List<String> definitionFiles;

	/**
	 * Create empty molecular system
	 */
	public MolecularSystem(){
		soluteMolecules = new ArrayList<Molecule>();
		solventMolecules = new ArrayList<Molecule>();
		ions = new ArrayList<Ion>();
	}
	
	/**
	 * Create molecular system
	 * @param name Name of the molecular system
	 */
	public MolecularSystem(String name, String description){
		this.name = name;
		this.description = description;
		soluteMolecules = new ArrayList<Molecule>();
		solventMolecules = new ArrayList<Molecule>();
		ions = new ArrayList<Ion>();
	}
	
	/**
	 * Get molecular system name
	 * @return molecular system name
	 */
	@XmlAttribute(name="name")
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set molecular system name
	 * @param name molecular system name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get molecular system description
	 * @return molecule description
	 */
	@XmlElement(name="description")
	public String getDescription(){
		return this.description;
	}
	
	/**
	 * Set molecular system description
	 * @param description molecular system description
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * Get apparent pH
	 * @return apparent pH
	 */
	public double getApparentPH() {
		return apparentPH;
	}

	/**
	 * Set apparent pH
	 * @param apparentPH apparent pH
	 */
	public void setApparentPh(double apparentPH) {
		this.apparentPH = apparentPH;
	}
	
	/**
	 * Get list of molecules present in the system
	 * @return List of molecules present in the system
	 */
	@XmlTransient
	public List<Molecule> getSoluteMolecules() {
		return soluteMolecules;
	}

	/**
	 * Set the list of molecules present in the system
	 * @param molecules List of molecules to add to the system
	 */
	public void setSoluteMolecules(List<Molecule> molecules) {
		this.soluteMolecules = molecules;
	}
	
	/**
	 * Set the molecule present in the system
	 * @param molecule Single molecule to add to the system
	 */
	public void setSoluteMolecules(Molecule molecule) {
		this.soluteMolecules = new ArrayList<Molecule>();
		this.soluteMolecules.add(molecule);
	}
	
	/**
	 * Get number of atoms in the system
	 * @return number of atoms in the system
	 */
	@XmlElement
	public int getAtomCount(){
		int atomCount = 0;
		
		if (this.soluteMolecules != null){
			for (Molecule m : this.soluteMolecules){
				atomCount += m.getAtomCount();
			}
		}
		if (this.ions != null){
			for (Ion i : this.ions){
				atomCount += i.getAtomCount();
			}
		}
		if (this.solventMolecules != null){
			atomCount += solventMolecules.size()*3;
		}
		return atomCount;
	}
	
	/**
	 * Get list of solvent molecules in the system
	 * @return list of solvent molecules
	 */
	@XmlTransient
	public List<Molecule> getSolventMolecules() {
		return solventMolecules;
	}

	/**
	 * Set solvent molecules of the system
	 * @param solvent Solvent molecules in the system
	 */
	public void setSolventMolecules(List<Molecule> solvent) {
		this.solventMolecules = solvent;
	}

	/**
	 * Get ions present in the system
	 * @return Ions present in the system
	 */
	@XmlTransient
	public List<Ion> getIons() {
		return ions;
	}

	/**
	 * Set ions
	 * @param ions List of ions in the system
	 */
	public void setIons(List<Ion> ions) {
		this.ions = ions;
	}
	
	/**
	 * Get number of molecules (other than solvent)
	 * @return number of molecules in the system
	 */
	@XmlElement
	public int getSoluteMoleculeCount(){
		return soluteMolecules.size();
	}
	
	/**
	 * Get number of solvent molecules in the system
	 * @return number of solvent molecules in the system
	 */
	@XmlElement
	public int getSolventMoleculeCount(){
		return solventMolecules.size();
	}
	
	/**
	 * Get number of ions
	 * @return number of ions in the system
	 */
	@XmlElement
	public int getIonCount(){
		return ions.size();
	}

	/**
	 * Get list of files defining this molecular system
	 * @return List of files
	 */
	@XmlElement(name="definitionFiles")
	@XmlElementWrapper(name="file")
	public List<String> getDefinitionFiles() {
		return definitionFiles;
	}
	
	/**
	 * Set list of files defining this molecular system
	 * @param definitionFiles List of files defining this molecular system
	 */
	public void setDefinitionFiles(List<String> definitionFiles) {
		this.definitionFiles = definitionFiles;
	}

	/**
	 * Set file defining this molecular system
	 * @param definitionFile Single file defining this molecular system
	 */
	public void setDefinitionFiles(String definitionFile) {
		this.definitionFiles = new ArrayList<String>();
		this.definitionFiles.add(definitionFile);
	}
	
	/**
	 * Get occurrences of different ions in the system
	 * @return List of ion occurrences
	 */
	@XmlElement(name="moleculeOccurrence")
	@XmlElementWrapper(name="ionOccurrences")
	public List<MoleculeOccurrence> getIonOccurrences(){
		HashMap<String, MoleculeOccurrence> occurences = new HashMap<String, MoleculeOccurrence>();
		List<MoleculeOccurrence> occurrenceList = new ArrayList<MoleculeOccurrence>();
		if (this.ions != null){
			for (Molecule molecule : ions){
				String name = molecule.getName();
				if (name!=null && occurences.containsKey(name)){
					occurences.get(name).addOccurrence();
				}
				else {
					occurences.put(name, new MoleculeOccurrence(molecule, 1));
				}
			}
		}
		for (MoleculeOccurrence occurrence : occurences.values()){
			occurrenceList.add(occurrence);
		}
		return occurrenceList;
	}
	
	/**
	 * Get occurrences of different molecules
	 * @return List of molecule occurrences
	 */
	private List<MoleculeOccurrence> getMoleculeOccurrences(List<Molecule> moleculeList) {
		HashMap<String, MoleculeOccurrence> occurences = new HashMap<String, MoleculeOccurrence>();
		List<MoleculeOccurrence> occurrenceList = new ArrayList<MoleculeOccurrence>();
		int m = 0;
		if (moleculeList != null){
			for (Molecule molecule : moleculeList){
				String name = molecule.getName();
				if (name!=null && name.length()>0 && occurences.containsKey(name)){
					occurences.get(name).addOccurrence();
				}
				else {
					if (name==null || name.length()==0)
						name = "molecule$" + m;
					occurences.put(name, new MoleculeOccurrence(molecule, 1));
				}
				m++;
			}
		}
		for (MoleculeOccurrence occurrence : occurences.values()){
			occurrenceList.add(occurrence);
		}
		return occurrenceList;
	}
	
	/**
	 * Get occurrences of different solute molecules in the system
	 * @return List of solute molecule occurrences
	 */
	public List<MoleculeOccurrence> getSoluteMoleculeOccurrences() {
		return getMoleculeOccurrences(this.soluteMolecules);
	}

	/**
	 * Get occurrences of different solvent molecules in the system
	 * @return List of molecule occurrences
	 */
	@XmlElement(name="moleculeOccurrence")
	@XmlElementWrapper(name="solventMoleculeOccurrences")
	public List<MoleculeOccurrence> getSolventMoleculeOccurrences() {
		return getMoleculeOccurrences(this.solventMolecules);
	}
	
	/**
	 * Build molecular system using list of residues
	 * @param residues List of residues
	 * @throws IOException 
	 */
	public MolecularSystem(List<Residue> residues) throws IOException {
		
		List<Ion> ions = new ArrayList<Ion>();
		List<Molecule> waters = new ArrayList<Molecule>();
		ArrayList<Molecule> molecules = new ArrayList<Molecule>();
		
		//first pass to extract water and ions
		for (Residue residue : residues){
			if (residue.getType() == ResidueType.ION){
				ions.add(new Ion(residue));
			}
			else if (residue.getType() == ResidueType.WATER){
				waters.add(new Water());
			}
		}
		this.setSolventMolecules(waters);
		
		int r=0;
		while ( r < residues.size() ){
			Residue residue = residues.get(r);
			if (residue.getType() == ResidueType.ION || residue.getType() == ResidueType.WATER){
				residues.remove(r);
			}
			else r++;
		}
		this.setIons(ions);
		
		//second pass to extract molecules of interest
		List<Residue> residueChain = new ArrayList<Residue>();
		BiomoleculeType moleculeType = BiomoleculeType.UNKNOWN;
		for (Residue residue : residues)
		{
			ResidueType currentType = residue.getType();
			
			if (currentType == ResidueType.TERMINAL) 
			{
				if (residueChain.size()>0)
					molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
				residueChain = new ArrayList<Residue>();
				moleculeType = BiomoleculeType.UNKNOWN;
			}
			
			else if (currentType == ResidueType.AMINO_ACID) 
			{
				if (moleculeType != BiomoleculeType.PROTEIN){
					if (residueChain.size()>0)
						molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
					residueChain = new ArrayList<Residue>();
					moleculeType = BiomoleculeType.PROTEIN;
				}
				residueChain.add(residue);
			}
			
			else if (currentType == ResidueType.NUCLEIC_ACID_BASE)
			{
				if (moleculeType == BiomoleculeType.PROTEIN){
					molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
					residueChain = new ArrayList<Residue>();
				}
				else if (moleculeType == BiomoleculeType.UNKNOWN){
					moleculeType = BiomoleculeType.NUCLEIC_ACID;
				}
				//else keep RNA or DNA
				residueChain.add(residue);
			}

			else if (currentType == ResidueType.RNA_BASE)
			{
				if (moleculeType == BiomoleculeType.PROTEIN){
					molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
					residueChain = new ArrayList<Residue>();
				}
				moleculeType = BiomoleculeType.RNA;
				residueChain.add(residue);
			}
			
			else if (currentType == ResidueType.DNA_BASE)
			{
				if (moleculeType == BiomoleculeType.PROTEIN){
					molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
					residueChain = new ArrayList<Residue>();
				}
				moleculeType = BiomoleculeType.DNA;
				residueChain.add(residue);
			}
			
			else //unknown residue 
			{
				//System.out.println("Unknown residue: " + residue.toString());
				residueChain.add(residue);
			}
		}
		//add last molecule
		if (residueChain.size()>0)
			molecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, residueChain));
		
		this.soluteMolecules = molecules;
	}
	
	/**
	 * Build molecular system using list of molecules and residues
	 * @param residues List of residues
	 * @throws IOException 
	 */
	public static MolecularSystem loadSystem(List<List<Residue>> residues) throws IOException {
		
		List<Ion> ions = new ArrayList<Ion>();
		List<Molecule> waters = new ArrayList<Molecule>();
		ArrayList<Molecule> soluteMolecules = new ArrayList<Molecule>();
		MolecularSystem system = new MolecularSystem();
		
		//first pass to extract water, ions, and single residue molecules (ligands?)
		int r=0; 
		while (r < residues.size())
		{
			List<Residue> moleculeResidues = residues.get(r);
			if (moleculeResidues.size()==1){
				Residue residue = moleculeResidues.get(0);
				System.out.println(residue.getCode()+"?");
				if (residue.getType() == ResidueType.ION){
					ions.add(new Ion(residue));
				}
				else if (residue.getType() == ResidueType.WATER){
					waters.add(new Water());
				}
				else //default to compound
				{
					soluteMolecules.add(new Compound(residue.getName(), residue.getAtoms()));
				}
				residues.remove(r);
			}
			else r++;
		}
		system.setSolventMolecules(waters);
		system.setIons(ions);
		
		//third pass to extract solute molecules
		
		for (List<Residue> moleculeResidues : residues){
			for (Residue residue : moleculeResidues){
				System.out.print(residue.getCode()+" ");
			}
			System.out.println("?");
			
			BiomoleculeType moleculeType = BiomoleculeType.UNKNOWN;
			r=0;
			boolean typeFound = false;
			while (!typeFound && r<moleculeResidues.size())
			{
				Residue residue = moleculeResidues.get(r);
				ResidueType currentType = residue.getType();
				
				if (currentType == ResidueType.AMINO_ACID) {
					typeFound = true;
					moleculeType = BiomoleculeType.PROTEIN;
				}
				else if (currentType == ResidueType.NUCLEIC_ACID_BASE){
					moleculeType = BiomoleculeType.NUCLEIC_ACID;
				}
				else if (currentType == ResidueType.RNA_BASE){
					typeFound = true;
					moleculeType = BiomoleculeType.RNA;
				}
				else if (currentType == ResidueType.DNA_BASE){
					typeFound = true;
					moleculeType = BiomoleculeType.DNA;
				}
				r++;
			}
			soluteMolecules.add(BiomoleculeFactory.getMoleculeFromType(moleculeType, moleculeResidues));
		}
		system.setSoluteMolecules(soluteMolecules);
		return system;
	}
	
	
	/**
	 * Identify molecule residues based on atom bond information
	 * @param molecules List of molecules
	 * @return List of molecule residues
	 */
	public static List<List<Residue>> identifyMoleculeResidues(List<Molecule> molecules){
		List<List<Residue>> moleculeResidues = new ArrayList<List<Residue>>();
		for (Molecule m : molecules){
			Residue prevRes = null;
			List<Residue> residueList = new ArrayList<Residue>();
			for (Atom a : m.getAtoms()){
				Residue res = a.getResidue();
				if (prevRes == null || !res.equals(prevRes)){
					residueList.add(res);
					res.setMolecule(m);
				}
			}
			moleculeResidues.add(residueList);
		}
		return moleculeResidues;
	}
	
	/**
	 * Get total atomic weight of this system
	 * @return Total atomic weight of this molecule or -1 if an error occurred (e.g. unknown element).
	 */
	public double getAtomicWeight(){
		double weight = 0d; 
		if (this.soluteMolecules!=null){
			for (Molecule m: this.soluteMolecules){
				double moleculeWeight = m.getAtomicWeight();
				if (moleculeWeight>0)
					weight += moleculeWeight;
				else return -1.0;
			}
			return weight;
		}
		else return 0;
	}
	
	/**
	 * Get atomic composition in format 'E1:n1 E2:n2' ...
	 * @return Atomic composition
	 */
	public String getAtomicComposition()
	{
		List<AtomicElementOccurrence> elements = new ArrayList<AtomicElementOccurrence>();
		if (this.soluteMolecules!=null)
		{
			for (Molecule molecule : this.soluteMolecules){
				List<AtomicElementOccurrence> occurencesInMolecule = molecule.getAtomicElementOccurrences();
				if (occurencesInMolecule!=null)
					elements = MolecularComposition.merge(elements, occurencesInMolecule);
			}
			return MolecularComposition.getAtomicComposition(elements);
			
		}
		else return null;
	}
	
	/**
	 * Get atomic composition in format 'E1[n1]E2[n2]' ...
	 * @return Atomic composition
	 */
	public String getAtomicCompositionCompact()
	{
		List<AtomicElementOccurrence> elements = new ArrayList<AtomicElementOccurrence>();
		if (this.soluteMolecules!=null)
		{
			for (Molecule molecule : this.soluteMolecules){
				List<AtomicElementOccurrence> occurencesInMolecule = molecule.getAtomicElementOccurrences();
				if (occurencesInMolecule!=null)
					elements = MolecularComposition.merge(elements, occurencesInMolecule);
			}
			return MolecularComposition.getAtomicCompositionCompact(elements);
		}
		else return null;
	}
	
	/**
	 * Get metadata about molecular system
	 * @return Metadata (AVU list)
	 */
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.description!=null && this.description.length()>0){
			metadata.add(new MetadataAVU(TopologyMetadata.MOLECULAR_SYSTEM_DESCRIPTION, this.description));
		}
		
		//get molecule metadata
		for (Molecule molecule : soluteMolecules){
			metadata.addAll(molecule.getMetadata());
		}
		metadata.removeAVUWithAttribute(TopologyMetadata.COUNT_ATOMS);
		metadata.add(new MetadataAVU(TopologyMetadata.COUNT_ATOMS, String.valueOf(this.getAtomCount())));
		
		/*//add molecular composition/weight for the whole system
		if (this.molecules!=null && this.molecules.size()>0){
			String c1 = this.getAtomicComposition();
			String c2 = this.getAtomicCompositionCompact();
			double weight = this.getAtomicWeight();
			if (c1!=null && c1.length()>0)
				metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION, c1));
			if (c2!=null && c2.length()>0)
				metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION2, c2));
			if (weight>0.0)
				metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_ATOMIC_WEIGHT, String.valueOf(weight)));
		}*/
		
		//ions
		if (ions!=null && ions.size()>0){
			for (Ion ion : ions){
				metadata.add(new MetadataAVU(TopologyMetadata.ION_TYPE, ion.getName()));
			}
			metadata.add(new MetadataAVU(TopologyMetadata.COUNT_IONS, String.valueOf(ions.size())));
		}
		else metadata.add(new MetadataAVU(TopologyMetadata.COUNT_IONS, "0"));
		
		//waters
		if (solventMolecules!=null && solventMolecules.size()>0){
			metadata.add(new MetadataAVU(TopologyMetadata.COUNT_SOLVENT, String.valueOf(solventMolecules.size())));
			for (Molecule solventMolecule : solventMolecules){
				metadata.add(new MetadataAVU(TopologyMetadata.SOLVENT_MOLECULE, solventMolecule.getName()));
			}
		}
		else metadata.add(new MetadataAVU(TopologyMetadata.COUNT_SOLVENT, "0"));

		// apparent pH
		if (this.apparentPH>0.0)
			metadata.add(new MetadataAVU(MethodMetadata.APPARENT_PH, String.valueOf(this.apparentPH)));
		
		return metadata;
	}
	
	/**
	 * Get string representation of the molecular system
	 * @return string representation of the molecular system
	 */
	public String toString(){
		String descriptionStr = "";
		if (this.description != null && this.description.length()>0)
			descriptionStr = ": " + this.description;
		return ("Molecular system [" + soluteMolecules.size() + " molecules] ["+ions.size()+" ions] ["+solventMolecules.size()+" waters] [" + this.getAtomCount() + " atoms]" + descriptionStr);
	}	
}
