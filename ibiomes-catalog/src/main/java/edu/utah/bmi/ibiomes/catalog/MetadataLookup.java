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

package edu.utah.bmi.ibiomes.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataAttribute;
import edu.utah.bmi.ibiomes.metadata.MetadataAttributeList;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataRichAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataValue;

/**
 * Lookup metadata attributes in dictionary.
 * @author Julien Thibault, University of Utah
 *
 */
public class MetadataLookup {

	private final Logger logger = Logger.getLogger(MetadataLookup.class);
	public final static String LOOKUP_FIELD_CODE = "code";
	public final static String LOOKUP_FIELD_TERM = "term";
	public final static String LOOKUP_FIELD_DEFINITION = "definition";
	public final static String LOOKUP_FIELD_TYPE = "type";
	private final static String lookupFieldName = LOOKUP_FIELD_CODE;
	
	private File indexFile;
	
	public MetadataLookup(String indexFilePath){
		this.indexFile = new File(indexFilePath);
	}
	
	public MetadataLookup(File indexFile){
		this.indexFile = indexFile;
	}
	
	/**
	 * Get all standard attributes from the dictionary
	 * @return List of standard metadata attributes
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public MetadataAttributeList getAllMetadataAttributes() throws CorruptIndexException, IOException {
		logger.info("Loading list of standard metadata attributes");
		MetadataAttributeList attrs = new MetadataAttributeList();
		IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile));
		for (int d=0; d<reader.numDocs();d++){
			Document doc = reader.document(d);
			MetadataAttribute attribute = getAttributeFromDocument(doc);
			attrs.add(attribute);
		}
		return attrs;
	}
	
	/**
	 * Find standard definitions of given metadata attribute
	 * @param attributeCode Attribute code
	 * @return Metadata with definitions
	 * @throws Exception
	 */
	public MetadataAttribute lookupMetadataAttribute(String attributeCode) throws Exception{
		List<String> codes = new ArrayList<String>();
		codes.add(attributeCode);
		MetadataAttributeList attrs = lookupMetadataAttributes(codes, true, true);
		if (attrs.size()>0)
			return attrs.get(0);
		else return null;
	}
	
	/**
	 * Find standard definitions of given metadata attributes
	 * @param attributeCodes List of attribute codes
	 * @return List of metadata with definitions
	 * @throws Exception
	 */
	public MetadataAttributeList lookupMetadataAttributes(List<String> attributeCodes) throws Exception{
		return lookupMetadataAttributes(attributeCodes, true, true);
	}
	
	/**
	 * Find standard definitions of given metadata attributes
	 * @param attributeCodes List of attribute codes
	 * @param requireStd Output standard attributes (with found definitions).
	 * @param requireNonStd Output non-standard attributes (with no definition).
	 * @return List of metadata with definitions
	 * @throws Exception
	 */
	public MetadataAttributeList lookupMetadataAttributes(List<String> attributeCodes, boolean requireStd, boolean requireNonStd) throws Exception
	{
		LuceneDictionary dictionary = new LuceneDictionary(indexFile, lookupFieldName, 1);
		
		MetadataAttributeList attrs = new MetadataAttributeList();
		for (String attrCode : attributeCodes){
			logger.info("Looking up metadata attribute '"+attrCode+"'");
			List<Document> docs = dictionary.lookupExactTerm(attrCode);
			if (docs!=null && docs.size()>0){
				if (requireStd){
					Document doc = docs.get(0);
					MetadataAttribute attribute = getAttributeFromDocument(doc);
					attribute.setStandard(true);
					attrs.add(attribute);
				}
			}
			else if (requireNonStd){
				MetadataAttribute attribute = new MetadataAttribute(attrCode, "", "" , null);
				attribute.setStandard(false);
				attrs.add(attribute);
			}
		}
		dictionary.closeSearch();
		return attrs;
	}
	
	/**
	 * Find standard definitions of given metadata
	 * @param avus List of metadata
	 * @return Metadata with definitions if available
	 * @throws Exception 
	 */
	public MetadataRichAVUList lookupMetadataAVU(MetadataAVUList avus) throws Exception{
		return lookupMetadataAVU(avus, true, true);
	}
	
	/**
	 * Find standard definitions of given metadata
	 * @param avus List of metadata
	 * @param requireStd Output standard attributes (with found definitions).
	 * @param requireNonStd Output non-standard attributes (with no definition).
	 * @return Metadata with definitions if available
	 * @throws Exception
	 */
	public MetadataRichAVUList lookupMetadataAVU(MetadataAVUList avus, boolean requireStd, boolean requireNonStd) throws Exception{
		
		LuceneDictionary dictionary = new LuceneDictionary(indexFile, lookupFieldName, 1);
		
		MetadataRichAVUList richAVUs = new MetadataRichAVUList();
		for (MetadataAVU avu : avus){
			logger.info("Looking up metadata attribute '"+avu.getAttribute()+"'");
			List<Document> docs = dictionary.lookupExactTerm(avu.getAttribute());
			if (docs!=null && docs.size()>0){
				if (requireStd){
					Document doc = docs.get(0);
					MetadataAttribute attribute = getAttributeFromDocument(doc);
					attribute.setStandard(true);
					MetadataValue value = new MetadataValue(avu.getValue(), avu.getValue(), "");
					richAVUs.add(new MetadataRichAVU(attribute, value, avu.getUnit()));
				}
			}
			else if (requireNonStd) {
				MetadataAttribute attribute = new MetadataAttribute(avu.getAttribute(), "", "" , null);
				attribute.setStandard(false);
				MetadataValue value = new MetadataValue(avu.getValue(), avu.getValue(), "");
				richAVUs.add(new MetadataRichAVU(attribute, value, avu.getUnit()));
			}
		}
		dictionary.closeSearch();
		return richAVUs;
	}
	
	
	private MetadataAttribute getAttributeFromDocument(Document doc){
		MetadataAttribute attribute = new MetadataAttribute(doc.get("code"),doc.get("term"),doc.get("definition"),doc.get("type"));
		return attribute;
	}
}
