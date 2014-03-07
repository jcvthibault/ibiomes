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

package edu.utah.bmi.ibiomes.topo.comp;

import java.util.HashMap;

import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;
import edu.utah.bmi.ibiomes.dictionaries.PeriodicTable;

/**
 * Dictionary of atom types for specific software packages.
 * @author Julien Thibault, University of Utah
 *
 */
public abstract class AtomTypeDictionary {

	protected HashMap<String, String> entries;
	protected PeriodicTable periodicTable;
	
	protected AtomTypeDictionary(){
		periodicTable = PeriodicTable.getInstance();
	}
	
	/**
	 * Get element definition for the given atom type
	 * @param atomType Atom type
	 * @return Element mapped to the atom type or null if no mapping exist.
	 */
	public AtomicElement getElementForAtomType(String atomType){
		String symbol = entries.get(atomType.toUpperCase());
		if (symbol!=null){
			return periodicTable.getElementBySymbol(symbol);
		}
		else {
			return null;
		}
	}
}
