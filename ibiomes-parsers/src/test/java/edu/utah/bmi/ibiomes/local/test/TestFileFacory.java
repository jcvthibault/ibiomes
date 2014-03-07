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

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.LocalFileFactory;

public class TestFileFacory {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
		LocalFileFactory factory = LocalFileFactory.instance();
		LocalFile file = null;
		
		file = factory.getFile(TestCommon.TEST_DATA_DIR + "/images/add_48.png", null);
		System.out.println(file.getMetadata());
		
		file = factory.getFile(TestCommon.TEST_DATA_DIR + "/amber/1BIVm1/solvtd.topo", "AMBER");
		System.out.println(file.getMetadata());

		file = factory.getFile(TestCommon.TEST_DATA_DIR + "/amber/1BIVm1/1BIVm1.pdb", null);
		System.out.println(file.getMetadata());

		file = factory.getFile(TestCommon.TEST_DATA_DIR + "/amber/rnamod_drd/analysis/summary.EPTOT.csv", null);
		System.out.println(file.getMetadata());
		
		file = factory.getFile(TestCommon.TEST_DATA_DIR + "/nanoparticles/Molecule_20_PEI-PAMAM.sdf", null);
		System.out.println(file.getMetadata());
	}

}
