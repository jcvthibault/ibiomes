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
 * Pressure (value with associated unit)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="pressure")
public class Pressure extends DoubleQuantity {
	
	private static final long serialVersionUID = 4933431490447554142L;
	
	public static final String Bar = "Bar";
	public static final String Pascal = "Pa";
	public static final String Atmosphere = "atm";

	public Pressure(){	
		super(0, Bar);
	}

	/**
	 * New pressure value with associated unit
	 * @param value Value
	 * @param unit Unit
	 */
	public Pressure(double value, String unit){
		super(value, unit);
	}
	
	/**
	 * New pressure value (in Bars)
	 * @param value Value
	 */
	public Pressure(double value){
		super(value, Bar);
	}
}
