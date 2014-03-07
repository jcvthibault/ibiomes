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

package edu.utah.bmi.ibiomes.grid.test;

import java.io.File;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.grid.test.common.TestCommon;
import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFactory;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.pub.IBIOMESPublisher;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

/**
 * Test suite to publish experiment to iBIOMES with XML parser rule descriptor
 * @author Julien Thibault, University of Utah
 *
 */
public class TestPublishWithDescriptor
{
	private static final String[] gaussianCollections = {
			TestCommon.TEST_DATA_DIR + "/gaussian/acac", 
			TestCommon.TEST_DATA_DIR + "/gaussian/fosfina", 
			TestCommon.TEST_DATA_DIR + "/gaussian/gli", 
			TestCommon.TEST_DATA_DIR + "/gaussian/N6-SH2",
			TestCommon.TEST_DATA_DIR + "/gaussian/modredundant",  
			TestCommon.TEST_DATA_DIR + "/gaussian/clx2-multi", 
			TestCommon.TEST_DATA_DIR + "/gaussian/PDTO"};
	
	private static final String[] nwchemCollections = {
			TestCommon.TEST_DATA_DIR + "/nwchem/n2_ccsd",
			TestCommon.TEST_DATA_DIR + "/nwchem/oniom1",
			TestCommon.TEST_DATA_DIR + "/nwchem/prop_ch3f",
			TestCommon.TEST_DATA_DIR + "/nwchem/dft_bsse",
			TestCommon.TEST_DATA_DIR + "/nwchem/ch5n_nbo",
			TestCommon.TEST_DATA_DIR + "/nwchem/water",
		};
	
	private static final String[] amberCollections = {
			TestCommon.TEST_DATA_DIR + "/amber/1BIVm1", 
			TestCommon.TEST_DATA_DIR + "/amber/rnamod_drd",
			TestCommon.TEST_DATA_DIR + "/amber/tutorial3",
			TestCommon.TEST_DATA_DIR + "/amber/tutorial1/a-dna",
			TestCommon.TEST_DATA_DIR + "/amber/2H49",
			TestCommon.TEST_DATA_DIR + "/amber/qm_mm",
			TestCommon.TEST_DATA_DIR + "/amber/kang_Na_orig"
		};
	
	private static final String amberDescriptor 	= TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_amber.xml";
	private static final String gaussianDescriptor 	= TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_gaussian.xml";
	private static final String nwchemDescriptor 	= TestCommon.TEST_DATA_DIR + "/project_descriptor/desc_nwchem.xml";

	
	//@Test
	public void publishWithDescriptor() throws Exception
	{
		IRODSAccessObjectFactory aoFactory = null;
		try{
			IRODSConnector cnx = TestIRODSConnector.getTestConnector();
			aoFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
			IRODSAccount acc = cnx.getAccount();
			IBIOMESPublisher publisher = new IBIOMESPublisher(acc, aoFactory);

			System.out.println("User " + acc.getUserName() + " connected to "+acc.getZone()+"@"+acc.getHost());
			String IBIOMES_DIR = TestCommon.getProperty(TestCommon.PROP_IRODS_TEST_USER_HOME1);
			
			// GAUSSIAN QM
			DirectoryStructureDescriptor desc = new DirectoryStructureDescriptor(gaussianDescriptor);
			for (int c=0;c<gaussianCollections.length;c++){
				System.out.println("Publishing Gaussian experiment "+gaussianCollections[c]+"...");
				File dir = new File(gaussianCollections[c]);
				ExperimentFactory expFactory = new ExperimentFactory(dir.getAbsolutePath());
				ExperimentFolder experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata( Software.GAUSSIAN, desc, null );
				publisher.publishExperiment(experimentFolder.getFileDirectory(), IBIOMES_DIR + "/qm_gaussian_"+ dir.getName(), true);
			}
			
			// NWCHEM QM
			desc = new DirectoryStructureDescriptor(nwchemDescriptor);
			for (int c=0;c<nwchemCollections.length;c++){
				System.out.println("Publishing NWChem experiment "+nwchemCollections[c]+"...");
				File dir = new File(nwchemCollections[c]);
				ExperimentFactory expFactory = new ExperimentFactory(dir.getAbsolutePath());
				ExperimentFolder experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata( Software.NWCHEM, desc, null );
				publisher.publishExperiment(experimentFolder.getFileDirectory(), IBIOMES_DIR + "/qm_nwchem_"+ dir.getName(), true);
			}
			
			// AMBER MD
			desc = new DirectoryStructureDescriptor(amberDescriptor);
			for (int c=0;c<amberCollections.length;c++){
				System.out.println("Publishing AMBER experiment "+amberCollections[c]+"...");
				File dir = new File(amberCollections[c]);
				ExperimentFactory expFactory = new ExperimentFactory(dir.getAbsolutePath());
				ExperimentFolder experimentFolder = expFactory.parseDirectoryForExperimentWorkflowAndMetadata( Software.AMBER, desc, null );
				publisher.publishExperiment(experimentFolder.getFileDirectory(), IBIOMES_DIR + "/md_amber_"+ dir.getName(), true);
			}
			
		/*	
			System.out.println("\n======================================================================");
			System.out.println("                            AMBER (SET OF EXPERIMENTS)\n\n");
			System.out.println("----------------------------------------------------------------------");
			
			//create collection of experiments
			ExperimentSet set = new ExperimentSet("protein_A12");
			
			coll_amber = new AMBERCollection("/amber/A12_ES", xmlDescFileAmber);
			coll_amber.addForceField("AMBER 99");
			set.addExperiment(coll_amber);
			
			coll_amber = new AMBERCollection("/amber/A12B", xmlDescFileAmber);
			coll_amber.addForceField("AMBER 99SB");
			coll_amber.setDescription("A12B");
			set.addExperiment(coll_amber);
			
			coll_amber = new AMBERCollection("/amber/A_ES", xmlDescFileAmber);
			coll_amber.addForceField("AMBER 99SB");
			coll_amber.setDescription("A_ES");
			set.addExperiment(coll_amber);
	
			coll_amber = new AMBERCollection("/amber/A12B12_ES", xmlDescFileAmber);
			coll_amber.addForceField("AMBER 94");
			coll_amber.setDescription("A12B12_ES");
			set.addExperiment(coll_amber);
			
			set.setDescription("Set of MD simulation runs with AMBER");
			set.copyAndPublish(true);
			
			System.out.println("----------------------------------------------------------------------");*/
			
			//close connection to iRODS
			aoFactory.closeSessionAndEatExceptions();
			
			System.exit(0);
		}
		catch(Exception e){
			e.printStackTrace();
			if (aoFactory!=null){
				//close connection to iRODS
				aoFactory.closeSessionAndEatExceptions();
			}
		}
	}
}
