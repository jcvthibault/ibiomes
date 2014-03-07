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

package edu.utah.bmi.ibiomes.pub;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.query.MetaDataAndDomainData;

import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.parse.LocalFile;

/**
 * Container for iBIOMES metadata and iRODS system metadata 
 * @author Julien Thibault
 *
 */
@XmlRootElement (name="file")
public class IBIOMESFile implements Serializable {

	private static final long serialVersionUID = 3854447447237480028L;

	private String absolutePath;
	private String parent;
	private String owner;
	private long size;
	private int id;
	private long registrationTimestamp;
	private String registrationDate;
	private String name;
	private MetadataAVUList metadataList;
	private String zone;
	private String resource;
	/*private boolean readable;
	private boolean writable;
	private boolean executable;*/
	
	public IBIOMESFile(){
		this.metadataList = new MetadataAVUList();
	}
	
	public IBIOMESFile(IRODSFile file, List<MetaDataAndDomainData> metadata) throws JargonException
	{
		this.absolutePath = file.getAbsolutePath();
		this.name = file.getName();
		this.parent = file.getParent();
		this.size = file.length();
		this.resource = file.getResource();
		this.setRegistrationTimestamp(file.lastModified());
		/*this.readable = file.canRead();
		this.writable = file.canWrite();
		this.executable = file.canExecute();*/
		
		this.metadataList = new MetadataAVUList();
		if (metadata!=null && metadata.size()>0)
		{
			for (MetaDataAndDomainData m : metadata){
				this.metadataList.add(new MetadataAVU(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
			}
		}
	}

	@XmlAttribute(name="absolutePath")
	public String getAbsolutePath() {
		return absolutePath;
	}

	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}

	@XmlAttribute(name="parent")
	public String getParent() {
		return parent;
	}

	@XmlAttribute(name="owner")
	public String getOwner() {
		return owner;
	}

	@XmlAttribute(name="size")
	public long getSize() {
		return size;
	}

	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}

	@XmlAttribute(name="registrationTimestamp")
	public long getRegistrationTimestamp() {
		return registrationTimestamp;
	}

	@XmlAttribute(name="registrationDate")
	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationTimestamp(long registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
		Date date = new Date(this.registrationTimestamp);
		this.registrationDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
	}

	public void setRegistrationDate(String date){
		this.registrationDate = date;
	}

	/*@XmlAttribute(name="readable")
	public boolean isReadable() {
		return this.readable;
	}

	@XmlAttribute(name="writable")
	public boolean isWritable() {
		return this.writable;
	}

	@XmlAttribute(name="executable")
	public boolean isExecutable() {
		return this.executable;
	}*/

	@XmlAttribute(name="resource")
	public String getResource() {
		return resource;
	}
	
	@XmlElementWrapper(name="metadata")
	@XmlElement(name="AVU")
	public MetadataAVUList getMetadata() {
		return metadataList;
	}

	public String getZone() {
		return zone;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setMetadata(MetadataAVUList metadata) {
		this.metadataList = metadata;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	/**
	 * Get file description
	 * @return media type
	 */
	@XmlTransient
	public String getDescription() {
		if (this.getMetadata()==null)
			return null;
		else
			return this.getMetadata().getValue(FileMetadata.FILE_DESCRIPTION);
	}
	
	public void setDescription(String description) {
		if (metadataList==null)
			metadataList = new MetadataAVUList();
		this.metadataList.add(new MetadataAVU(FileMetadata.FILE_DESCRIPTION, description));
	}

	/**
	 * Get file format
	 * @return media type
	 */
	@XmlTransient
	public String getFormat() {
		if (this.getMetadata()==null)
			return LocalFile.FORMAT_UNKNOWN;
		else{
			if (this.getMetadata().containsAttribute(FileMetadata.FILE_FORMAT))
				return this.getMetadata().getValue(FileMetadata.FILE_FORMAT);
			else return LocalFile.FORMAT_UNKNOWN;
		}
	}
	
	public void setFormat(String format) {
		if (metadataList==null)
			metadataList = new MetadataAVUList();
		this.metadataList.updatePair(FileMetadata.FILE_FORMAT, format);
	}
	
	/**
	 * Get media type (text, spreadsheet, image...)
	 * @return media type
	 */
	@XmlTransient
	public String getType() {
		if (this.getMetadata()==null)
			return null;
		else
			return this.getMetadata().getValue(FileMetadata.MEDIA_TYPE);
	}
	
	public void setType(String type) {
		if (metadataList==null)
			metadataList = new MetadataAVUList();
		this.metadataList.updatePair(FileMetadata.MEDIA_TYPE, type);
	}
	
	/**
	 * Get file visibility
	 * @return media type
	 */
	@XmlTransient
	public boolean getIsVisible() {
		if (this.getMetadata()==null)
			return true;
		else {
			String hiddenFlag = this.getMetadata().getValue(FileMetadata.FILE_IS_HIDDEN);
			return (!hiddenFlag.equals("true") && !hiddenFlag.equals("yes") );
		}
	}
	
	public void setIsVisible(boolean visible) {
		if (metadataList==null)
			metadataList = new MetadataAVUList();
		this.metadataList.updatePair(FileMetadata.FILE_IS_HIDDEN, "true");
	}
}
