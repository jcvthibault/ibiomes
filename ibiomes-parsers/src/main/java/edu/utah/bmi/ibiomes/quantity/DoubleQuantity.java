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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Attribute value with unit
 * @author Julien Thibault
 *
 */
@XmlRootElement
public class DoubleQuantity implements Serializable {

	private static final long serialVersionUID = -5319105857971946333L;
	
	protected String unit;
	protected double value;

	protected DoubleQuantity(){
	}
	
	public DoubleQuantity(double value, String unit){
		this.unit = unit;
		this.value = value;
	}

	/**
	 * Get value
	 * @return Value
	 */
	@XmlAttribute(name="value")
	public double getValue() {
		return value;
	}

	/**
	 * Set value
	 * @param value
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * Get unit
	 * @return Unit
	 */
	@XmlAttribute(name="unit")
	public String getUnit() {
		return unit;
	}

	/**
	 * Set unit
	 * @param unit Unit
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
