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

import java.io.IOException;
import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * XPath reader
 * @author Julien Thibault, University of Utah
 *
 */
public class XPathReader {
    
    private String xmlFile;
    private Document xmlDocument;
    private XPath xPath;
    
    public XPathReader(String xmlFile) {
        this.xmlFile = xmlFile;
        initObjects();
    }
    
    public XPathReader(Document xmlDoc) {
    	xmlDocument = xmlDoc;            
        xPath =  XPathFactory.newInstance().
		newXPath();
    }
    
    private void initObjects(){        
        try {
            xmlDocument = DocumentBuilderFactory.
			newInstance().newDocumentBuilder().
			parse(xmlFile);            
            xPath =  XPathFactory.newInstance().
			newXPath();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }       
    }
    
    public Object read(String expression, QName returnType){
        try {
            XPathExpression xPathExpression = xPath.compile(expression);
	        return xPathExpression.evaluate(xmlDocument, returnType);
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
