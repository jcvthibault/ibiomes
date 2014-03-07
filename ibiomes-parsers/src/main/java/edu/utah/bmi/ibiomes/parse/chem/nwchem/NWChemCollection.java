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

package edu.utah.bmi.ibiomes.parse.chem.nwchem;

import javax.xml.bind.annotation.XmlRootElement;

import edu.utah.bmi.ibiomes.conf.DirectoryStructureDescriptor;
import edu.utah.bmi.ibiomes.experiment.Software;
import edu.utah.bmi.ibiomes.experiment.comp.ParameterSet;
import edu.utah.bmi.ibiomes.experiment.summary.SummaryExperimentTasks;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.metadata.PlatformMetadata;
import edu.utah.bmi.ibiomes.parse.LocalDirectory;
import edu.utah.bmi.ibiomes.parse.LocalFile;
import edu.utah.bmi.ibiomes.parse.chem.ExperimentFolder;
import edu.utah.bmi.ibiomes.parse.chem.common.TopologyFileCollection;

/**
 * Collection of NWChem files
 * @author Julien Thibault
 *
 */
@XmlRootElement(name="experiment")
public class NWChemCollection extends ExperimentFolder
{	
	private final static String[] structureFormats = {LocalFile.FORMAT_NWCHEM_OUTPUT, LocalFile.FORMAT_PDB, LocalFile.FORMAT_SDF };
	private final static String[] parameterAndTopologyFormats = {LocalFile.FORMAT_NWCHEM_INPUT, LocalFile.FORMAT_NWCHEM_OUTPUT };

	@SuppressWarnings(value = { "unused" })
	private NWChemCollection(){	
	}
	/**
	 * New NWChem collection
	 * @param fileDirectory Local directory
	 * @throws Exception
	 */
	public NWChemCollection(LocalDirectory fileDirectory) throws Exception{
		super(fileDirectory, Software.NWCHEM, null, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_NWCHEM);
		this.initializeProcessGroups(parameterAndTopologyFormats);
	}
	
	/**
	 * New NWChem collection
	 * @param fileDirectory Local directory
	 * @param desc XML descriptor
	 * @throws Exception
	 */
	public NWChemCollection(LocalDirectory fileDirectory, DirectoryStructureDescriptor desc) throws Exception{
		super(fileDirectory, Software.NWCHEM, desc, structureFormats, TopologyFileCollection.TOPOLOGY_FORMATS_NWCHEM);
		this.initializeProcessGroups(parameterAndTopologyFormats);
	}
	
	/**
	 * Get NWChem collection metadata
	 * @throws Exception 
	 */
	@Override
	public MetadataAVUList getMetadata() throws Exception
	{	
		MetadataAVUList metadata = super.getMetadata();
		
		//add aggregated metadata
		SummaryExperimentTasks tasksSummary = this.getExperimentTasksSummary();
		if (tasksSummary != null){
			metadata.addAll(tasksSummary.getMetadata());
			
			//remove minimization method if other method available
			if ( !(metadata.hasPair(new MetadataAVU(
					MethodMetadata.COMPUTATIONAL_METHOD_NAME, 
					ParameterSet.METHOD_MINIMIZATION))
				&& metadata.countAttributeOccurrences(
						MethodMetadata.COMPUTATIONAL_METHOD_NAME)==1) ){
				metadata.removePairs(MethodMetadata.COMPUTATIONAL_METHOD_NAME, ParameterSet.METHOD_MINIMIZATION);
			}
		}

		// default to QM if no method found
		if (!metadata.containsAttribute(MethodMetadata.COMPUTATIONAL_METHOD_NAME)){
			metadata.add(new MetadataAVU(MethodMetadata.COMPUTATIONAL_METHOD_NAME, ParameterSet.METHOD_QM));
		}

		//if no task found, default software name to NWChem
		if (this.getExperimentTasksSummary()==null || 
				!this.getExperimentTasksSummary().getMetadata().containsAttribute(PlatformMetadata.SOFTWARE_NAME))
			metadata.add(new MetadataAVU(PlatformMetadata.SOFTWARE_NAME, Software.NWCHEM));
		
		//add topology
		if (mainTopologyFile != null 
				&& mainTopologyFile.getMolecularSystems()!=null
				&& mainTopologyFile.getMolecularSystems().size()>0){
			metadata.addAll(mainTopologyFile.getMolecularSystems().get(0).getMetadata());
		}
		return metadata;
	}
}
