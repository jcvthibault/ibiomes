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

package edu.utah.bmi.ibiomes.topo.bio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.topo.Atom;
import edu.utah.bmi.ibiomes.topo.Molecule;

/**
 * Simulated biomolecule (structural information)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="molecule")
public class Biomolecule extends Molecule
{
	public enum BiomoleculeType { 
		PROTEIN("Protein"),
		NUCLEIC_ACID("Nucleic acid"),
		DNA("DNA"),
		RNA("RNA"),
		CARBOHYDRATE("Carbohydrate"),
		LIPID("Lipid"),
		UNKNOWN("Biomolecule");
		private String literal;
		BiomoleculeType(String literal){
			this.literal = literal;
		}
		@Override
		public String toString(){
			return this.literal;
		}
	};
	
	protected List<Residue> residues;
	protected List<Residue> residuesNonStd;
	protected int nWater;
	protected int nIons;
	protected List<String> ionTypes;
	
	/**
	 * Create empty molecule
	 * @param name Name of the molecule
	 * @throws IOException 
	 */
	public Biomolecule(String name)
	{
		super(name);
		this.init();
	}
	
	/**
	 * Create new molecule with residues
	 * @param residues List of residues
	 * @throws IOException 
	 */
	public Biomolecule(List<Residue> residues){
		super();
		this.init();
		this.addResidues(residues);
	}
	
	/**
	 * Create new molecule with residues
	 * @param name Name of the molecule
	 * @throws IOException 
	 */
	public Biomolecule(String name, List<Residue> residues)
	{
		super(name);
		this.init();
		this.addResidues(residues);
	}
	
	private void init() {
		this.type = BiomoleculeType.UNKNOWN.toString();
		this.residues = new ArrayList<Residue>();
		this.residuesNonStd = new ArrayList<Residue>();
	}
	
	/**
	 * private no-arg constructor for JAXB
	 */
	protected Biomolecule(){
		super();
		this.init();
	}
	
	/**
	 * Get number of residues
	 * @return Number of residues
	 */
	@XmlAttribute
	public int getResidueCount(){
		if (residues==null)
			return 0;
		else return residues.size();
	}
	
	/**
	 * Add residues to the molecule
	 * @param residues Residues
	 * @throws IOException 
	 */
	public void addResidues( List<Residue> residues)
	{
		if (residues != null){
			for (Residue r : residues){
				this.addResidue(r);
			}
		}
	}
	
	/**
	 * Add new residue to the molecule
	 * @param r Residue
	 * @throws IOException 
	 */
	public void addResidue(Residue r)
	{
		if (r != null)
		{
			this.residues.add(r);
			
			if (!r.isStandard()){
				this.residuesNonStd.add(r);
			}
			//add corresponding atoms
			this.addAtoms(r.getAtoms());
		}
	}
	
	/**
	 * Get molecule formal charge
	 * @return Molecule formal charge
	 */
	@Override
	public float getCharge() {
		float molCharge = 0.0f;
		if (this.atoms!=null){
			for (Atom a : this.atoms){
				molCharge += a.getCharge();
			}
			molCharge = (float)Math.round(molCharge);
		}
		return molCharge;
	}
	
	/**
	 * Get list of residues
	 * @return List of residues
	 */
	@XmlElementWrapper(name="residues")
	@XmlElement(name="residue")
	public List<Residue> getResidues(){
		return this.residues;
	}

	/**
	 * Get chain of residues
	 * @return chain of residues
	 */
	@XmlElement(name="residueChain")
	public String getResidueChain()
	{
		String residueChain = "";
		for (Residue res : this.residues){
			residueChain += res.getCode() + " ";
		}
		return residueChain.trim();
	}
	
	/**
	 * Get normalized chain of residues
	 * @return chain of residues
	 * @throws IOException 
	 */
	@XmlElement(name="residueChainNorm")
	public String getResidueChainNormalized()
	{
		String residueChain = "";
		for (Residue res : this.residues){
			String stdCode = res.getStandardCode();
			if (stdCode==null || stdCode.trim().length()==0)
				stdCode = "?";
			residueChain += stdCode;
		}
		return residueChain.trim();
	}
		
	/**
	 * Get list of non-standard residues
	 * @return list of non-standard residues
	 */
	@XmlTransient
	public List<Residue> getNonStandardResidues(){
		return this.residuesNonStd;
	}

	/**
	 * Get molecule type
	 * @return Molecule type
	 */
	@Override
	public String getType() {
		if (type==null)
		{
			ResidueChainParser parser;
			try {
				parser = new ResidueChainParser(this.getResidueChain());
				ArrayList<String> moleculeTypes = parser.getMoleculeTypes();
				if (moleculeTypes.size()>0)
					type = moleculeTypes.get(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CompressorException e) {
				e.printStackTrace();
			}			
		}
		return type;
	}
	
	/**
	 * Get list of different residues present in the molecule.
	 * @return List of residue occurrences
	 */
	public List<ResidueOccurrence> getResidueOccurences()
	{
		HashMap<String, ResidueOccurrence> occurences = new HashMap<String, ResidueOccurrence>();
		List<ResidueOccurrence> occurrenceList = new ArrayList<ResidueOccurrence>();
		if (this.residues != null){
			for (Residue residue : residues){
				String residueType = residue.getCode();
				if (occurences.containsKey(residueType)){
					occurences.get(residueType).addOccurrence();
				}
				else {
					occurences.put(residueType, new ResidueOccurrence(residue, 1));
				}
			}
		}
		for (ResidueOccurrence occurrence : occurences.values()){
			occurrenceList.add(occurrence);
		}
		return occurrenceList;
	}
	
	/**
	 * Get string representation of the biomolecule
	 * @return string representation of the biomolecule
	 */
	@Override
	public String toString(){
		
		return ("Biomolecule [" + this.residues.size() + " residues][" + this.getAtomCount() + " atoms]");
	}

	/**
	 * Get metadata about biomolecule structure
	 * @return Metadata
	 * @throws Exception
	 */
	@Override
	public MetadataAVUList getMetadata()
	{

		MetadataAVUList supermetadata = super.getMetadata();
		
		MetadataAVUList metadata = new MetadataAVUList();
		metadata.addAll(supermetadata);
		
		//get topology metadata
		String resChain = this.getResidueChain();
		if (resChain!=null && resChain.length()>0){
			metadata.add(new MetadataAVU(TopologyMetadata.RESIDUE_CHAIN, resChain));
			metadata.add(new MetadataAVU(TopologyMetadata.RESIDUE_CHAIN_NORM, this.getResidueChainNormalized()));
		}
		if (this.getType() != null)
			metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_TYPE, type));

		
		List<Residue> residues = this.getNonStandardResidues();
		for (Residue res : residues){
			MetadataAVU avu = new MetadataAVU(TopologyMetadata.RESIDUE_NON_STD, res.getCode() + ": " + res.getChainOfAtoms());
			if (!metadata.hasPair(avu))
				metadata.add(avu);
		}
		return metadata;
	}

}
