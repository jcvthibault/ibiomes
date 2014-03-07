package edu.utah.bmi.ibiomes.catalog.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.catalog.MetadataLookup;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;
import edu.utah.bmi.ibiomes.metadata.MetadataAttributeList;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataValue;
import edu.utah.bmi.ibiomes.metadata.MetadataValueList;

/**
 * Tests for metadata lookup index (Lucene)
 * @author Julien Thibault
 *
 */
public class MetadataLuceneIndexTest {

	private final Logger logger = Logger.getLogger(MetadataLuceneIndexTest.class);
	
	@Test
	public void testLookup() throws Exception
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		String indexPath = (String) context.getBean("metadataLuceneIndexPath");
		
		//create test metadata
		int N_ATTRIBUTES = 4;
		int N_VALUES = 7;
		
		MetadataAttributeList attributes = new MetadataAttributeList();
		for (int a=0;a<N_ATTRIBUTES;a++){
			attributes.add(new MetadataAttribute(String.valueOf(a+1),"attribute_" + (a+1), "attribute " + (a+1), "STRING"));
		}
		MetadataValueList values = new MetadataValueList();
		for (int a=0;a<N_VALUES;a++){
			values.add(new MetadataValue(String.valueOf(a+1),"value_" + (a+1), "value " + (a+1)));
		}
				
		//build test dictionary
		File indexDir = new File(indexPath);
		Directory dir = FSDirectory.open(indexDir);
		Analyzer analyzer = new KeywordAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, analyzer);
		IndexWriter writer = new IndexWriter(dir, config);
		writer.deleteAll();
		
		for (int a=0;a<N_ATTRIBUTES;a++){
			MetadataAttribute attribute = attributes.get(a);
			Document document = new Document();		
	        document.add(new TextField(MetadataLookup.LOOKUP_FIELD_CODE, attribute.getCode(), Field.Store.YES));
	        document.add(new TextField(MetadataLookup.LOOKUP_FIELD_TERM, attribute.getTerm(), Field.Store.YES));
	        document.add(new TextField(MetadataLookup.LOOKUP_FIELD_DEFINITION , attribute.getDefinition(), Field.Store.YES));
	        document.add(new TextField(MetadataLookup.LOOKUP_FIELD_TYPE, attribute.getType(), Field.Store.YES));
	        writer.addDocument(document);
		}
		writer.close();
		logger.info("Lucene index created with "+ N_ATTRIBUTES +" attributes.");
		
		//test lookup index
		MetadataLookup lookup = new MetadataLookup(indexPath);
		MetadataAttributeList resultAttributes = null;
		MetadataRichAVUList richAVUs = null;
		
		//get list of attributes
		resultAttributes = lookup.getAllMetadataAttributes();
		Assert.assertEquals("getAllMetadataAttributes() returned " + resultAttributes.size() + "attributes instead of "+ N_ATTRIBUTES,
				N_ATTRIBUTES, resultAttributes.size());
		
		//lookup standard attributes
		List<String> attributeCodes = new ArrayList<String>();
		attributeCodes.add(attributes.get(1).getCode());
		attributeCodes.add(attributes.get(2).getCode());
		attributeCodes.add("made-up code");
		
		resultAttributes = lookup.lookupMetadataAttributes(attributeCodes);
		Assert.assertEquals("lookupMetadataAttributes() returned " + resultAttributes.size() + "attributes instead of 3",
				3, resultAttributes.size());

		resultAttributes = lookup.lookupMetadataAttributes(attributeCodes, true, false);
		Assert.assertEquals("lookupMetadataAttributes() returned " + resultAttributes.size() + "attributes instead of 2",
				2, resultAttributes.size());
		
		resultAttributes = lookup.lookupMetadataAttributes(attributeCodes, false, true);
		Assert.assertEquals("lookupMetadataAttributes() returned " + resultAttributes.size() + "attributes instead of 1",
				1, resultAttributes.size());

		resultAttributes = lookup.lookupMetadataAttributes(attributeCodes, false, false);
		Assert.assertEquals("lookupMetadataAttributes() returned " + resultAttributes.size() + "attributes instead of 0",
				0, resultAttributes.size());
		
		//lookup AVUs
		MetadataAVUList avus = new MetadataAVUList();
		for (int a=0;a<N_ATTRIBUTES;a++){
			avus.add(new MetadataAVU(attributes.get(a).getCode(), values.get(a).getTerm()));
		}
		for (int a=N_ATTRIBUTES;a<N_VALUES;a++){
			avus.add(new MetadataAVU("unknown attribute", values.get(a).getTerm()));
		}
		richAVUs = lookup.lookupMetadataAVU(avus);
		Assert.assertEquals("lookupMetadataAVU() returned " + richAVUs.size() + "attributes instead of "+ (N_VALUES),
				N_VALUES, richAVUs.size());
		
		richAVUs = lookup.lookupMetadataAVU(avus, true, false);
		Assert.assertEquals("lookupMetadataAVU() returned " + richAVUs.size() + "attributes instead of "+ N_ATTRIBUTES,
				N_ATTRIBUTES, richAVUs.size());
		
		richAVUs = lookup.lookupMetadataAVU(avus, false, true);
		Assert.assertEquals("lookupMetadataAVU() returned " + richAVUs.size() + "attributes instead of "+(N_VALUES-N_ATTRIBUTES),
				N_VALUES-N_ATTRIBUTES, richAVUs.size());
		
		//delete index
		//TODO recursive delete
		indexDir.delete();
	}
}
