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

package edu.utah.bmi.ibiomes.topo.bio;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;

/**
 * Standard residue mapper
 * @author Julien Thibault, University of Utah
 *
 */
public class ResidueCodeMapper 
{
	private static final String _dataFile = System.getenv().get("IBIOMES_HOME") + "/" + "data/residues-std.csv";
	
	private HashMap<String, String> hashResidues;
	private static ResidueCodeMapper _instance = null;
	
	
	private ResidueCodeMapper() throws IOException, CompressorException 
	{
		hashResidues = new HashMap<String, String>();
		IBIOMESFileReader br =  new IBIOMESFileReader(new File(_dataFile));
	    String line = null;
        while (( line = br.readLine()) != null){
          String[] entries = line.split("\\,");
          hashResidues.put(entries[0].toUpperCase(), entries[1]);
        }
        br.close();
	}
	
	public static ResidueCodeMapper getInstance() throws IOException, CompressorException {

		if (_instance == null)
			_instance = new ResidueCodeMapper();
		
		return _instance;
	}
	
	/**
	 * Get one letter code from 3 letter code
	 * @param code 3-letter code
	 * @return 1-letter code if mapped, null otherwise.
	 */
	public String getStandardCode(String code){
		if (code == null)
			return null;
		else
			return hashResidues.get(code.toUpperCase());
	}
}
