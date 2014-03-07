package edu.utah.bmi.ibiomes.local.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.utah.bmi.Utils;
import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;

/**
 * Test to check operating system
 * @author Julien Thibault, University of Utah
 *
 */
public class TestOS{

	private final Logger logger = Logger.getLogger(TestOS.class);

	@Test
	public void testOSCheck() throws Exception{
		System.out.println(Utils.getOperatingSystem());
	}
}
