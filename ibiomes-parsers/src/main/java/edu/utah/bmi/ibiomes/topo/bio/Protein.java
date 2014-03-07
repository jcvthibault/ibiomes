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
import java.util.List;


/**
 * Protein
 * @author Julien Thibault, University of Utah
 *
 */
public class Protein extends Biomolecule {
	/**
	 * Create new molecule with residues
	 * @throws IOException 
	 */
	public Protein(List<Residue> residues) throws IOException{
		super(residues);
		this.type = BiomoleculeType.PROTEIN.toString();
	}
	
	public Protein(String moleculeName, List<Residue> residues) {
		super(moleculeName, residues);
		this.type = BiomoleculeType.PROTEIN.toString();
	}

	/**
	 * Get string representation of this protein
	 * @return string representation of this protein
	 */
	@Override
	public String toString(){
		
		return ("Protein [" + this.residues.size() + " residues][" + this.atoms.size() + " atoms]");
	}
}
