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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Temperature (value with associated unit)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="temperature")
public class Temperature extends DoubleQuantity {

	private static final long serialVersionUID = -6247401823945286432L;
	
	public static final String Kelvin = "K";
	public static final String Celsius = "C";
	public static final String Fahrenheit = "F";

	protected Temperature(){
	}
	
	/**
	 * New temperature value with associated unit
	 * @param value Value
	 * @param unit Unit
	 */
	public Temperature(double value, String unit){
		super(value, unit);
	}
	
	/**
	 * New temperature value in Kelvin
	 * @param value Value
	 */
	public Temperature(double value){
		super(value, Kelvin);
	}
}
