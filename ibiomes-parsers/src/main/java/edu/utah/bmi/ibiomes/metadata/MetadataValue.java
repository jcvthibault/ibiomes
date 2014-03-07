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
 * Metadata value
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="metadataValue")
public class MetadataValue implements Serializable {

	private static final long serialVersionUID = -7703046472403151141L;
	private String code;
	private String term;
	private String definition;
	private boolean standard;
	
	public MetadataValue(String code, String term, String definition){
		this.code = code;
		this.term = term;
		this.definition = definition;
	}
	
	public MetadataValue(){
	}
	
	public String getCode() {
		return this.code;
	}
	public String getTerm() {
		return this.term;
	}
	public String getDefinition() {
		return this.definition;
	}
	public boolean isStandard() {
		return this.standard;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	public void setDefinition(String def){
		this.definition = def;
	}
	public void setTerm(String term){
		this.term = term;
	}
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
}
