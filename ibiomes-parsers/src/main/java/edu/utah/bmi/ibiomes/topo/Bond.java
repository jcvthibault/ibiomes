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
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bond (connection between 2 atoms)
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="bond")
public class Bond 
{
	private List<Atom> atoms;
	private boolean incHydrogen;
	private double forceCst;
	private double equilVal;
	
	@XmlElementWrapper(name="atoms")
	@XmlElement(name="atom")
	public List<Atom> getAtoms() {
		return atoms;
	}
	
	@XmlAttribute(name="hasHydrogen")
	public boolean hasHydrogen() {
		return incHydrogen;
	}
	
	public Bond(Atom atom1, Atom atom2, int index, double iforceCst, double iequilVal, boolean iHasHydrogen)
	{	
		this.atoms = new ArrayList<Atom>();
		this.atoms.add(atom1);
		this.atoms.add(atom2);
		this.incHydrogen = iHasHydrogen;
		this.forceCst = iforceCst;
		this.equilVal = iequilVal;
	}
	
	public String toString()
	{
		return ("Bond between " + atoms.get(0).getName() + " and " + atoms.get(1).getName() + " [ "+ forceCst + " kcal/mol  " + equilVal + " Angstroms ]");
	}
	
}
