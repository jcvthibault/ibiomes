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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.conf.IBIOMESConfiguration;
import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;
import edu.utah.bmi.ibiomes.metadata.MetadataAttributeList;
import edu.utah.bmi.ibiomes.metadata.MetadataSqlConnector;
import edu.utah.bmi.ibiomes.metadata.MetadataValue;
import edu.utah.bmi.ibiomes.metadata.MetadataValueList;

/**
 * Tests for SQL queries on dictionaries
 * @author Julien Thibault
 *
 */
public class DictionariesTest {

	private void printResults(String attr, MetadataValueList values){
		System.out.println("\n" + attr.toUpperCase());
		for (MetadataValue val : values){
			System.out.println("\t" + val.getTerm());
		}
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		IBIOMESConfiguration.getInstance(TestCommon.TEST_IBIOMES_CONFIG_FILE, true);
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		
		DictionariesTest test = new DictionariesTest();
		MetadataSqlConnector sql = (MetadataSqlConnector) context.getBean("metadataSqlConnector");
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tMETADATA VALUE SEARCH");
		System.out.println("---------------------------------------------------------------------------");
		MetadataAttribute attrTarget = sql.getAttributeByCode("SOFTWARE_NAME");
		MetadataValueList valList = sql.getAttributeValuesWithFilter(attrTarget.getCode(), "am");
		for (MetadataValue val : valList){
			System.out.println("\t" + val.getTerm());
		}
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tMETADATA ATTRIBUTES");
		System.out.println("---------------------------------------------------------------------------");
		MetadataAttributeList attrList = sql.getAllAttributes();
		for (MetadataAttribute attr : attrList){
			System.out.println(attr.getCode() + ": " + attr.getTerm() + " (" + attr.getDefinition() + ")");
			
			valList = sql.getAttributeValues(attr.getCode());
			for (MetadataValue val : valList){
				System.out.println("\t" + val.getTerm());
			}
		}
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tHARDWARE / SOFTWARE");
		System.out.println("---------------------------------------------------------------------------");

		test.printResults("CPU architectures", sql.getCpuArchitectureList());
		test.printResults("Hardware makes", sql.getHardwareMakeList());
		test.printResults("Operating systems", sql.getOperatingSystemList());
		test.printResults("Software", sql.getSoftwareList());
		test.printResults("File formats", sql.getFileFormatList());
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tAUTHORSHIP");
		System.out.println("---------------------------------------------------------------------------");
		
		test.printResults("Structure databases", sql.getStructureDbList());
		test.printResults("Literature databases", sql.getLiteratureDbList());
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tMETHODS");
		System.out.println("---------------------------------------------------------------------------");

		test.printResults("Methods", sql.getMethodList());
		test.printResults("Boundary conditions", sql.getBoundaryConditionList());
		test.printResults("Solvent types", sql.getSolventTypeList());
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tQUANTUM MECHANICS");
		System.out.println("---------------------------------------------------------------------------");
		
		test.printResults("Basis set types", sql.getQmBasisSetTypeList());
		test.printResults("Basis sets", sql.getQmBasisSetList());
		test.printResults("Levels of theory", sql.getQmMethodList());
		test.printResults("Families of QM methods", sql.getQmMethodFamilyList());
		
		System.out.println("\n---------------------------------------------------------------------------");
		System.out.println("\tMOLECULAR DYNAMICS");
		System.out.println("---------------------------------------------------------------------------");
		
		test.printResults("Force fields", sql.getMdForceFieldList());
		test.printResults("Electrostatics models", sql.getMdElectrostaticsList());
		test.printResults("Constraint algorithms", sql.getMdConstraintList());
		test.printResults("Unit shapes", sql.getMdUnitShapeList());
		test.printResults("MM integrators", sql.getMdIntegratorsList());
		test.printResults("Ensembles", sql.getEnsembleList());
		test.printResults("Barostat", sql.getBarostatList());
		test.printResults("Thermostat", sql.getThermostatList());
	}
}
