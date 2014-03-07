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

/**
 * Residue occurrence
 * @author Julien Thibault, University of Utah
 *
 */
public class ResidueOccurrence {
	private Residue residue;
	private int count;
	
	public ResidueOccurrence(Residue residue, int count){
		this.residue = residue;
		this.count = count;
	}
	
	public ResidueOccurrence(Residue residue){
		this.residue = residue;
		this.count = 0;
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
	 * Get residue
	 * @return Residue
	 */
	public Residue getResidue() {
		return residue;
	}
	/**
	 * Set residue
	 * @param residue Residue
	 */
	public void setResidue(Residue residue) {
		this.residue = residue;
	}
}
