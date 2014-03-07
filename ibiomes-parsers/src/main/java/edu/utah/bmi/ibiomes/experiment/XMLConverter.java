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

package edu.utah.bmi.ibiomes.experiment;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.utah.bmi.ibiomes.experiment.comp.min.MinimizationTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.MDTask;
import edu.utah.bmi.ibiomes.experiment.comp.mm.REMDTask;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QMTask;
import edu.utah.bmi.ibiomes.experiment.comp.qm.QuantumMDTask;
import edu.utah.bmi.ibiomes.experiment.comp.qmmm.QMMMTask;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.parse.chem.LocalExperiment;
import edu.utah.bmi.ibiomes.topo.Compound;
import edu.utah.bmi.ibiomes.topo.MolecularSystem;
import edu.utah.bmi.ibiomes.topo.Molecule;
import edu.utah.bmi.ibiomes.topo.bio.Biomolecule;

/**
 * Generate XML representation of iBIOMES experiments 
 * @author Julien Thibault, University of Utah
 *
 */
public class XMLConverter {

	/**
	 * New converter
	 */
	public XMLConverter(){
	}
	
	/**
	 * Convert experiment to XML
	 * @param experiment Experiment
	 * @return XML document
	 * @throws JAXBException
	 * @throws TransformerException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public Document convert(LocalExperiment experiment) throws JAXBException, TransformerException, SAXException, IOException, ParserConfigurationException{
		
		//marshall experiment object
		final JAXBContext context = JAXBContext.newInstance(experiment.getClass());
        final Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(experiment, stringWriter);
        String xml = stringWriter.toString();
        
        //create XML document
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		//add summary of computational tasks
		SummaryExperimentTasks tasksSummary = experiment.getTasksSummary();
		final JAXBContext contextTaskSummary = JAXBContext.newInstance(tasksSummary.getClass());
		final Marshaller marshallerTaskSummary = contextTaskSummary.createMarshaller();
        stringWriter = new StringWriter();
        marshallerTaskSummary.marshal(tasksSummary, stringWriter);
        xml = stringWriter.toString();
		Document docSummary = db.parse((new InputSource(new StringReader(xml))));
		if (docSummary!=null && docSummary.hasChildNodes()){
			Node summaryNode = docSummary.getDocumentElement();
			doc.getDocumentElement().appendChild(doc.importNode(summaryNode, true));
		}
				
        return doc;
	}
	
	/**
	 * Get XML representation of computational method and parameters 
	 * @param task Task
	 * @return XML document
	 * @throws ParserConfigurationException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document convertTask(ExperimentTask task) throws ParserConfigurationException, JAXBException, SAXException, IOException{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;

		context = JAXBContext.newInstance(
				ExperimentTask.class, MDTask.class, REMDTask.class, 
				MinimizationTask.class, QMTask.class, QMMMTask.class, QuantumMDTask.class);
		
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(task, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		return doc;
	}
	
	/**
	 * Convert experiment process to XML document
	 * @param process Process
	 * @return XML document
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException 
	 */
	public Document convertExperimentProcess(ExperimentProcess process) throws JAXBException, SAXException, IOException, ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;
			
		context = JAXBContext.newInstance(process.getClass());
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(process, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		Element methodsNode = doc.createElement("tasks");
		
		//add task nodes
		List<ExperimentTask> tasks = process.getTasks();
		if (tasks!=null){
			for (ExperimentTask task : tasks){
				Document methodDoc = convertTask(task);
				Node methodNode = methodDoc.getDocumentElement();
				methodsNode.appendChild(doc.importNode(methodNode, true));
			}
			doc.getDocumentElement().appendChild(methodsNode);
		}
		return doc;
	}
	
	/**
	 * Convert experiment process group to XML document
	 * @param processGroup Process group
	 * @return XML document
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException 
	 */
	public Document convertExperimentProcessGroup(ExperimentProcessGroup processGroup) throws JAXBException, SAXException, IOException, ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;
			
		context = JAXBContext.newInstance(processGroup.getClass());
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(processGroup, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
				
		//add molecular system node
		MolecularSystem molecularSystem = processGroup.getMolecularSystem();
		if (molecularSystem!=null){
			Document systemDoc = convertMolecularSystem(molecularSystem);
			if (systemDoc!=null && systemDoc.hasChildNodes()){
				Node systemNode = systemDoc.getDocumentElement();
				doc.getDocumentElement().appendChild(doc.importNode(systemNode, true));
			}
		}
		//add processes
		Element processesNode = doc.createElement("processes");
		
		List<ExperimentProcess> processes = processGroup.getProcesses();
		for (ExperimentProcess process : processes){
			Document processDoc = convertExperimentProcess(process);
			Node processNode = processDoc.getDocumentElement();
			processesNode.appendChild(doc.importNode(processNode, true));
		}
		doc.getDocumentElement().appendChild(processesNode);
		
		return doc;
	}
	
	/**
	 * Get XML representation of molecular system and contained molecules
	 * @param molecularSystem Molecular system
	 * @return XML document
	 * @throws SAXException
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 */
	private Document convertMolecularSystem(MolecularSystem molecularSystem) throws SAXException, IOException, JAXBException, ParserConfigurationException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;
			
		context = JAXBContext.newInstance(molecularSystem.getClass());
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(molecularSystem, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		List<Molecule> molecules = molecularSystem.getSoluteMolecules();
		if (molecules!=null && molecules.size()>0){
			Element moleculesNode = doc.createElement("soluteMolecules");
			for (Molecule molecule : molecules){
				if (molecule!=null){
					Document moleculeDoc = convertMolecule(molecule);
					Node moleculeNode = moleculeDoc.getDocumentElement();
					moleculesNode.appendChild(doc.importNode(moleculeNode, true));
				}
			}
			doc.getDocumentElement().appendChild(moleculesNode);
		}
		
		return doc;
	}

	/**
	 * Get XML representation of molecule
	 * @param molecule Molecule
	 * @return XML document
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Document convertMolecule(Molecule molecule) throws JAXBException, ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;
		
		context = JAXBContext.newInstance(Molecule.class, Biomolecule.class, Compound.class);
		
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(molecule, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		return doc;
	}

	/**
	 * Get XML representation of experiment
	 * @param experiment Experiment
	 * @return XML document
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException 
	 */
	public Document convertExperiment(Experiment experiment) throws JAXBException, SAXException, IOException, ParserConfigurationException, TransformerException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		JAXBContext context = null;
		Marshaller marshaller = null;
		
		context = JAXBContext.newInstance(experiment.getClass());
		marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(experiment, stringWriter);
        String xml = stringWriter.toString();
		Document doc = db.parse((new InputSource(new StringReader(xml))));
		
		//process groups
		Element processGroupsNode = doc.createElement("processGroups");
		List<ExperimentProcessGroup> processGroups = experiment.getProcessGroups();
		if (processGroups!=null){
			for (ExperimentProcessGroup processGroup : processGroups){
				Document processGroupDoc = convertExperimentProcessGroup(processGroup);
				Node processGroupNode = processGroupDoc.getDocumentElement();
				processGroupsNode.appendChild(doc.importNode(processGroupNode, true));
			}
		}
		doc.getDocumentElement().appendChild(processGroupsNode);
		
		return doc;
	}
}
