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

import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.domain.Collection;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.query.MetaDataAndDomainData;

import edu.utah.bmi.ibiomes.metadata.BiosimMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Container for iBIOMES metadata and iRODS system metadata 
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="collection")
public class IBIOMESCollection implements Serializable {

	private static final long serialVersionUID = 3854447447237480028L;
	
	private String owner;
	private int id;
	private long registrationTimestamp;
	private String registrationDate;
	private String name;
	private String parent;
	private String absolutePath;
	private MetadataAVUList metadataList;
	private String zone;
	//private String resource;
	private boolean readable;
	private boolean writable;
	
	private List<IBIOMESCollection> collections;
	private List<IBIOMESFile> files;
	
	/**
	 * 
	 */
	public IBIOMESCollection(){
		this.metadataList = new MetadataAVUList();
	}
	
	/**
	 * 
	 * @param collection iRODS
	 * @param collectionInfo iRODS collection pojo
	 * @param metadata List of metadata
	 * @throws JargonException
	 */
	public IBIOMESCollection(IRODSFile collection, Collection collectionInfo, List<MetaDataAndDomainData> metadata) throws JargonException
	{	
		this.absolutePath = collection.getAbsolutePath();
		if (this.absolutePath.endsWith("/")){
			this.absolutePath = this.absolutePath.substring(0, this.absolutePath.length()-1);
		}
		this.name = this.absolutePath.substring(this.absolutePath.lastIndexOf('/')+1);
		this.parent = this.absolutePath.substring(0, this.absolutePath.lastIndexOf('/'));
		
		//this.resource = collection.getResource();
		
		//this.readable = collection.canRead();
		//this.writable = collection.canWrite();
		
		this.owner = collectionInfo.getCollectionOwnerName();
		this.registrationDate = DateFormat.getDateInstance(DateFormat.SHORT).format(collectionInfo.getCreatedAt());
		this.registrationTimestamp = collectionInfo.getCreatedAt().getTime();
		
		this.metadataList = new MetadataAVUList();
		if (metadata!=null && metadata.size()>0)
		{
			for (MetaDataAndDomainData m : metadata){
				this.metadataList.add(new MetadataAVU(m.getAvuAttribute(), m.getAvuValue(), m.getAvuUnit()));
			}
		}
	}
	/**
	 * Set metadata
	 * @param metadata Metadata
	 */
	public void setMetadata(MetadataAVUList metadata) {
		this.metadataList = metadata;
	}

	/**
	 * Get collection owner
	 * @return Collection owner
	 */
	@XmlAttribute(name="owner")
	public String getOwner() {
		return owner;
	}
	/**
	 * Set owner
	 * @param owner Owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Get collection ID
	 * @return collection ID
	 */
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}
	/**
	 * Set collection ID
	 * @param id Collection ID
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get registration timestamp
	 * @return Registration timestamp
	 */
	@XmlAttribute(name="registrationTimestamp")
	public long getRegistrationTimestamp() {
		return this.registrationTimestamp;
	}
	/**
	 * Set registration timestamp
	 * @param registrationTimestamp Registration timestamp
	 */
	public void setRegistrationTimestamp(long registrationTimestamp) {
		this.registrationTimestamp = registrationTimestamp;
		Date date = new Date(registrationTimestamp);
		this.registrationDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
	}
	/**
	 * Get registration date
	 * @return Rregistration date
	 */
	@XmlAttribute(name="registrationDate")
	public String getRegistrationDate() {
		return this.registrationDate;
	}
	/**
	 * Set registration date
	 * @param date Registration date
	 */
	public void setRegistrationDate(String date){
		this.registrationDate = date;
	}
	/**
	 * Get description
	 * @return Description
	 */
	public String getDescription() {
		if (this.getMetadata()==null)
			return null;
		else
			return this.getMetadata().getValue(GeneralMetadata.EXPERIMENT_DESCRIPTION);
	}
	/**
	 * Set description
	 * @param description Description
	 */
	public void setDescription(String description) {
		if (metadataList==null)
			metadataList = new MetadataAVUList();
		this.metadataList.updatePair(GeneralMetadata.EXPERIMENT_DESCRIPTION, description);
	}
	/**
	 * Get collection name
	 * @return Collection name
	 */
	@XmlAttribute(name="name")
	public String getName() {
		return name;
	}
	/**
	 * Set collection name
	 * @param name Collection name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get absolute path
	 * @return Absolute path
	 */
	@XmlAttribute(name="absolutePath")
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * Get path to parent collection
	 * @return Path to parent collection
	 */
	@XmlAttribute(name="parent")
	public String getParent() {
		return this.parent;
	}
	
	/**
	 * Get list of metadata
	 * @return List of metadata
	 */
	@XmlElementWrapper(name="metadata")
	@XmlElement(name="AVU")
	public MetadataAVUList getMetadata() {
		return metadataList;
	}

	/**
	 * Get iRODS zone
	 * @return iRODS zone
	 */
	public String getZone() {
		return zone;
	}

	/*public String getResource() {
		return resource;
	}*/
	
	/**
	 * Get child collections
	 * @return Child collections
	 */
	public List<IBIOMESCollection> getCollections() {
		return collections;
	}
	/**
	 * Set child collections
	 * @param collections Child collections
	 */
	public void setCollections(List<IBIOMESCollection> collections) {
		this.collections = collections;
	}
	/**
	 * Get list of files in the collection
	 * @return List of files in the collection
	 */
	public List<IBIOMESFile> getFiles() {
		return files;
	}
	/**
	 * Set the list of files in the collection
	 * @param files List of files in the collection
	 */
	public void setFiles(List<IBIOMESFile> files) {
		this.files = files;
	}

	/**
	 * Check if the collection is readable
	 * @return True if readable
	 */
	@XmlAttribute
	public boolean isReadable() {
		return this.readable;
	}

	/**
	 * Check if the collection is writable
	 * @return True if readable
	 */
	@XmlAttribute
	public boolean isWritable() {
		return this.writable;
	}
	
	/**
	 * Check if this collection is an experiment
	 * @return True if its an experiment
	 */
	public boolean isExperiment(){
		if (this.getMetadata()!=null && 
				this.getMetadata().hasPair(new MetadataAVU(GeneralMetadata.IBIOMES_FILE_TYPE, BiosimMetadata.DIRECTORY_EXPERIMENT)))
			return true;
		else return false;
	}
}
