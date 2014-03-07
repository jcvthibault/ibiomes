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

package edu.utah.bmi.ibiomes.metadata;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * SQL interface for the metadata schema database
 * @author Julien Thibault
 *
 */
public class MetadataSqlConnector {

	private final Logger logger = Logger.getLogger(MetadataSqlConnector.class);

	private DataSource dataSource;
	 
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public enum MD_ATTRIBUTE_TYPES {
		STRING, BOOLEAN, INTEGER, FLOAT;
	}
	
	public MetadataSqlConnector()
	{
	}
	
	/**
	 * Retrieve metadata attribute information from its code
	 * @param attrCode Metadata attribute code
	 * @return Metadata attribute
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataAttribute getAttributeByCode(String attrCode) throws SQLException, ClassNotFoundException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_attribute_bycode( ? ) }");
		proc.setString(1, attrCode);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		if (results.next())
		{
			MetadataAttribute rec = new MetadataAttribute(
					results.getString("code"), 
					results.getString("term"), 
					results.getString("definition"),
					results.getString("type"),
					results.getString("term_id"));
			con.close();
			return rec;
		}
		else {
			con.close();
			return null;
		}
	}
	
	/**
	 * Retrieve all the possible values for a given attribute
	 * @param attributeCode Metadata attribute code
	 * @return List of metadata values.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getAttributeValues(String attributeCode) throws SQLException, ClassNotFoundException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_attribute_values( ? ) }");
		proc.setString(1, attributeCode);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		MetadataValueList recSet = new MetadataValueList();
		
		if (results!=null){
			while (results.next())
			{
				MetadataValue rec = new MetadataValue(
						results.getString("code"), 
						results.getString("term"), 
						results.getString("definition"));
				recSet.add(rec);
			}
		}
		
		con.close();
		return recSet;
	}
	
	/**
	 * Retrieve values for a given attribute, with filter on the value
	 * @param attributeCode Metadata attribute code
	 * @return List of metadata values.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getAttributeValuesWithFilter(String attributeCode, String filter) throws SQLException, ClassNotFoundException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_attribute_values_filter( ? , ? ) }");
		proc.setString(1, attributeCode);
		proc.setString(2, filter);
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		MetadataValueList recSet = new MetadataValueList();
		
		if (results!=null){
			while (results.next())
			{
				MetadataValue rec = new MetadataValue(
						results.getString("code"), 
						results.getString("term"), 
						results.getString("definition"));
				recSet.add(rec);
			}
		}
		
		con.close();
		return recSet;
	}
	
	/**
	 * Retrieve all metadata attributes
	 * @return List of metadata attributes.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataAttributeList getAllAttributes() throws SQLException, ClassNotFoundException
	{
		//open connection to DB
		Connection con = dataSource.getConnection();

		CallableStatement proc = null;
		proc = con.prepareCall("{ call get_attributes( ) }");
		proc.execute();
		
		ResultSet results = proc.getResultSet();
		
		MetadataAttributeList recSet = new MetadataAttributeList();
		
		while (results.next())
		{
			MetadataAttribute rec = new MetadataAttribute(
					results.getString("code"), 
					results.getString("term"), 
					results.getString("definition"),
					results.getString("type"),
					results.getString("term_id"));
			
			recSet.add(rec);
		}
		con.close();
		return recSet;
	}
	
	/**
	 * Retrieve list of values as String list
	 * @return list of values
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	private MetadataValueList getListFromProcedure(String procName) throws SQLException, ClassNotFoundException
	{
		Connection con = dataSource.getConnection();
		CallableStatement proc = null;
		proc = con.prepareCall("{ call "+procName+"( ) }");
		proc.execute();
		MetadataValueList list = new MetadataValueList();
		ResultSet results = proc.getResultSet();
		while (results.next()){
			MetadataValue value = new MetadataValue(
					results.getString("code"),
					results.getString("term"),
					results.getString("definition")
					);
				list.add(value);
		}
		con.close();
		return list;
	}
	
	/* 
	 * ================================================================================================
	 * 	 ALL METHODS
	 * ================================================================================================
	 */
	
	/**
	 * Retrieve list of computational methods
	 * @return list of computational methods
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMethodList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.COMPUTATIONAL_METHOD_NAME);
	}
	
	/**
	 * Retrieve list of MD constraint algorithms
	 * @return list of MD constraint algorithms
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMdConstraintList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.CONSTRAINT_ALGORITHM);
	}
	
	/**
	 * Retrieve list of boundary conditions
	 * @return list of boundary conditions
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getBoundaryConditionList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.BOUNDARY_CONDITIONS);
	}
	
	/**
	 * Retrieve list of solvent types
	 * @return list of bsolvent types
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getSolventTypeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.SOLVENT_TYPE);
	}
	
	/* 
	 * ================================================================================================
	 * 	 MOLECULAR DYNAMICS
	 * ================================================================================================
	 */

