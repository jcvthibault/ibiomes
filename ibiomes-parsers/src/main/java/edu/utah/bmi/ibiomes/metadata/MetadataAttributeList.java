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
 * List of metadata attributes
 * @author Julien Thibault
 *
 */
@XmlRootElement (name="metadataAttributes")
@XmlSeeAlso({MetadataAttribute.class})
public class MetadataAttributeList extends ArrayList<MetadataAttribute> implements Serializable {
	
	private static final long serialVersionUID = 4478819661122501613L;

	public MetadataAttributeList(){
	}
	
	@XmlElement(name="metadataAttribute")
	public List<MetadataAttribute> getAttributes(){
		return this;
	}
}
