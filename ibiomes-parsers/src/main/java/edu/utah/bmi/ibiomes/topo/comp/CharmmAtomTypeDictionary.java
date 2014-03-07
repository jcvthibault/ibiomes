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

/**
 * Dictionary of AMBER atom types.
 * @author Julien Thibault, University of Utah
 *
 */
public class CharmmAtomTypeDictionary extends AtomTypeDictionary {

	private static CharmmAtomTypeDictionary dictionary;
	
	/**
	 * Get dictionary instance
	 * @return Dictionary instance
	 */
	public static CharmmAtomTypeDictionary getInstance() {
		if (dictionary == null)
			dictionary = new CharmmAtomTypeDictionary();
		return dictionary;
	}

	private CharmmAtomTypeDictionary(){
		this.entries = new HashMap<String, String>();
		//TODO load from external config file
		
		//all-atom carbon types
		this.entries.put("C", "C");
		
		//hydrogen types
		this.entries.put("H", "H");

		this.entries.put("N", "N");

		//oxygen types
		this.entries.put("O", "O"); //carbonyl oxygen
		
		//ions
		this.entries.put("NA+", "Na");
		this.entries.put("POT", "K");
		this.entries.put("CLA", "Cl");
		this.entries.put("CAL", "Ca");
		this.entries.put("MG" , "Mg");
		this.entries.put("CES", "Cs");
		this.entries.put("ZN" , "Zn");
	}
}
