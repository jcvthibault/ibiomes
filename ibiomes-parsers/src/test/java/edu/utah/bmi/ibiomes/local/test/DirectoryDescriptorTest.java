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

package edu.utah.bmi.ibiomes.local.test;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureRule;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;

/**
 * Test suite for descriptor files defining file extended attributes and parsing rules 
 * @author Julien Thibault, University of Utah
 *
 */
public class DirectoryDescriptorTest{

	private final Logger logger = Logger.getLogger(DirectoryDescriptorTest.class);

	String[] descriptors = {
			"/project_descriptor/desc_abc2.xml",
			"/project_descriptor/desc_amber.xml", 
			"/project_descriptor/desc_gaussian.xml",
			"/project_descriptor/desc_nwchem.xml"
		};

	public DirectoryDescriptorTest() throws Exception{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
	}
	
	@Test
	public void test() throws Exception{

		for (int d=0; d<descriptors.length;d++)
		{
			logger.info("Parsing rule descriptor: " + descriptors[d]);
			DirectoryStructureDescriptor desc = new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + descriptors[d]);
			List<DirectoryStructureRule> rules = desc.getRules();
			if (rules != null){
				String rulesStr = "";
				for (DirectoryStructureRule rule : rules){
					rulesStr+=rule.toString() + "\n";
				}
				logger.debug(rulesStr);
			}
		}
	}
	
	@Test
	public void testFileClasses() throws Exception {
		String[] test_descriptors = {
				"/project_descriptor/test/test_classes1.xml",
				"/project_descriptor/test/test_classes2.xml"};
		
		LocalDirectory coll = null;
		MetadataAVUList md = null;
		DirectoryStructureDescriptor desc = null;

		logger.info("Testing class definitions in XML descriptors:");
		try{
			logger.info("With descriptor "+test_descriptors[0]+"...");
			desc = new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + test_descriptors[0]);
			ExperimentFactory factory = new ExperimentFactory(TestCommon.TEST_DATA_DIR + "/project_descriptor/test/experiment");
			coll = factory.parseDirectoryForMetadata(Software.AMBER, desc, null);
			md = coll.getMetadata();
			logger.info(md.toString());
			assertEquals("Expected AVU: " + ExperimentMetadata.TOPOLOGY_FILE_PATH + " = topo1.top" , 
					md.getValue(ExperimentMetadata.TOPOLOGY_FILE_PATH), 
					"topo1.top");

			logger.info("With descriptor "+test_descriptors[1]+"...");
			desc = new DirectoryStructureDescriptor(TestCommon.TEST_DATA_DIR + test_descriptors[1]);
			factory = new ExperimentFactory(TestCommon.TEST_DATA_DIR + "/project_descriptor/test/experiment");
			coll = factory.parseDirectoryForMetadata(Software.AMBER, desc, null);
			md = coll.getMetadata();
			logger.info(md.toString());
			assertEquals("Expected AVU: " + ExperimentMetadata.TOPOLOGY_FILE_PATH + " = topo2.top" , 
					md.getValue(ExperimentMetadata.TOPOLOGY_FILE_PATH), 
					"topo2.top");
			
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}

}
