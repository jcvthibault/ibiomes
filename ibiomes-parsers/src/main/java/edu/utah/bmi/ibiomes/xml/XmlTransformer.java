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

package edu.utah.bmi.ibiomes.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Map JAXB-annotated object to XML then transform to HTML through XSLT.
 * @author Julien Thibault
 *
 */
public class XmlTransformer {

	/**
	 * Map JAXB-annotated object to XML then transform to HTML through XSLT.
	 * @param obj Object to marshall
	 * @param xslUrl URL to the XSL to use for the transformation
	 * @return String with HTML content
	 */
	public static String mapToHTML(Object obj, String xslUrl) throws Exception
	{
		//get XML
		String doc = mapToXML(obj);        
		Source docSource = new StreamSource(new StringReader(doc));
		
		//transform XML to HTML
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer(new javax.xml.transform.stream.StreamSource(xslUrl));
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer outWriter = new StringWriter();
		StreamResult res = new StreamResult( outWriter );
		transformer.transform(docSource, res);
		
		return res.getWriter().toString();
	}
	
	/**
	 * Map JAXB-annotated object to XML string.
	 * @param obj Object to marshall
	 * @return String with XML content
	 */
	public static String mapToXML(Object obj) throws Exception{
		
        final JAXBContext context = JAXBContext.newInstance(obj.getClass());
        final Marshaller marshaller = context.createMarshaller();
        
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(obj, stringWriter);
        String xml = stringWriter.toString();
        
        Source docSource = new StreamSource(new StringReader(xml));
		
		//transform XML to HTML
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer outWriter = new StringWriter();
		StreamResult res = new StreamResult( outWriter );
		transformer.transform(docSource, res);
		
		return xml;
	}
}
