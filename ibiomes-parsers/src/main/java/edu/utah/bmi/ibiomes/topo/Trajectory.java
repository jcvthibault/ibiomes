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

package edu.utah.bmi.ibiomes.topo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import edu.utah.bmi.ibiomes.xml.XmlTransformer;


/**
 * Trajectory of a single atom
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="trajectory")
public class Trajectory {

	private ArrayList<Coordinate3D> _coordinates;
	
	/**
	 * Create empty trajectory
	 */
	public Trajectory(){
		_coordinates = new ArrayList<Coordinate3D>();
	}
	
	@XmlElement(name="coordinates")
	public List<Coordinate3D> getCoordinates(){
		return _coordinates;
	}
	
	/**
	 * Add new coordinates to the end of the trajectory
	 * @param c
	 */
	public void addCoordinates(Coordinate3D c){
		_coordinates.add(c);
	}
	
	/**
	 * Store trajectory into specified file
	 * @param filePath
	 * @throws IOException 
	 */
	public void store(String filePath) throws IOException
	{
		File outFile = new File(filePath);
	    BufferedWriter w = new BufferedWriter(new FileWriter(outFile));
	    
		for (Coordinate3D coord : _coordinates){
			w.write(coord.getX() + " " + coord.getY() + " " + coord.getZ() + "\n");
		}
		w.close();
		
	}
	
	/**
	 * Store trajectory into XML file
	 * @param filePath
	 * @throws Exception 
	 */
	public void storeXML(String filePath) throws Exception
	{
		String xmlTraj = XmlTransformer.mapToXML(this);
		System.out.println(xmlTraj);
		
		//save XML to file
		Source docSource = new StreamSource(new StringReader(xmlTraj));
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer t = tf.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		FileOutputStream fos = new FileOutputStream(filePath);
		Result transResult = new StreamResult(fos);
		t.transform(docSource, transResult);
		
		
	}
}
