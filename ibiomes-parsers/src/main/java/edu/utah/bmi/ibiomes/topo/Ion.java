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

import edu.utah.bmi.ibiomes.topo.bio.Residue;

/**
 * Ion
 * @author Julien Thibault, University of Utah
 *
 */
public class Ion extends Molecule {
	
	@SuppressWarnings(value = { "unused" })
	private Ion(){	
	}
	
	/**
	 * Create new ion
	 */
	public Ion(String name, Atom atom){
		this.name = name;
		atoms = new ArrayList<Atom>();
		if (atom != null)
			atoms.add(atom);
		else atoms.add(new Atom(1,name,name));
		this.description = this.name + " ion";
	}
	
	/**
	 * Create new ion from atom
	 */
	public Ion(Atom atom){
		initSingleAtomIon(atom);
	}
	
	private void initSingleAtomIon(Atom atom){
		//add atom
		atoms = new ArrayList<Atom>();
		atoms.add(atom);
		
		//set ion name
		if (atom.getElement()!=null && atom.getElement().getSymbol()!=null)
			this.name = atom.getElement().getSymbol();
		else this.name = atom.getType();
		
		//set charge
		int charge = (int)this.getCharge();
		String sign = "";
		String chargeStr = "";
		if (charge>0)
			sign = "+";
		else if (charge < 0)
			sign = "-";
		if (Math.abs(charge)>1)
			chargeStr = String.valueOf(charge);
		this.name += chargeStr + sign;
		
		//set description
		this.description = this.name + " ion";
	}

	/**
	 * Create new ion from residue
	 * @param residue
	 */
	public Ion(Residue residue){
		//if only one atom
		if (residue.getAtoms() != null && residue.getAtoms().size()==1){
			initSingleAtomIon(residue.getAtoms().get(0));
		}
		else {
			this.name = residue.getCode();
			if (residue.getAtoms() != null && residue.getAtoms().size()>0)
				atoms = residue.getAtoms();
			else {
				atoms = new ArrayList<Atom>();
				atoms.add(new Atom(1,name,name));
			}
			this.description = this.name + " ion";
		}
	}

	/**
	 * Get ion formal charge
	 * @return ion formal charge
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
}
