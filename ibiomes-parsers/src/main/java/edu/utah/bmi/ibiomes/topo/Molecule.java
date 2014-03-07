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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;

/**
 * Molecule (structural information). Abstract class.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="molecule")
public abstract class Molecule implements MetadataMappable
{
	protected List<Atom> atoms;
	protected String name = null;
	protected String description = null;
	protected String type = null;
	protected float charge = 0.0f;
	private PeriodicTable periodicTable = null;

	/**
	 * Create empty molecule
	 * @param name Name of the molecule
	 */
	public Molecule(String name){
		this.name = name;
		this.periodicTable = PeriodicTable.getInstance();
	}
	
	/**
	 * private no-arg constructor for JAXB
	 */
	protected Molecule(){
		this.periodicTable = PeriodicTable.getInstance();
	}
	
	/**
	 * Add atoms to the molecule
	 * @param atoms Atoms
	 */
	public void addAtoms(List<Atom> atoms){
		if (this.atoms == null)
			this.atoms = new ArrayList<Atom>();
		for (Atom a : atoms){
			this.addAtom(a);
		}
	}
	
	/**
	 * Add atom to the molecule
	 * @param atom Atom
	 */
	public void addAtom(Atom atom) {
		if (this.atoms == null)
			this.atoms = new ArrayList<Atom>();
		this.atoms.add(atom);
	}
	
	/**
	 * Get list of atoms
	 * @return List of atoms
	 */
	@XmlTransient
	public List<Atom> getAtoms(){
		return this.atoms;
	}
	
	/**
	 * Get molecule name
	 * @return molecule name
	 */
	@XmlAttribute(name="name")
	public String getName(){
		return this.name;
	}
	
	/**
	 * Set molecule name
	 * @param name molecule name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Get molecule description
	 * @return molecule description
	 */
	@XmlElement
	public String getDescription(){
		return this.description;
	}
	
	/**
	 * Set molecule description
	 * @param description molecule description
	 */
	public void setDescription(String description){
		this.description = description;
	}

	/**
	 * Get molecule type
	 * @return Molecule type
	 */
	@XmlAttribute
	public String getType() {
		return type;
	}

	/**
	 * Set molecule type
	 * @param type Molecule type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get molecule formal charge
	 * @return Molecule formal charge
	 */
	@XmlElement
	public abstract float getCharge();
	
	/**
	 * Get number of atoms in the molecule
	 * @return total number of atoms in the molecule
	 */
	@XmlAttribute
	public int getAtomCount()
	{
		if (this.atoms == null)
			return 0;
		else
			return this.atoms.size();
	}
	
	/**
	 * Get chain of atoms
	 * @return String representing the chain of atoms
	 */
	@XmlTransient
	public String getAtomChain()
	{
		if (this.atoms == null)
			return "";
		String chain = "";
		for (Atom a : this.atoms){
			chain += a.getType() + " ";
		}
		return chain.trim();
	}
	
	/**
	 * Get atom occurrences in this molecule
	 * @return List of atom occurrences
	 */
	public List<AtomOccurrence> getAtomOccurrences()
	{
		HashMap<String, AtomOccurrence> occurences = new HashMap<String, AtomOccurrence>();
		List<AtomOccurrence> occurrenceList = new ArrayList<AtomOccurrence>();
		if (this.atoms != null){
			for (Atom atom : atoms){
				String atomType = atom.getType();
				if (occurences.containsKey(atomType)){
					occurences.get(atomType).addOccurrence();
				}
				else {
					occurences.put(atomType, new AtomOccurrence(atom, 1));
				}
			}
		}
		for (AtomOccurrence occurrence : occurences.values()){
			occurrenceList.add(occurrence);
		}
		return occurrenceList;
	}
	
	/**
	 * Get occurrences of different elements in this molecule
	 * @return List of element occurrences
	 */
	public List<AtomicElementOccurrence> getAtomicElementOccurrences()
	{
		HashMap<String, AtomicElementOccurrence> occurences = new HashMap<String, AtomicElementOccurrence>();
		List<AtomicElementOccurrence> occurrenceList = new ArrayList<AtomicElementOccurrence>();
		if (this.atoms != null){
			for (Atom atom : atoms){
				AtomicElement element = atom.getElement();
				if (element!=null){
					if (occurences.containsKey(element.getSymbol())){
						occurences.get(element.getSymbol()).addOccurrence();
					}
					else {
						occurences.put(element.getSymbol(), new AtomicElementOccurrence(element, 1));
					}
				}
			}
		}
		for (AtomicElementOccurrence occurrence : occurences.values()){
			occurrenceList.add(occurrence);
		}
		return occurrenceList;
	}
	
	/**
	 * Get total atomic weight of this molecule
	 * @return Total atomic weight of this molecule or -1 if an error occurred (e.g. unknown element).
	 */
	public double getAtomicWeight(){
		double weight = 0; 
		if (this.atoms!=null){
			for (Atom a: this.atoms)
			{
				AtomicElement element = a.getElement();
				if (element != null)
					weight += element.getWeight();
				else {
					return -1;
				}
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
		List<AtomicElementOccurrence> elements = getAtomicElementOccurrences();
		return MolecularComposition.getAtomicComposition(elements);
	}
	
	/**
	 * Get atomic composition in format 'E1[n1]E2[n2]' ...
	 * @return Atomic composition
	 */
	public String getAtomicCompositionCompact()
	{
		if (this.atoms!=null){
			String chain = getAtomicComposition();
			String[] elts = chain.split(" ");
			chain = "";
			for (int e=0; e<elts.length;e++)
			{
				if (elts[e].endsWith(":1"))
					chain +=  elts[e].substring(0, elts[e].length()-2);
				else
					chain += elts[e].replaceFirst("\\:", "[") + "]";
			}
			return chain;
		}
		else return null;
	}
	
	/**
	 * Get metadata about molecule structure
	 * @return Metadata (AVU list)
	 */
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.description!=null && this.description.length()>0){
			metadata.add(new MetadataAVU(TopologyMetadata.MOLECULE_DESCRIPTION, this.description));
		}
		metadata.add(new MetadataAVU(TopologyMetadata.COUNT_ATOMS,String.valueOf(this.getAtomCount())));
		
		if (this.getCharge()!=0.0f){
			metadata.add(new MetadataAVU(TopologyMetadata.TOTAL_MOLECULE_CHARGE, String.valueOf(this.getCharge())));
		}
		return metadata;
	}
	/**
	 * Get string representation of the molecule
	 * @return string representation of the molecule
	 */
	public String toString(){
		
		return ("Molecule '" + this.name + "': " + this.getAtomCount() + " atoms");
	}
}
