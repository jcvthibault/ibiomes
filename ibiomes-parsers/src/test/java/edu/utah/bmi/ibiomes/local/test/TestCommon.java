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

package edu.utah.bmi.ibiomes.local.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.compress.compressors.CompressorException;

import edu.utah.bmi.ibiomes.io.IBIOMESFileReader;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Common variables and methods for the test suites 
 * @author Julien Thibault, University of Utah
 *
 */
public class TestCommon {
	
	public final static String TEST_DATA_DIR = System.getenv("IBIOMES_HOME") + "/test";
	public final static String TEST_IBIOMES_CONFIG_FILE = TEST_DATA_DIR + "/config/ibiomes-parser.properties";
	
	/**
	 * Compare list of metadata to reference metadata
	 * @param refMetadata Reference
	 * @param sysMetadata
	 * @return
	 */
	public static boolean compareMetadata(MetadataAVUList refMetadata, MetadataAVUList sysMetadata)
	{
		boolean haNosError = true;
		for (MetadataAVU avu : refMetadata){
			if (!sysMetadata.hasPair(avu) && !hasSameFloatValue(sysMetadata, avu)){
				System.out.println("Missing AVU: " + avu.toString());
				haNosError = false;
			}
		}
		for (MetadataAVU avu : sysMetadata){
			if (!refMetadata.hasPair(avu) && !hasSameFloatValue(refMetadata, avu)){
				System.out.println("Extra AVU found: " + avu.toString());
				haNosError = false;
			}
		}
		return haNosError;
	}
	
	/**
	 * 
	 * @param metadata
	 * @param avu
	 * @return
	 */
	private static boolean hasSameFloatValue(MetadataAVUList metadata, MetadataAVU avu) {
		
		try {
			double fValue1 = 0.0;
			fValue1 = Double.parseDouble(avu.getValue());
			fValue1 = Math.round(fValue1*1000)/1000;
			List<String> values = metadata.getValues(avu.getAttribute());
			if (values!=null){
				for (String value : values){
					double fValue2 = Double.parseDouble(value);
					fValue2 = Math.round(fValue2*1000)/1000;
					if (fValue2 == fValue1)
						return true;
				}
			}
			return false;
		}
		catch (NumberFormatException e){
			return false;
		}
		
	}

	/**
	 * Compare list of metadata to reference metadata loaded from file
	 * @param sysMetadata
	 * @param filePath File path
	 * @return
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public static boolean compareMetadataToExpected(MetadataAVUList sysMetadata, String filePath) throws IOException, CompressorException
	{
		MetadataAVUList refMetadata = loadExpectedMetadataFromFile(filePath);
		return compareMetadata(refMetadata, sysMetadata);
	}
	
	/**
	 * Load list of metadata from file
	 * @param filePath Path to file
	 * @return List of metadata
	 * @throws IOException 
	 * @throws CompressorException 
	 */
	public static MetadataAVUList loadExpectedMetadataFromFile(String filePath) throws IOException, CompressorException{
		MetadataAVUList metadata = new MetadataAVUList();
		File file = new File(filePath);
		String line = null;
		IBIOMESFileReader br = new IBIOMESFileReader(file);
		while ((line = br.readLine()) != null){
			line = line.trim();
			if (line.length()>0){
				MetadataAVU avu = parseLineForAVU(line);
				metadata.add(avu);
			}
		}
		br.close();
		return metadata;
	}
	
	/**
	 * Parse AVU from file line
	 * @param line
	 * @return AVU
	 */
	private static MetadataAVU parseLineForAVU(String line){
		int index = line.indexOf('=');
		if (index>0){
			MetadataAVU avu = new MetadataAVU(line.substring(0,index), line.substring(index+1));
			return avu;
		}
		return null;
	}
}
