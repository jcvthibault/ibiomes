package edu.utah.bmi.ibiomes.catalog.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;
import edu.utah.bmi.ibiomes.metadata.MetadataAttributeList;
import edu.utah.bmi.ibiomes.metadata.MetadataSqlConnector;

public class TestMetadataSQL {
	
	private final Logger logger = Logger.getLogger(TestMetadataSQL.class);
	
	@Test
	public void testMetadataSQL() throws Exception
	{
		try{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
			MetadataSqlConnector sql = (MetadataSqlConnector)context.getBean("metadataSqlConnector");
			
			MetadataAttributeList attrs = sql.getAllAttributes();
			String attrsStr = "";
			for (MetadataAttribute attr : attrs){
				attrsStr += "[" + attr.getCode() + "] " + attr.getTerm() + "\n";
			}
			logger.info("Attributes:\n" + attrsStr);
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
