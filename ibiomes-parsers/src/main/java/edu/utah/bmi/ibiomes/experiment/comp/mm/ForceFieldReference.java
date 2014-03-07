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

package edu.utah.bmi.ibiomes.experiment.comp.mm;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Force field reference (published)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class ForceFieldReference {
	
	private String name;
	private List<String> authors;
	private Date publicationDate;
	private String type;
	
	public ForceFieldReference(){
	}
	
	/**
	 * Get force field name
	 * @return Force field name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set force field name
	 * @param name Force field name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get list of authors
	 * @return List of authors
	 */
	public List<String> getAuthors() {
		return authors;
	}
	
	/**
	 * Set list of authors
	 * @param authors List of authors
	 */
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	/**
	 * Get publication date
	 * @return Publication date
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}
	
	/**
	 * Set publication date
	 * @param publicationDate Publication date
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	
	/**
	 * Get force field type
	 * @return Force field type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Set force field type
	 * @param type Force field type
	 */
	public void setType(String type) {
		this.type = type;
	}
}
