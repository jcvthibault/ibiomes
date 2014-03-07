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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * List of metadata values
 * @author Julien Thibault
 *
 */
@XmlRootElement (name="metadataValues")
@XmlSeeAlso({MetadataValue.class})
public class MetadataValueList extends ArrayList<MetadataValue> implements Serializable {

	private static final long serialVersionUID = 7429514572306357963L;
	
	public MetadataValueList(){
	}
	
	@XmlElement(name="metadataValue")
	public List<MetadataValue> getValues(){
		return this;
	}
	
	/**
	 * Check if the list contains a metadata value with the given code.
	 * @param valCode Value code
	 * @return True if the list contains a metadata value with the given code.
	 */
	public boolean containsCode(String valCode)
	{
		boolean found = false;
		int i = 0;
		while (!found && i < this.size()){
			if (this.get(i).getCode().equals(valCode))
				found = true;
			i++;
		}
		return found;
	}
}
