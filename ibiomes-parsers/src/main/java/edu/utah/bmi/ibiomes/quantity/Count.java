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
 * Count (integer value)
 * @author Julien Thibault, University of Utah
 *
 */
public class Count extends IntegerQuantity {
	
	private static final long serialVersionUID = -1026700456953386166L;
	public static final String Degree = "°";
	public static final String Radian = "rad";
	
	public Count(){	
		super(0);
	}
	
	/**
	 * New count
	 * @param value Value
	 */
	public Count(int value){
		super(value);
	}
}
