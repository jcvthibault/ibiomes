/* iBIOMES - Integrated Biomolecular Simulations
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

package edu.utah.bmi.ibiomes.local.test.parser;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.local.test.TestCommon;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.CSVFile;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;

public class CSVParserTest{

	private final Logger logger = Logger.getLogger(CSVParserTest.class);

	public CSVParserTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void test() throws Exception
	{
		MetadataAVUList md = null;
		LocalFileFactory ffactory;
	
		ffactory = LocalFileFactory.instance();
	
		String[] inputFileNames = {
				"/amber/rnamod_drd/analysis/summary.ETOT.csv", 
				"/amber/tutorial3/analysis/summary.EKTOT",
				"/csv/3r1b-m3.1-8.2d.dat",
				"/csv/autocorr.dat",
				"/csv/heatmap.csv",
				"/csv/rmsd.csv"
		};
		
		// CSV files
		for (int f=0; f<inputFileNames.length; f++)
		{
			String inputFile = TestCommon.TEST_DATA_DIR + inputFileNames[f];
			System.out.println("Parsing CSV file " + inputFile);
			CSVFile csv  = new CSVFile(inputFile);
			md = csv.getMetadata();
			logger.info(md.toString());
			
			//with provided metadata
			csv  = new CSVFile(inputFile);
			MetadataAVUList overriding = new MetadataAVUList();
			overriding.add(new MetadataAVU(CSVFile.DATA_LABELS, "Time,Total energy"));
			overriding.add(new MetadataAVU(CSVFile.DATA_UNITS, "ps,Kcal/mol"));
			csv.setExtendedAttributes(overriding);
			logger.info(csv.getMetadata().toString());
			
			//best guess on format
			LocalFile inpGen  = ffactory.getFile(inputFile, null);
			logger.debug("----------------------------------------");
			md = inpGen.getMetadata();
			logger.info(md.toString());
			
			//format is provided
			inpGen  = ffactory.getFileInstanceFromFormat(inputFile, LocalFile.FORMAT_CSV);
			logger.debug("----------------------------------------");
			md = inpGen.getMetadata();
			logger.info(md.toString());
		}
	}
}
