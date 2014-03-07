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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Molecular dynamics force field
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class ForceField
{
	private String name;
	private ForceFieldReference reference;
	
	public ForceField(){
	}
	
	/**
	 * Get force field name
	 * @return Force field name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set force field name
	 * @param name Force field name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get force field reference
	 * @return Force field reference
	 */
	public ForceFieldReference getReference() {
		return reference;
	}
	
	/**
	 * Set force field reference
	 * @param reference Force field reference
	 */
	public void setReference(ForceFieldReference reference) {
		this.reference = reference;
	}

}
