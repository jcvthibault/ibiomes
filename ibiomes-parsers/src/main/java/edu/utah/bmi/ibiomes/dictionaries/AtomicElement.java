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

package edu.utah.bmi.ibiomes.dictionaries;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Atomic element (as defined in the periodic table)
 * @author Julien Thibault
 *
 */
@XmlRootElement
public class AtomicElement {

	public final static String ALKALI_METAL = "Alkali metal"; 
	public final static String ALKALINE_EARTH_METAL = "Alkaline earth metal";
	public final static String TRANSITION_METAL = "Transition metal";
	public final static String METAL_OTHER = "Other metal";
	public final static String NON_METAL = "Non metal"; 
	public final static String HALOGEN = "Halogen"; 
	public final static String NOBLE_GAS = "Noble gas"; 
	public final static String RARE_EARTH_ELEMENT = "Rare earth element";

	private String name;
	private String symbol;
	private double weight;
	private int atomicNumber;
	private String family;

	@SuppressWarnings(value = { "unused" })
	private AtomicElement(){	
	}
	
	/**
	 * Default constructor
	 * @param z Atomic number
	 * @param name Name
	 * @param symbol Symbol
	 * @param weight Atomic weight
	 * @param family Family
	 */
	public AtomicElement(int z, String name, String symbol, double weight, String family){
		this.atomicNumber = z;
		this.name = name;
		this.symbol = symbol;
		this.weight = weight;
		this.family = family;
	}

	/**
	 * Full name of the element
	 * @return Name of the element
	 */
	@XmlAttribute
	public String getName() {
		return name;
	}

	/**
	 * Element symbol
	 * @return Symbol
	 */
	@XmlAttribute
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Atomic weight
	 * @return Atomic weight
	 */
	@XmlTransient
	public double getWeight() {
		return weight;
	}

	/**
	 * Element ID
	 * @return ID
	 */
	@XmlAttribute
	public int getAtomicNumber() {
		return atomicNumber;
	}

	/**
	 * Element family (e.g. Noble gas, Alkali metal)
	 * @return Family name
	 */
	@XmlAttribute
	public String getFamily() {
		return family;
	}
	
	@Override
	public String toString(){
		return (atomicNumber + ". " + name + " ("+symbol+"): "+ family +" | weight="+weight+"");
	}
}
