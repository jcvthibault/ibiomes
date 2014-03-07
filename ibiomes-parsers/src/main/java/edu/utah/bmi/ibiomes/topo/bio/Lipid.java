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
 * Lipid
 * @author Julien Thibault, University of Utah
 *
 */
public class Lipid extends Biomolecule {

	/**
	 * Create new lipid molecule with residues
	 * @throws IOException 
	 */
	public Lipid(List<Residue> residues) throws IOException{
		super(residues);
		this.type = BiomoleculeType.LIPID.toString();
	}
	
	/**
	 * Get string representation of the lipid molecule
	 * @return string representation of the lipid molecule
	 */
	@Override
	public String toString(){
		
		return ("Lipid [" + this.residues.size() + " residues][" + this.atoms.size() + " atoms]");
	}
}
