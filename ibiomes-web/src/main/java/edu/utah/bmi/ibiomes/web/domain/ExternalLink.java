package edu.utah.bmi.ibiomes.web.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * External resource (link)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class ExternalLink {

	private String url;
	private String description;

	public ExternalLink()
	{}
	
	/**
	 * Constructor with implicit arguments in a single string in the format 'url:description'.
	 * @param linkInfo
	 */
	public ExternalLink(String linkInfo)
	{
		if (linkInfo.length()>0 ){
			int index = linkInfo.lastIndexOf(':');
			this.url = linkInfo.substring(0, index);
			this.description = linkInfo.substring(index+1);
		}
	}
	
	/**
	 * Constructor with explicit arguments.
	 * @param url Resource URL
	 * @param description Resource description
	 */
	public ExternalLink(String url, String description){
		this.url = url;
		this.description = description;
	}
	
	/**
	 * Get resource URL
	 * @return Resource URL
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * Set resource URL
	 * @param url Resource URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Get resource description
	 * @return Resource description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set resource description
	 * @param description Resource description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Extract list of external resources from list of iBIOMES metadata.
	 * @param metadata List of iBIOMES metadata
	 * @return List of external resources
	 */
	public static List<ExternalLink> getList(MetadataAVUList metadata) 
	{
		List<String> linkMetadata = metadata.getValues(ExperimentMetadata.EXTERNAL_LINK);
		List<ExternalLink> links = new ArrayList<ExternalLink>();
		for (String linkInfo : linkMetadata){
			links.add(new ExternalLink(linkInfo));
		}
		return links;
	}
	
}
