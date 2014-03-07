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

package edu.utah.bmi.ibiomes.experiment.summary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.utah.bmi.ibiomes.experiment.ExperimentTask;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.metadata.MetadataMappable;

/**
 * Summarizes a list of software packages
 * @author Julien Thibault, University of Utah
 *
 */
@XmlRootElement(name="softwarePackagesSummary")
public class SummarySoftwarePackageUses implements MetadataMappable {
	
	private HashMap<String, SummarySoftwarePackageUse> softwareSummaries;
	
	@SuppressWarnings(value = { "unused" })
	private SummarySoftwarePackageUses(){	
	}
	

	/**
	 * Create new summary of software packages used by the given tasks
	 * @param tasks List of computational tasks
	 */
	public SummarySoftwarePackageUses(List<ExperimentTask> tasks)
	{
		if (tasks!=null){
			softwareSummaries = new HashMap<String, SummarySoftwarePackageUse>();
			
			for (ExperimentTask task : tasks)
			{
				//software packages
				if (task.getSoftware()!=null){
					String swName = task.getSoftware().getName();
					String swFullName = task.getSoftware().getFullName();
					String swVersion = task.getSoftware().getVersion();
					String swExec = task.getSoftware().getExecutableName();
					
					SummarySoftwarePackageUse summary = null;
					if (softwareSummaries.containsKey(swName))
						summary = softwareSummaries.get(swName);
					else summary = new SummarySoftwarePackageUse(swName);
					
					if (swExec != null && !summary.getExecutables().contains(swExec))
						summary.getExecutables().add(swExec);
					
					if (swVersion != null && !summary.getVersions().contains(swVersion))
						summary.getVersions().add(swVersion);

					if (swFullName != null && !summary.getFullNames().contains(swFullName))
						summary.getFullNames().add(swFullName);
					
					softwareSummaries.put(swName, summary);
				}
			}
		}
	}
	
	/**
	 * Get individual summaries for each software package
	 * @return List of summaries for each software package
	 */
	@XmlElement(name="softwarePackageSummary")
	public List<SummarySoftwarePackageUse> getSoftwareSummaries(){
		if (softwareSummaries!=null){
			return new ArrayList<SummarySoftwarePackageUse>(softwareSummaries.values());
		}
		else return null;
	}
	
	/**
	 * Generate associated metadata
	 */
	@XmlTransient
	public MetadataAVUList getMetadata()
	{
		MetadataAVUList metadata = new MetadataAVUList();
		
		if (this.softwareSummaries!=null){
			Collection<SummarySoftwarePackageUse> summaries = softwareSummaries.values();
			for (SummarySoftwarePackageUse m : summaries){
				metadata.addAll(m.getMetadata());
			}
		}
		return metadata;
	}
}
