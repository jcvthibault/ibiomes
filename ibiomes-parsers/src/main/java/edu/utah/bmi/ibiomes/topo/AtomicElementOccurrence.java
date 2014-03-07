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

import edu.utah.bmi.ibiomes.dictionaries.AtomicElement;

/**
 * Atomic element occurrences.
 * @author Julien Thibault, University of Utah
 *
 */
public class AtomicElementOccurrence
{
	private AtomicElement element;
	private int count;
	
	public AtomicElementOccurrence(AtomicElement element, int count){
		this.element = element;
		this.count = count;
	}
	
	public AtomicElementOccurrence(AtomicElement element){
		this.element = element;
		this.count = 0;
	}
	
	/**
	 * Add occurence
	 */
	public void addOccurrence(){
		this.count ++;
	}
	
	/**
	 * Get number of occurrences
	 * @return Number of occurrences
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Set number of occurrences
	 * @param count Number of occurrences
	 */
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * Get element
	 * @return Element
	 */
	public AtomicElement getElement() {
		return element;
	}
	/**
	 * Set element
	 * @param element Element
	 */
	public void setElement(AtomicElement element) {
		this.element = element;
	}
}
