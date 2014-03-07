package edu.utah.bmi.ibiomes.web.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.web.domain.ExternalLink;
import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

/**
 * Experiment presentation/summary (e.g. representative 3D structures, analysis data)
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Presentation {

	private String mainTopology;
	private String main3DStructure;
	private List<ExternalLink> links;
	
	public Presentation(){
		
	}
	/**
	 * Create presentation object based on a list of iBIOMES metadata.
	 * @param metadata List of iBIOMES metadata
	 */
	public Presentation(MetadataAVUList metadata){

		this.setMainTopology(metadata.getValue(ExperimentMetadata.TOPOLOGY_FILE_PATH));
		this.setMain3DStructure(metadata.getValue(ExperimentMetadata.MAIN_3D_STRUCTURE_FILE));
		this.setLinks(ExternalLink.getList(metadata));
	}
	
	/**
	 * Get relative path to main topology file
	 * @return Relative path to main topology file
	 */
	public String getMainTopology() {
		return mainTopology;
	}
	/**
	 * Set relative path to main topology file
	 * @param mainTopology Relative path to main topology file
	 */
	public void setMainTopology(String mainTopology) {
		this.mainTopology = mainTopology;
	}
	/**
	 * Get relative path to main 3D structure file
	 * @return Relative path to main 3D structure file
	 */
	public String getMain3DStructure() {
		return main3DStructure;
	}
	/**
	 * Set relative path to main 3D structure file
	 * @param main3dFile Relative path to main 3D structure file
	 */
	public void setMain3DStructure(String main3dFile) {
		this.main3DStructure = main3dFile;
	}

	/**
	 * Get list of external links
	 * @return List of external links
	 */
	@XmlElementWrapper(name="links")
	@XmlElement(name="link")
	public List<ExternalLink> getLinks() {
		return links;
	}

	/**
	 * Set the list of external links
	 * @param links List of external links
	 */
	public void setLinks(List<ExternalLink> links) {
		this.links = links;
	}
}
