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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Metadata AVU with full definition of attribute and value
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="AVU")
public class MetadataRichAVU implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2936618452395645172L;
	private MetadataAttribute attribute;
	private MetadataValue value;
	private String unit;
	
	public MetadataRichAVU(){
		
	}
	
	public MetadataRichAVU(MetadataAttribute attribute, MetadataValue value, String unit){
		this.attribute = attribute;
		this.value = value;
		this.unit = unit;
	}
	
	public MetadataRichAVU(MetadataAttribute attribute, MetadataValue value){
		this.attribute = attribute;
		this.value = value;
		this.unit = null;
	}

	public MetadataAttribute getAttribute() {
		return attribute;
	}
	public void setAttribute(MetadataAttribute attribute) {
		this.attribute = attribute;
	}

	public MetadataValue getValue() {
		return value;
	}
	public void setValue(MetadataValue value) {
		this.value = value;
	}
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString(){
		return (this.attribute.getTerm() + " = \"" + this.value.getTerm() + "\"");
	}

}
