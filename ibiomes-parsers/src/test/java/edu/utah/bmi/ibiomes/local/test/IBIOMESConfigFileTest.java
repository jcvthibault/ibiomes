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

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;

/**
 * Test suite for descriptor files defining file extended attributes and parsing rules 
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESConfigFileTest{

	private final Logger logger = Logger.getLogger(IBIOMESConfigFileTest.class);

	String[] descriptors = {
			"/project_descriptor/parser1.properties",
			"/project_descriptor/parser2.properties",
			"/project_descriptor/parser3.properties",
			"/project_descriptor/parser4.properties",
			"/project_descriptor/parser5.properties"
		};

	@Test
	public void testParserConfigFileLoad() throws Exception{
		
		for (int d=0; d<descriptors.length;d++)
		{
			logger.info("Loading parser config file: " + TestCommon.TEST_DATA_DIR + descriptors[d]);
			IBIOMESConfiguration config = IBIOMESConfiguration.getInstance(TestCommon.TEST_DATA_DIR + descriptors[d], true);
			String softwareContext = config.getDefaultSoftwareContext();
			DirectoryStructureDescriptor desc = config.getDefaultParserRuleFile();
			if (softwareContext!=null)
				logger.debug("Default software context: " + softwareContext);
			if (desc!=null)
				logger.debug("Default parser rule file: " + desc.getAbsolutePath());
		}
	}
}
