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

package edu.utah.bmi.ibiomes.metadata;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Metadata pair
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="AVU")
public class MetadataAVU implements Serializable {

	private static final long serialVersionUID = 5371841641435426907L;
	
	private String attribute;
	private String value;
	private String unit;
	
	public MetadataAVU(){
		
	}
	
	public MetadataAVU(String attribute, String value, String unit){
		this.attribute = attribute;
		this.value = value;
		this.unit = unit;
	}
	
	public MetadataAVU(String attribute, String value){
		this.attribute = attribute;
		this.value = value;
		this.unit = null;
	}

	@XmlAttribute(name="id")
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@XmlValue
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlAttribute(name="unit")
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Override
	public String toString(){
		return (this.attribute + " = \"" + this.value + "\"");
	}
}