	/**
	 * Retrieve list of MD force fields
	 * @return list of MD force fields
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMdForceFieldList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.FORCE_FIELD);
	}
	
	/**
	 * Retrieve list of MD electrostatics interaction models
	 * @return list of MD electrostatics interaction models
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMdElectrostaticsList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.ELECTROSTATICS_MODELING);
	}
	
	/**
	 * Retrieve list of MD unit shapes
	 * @return list of MD unit shapes
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMdUnitShapeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.UNIT_SHAPE);
	}
	
	/**
	 * Retrieve list of MD integrators
	 * @return list of MD integrators
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMdIntegratorsList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.MM_INTEGRATOR);
	}
	
	/**
	 * Retrieve list of ensembles
	 * @return list of ensembles
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getEnsembleList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.ENSEMBLE_MODELING);
	}
	
	/**
	 * Retrieve list of barostat models
	 * @return list of barostat models
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getBarostatList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.BAROSTAT_ALGORITHM);
	}
	
	/**
	 * Retrieve list of thermostat models
	 * @return list of thermostat models
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getThermostatList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.THERMOSTAT_ALGORITHM);
	}
	
	/**
	 * Retrieve list of MD sampling methods
	 * @return list of  MD sampling methods
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMDSamplingMethods() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.ENHANCED_SAMPLING_METHOD_NAME);
	}
	
	/* 
	 * ================================================================================================
	 * 	 QUANTUM MECHANICS
	 * ================================================================================================
	 */
	
	/**
	 * Retrieve list of QM methods
	 * @return list of QM methods
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getQmMethodList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.QM_METHOD_NAME);
	}
	
	/**
	 * Retrieve list of QM method families
	 * @return list of QM method families
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getQmMethodFamilyList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.QM_LEVEL_OF_THEORY);
	}
	
	/**
	 * Retrieve list of basis sets
	 * @return list of basis sets
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getQmBasisSetList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.QM_BASIS_SET);
	}

	/**
	 * Retrieve list of basis set types
	 * @return list of basis set types
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getQmBasisSetTypeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.QM_BASIS_SET_FAMILY);
	}

	/**
	 * Retrieve list of QM calculation types
	 * @return list of QM calculation types
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getQmCalculationTypeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(MethodMetadata.CALCULATION);
	}

	/* 
	 * ================================================================================================
	 * 	 QUANTUM MD
	 * ================================================================================================
	 */

	/**
	 * Retrieve list of Quantum MD methods
	 * @return List of Quantum MD methods
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public MetadataValueList getQuantumMdMethodList() throws ClassNotFoundException, SQLException {
		return getAttributeValues(MethodMetadata.QMD_METHOD);
	}
	
	/* 
	 * ================================================================================================
	 * 	 SOFTWARE / HARDWARE
	 * ================================================================================================
	 */
	
	/**
	 * Retrieve list of software
	 * @return list of software
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getSoftwareList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(PlatformMetadata.SOFTWARE_NAME);
	}
	
	/**
	 * Retrieve list of file formats
	 * @return list of file formats
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getFileFormatList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(FileMetadata.FILE_FORMAT);
	}
	
	/**
	 * Retrieve list of operating systems
	 * @return list of operating systems
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getOperatingSystemList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(PlatformMetadata.OPERATING_SYSTEM);
	}
	
	/**
	 * Retrieve list of CPU architecture
	 * @return list of CPU architecture
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getCpuArchitectureList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(PlatformMetadata.CPU_ARCHITECTURE);
	}
	
	/**
	 * Retrieve list of hardware manufacturers
	 * @return list of hardware manufacturers
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getHardwareMakeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(PlatformMetadata.HARDWARE_MAKE);
	}

	/* 
	 * ================================================================================================
	 * 	 MOLECULAR SYSTEM
	 * ================================================================================================
	 */
	
	/**
	 * Retrieve list of structure databases
	 * @return list of structure databases
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getMoleculeTypeList() throws SQLException, ClassNotFoundException{
		return getAttributeValues(TopologyMetadata.MOLECULE_TYPE);
	}
	

	/* 
	 * ================================================================================================
	 * 	 AUTHORSHIP
	 * ================================================================================================
	 */
	
	/**
	 * Retrieve list of structure databases
	 * @return list of structure databases
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getStructureDbList() throws SQLException, ClassNotFoundException{
		return getListFromProcedure("get_list_structure_db");
	}
	
	/**
	 * Retrieve list of literature DBs
	 * @return list of literature DBs
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public MetadataValueList getLiteratureDbList() throws SQLException, ClassNotFoundException{
		return getListFromProcedure("get_list_literature_db");
	}
}
