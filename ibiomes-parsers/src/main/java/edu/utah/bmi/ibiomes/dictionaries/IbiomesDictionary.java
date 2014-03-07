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
package edu.utah.bmi.ibiomes.dictionaries;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** 
 * Abstract class for metadata value dictionaries
 * @author Julien Thibault
 *
 */
public abstract class IbiomesDictionary {
	
	/**
	 * Get list of all values in dictionary
	 * @return List of all values in dictionary
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected static String[] values() throws IllegalArgumentException, IllegalAccessException{
		return IbiomesDictionary.values(IbiomesDictionary.class);
	}
	/**
	 * Get list of all values in given class
	 * @return List of all values in dictionary
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	protected static String[] values(Class myclass) throws IllegalArgumentException, IllegalAccessException{
		Field[] fields = myclass.getDeclaredFields();
		String[] values = new String[fields.length]; 
		for (int f=0; f<fields.length; f++){
			values[f] = (String)fields[f].get(null);
		}
		return values;
	}
	
	protected static List<String> getMetadataAttributes(Class myclass) throws IllegalArgumentException, IllegalAccessException {
		List<String> metadataList = new ArrayList<String>();
		String[] attributes = IbiomesDictionary.values(myclass);
		for (int i=0; i< attributes.length; i++){
			metadataList.add(attributes[i]);
		}
		return metadataList;
	}
}
