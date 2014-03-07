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

package edu.utah.bmi.ibiomes.experiment.summary;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.topo.Compound;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;
import edu.utah.bmi.ibiomes.topo.MoleculeOccurrence;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;

/**
 * Summarizes a list of molecular systems
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="molecularSystemsSummary")
public class SummaryMolecularSystems implements MetadataMappable {
	private List<String> solventMolecules;
	private List<String> soluteMoleculeTypes;
	private List<String> normalizedSequences;
	private List<String> specificSequences;
	private List<String> compounds;
	private List<String> files;
	private List<String> ions;
	
	@SuppressWarnings(value = { "unused" })
	private SummaryMolecularSystems(){	
	}
	
	/**
	 * Create summary of given systems
	 * @param systems List of molecular systems
	 */
	public SummaryMolecularSystems(List<MolecularSystem> systems)
	{
		if (systems!=null){
			
			this.soluteMoleculeTypes = new ArrayList<String>();
			this.solventMolecules = new ArrayList<String>();
			this.normalizedSequences = new ArrayList<String>();
			this.specificSequences = new ArrayList<String>();
			this.compounds = new ArrayList<String>();
			this.files = new ArrayList<String>();
			this.ions = new ArrayList<String>();
			
			for (MolecularSystem system : systems)
			{
				//ions
				List<MoleculeOccurrence> ions = system.getIonOccurrences();
				if (ions!=null){
					for (MoleculeOccurrence ion : ions){
						String ionType = ion.getMolecule().getName();
						if (ionType!=null && !this.ions.contains(ionType))
							this.ions.add(ion.getMolecule().getName());
					}
				}
				
				//solvent
				List<MoleculeOccurrence> moleculesSolvent = system.getSolventMoleculeOccurrences();
				if (moleculesSolvent!=null){
					for (MoleculeOccurrence molOccurrence : moleculesSolvent){
						Molecule mol = molOccurrence.getMolecule();
						String molComposition = mol.getAtomicComposition();
						if (molComposition!=null  && !solventMolecules.contains(molComposition)){
							solventMolecules.add(molComposition);
						}
					}
				}
				
				//solute molecules
				List<MoleculeOccurrence> moleculesSolute = system.getSoluteMoleculeOccurrences();
				for (MoleculeOccurrence molOccurrence : moleculesSolute){
					Molecule mol = molOccurrence.getMolecule();
					if (mol instanceof Biomolecule){
						Biomolecule biomolecule = (Biomolecule)mol;
						String molType = biomolecule.getType();
						if (molType!=null  && !soluteMoleculeTypes.contains(molType)){
							soluteMoleculeTypes.add(molType);
						}
						String specificSequence = biomolecule.getResidueChain();
						String normalizedSequence = biomolecule.getResidueChainNormalized();
						if (specificSequence!=null && !specificSequences.contains(specificSequence)){
							specificSequences.add(specificSequence);
						}
						if (normalizedSequence!=null && !normalizedSequences.contains(normalizedSequence)){
							normalizedSequences.add(normalizedSequence);
						}
					}
					else if (mol instanceof Compound){
						Compound compound = (Compound)mol;
						String stoichiometry = compound.getAtomicCompositionCompact();
						if (stoichiometry!=null && !compounds.contains(stoichiometry)){
							compounds.add(stoichiometry);
						}
						if (!soluteMoleculeTypes.contains(MolecularSystem.TYPE_COMPOUND)){
							soluteMoleculeTypes.add(MolecularSystem.TYPE_COMPOUND);
						}
					}
				}
				//file references
				List<String> files = system.getDefinitionFiles();
				if (files!=null){
					for (String file : files){
						if (!this.files.contains(file)){
							this.files.add(file);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generate associated metadata
	 */
	@XmlTransient
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.soluteMoleculeTypes!=null){
			for (String m : this.soluteMoleculeTypes){
				metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_TYPE, m));
			}
		}
		if (this.solventMolecules!=null){
			for (String m : this.solventMolecules){
				metadata.add(new MetadataAVU(TopologyMetadata.SOLVENT_MOLECULE, m));
			}
		}
		if (this.normalizedSequences!=null){
			for (String m : this.normalizedSequences){
				metadata.add(new MetadataAVU(TopologyMetadata.RESIDUE_CHAIN_NORM, m));
			}
		}
		if (this.specificSequences!=null){
			for (String m : this.specificSequences){
				metadata.add(new MetadataAVU(TopologyMetadata.RESIDUE_CHAIN, m));
			}
		}
		if (this.compounds!=null){
			for (String m : this.compounds){
				metadata.add(new MetadataAVU(TopologyMetadata.CHEMICAL_FORMULA, m));
			}
		}
		if (this.files!=null){
			for (String m : this.files){
				metadata.add(new MetadataAVU(ExperimentMetadata.TOPOLOGY_FILE_PATH, m));
			}
		}
		if (this.ions!=null){
			for (String m : this.ions){
				metadata.add(new MetadataAVU(TopologyMetadata.ION_TYPE, m));
			}
		}
		return metadata;
	}

	/**
	 * Get list of solvent molecules present in the systems
	 * @return List of solvent molecules present in the systems
	 */
	@XmlElementWrapper(name="soluteMoleculeTypes")
	@XmlElement(name="soluteMoleculeType")
	public List<String> getSolventMoleculeTypes() {
		return soluteMoleculeTypes;
	}

	/**
	 * Set list of solvent molecules present in the systems
	 * @param moleculeTypes List of solvent molecules present in the systems
	 */
	public void setSolventMoleculeTypes(List<String> moleculeTypes) {
		this.soluteMoleculeTypes = moleculeTypes;
	}

	/**
	 * Get list of normalized residue sequences
	 * @return List of normalized residue sequences
	 */
	@XmlElementWrapper(name="normalizedSequences")
	@XmlElement(name="normalizedSequence")
	public List<String> getNormalizedSequences() {
		return normalizedSequences;
	}

	/**
	 * Set list of normalized residue sequences
	 * @param normalizedSequences List of normalized residue sequences
	 */
	public void setNormalizedSequences(List<String> normalizedSequences) {
		this.normalizedSequences = normalizedSequences;
	}

	/**
	 * Get list of software-specific residue sequences
	 * @return List of software-specific residue sequences
	 */
	@XmlElementWrapper(name="specificSequences")
	@XmlElement(name="specificSequence")
	public List<String> getSpecificSequences() {
		return specificSequences;
	}

	/**
	 * Set list of software-specific residue sequences
	 * @param specificSequences List of software-specific residue sequences
	 */
	public void setSpecificSequences(List<String> specificSequences) {
		this.specificSequences = specificSequences;
	}

	/**
	 * Get list of compounds present in the systems
	 * @return List of compounds present in the systems
	 */
	@XmlElementWrapper(name="compounds")
	@XmlElement(name="compound")
	public List<String> getCompounds() {
		return compounds;
	}

	/**
	 * Set list of compounds present in the systems
	 * @param compounds List of compounds present in the systems
	 */
	public void setCompounds(List<String> compounds) {
		this.compounds = compounds;
	}

	/**
	 * Get reference to the files defining the molecular systems
	 * @return List of files defining the molecular systems
	 */
	@XmlElementWrapper(name="files")
	@XmlElement(name="file")
	public List<String> getFiles() {
		return files;
	}

	/**
	 * Set reference to the files defining the molecular systems
	 * @param files List of files defining the molecular systems
	 */
	public void setFiles(List<String> files) {
		this.files = files;
	}

	/**
	 * Get list of ion types present in the systems
	 * @return List of ion types present in the systems
	 */
	@XmlElementWrapper(name="ions")
	@XmlElement(name="ion")
	public List<String> getIons() {
		return ions;
	}

	/**
	 * Set list of ion types present in the systems
	 * @param ions List of ion types present in the systems
	 */
	public void setIons(List<String> ions) {
		this.ions = ions;
	}

	/**
	 * Get solvent molecule types
	 * @return Solvent molecule types
	 */
	@XmlElementWrapper(name="solventMolecules")
	@XmlElement(name="solventMolecule")
	public List<String> getSolventMolecules() {
		return solventMolecules;
	}

	/**
	 * Set solvent molecule types
	 * @param solventMolecules Solvent molecule types
	 */
	public void setSolventMolecules(List<String> solventMolecules) {
		this.solventMolecules = solventMolecules;
	}
}
