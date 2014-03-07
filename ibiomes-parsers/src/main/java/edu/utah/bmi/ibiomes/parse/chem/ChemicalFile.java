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

package edu.utah.bmi.ibiomes.parse.chem;

import java.io.IOException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import edu.utah.bmi.ibiomes.conf.ExecutionTimingRecord;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.parse.AbstractLocalFileImpl;
import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Generic local simulation file
 * @author Julien Thibault
 *
 */
public class ChemicalFile extends AbstractLocalFileImpl
{
	private static final long serialVersionUID = -7253469980794629549L;
	private String computationalMethod;
	
	/**
	 * Reference new simulation file
	 * @param fileFormat Format
	 * @param localPath Local path to file
	 * @throws IOException
	 */
	public ChemicalFile(String localPath, String fileFormat) throws IOException{
		super(localPath);
		if (this.exists()){
			this.setFormat(fileFormat);
			this.setFileType(LocalFile.TYPE_CHEMICAL);
		}
		else throw new IOException("Local file \""+ localPath +"\" does not exist!");
	}
	
	/**
	 * Reference new simulation file
	 * @param localPath Local path to file
	 * @throws IOException
	 */
	public ChemicalFile(String localPath) throws IOException {
		super(localPath);
		if (this.exists()){
			this.setFormat(LocalFile.FORMAT_UNKNOWN);
			this.setFileType(LocalFile.TYPE_CHEMICAL);
		}
		else throw new IOException("Local file \""+ localPath +"\" does not exist!");
	}
	
	/**
	 * Get computational method
	 * @return Computational method
	 */
	public String getComputationalMethod() {
		return this.computationalMethod;	
	}
	
	/**
	 * Set computational method
	 * @param method Computational method
	 */
	public void setComputationalMethod(String method) {
		this.computationalMethod = method;
	}
	
	/**
	 * Return the set of metadata defined for this file.
	 * @return Set of metadata defined for this file
	 * @throws Exception 
	 */
	@XmlElementWrapper(name="metadata")
	@XmlElement(name="AVU")
	public MetadataAVUList getMetadata() throws Exception
	{
		MetadataAVUList metadata = super.getMetadata();
		if (this.computationalMethod != null && this.computationalMethod.length()>0)
			metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, this.computationalMethod));
		return metadata;
	}
	
}
