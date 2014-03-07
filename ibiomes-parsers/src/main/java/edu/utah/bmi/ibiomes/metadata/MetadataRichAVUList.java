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
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * List of rich metadata AVUs
 * @author Julien Thibault
 *
 */
@XmlRootElement (name="AVUs")
@XmlSeeAlso({MetadataAVU.class})
public class MetadataRichAVUList extends ArrayList<MetadataRichAVU> implements Serializable {

	private static final long serialVersionUID = -2599403472786393224L;

	public MetadataRichAVUList(){
	}
	
	@XmlElement(name="AVU")
	public List<MetadataRichAVU> getAVUs(){
		return this;
	}
	
	/**
	 * Find the attributes defined in this list
	 * @return a list of attributes or an empty list.
	 */
	public List<MetadataAttribute> getAttributes()
	{
		ArrayList<MetadataAttribute> attributes = new ArrayList<MetadataAttribute>();
		for (MetadataRichAVU pair : this){
			boolean exists = false;
			for (MetadataAttribute attr : attributes){
				if (pair.getAttribute().getCode().toUpperCase().equals(attr.getCode().toUpperCase())){
					exists = true;
				}
			}
			if (!exists)
				attributes.add(pair.getAttribute());
		}
		return attributes;
	}
	
	/**
	 * Add AVU to list only if it doesn't exist already (no duplicate allowed).
	 */
	@Override
	public boolean add(MetadataRichAVU avu){
		if (!this.hasPair(avu)){
			return super.add(avu);
		}
		else return false;
	}
	
	/**
	 * Add AVUs to list only if they don't exist already (no duplicate allowed).
	 */
	@Override
	public boolean addAll(Collection<? extends MetadataRichAVU> avuList)
	{
		for (MetadataRichAVU avu : avuList){
			this.add(avu);
		}
		return true;
	}
	
	
	
	/**
	 * Finder the values for a given metadata attribute.
	 * @param attributeCode
	 * @return a list of values or an empty list.
	 */
	public List<MetadataValue> getValues(String attributeCode)
	{
		ArrayList<MetadataValue> values = new ArrayList<MetadataValue>();
		for (int i=0; i<this.size(); i++){
			if (this.get(i).getAttribute().getCode().toUpperCase().equals(attributeCode.toUpperCase())){
				values.add(this.get(i).getValue());
			}
		}
		return values;
	}
	
	/**
	 * Finder a single value for a given metadata attribute. Returns null if the attribute was not found.
	 * @param attributeCode
	 * @return Metadata value.
	 */
	public MetadataValue getValue(String attributeCode)
	{
		MetadataValue value = null;
		int i = 0;
		boolean found = false;
		while (i < this.size() && !found)
		{
			if (this.get(i).getAttribute().getCode().toUpperCase().equals(attributeCode.toUpperCase())){
				value = this.get(i).getValue();
				found = true;
			}
			i++;
		}
		return value;
	}
	
	/**
	 * Check if the given metadata attribute has at least one associated value.
	 * @param attributeCode
	 * @return True if the given metadata attribute has at least one associated value
	 */
	public boolean hasValues(String attributeCode)
	{
		int i = 0;
		boolean found = false;
		while (i < this.size() && !found)
		{
			if (this.get(i).getAttribute().getCode().toUpperCase().equals(attributeCode.toUpperCase())){
				found = true;
			}
			i++;
		}
		return found;
	}
	
	/**
	 * Check if the given metadata pair is already in the list.
	 * @param avu Metadata AVU
	 * @return True if the given metadata pair is already in the list.
	 */
	public boolean hasPair(MetadataRichAVU avu)
	{
		String attribute = avu.getAttribute().getCode();
		String value = avu.getValue().getCode();
		int i = 0;
		boolean found = false;
		while (i < this.size() && !found)
		{
			if ( (this.get(i).getAttribute().getCode().toUpperCase().equals(attribute.toUpperCase()))
					&& (this.get(i).getValue().getCode().toUpperCase().equals(value.toUpperCase())) ){
				found = true;
			}
			i++;
		}
		return found;
	}
	
	/**
	 * Check if the given metadata pair is already in the list.
	 * @param avu Metadata AVU
	 * @return Index of pair in the list.
	 */
	public int indexOfPair(MetadataAVU avu)
	{
		String attribute = avu.getAttribute();
		String value = avu.getValue();
		int i = 0;
		boolean found = false;
		while (i < this.size() && !found)
		{
			if ( (this.get(i).getAttribute().getCode().toUpperCase().equals(attribute.toUpperCase()))
					&& (this.get(i).getValue().getCode().toUpperCase().equals(value.toUpperCase())) ){
				found = true;
			}
			i++;
		}
		if (!found)
			i = -1;
		return i;
	}
	
	/**
	 * Remove AVUs with the given attribute
	 * @param attribute Attribute name
	 */
	public void removeAVUWithAttribute(String attribute){
		int n = this.size();
		for (int i=n-1; i>=0; i--){
			if (this.get(i).getAttribute().getCode().toLowerCase().equals(attribute.toLowerCase())){
				this.remove(i);
			}
		}
	}
	
	/**
	 * Filter list of metadata using list of requested attributes
	 * @param attributes List of attributes to keep
	 * @return Filtered list of metadata
	 */
	public MetadataRichAVUList filter(List<String> attributes){
		MetadataRichAVUList restrictedList = new MetadataRichAVUList();
		for (MetadataRichAVU avu : this){
			if (attributes.contains(avu.getAttribute().getCode())){
				restrictedList.add(avu);
			}
		}
		return restrictedList;
	}
	
	/**
	 * Get list of topology metadata AVUs
	 * @return List of topology metadata AVUs
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public MetadataRichAVUList getTopologyMetadata() throws IllegalArgumentException, IllegalAccessException{
		return this.filter(TopologyMetadata.getMetadataAttributes());
	}
	
	/**
	 * Get list of trajectory metadata AVUs
	 * @return List of trajectory metadata AVUs.
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public MetadataRichAVUList getTrajectoryMetadata() throws IllegalArgumentException, IllegalAccessException{
		return this.filter(TrajectoryMetadata.getMetadataAttributes());
	}
	
	/**
	 * Get list of method (e.g MD, QM) metadata AVUs
	 * @return List of method (e.g MD, QM) metadata AVUs
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public MetadataRichAVUList getMethodMetadata() throws IllegalArgumentException, IllegalAccessException{
		return this.filter(MethodMetadata.getMetadataAttributes());
	}
	
	/**
	 * Get list of general metadata AVU
	 * @return list of general metadata AVU
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public MetadataRichAVUList getGeneralMetadata() throws IllegalArgumentException, IllegalAccessException{
		return this.filter(GeneralMetadata.getMetadataAttributes());
	}
	
	/**
	 * Get list of platform (software/hardware) metadata AVU
	 * @return List of platform (software/hardware) metadata AVU
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public MetadataRichAVUList getPlatformMetadata() throws IllegalArgumentException, IllegalAccessException{
		return this.filter(PlatformMetadata.getMetadataAttributes());
	}
	
	/**
	 * Convert list of metadata to a string
	 */
	public String toString(){
		String res = "Metadata\n";
		for (MetadataRichAVU pair : this){
			res = res + "\t" + pair.toString() + "\n";
		}
		return res;
	}
}
