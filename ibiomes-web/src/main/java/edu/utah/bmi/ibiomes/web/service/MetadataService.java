package edu.utah.bmi.ibiomes.web.service;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;
import edu.utah.bmi.ibiomes.metadata.MetadataAttributeList;
import edu.utah.bmi.ibiomes.metadata.MetadataSqlConnector;
import edu.utah.bmi.ibiomes.metadata.MetadataValueList;

/**
 * REST interface to query the metadata catalog
 * @author Julien Thibault, University of Utah
 *
 */
@Controller
@RequestMapping(value = "/metadata")
public class MetadataService 
{

    @Autowired
    private ServletContext context;
    
	private MetadataSqlConnector getDbCnx()
	{	
		WebApplicationContext wContext = WebApplicationContextUtils.getWebApplicationContext(this.context);
		DataSource ds = (DataSource)wContext.getBean("dataSource");
		
		MetadataSqlConnector mdb = new MetadataSqlConnector();
		//DataSource ds = new DriverManagerDataSource("jdbc:mysql://juliens-grid-node2.chpc.utah.edu:3306/biosim_dictionaries?noAccessToProcedureBodies=true", "ibiomes", "ibiomes");
		mdb.setDataSource(ds);
		return mdb;
	}
	/* 
	 * --------------------------------------------------------------------
	 * 	 GET
	 * --------------------------------------------------------------------
	 */

	/**
	 * Retrieve list of metadata attributes
	 * @return List of metadata attributes.
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public MetadataAttributeList listAttributes() throws Exception{	
		return getDbCnx().getAllAttributes();
	}
	
	/**
	 * Retrieve metadata attribute information from its code
	 * @param attrCode Metadata attribute code
	 * @return Metadata attribute (XML)
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	@ResponseBody
	public MetadataAttribute getAttribute(
			@PathVariable("code") String attributeCode) throws SQLException, ClassNotFoundException
	{
		return getDbCnx().getAttributeByCode(attributeCode);
	}
	
	/**
	 * Retrieve all the possible values for a given attribute
	 * @param attributeCode Metadata attribute code
	 * @return List of metadata values.
	 */
	@RequestMapping(value = "/{code}/values", method = RequestMethod.GET)
	@ResponseBody
	public MetadataValueList getAttributeValues(
			@PathVariable("code") String attributeCode, 
			@RequestParam(value="term", required=false) String term) throws Exception
	{
		if (term != null)
			return getDbCnx().getAttributeValuesWithFilter(attributeCode, term);
		else
			return getDbCnx().getAttributeValues(attributeCode);
	}
}
