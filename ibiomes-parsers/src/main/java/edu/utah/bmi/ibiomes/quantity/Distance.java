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

package edu.utah.bmi.ibiomes.quantity;

/**
 * Distance (value with associated unit)
 * @author Julien Thibault, University of Utah
 *
 */
public class Distance extends DoubleQuantity {
	
	private static final long serialVersionUID = 9009015219272769575L;
	
	public static final String Angstrom = "A";
	public static final String Nanometer = "nm";
	
	public Distance(){	
		super(0, Angstrom);
	}
	
	/**
	 * New distance
	 * @param value Value
	 * @param unit Unit
	 */
	public Distance(double value, String unit){
		super(value, unit);
	}
	
	/**
	 * New distance in Angstroms
	 * @param value Value
	 */
	public Distance(double value){
		super(value, Angstrom);
	}
}
