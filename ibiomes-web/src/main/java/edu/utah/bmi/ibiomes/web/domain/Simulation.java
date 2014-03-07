package edu.utah.bmi.ibiomes.web.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.metadata.ExperimentMetadata;
import edu.utah.bmi.ibiomes.metadata.GeneralMetadata;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.metadata.TopologyMetadata;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;

/**
 * Simulation run
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement
public class Simulation
{
	private Author author;
	private List<CitationReference> citations;
	private List<StructureReference> structureReferences;
	private Method method;
	private MolecularSystem molecularSystem;
	private List<String> softwarePackages;
	private List<String> executables;
	private ExecutionInformation executionInfo;
	private IBIOMESCollection fileCollection;
	private Presentation presentation;
	
	private String registrationDate;
	private String title;
	private String description;
	
	public Simulation(){
	}
	
	/**
	 * Get owner of the simulation data
	 * @return Owner of the simulation data
	 */
	public Author getOwner() {
		return author;
	}
	public void setOwner(Author owner) {
		this.author = owner;
	}
	
	/**
	 * Get citations related to this simulation run
	 * @return List of citations related to this simulation run
	 */
	@XmlElementWrapper(name="citations")
	@XmlElement(name="citation")
	public List<CitationReference> getCitations() {
		return citations;
	}
	/**
	 * Set the list of citations related to this simulation run
	 * @param citations List of citations related to this simulation run
	 */
	public void setCitations(List<CitationReference> citations) {
		this.citations = citations;
	}
	
	/**
	 * Get the list of structure references related to this simulation run
	 * @return List of structure references related to this simulation run
	 */
	@XmlElementWrapper(name="structureReferences")
	@XmlElement(name="structureReference")
	public List<StructureReference> getStructureReferences() {
		return structureReferences;
	}
	/**
	 * Set the list of structure references related to this simulation run
	 * @param structureReferences List of structure references related to this simulation run
	 */
	public void setStructureReferences(List<StructureReference> structureReferences) {
		this.structureReferences = structureReferences;
	}
	/**
	 * Get the simulation method
	 * @return Simulation method
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * Set simulation method
	 * @param method Simulation method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	/**
	 * Get simulated molecular system
	 * @return Simulated molecular system
	 */
	public MolecularSystem getMolecularSystem() {
		return molecularSystem;
	}
	/**
	 * Set the simulated molecular system
	 * @param molecularSystem Simulated molecular system
	 */
	public void setMolecularSystem(MolecularSystem molecularSystem) {
		this.molecularSystem = molecularSystem;
	}
	/**
	 * Get the main software packages used for this simulation
	 * @return Software packages used for this simulation
	 */
	@XmlElementWrapper(name="softwarePackages")
	@XmlElement(name="softwarePackage")
	public List<String> getSoftwarePackages() {
		return softwarePackages;
	}
	/**
	 * Set the software packages used for this simulation
	 * @param software Software packages used for this simulation
	 */
	public void setSoftwarePackages(List<String> softwarePackages) {
		this.softwarePackages = softwarePackages;
	}

	@XmlElementWrapper(name="executables")
	@XmlElement(name="executable")
	public List<String> getExecutables() {
		return executables;
	}

	public void setExecutables(List<String> executables) {
		this.executables = executables;
	}	
	
	/**
	 * Get execution info
	 * @return Execution info
	 */
	public ExecutionInformation getExecutionInfo() {
		return executionInfo;
	}
	/**
	 * Set execution info
	 * @param execution Execution info
	 */
	public void setExecutionInfo(ExecutionInformation execution) {
		this.executionInfo = execution;
	}
	/**
	 * Get list of files (input and output) associated with this experiment.
	 * @return List of files (input and output) associated with this experiment.
	 */
	public IBIOMESCollection getFileCollection() {
		return fileCollection;
	}
	/**
	 * Set the list of files (input and output) associated with this experiment.
	 * @param fileCollection List of files (input and output) associated with this experiment.
	 */
	public void setFileCollection(IBIOMESCollection fileCollection) {
		this.fileCollection = fileCollection;
	}
	/**
	 * Get date of registration into the system. 
	 * @return Date of registration into the system. 
	 */
	public String getRegistrationDate() {
		return registrationDate;
	}
	/**
	 * Set the date of registration into the system. 
	 * @param registrationDate Date of registration into the system. 
	 */
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	/**
	 * Get the title of this experiment
	 * @return Title of the experiment
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set the title of this experiment
	 * @param title Title of this experiment
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get a description of this experiment
	 * @return Description of this experiment
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Set the description of this experiment
	 * @param description Description of this experiment
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the presentation data
	 * @return Presentation data
	 */
	public Presentation getPresentation() {
		return presentation;
	}

	/**
	 * Set the presentation data
	 * @param presentation Presentation data
	 */
	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}
	
	/**
	 * Populate simulation access object from iBIOMES collection
	 * @param collection
	 */
	public Simulation(IBIOMESCollection collection)
	{
		String experimentName = collection.getMetadata().getValue(GeneralMetadata.EXPERIMENT_TITLE);
		if (experimentName.length()==0)
			experimentName = collection.getName();
		
		initSimulation(experimentName, collection.getOwner(), collection.getRegistrationDate(), collection.getMetadata());
		//file collection/directory
		this.fileCollection = collection;
	}
	
	/**
	 * Populate simulation access object from iBIOMES collection
	 * @param owner Collection owner
	 * @param registrationDate Registration date
	 * @param metadata List of collection metadata
	 */
	public Simulation(String name, String owner, String registrationDate, MetadataAVUList metadata)
	{
		initSimulation(name, owner, registrationDate, metadata);
	}
	
	/**
	 * Populate simulation access object from iBIOMES collection
	 * @param collection
	 */
	private void initSimulation(String name, String owner, String registrationDate, MetadataAVUList metadata)
	{
		this.registrationDate = registrationDate;
		// author
		this.author = new Author();
		this.author.setUsername(owner);
		
		this.title = name;
		
		String desc = metadata.getValue(GeneralMetadata.EXPERIMENT_DESCRIPTION);
		if (desc.length()>0){
			this.description = desc;
		}
		
		//citations
		List<String> citationList = metadata.getValues(ExperimentMetadata.PUBLICATION_REF_ID);
		if (citationList!=null && citationList.size()>0)
		{
			this.citations = new ArrayList<CitationReference>();
			for (String citation : citationList){
				CitationReference citationRef = new CitationReference();
				try{
					String[] attributes = citation.split("#");
					citationRef.setDatabase(attributes[0]);
					citationRef.setReferenceId(attributes[1]);
					this.citations.add(citationRef);
				}
				catch(Exception e){
				}
			}
		}
		
		//reference structures
		List<String> structureList = metadata.getValues(TopologyMetadata.STRUCTURE_REF_ID);
		if (structureList!=null && structureList.size()>0)
		{
			this.structureReferences = new ArrayList<StructureReference>();
			for (String structureRef : structureList){
				StructureReference structure = new StructureReference();
				try{
					String[] attributes = structureRef.split("#");
					structure.setDatabase(attributes[0]);
					structure.setReferenceId(attributes[1]);
					this.structureReferences.add(structure);
				}
				catch(Exception e){
					
				}
			}
		}
		
		//methods
		List<String> methodNames = metadata.getValues(MethodMetadata.COMPUTATIONAL_METHOD_NAME);
		for (int i=0;i<methodNames.size();i++){
			methodNames.set(i, methodNames.get(i).toLowerCase());
		}
		
		//REMD
		if (methodNames.contains(Method.METHOD_REMD.toLowerCase())){
			REMDMethod remdMethod = new REMDMethod(metadata);
			this.method = remdMethod;
		}
		//QM/MM
		else if (methodNames.contains(Method.METHOD_QMMM.toLowerCase())){
			QMMMMethod qmmmMethod = new QMMMMethod(metadata);
			this.method = qmmmMethod;
		}
		//Quantum MD
		else if (methodNames.contains(Method.METHOD_QUANTUM_MD.toLowerCase())){
			QuantumMDMethod qmMethod = new QuantumMDMethod(metadata);
			this.method = qmMethod;
		}
		//QM
		else if (methodNames.contains(Method.METHOD_QM.toLowerCase())){
			QMMethod qmMethod = new QMMethod(metadata);
			this.method = qmMethod;
		}
		//Langevin MD
		else if (methodNames.contains(Method.METHOD_LANGEVIN_DYNAMICS.toLowerCase())){
			LangevinDynamicsMethod mdMethod = new LangevinDynamicsMethod(metadata);
			this.method = mdMethod;
		}
		//MD
		else if (methodNames.contains(Method.METHOD_MD.toLowerCase())){
			MDMethod mdMethod = new MDMethod(metadata);
			this.method = mdMethod;
		}
		else {
			this.method = new Method(metadata);
		}
		
		//molecular system
		this.molecularSystem = new MolecularSystem(metadata);
		
		//software
		this.softwarePackages = metadata.getValues(PlatformMetadata.SOFTWARE_NAME);
		this.setExecutables(metadata.getValues(PlatformMetadata.SOFTWARE_EXEC_NAME));
				
		//platform/hardware
		this.executionInfo = new ExecutionInformation(metadata);
		
		//presentation data
		this.presentation = new Presentation(metadata);
		
	}
}