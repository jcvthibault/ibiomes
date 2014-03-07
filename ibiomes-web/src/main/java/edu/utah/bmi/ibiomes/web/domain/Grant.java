package edu.utah.bmi.ibiomes.web.domain;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Grant information
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Grant {

	private String id;
	private String title;
	private String source;
	
	public Grant(){	
	}
	
	/**
	 * Get grant ID
	 * @return Grant ID
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Set grant ID
	 * @param id Grant ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Get grant title
	 * @return Grant title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set grant title
	 * @param title Grant title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get funding source (e.g. NIH, NSF, NLM)
	 * @return Funding source
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * Set funding source (e.g. NIH, NSF, NLM)
	 * @param source Funding source
	 */
	public void setSource(String source) {
		this.source = source;
	}
}
