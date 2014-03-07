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
 * Angle (value with associated unit)
 * @author Julien Thibault, University of Utah
 *
 */
public class Angle extends DoubleQuantity {
	
	private static final long serialVersionUID = -1026700456953386166L;
	public static final String Degree = "degree";
	public static final String Radian = "radian";
	
	public Angle(){	
		super(0, Degree);
	}
	
	/**
	 * New angle
	 * @param value Value
	 * @param unit Unit
	 */
	public Angle(double value, String unit){
		super(value, unit);
	}
	
	/**
	 * New angle in degrees
	 * @param value Value
	 */
	public Angle(double value){
		super(value, Degree);
	}
}
