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
 * Dictionary of NWChem atom types.
 * @author Julien Thibault, University of Utah
 *
 */
public class NWChemAtomTypeDictionary extends AtomTypeDictionary {

	private static NWChemAtomTypeDictionary dictionary;
	
	/**
	 * Get dictionary instance
	 * @return Dictionary instance
	 */
	public static NWChemAtomTypeDictionary getInstance() {
		if (dictionary == null)
			dictionary = new NWChemAtomTypeDictionary();
		return dictionary;
	}

	private NWChemAtomTypeDictionary(){
		this.entries = new HashMap<String, String>();
		
		this.entries.put("bqO", "O"); //dummy oxygen center
		this.entries.put("bqH", "H"); //dummy hydrogen center
	}
}
