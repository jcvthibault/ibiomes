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
 * Metadata attribute
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="metadataAttribute")
public class MetadataAttribute implements Serializable {

	private static final long serialVersionUID = -2393718670655366820L;
	private String code;
	private String term;
	private String definition;
	private String type;
	private String ontologyValueTerm;
	private boolean standard;

	public MetadataAttribute(){
	}
	
	/**
	 * New AVU attribute
	 * @param code Code (ID)
	 * @param term Term
	 * @param definition Textual definition of attribute
	 * @param type Type expected for the values associated to this attribute
	 */
	public MetadataAttribute(String code, String term, String definition, String type){
		this.code = code;
		this.term = term;
		this.definition = definition;
		this.type = type;
	}
	
	/**
	 * New AVU attribute
	 * @param code Code (ID)
	 * @param term Term
	 * @param definition Textual definition of attribute
	 * @param type Type expected for the values associated to this attribute
	 * @param concept Ontology concept representing the parent class for permissible values  
	 */
	public MetadataAttribute(String code, String term, String definition, String type, String concept){
		this.code = code;
		this.term = term;
		this.definition = definition;
		this.type = type;
		this.ontologyValueTerm = concept;
	}
	
	/**
	 * Get attribute code
	 * @return Attribute code
	 */
	public String getCode() {
		return this.code;
	}
	/**
	 * Set attribute code
	 * @param code
	 */
	public void setCode(String code){
		this.code = code;
	}
	/**
	 * Get attribute term
	 * @return Attribute term
	 */
	public String getTerm() {
		return this.term;
	}
	/**
	 * Set attribute term
	 * @param term Attribute term
	 */
	public void setTerm(String term){
		this.term = term;
	}
	/**
	 * Get attribute definition
	 * @return Attribute definition
	 */
	public String getDefinition() {
		return this.definition;
	}
	/**
	 * Set attribute definition
	 * @param def Attribute definition
	 */
	public void setDefinition(String def){
		definition = def;
	}
	/**
	 * Get attribute value type
	 * @return Attribute value type
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * Set attribute value type
	 * @param type Attribute value type
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * Check if this attribute is standard (i.e. defined in the iBIOMES catalog)
	 * @return True if standard
	 */
	public boolean isStandard() {
		return this.standard;
	}
	/**
	 * Set standard flag (i.e. if the attribute is defined in the iBIOMES catalog or not)
	 * @param standard Whether this attribute is standard
	 */
	public void setStandard(boolean standard) {
		this.standard = standard;
	}
	/**
	 * Get ontology term representing the top concept for possible values 
	 * @return Ontology term representing the top concept for possible values 
	 */
	public String getOntologyValueTerm() {
		return ontologyValueTerm;
	}

	/**
	 * Set ontology term representing the top concept for possible values 
	 * @param ontologyValueTerm Ontology term representing the top concept for possible values 
	 */
	public void setOntologyValueTerm(String ontologyValueTerm) {
		this.ontologyValueTerm = ontologyValueTerm;
	}
	
	@Override
	public String toString(){
		return ("[" + this.code + "] " + this.term + " (standard="+standard+")");
	}
}
