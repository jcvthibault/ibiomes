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
 * Time length (value with associated unit)
 * @author Julien Thibault, University of Utah
 *
 */
public class TimeLength extends DoubleQuantity {
	
	private static final long serialVersionUID = -431505760845139729L;

	public static final String Femtosecond = "fs";
	public static final String Picosecond = "ps";
	public static final String Nanosecond = "ns";
	public static final String Second = "s";
	public static final String Hour = "h";
	public static final String Day = "day";
	public static final String Minute = "min";

	
	public TimeLength(){
		super(0, Picosecond);
	}
	
	public TimeLength(double value, String unit){
		super(value, unit);
	}
	
	public TimeLength(double value){
		super(value, Picosecond);
	}
}
