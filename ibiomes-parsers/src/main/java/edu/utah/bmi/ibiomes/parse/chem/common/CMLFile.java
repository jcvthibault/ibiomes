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

package edu.utah.bmi.ibiomes.parse.chem.common;

import java.io.IOException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.parse.chem.AbstractTopologyFile;

/**
 * Chemical Markup Language (CML) file
 * @author Julien Thibault
 *
 */
public class CMLFile extends AbstractTopologyFile {

	private static final long serialVersionUID = 4657993466367453269L;
	
	private String _compoundInfo;
	
	/**
	 * Load CML file
	 * @param pathname File path
	 * @throws Exception 
	 */
	public CMLFile(String pathname) throws Exception {
		super(pathname, FORMAT_CML);
		this.parseFile();
	}
	
	/**
	 * Set compound info
	 * @param compnd compound info
	 */
	public void setCompoundInfo(String compnd){
		_compoundInfo = compnd;
	}
	
	/**
	 * Get compound info
	 * @return compound info
	 */
	public String getCompoundInfo(){
		return _compoundInfo;
	}
	
	/**
	 * Parse CML file to get topology information
	 * @throws IOException
	 */
	private void parseFile() throws Exception {
		IBIOMESFileReader br = null;
	    try{
	    	br = new IBIOMESFileReader(this);
			//TODO
			br.close(); 
		}
	    catch (Exception e){
			e.printStackTrace();
			if (br!=null)
				try {
					br.close();
				} catch (IOException e1) {
				}
			throw e;
		}
	}

}
