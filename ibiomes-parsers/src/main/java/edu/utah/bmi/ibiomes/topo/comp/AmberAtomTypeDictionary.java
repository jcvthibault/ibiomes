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
public class AmberAtomTypeDictionary extends AtomTypeDictionary {

	private static AmberAtomTypeDictionary dictionary;
	
	/**
	 * Get dictionary instance
	 * @return Dictionary instance
	 */
	public static AmberAtomTypeDictionary getInstance() {
		if (dictionary == null)
			dictionary = new AmberAtomTypeDictionary();
		return dictionary;
	}

	private AmberAtomTypeDictionary(){
		this.entries = new HashMap<String, String>();
		//TODO load from external config file
		// use http://www.uoxray.uoregon.edu/local/manuals/biosym/discovery/General/Forcefields/AMBER.html
		
		//all-atom carbon types
		this.entries.put("C", "C");
		this.entries.put("CA", "C");
		this.entries.put("CB", "C");
		this.entries.put("CC", "C");
		this.entries.put("CK", "C");
		this.entries.put("CM", "C");
		this.entries.put("CN", "C");
		this.entries.put("CQ", "C");
		this.entries.put("CR", "C");
		this.entries.put("CT", "C");
		this.entries.put("CV", "C");
		this.entries.put("CW", "C");
		this.entries.put("C*", "C");
		
		//hydrogen types
		this.entries.put("H", "H");
		this.entries.put("HA", "H");
		this.entries.put("HC", "H");
		this.entries.put("HN", "H");
		this.entries.put("HP", "H");
		this.entries.put("HO", "H");
		this.entries.put("HS", "H");
		this.entries.put("HW", "H"); //hydrogen in water
		this.entries.put("H2", "H"); //amino hydrogen in NH2
		this.entries.put("H3", "H"); //hydrogen of lysine or arginine (positively charged)

		this.entries.put("NH", "N");
		this.entries.put("ND", "N");

		//oxygen types
		this.entries.put("O", "O"); //carbonyl oxygen
		this.entries.put("OH", "O"); //alcohol oxygen
		this.entries.put("OS", "O"); //ether or ester oxygen
		this.entries.put("OW", "O"); //water oxygen
		this.entries.put("O2", "O"); //carboxyl or phosphate nonbonded oxygen
		
		//ions
		this.entries.put("IP", "Na"); //sodium ion (Na+)
		this.entries.put("IM", "Cl"); //chlorine ion (Cl-)
		this.entries.put("I", "I");   //iodine ion (I-)
		this.entries.put("KA", "Ca"); //calcium ion (Ca+2)
		this.entries.put("C0", "Ca"); //calcium ion (Ca+2)
		this.entries.put("CU", "Cu"); //copper ion (Cu+2)
		this.entries.put("MG", "Mg"); //magnesium ion (Mg+2)
		this.entries.put("QC", "Cs"); //cesium ion (Cs+)
		this.entries.put("QK", "K");  //potassium ion (K+)
		this.entries.put("QL", "Li"); //lithium ion (Li+)
		this.entries.put("QN", "Na"); //sodium ion (Na+)
		this.entries.put("QR", "Rb"); //rubidium ion (Rb+)
	}
}
