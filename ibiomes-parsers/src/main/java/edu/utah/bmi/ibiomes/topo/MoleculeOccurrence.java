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

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;

/**
 * Molecule occurrence in the system.
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class MoleculeOccurrence implements MetadataMappable
{
	protected Molecule molecule;
	protected int count = 1;
	
	@SuppressWarnings("unused")
	private MoleculeOccurrence(){
	}
	
	/**
	 * Create new molecule occurrence
	 * @param molecule Molecule 
	 */
	public MoleculeOccurrence(Molecule molecule){
		this.molecule = molecule;
	}
	
	public MoleculeOccurrence(Molecule molecule, int count) {
		this.molecule = molecule;
		this.count = count;
	}

	/**
	 * Add occurence
	 */
	public void addOccurrence(){
		this.count ++;
	}
	
	/**
	 * Get number of occurrences
	 * @return Number of occurrences
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Set number of occurrences
	 * @param count Number of occurrences
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * Get molecule
	 * @return Molecule
	 */
	public Molecule getMolecule() {
		return this.molecule;
	}
	/**
	 * Set molecule
	 * @param molecule Molecule
	 */
	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}

	public MetadataAVUList getMetadata() {
		if (molecule!=null)
			return this.molecule.getMetadata();
		else return null;
	}
	
}
