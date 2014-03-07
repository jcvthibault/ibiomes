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

import java.util.List;

public interface AtomGroup {
	
	/**
	 * Get list of atoms
	 * @return List of atoms
	 */
	public List<Atom> getAtoms();
	
	/**
	 * Get molecule name
	 * @return molecule name
	 */
	public String getName();
	
	/**
	 * Get molecule description
	 * @return molecule description
	 */
	public String getDescription();
	
	/**
	 * Get number of atoms in the molecule
	 * @return total number of atoms in the molecule
	 */
	public int getAtomCount();
	
	/**
	 * Get chain of atoms
	 * @return String representing the chain of atoms
	 */
	public String getAtomChain();
}
