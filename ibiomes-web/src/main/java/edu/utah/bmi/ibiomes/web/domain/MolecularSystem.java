package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Molecular system (set of molecules)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class MolecularSystem 
{	
	private String name;
	private List<String> moleculeTypes;
	private List<String> solventMolecules;
	private int solventCount;
	private List<String> solventTypes;
	private int ionCount;
	private List<String> ionTypes;
	private int atomCount;
	private String atomChain;
	private List<String> residueChains;
	private List<String> chemicalFormula;
	private String molecularComposition;
	private String description;
	private double molecularWeight;
	private List<String> nonStandardResidues;
	private List<String> normalizedChains;
	
	public MolecularSystem(){
	}
	
	/**
	 * Get system name
	 * @return System name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set system name
	 * @param name System name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get the types of molecules present in the system
	 * @return Types of molecules present in the system
	 */
	@XmlElementWrapper(name="moleculeTypes")
	@XmlElement(name="moleculeType")
	public List<String> getMoleculeTypes() {
		return moleculeTypes;
	}
	
	/**
	 * Set the types of molecules present in the system
	 * @param moleculeTypes Types of molecules present in the system
	 */
	public void setMoleculeTypes(List<String> moleculeTypes) {
		this.moleculeTypes = moleculeTypes;
	}
	
	/**
	 * Get list of solvent molecules
	 * @return List of solvent molecules
	 */
	@XmlElementWrapper(name="solventMolecules")
	@XmlElement(name="solventMolecule")
	public List<String> getSolventMolecules() {
		return solventMolecules;
	}
	/**
	 * Set the list of solvent molecules
	 * @param solventMolecules List of solvent molecules
	 */
	public void setSolventMolecules(List<String> solventMolecules) {
		this.solventMolecules = solventMolecules;
	}
	
	/**
	 * Get the count of solvent molecules in the system
	 * @return Count of solvent molecules in the system
	 */
	public int getSolventCount() {
		return solventCount;
	}
	/**
	 * Set the count of solvent molecules in the system
	 * @param solventCount Count of solvent molecules in the system
	 */
	public void setSolventCount(int solventCount) {
		this.solventCount = solventCount;
	}
	/**
	 * Get number of ions in the system
	 * @return Number of ions in the system
	 */
	public int getIonCount() {
		return ionCount;
	}
	/**
	 * Set the number of ions in the system
	 * @param ionCount Number of ions in the system
	 */
	public void setIonCount(int ionCount) {
		this.ionCount = ionCount;
	}
	/**
	 * Get the number of atoms in the system
	 * @return Number of atoms in the system
	 */
	public int getAtomCount() {
		return atomCount;
	}
	/**
	 * Set the number of atoms in the system
	 * @param atomCount Number of atoms in the system
	 */
	public void setAtomCount(int atomCount) {
		this.atomCount = atomCount;
	}
	/**
	 * Get atom chain
	 * @return Atom chain
	 */
	public String getAtomChain() {
		return atomChain;
	}
	/**
	 * Set atom chain
	 * @param atomChain Atom chain
	 */
	public void setAtomChain(String atomChain) {
		this.atomChain = atomChain;
	}
	/**
	 * Get residue chain
	 * @return Residue chain
	 */
	@XmlElementWrapper(name="residueChains")
	@XmlElement(name="residueChain")
	public List<String> getResidueChains() {
		return residueChains;
	}
	/**
	 * Set residue chain
	 * @param residueChain Residue chain
	 */
	public void setResidueChains(List<String> residueChains) {
		this.residueChains = residueChains;
	}

	/**
	 * Get list of non-standard residues
	 * @return List of non-standard residues
	 */
	@XmlElementWrapper(name="nonStandardResidues")
	@XmlElement(name="nonStandardResidue")
	public List<String> getNonStandardResidues() {
		return nonStandardResidues;
	}
	/**
	 * Set the list of non-standard residues
	 * @param nonStandardResidues List of non-standard residues
	 */
	public void setNonStandardResidues(List<String> nonStandardResidues) {
		this.nonStandardResidues = nonStandardResidues;
	}
	
	/**
	 * Get list of normalized residue chains
	 * @return List of normalized residue chains
	 */
	@XmlElementWrapper(name="normalizedChains")
	@XmlElement(name="normalizedChain")
	public List<String> getNormalizedChains() {
		return normalizedChains;
	}
	/**
	 * Set the list of normalized residue chains
	 * @param normalizedChains List of normalized residue chains
	 */
	public void setNormalizedChains(List<String> normalizedChains) {
		this.normalizedChains = normalizedChains;
	}
	/**
	 * Get the chemical formula
	 * @return Chemical formula
	 */
	public List<String> getChemicalFormula() {
		return chemicalFormula;
	}
	/**
	 * Set the chemical formula
	 * @param chemicalFormula Chemical formula
	 */
	public void setChemicalFormula(List<String> chemicalFormula) {
		this.chemicalFormula = chemicalFormula;
	}
	/**
	 * Get molecular weight
	 * @return Molecular weight
	 */
	public double getMolecularWeight() {
		return molecularWeight;
	}
	/**
	 * Set molecular weight
	 * @param molecularWeight Molecular weight
	 */
	public void setMolecularWeight(double molecularWeight) {
		this.molecularWeight = molecularWeight;
	}
	/**
	 * Get molecular composition
	 * @return Molecular composition
	 */
	public String getMolecularComposition() {
		return molecularComposition;
	}
	/**
	 * Set molecular composition
	 * @param composition Molecular composition
	 */
	public void setMolecularComposition(String composition) {
		this.molecularComposition = composition;
	}

	/**
	 * Get list of ion types present in the system
	 * @return List of ion types present in the system
	 */
	public List<String> getIonTypes() {
		return ionTypes;
	}
	/**
	 * Set list of ion types present in the system
	 * @param ionTypes List of ion types present in the system
	 */
	public void setIonTypes(List<String> ionTypes) {
		this.ionTypes = ionTypes;
	}

	public List<String> getSolventTypes() {
		return solventTypes;
	}

	public void setSolventTypes(List<String> solventTypes) {
		this.solventTypes = solventTypes;
	}
	
	/**
	 * Get system description
	 * @return System description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set system description
	 * @param description System description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Create molecular system object based on list of iBIOMES metadata.
	 * @param metadata List of iBIOMES metadata
	 */
	public MolecularSystem(MetadataAVUList metadata)
	{
		if (metadata.containsAttribute(TopologyMetadata.COUNT_ATOMS))
			this.setAtomCount(Integer.parseInt(metadata.getValue(TopologyMetadata.COUNT_ATOMS)));
		if (metadata.containsAttribute(TopologyMetadata.COUNT_IONS))
			this.setIonCount(Integer.parseInt(metadata.getValue(TopologyMetadata.COUNT_IONS)));
		if (metadata.containsAttribute(TopologyMetadata.COUNT_SOLVENT))
			this.setSolventCount(Integer.parseInt(metadata.getValue(TopologyMetadata.COUNT_SOLVENT)));
		this.setResidueChains(metadata.getValues(TopologyMetadata.RESIDUE_CHAIN));
		this.setAtomChain(metadata.getValue(TopologyMetadata.ATOM_CHAIN));
		this.setNormalizedChains(metadata.getValues(TopologyMetadata.RESIDUE_CHAIN_NORM));
		this.setNonStandardResidues(metadata.getValues(TopologyMetadata.RESIDUE_NON_STD));
		
		this.chemicalFormula = metadata.getValues(TopologyMetadata.CHEMICAL_FORMULA);
		
		if (metadata.containsAttribute(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION2))
			this.molecularComposition = metadata.getValue(TopologyMetadata.MOLECULE_ATOMIC_COMPOSITION2);
		
		if (metadata.containsAttribute(TopologyMetadata.MOLECULAR_SYSTEM_DESCRIPTION))
			this.description = metadata.getValue(TopologyMetadata.MOLECULAR_SYSTEM_DESCRIPTION);
		
		if (metadata.containsAttribute(TopologyMetadata.MOLECULE_ATOMIC_WEIGHT))
			this.setMolecularWeight(Double.parseDouble(metadata.getValue(TopologyMetadata.MOLECULE_ATOMIC_WEIGHT)));
		
		this.setMoleculeTypes(metadata.getValues(TopologyMetadata.MOLECULE_TYPE));
		this.setIonTypes(metadata.getValues(TopologyMetadata.ION_TYPE));
		this.setSolventTypes(metadata.getValues(TopologyMetadata.SOLVENT_MOLECULE));
	}


}
