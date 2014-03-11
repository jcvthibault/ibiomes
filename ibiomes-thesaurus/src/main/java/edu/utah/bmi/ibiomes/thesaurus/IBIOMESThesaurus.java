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

package edu.utah.bmi.ibiomes.thesaurus;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.namespace.QName;

import edu.unc.ils.mrc.hive.api.SKOSConcept;
import edu.unc.ils.mrc.hive.api.SKOSScheme;
import edu.unc.ils.mrc.hive.api.SKOSSearcher;
import edu.unc.ils.mrc.hive.api.SKOSServer;
import edu.unc.ils.mrc.hive.api.impl.elmo.SKOSServerImpl;

/**
 * Thesaurus (controlled vocabulary) for biomolecular simulations. 
 * The thesaurus is assumed to be defined in HIVE (https://code.google.com/p/hive-mrc/) via a SKOS-encoded controlled vocabulary.
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class IBIOMESThesaurus {

	private final static String IBIOMES_VOCABULARY_NAME = "iBIOMES"; 
	private final static String IBIOMES_SKOS_URI = "http://edu.utah.bmi.ibiomes/skos/ibiomes.owl#"; 
	
	private SKOSServer server = null;
	private SKOSSearcher searcher = null;

	/**
	 * Create thesaurus instance
	 */
	public IBIOMESThesaurus(String propertyFile){
		this.server = new SKOSServerImpl(propertyFile);
		this.searcher = server.getSKOSSearcher();
	}
	
	public static void main(String args[]){
		//check property file is specified
		if (args.length>0)
		{
			String propertyFile = args[0];
			//check file exists
			if (!(new File(propertyFile)).exists()){
				System.out.println("HIVE property file not found at "+propertyFile);
				System.exit(1);
			}
			IBIOMESThesaurus thesaurus = null;
			
			try{
				//create thesaurus instance
				thesaurus = new IBIOMESThesaurus(propertyFile);
				//get stats
				//thesaurus.getSchemeStatistics();
				//search by keyword
				//thesaurus.searchByKeyword("dynamics", IBIOMES_VOCABULARY_NAME);
				//search concept children by URI
				//thesaurus.searchChildrenByURI(IBIOMES_SKOS_URI, "MTH11100");
				
				//create SPARQL searches
				//need to reinstantiate thesaurus for each query
				//bug with Sesame that closes connection after each SPARQL select?
				
				System.out.println("Children of 'Force field'");
				thesaurus.searchSPARQL(
					"PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
					+ "PREFIX ib: <http://edu.utah.bmi.ibiomes/skos/ibiomes.owl#> "
					+ "SELECT ?uri ?label "
					+ "WHERE { "
					+ "ib:MD00900 skos:narrower ?uri . "
					+ "?uri skos:prefLabel ?label"
					+ "}",
					IBIOMES_VOCABULARY_NAME);

				System.out.println("Parent of 'Classical molecular dynamics'");
				thesaurus.searchSPARQL(
					"PREFIX skos: <http://www.w3.org/2004/02/skos/core#> "
					+ "PREFIX ib: <http://edu.utah.bmi.ibiomes/skos/ibiomes.owl#> "
					+ "SELECT ?uri ?label "
					+ "WHERE { "
					+ "ib:MTH11100 skos:broader ?uri . "
					+ "?uri skos:prefLabel ?label"
					+ "}",
					IBIOMES_VOCABULARY_NAME);

				thesaurus.searchSPARQL(
					"SELECT ?s ?p ?o WHERE {?s ?p ?o} LIMIT 10",
					IBIOMES_VOCABULARY_NAME);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			finally
			{
				if (thesaurus!=null)
					thesaurus.close();
			}
		}
		else {
			System.out.println("HIVE property file not specified");
		}
	}
  
	/**
	 * Close thesaurus
	 */
	public void close() {
		this.server.close();
	}

	/**
	 * Perform SPARQL search
	 * @param query SPQRQL query
	 * @param vocabulary Target vocabulary
	 * @return List of matching entities
	 */
	public List<HashMap> searchSPARQL(String query, String vocabulary){

		this.searcher = server.getSKOSSearcher();
		
		System.out.println("SPARQL search:");
		System.out.println("\t "+query);
		List<HashMap> results = searcher.SPARQLSelect(query, vocabulary);
		if (results != null){
			for (HashMap<String, Object> result : results){
				System.out.println("\t Result:");
				Set<String> keys = result.keySet();
				for (String key : keys){
					System.out.println("\t\t "+key+": " + result.get(key));
				}
			}
		}
		System.out.println("");
		return results;
	}
  
	/**
	 * Perform keyword search
	 * @param keyword Keyword
	 * @return List of matching concepts
	 */
	public List<SKOSConcept> searchByKeyword(String keyword){
		return searchByKeyword(keyword, null);
	}
  
	/**
	 * Perform keyword search
	 * @param keyword Keyword
	 * @param vocabulary Target vocabulary
	 * @return List of matching concepts
	 */
	public List<SKOSConcept> searchByKeyword(String keyword, String vocabulary){
		System.out.println("Search by keyword:");
		System.out.println("\t Keyword: " + keyword);
		List<SKOSConcept> ranking = searcher.searchConceptByKeyword(keyword);
		System.out.println("\t Results in SKOSServer: " + ranking.size());
		String uri = "";
		String lp = "";
		for (SKOSConcept c : ranking) {
			uri = c.getQName().getNamespaceURI();
			lp = c.getQName().getLocalPart();
			QName qname = new QName(uri, lp);
			String origin = server.getOrigin(qname);
			if (vocabulary == null || origin.toLowerCase().equals(vocabulary)) {
				System.out.println("\t\tPrefLabel: " + c.getPrefLabel());
				System.out.println("\t\t  URI: " + uri + " Local part: " + lp);
				System.out.println("\t\t  Origin: " + origin);
			}
		}
		System.out.println("");
		return ranking;
	}
	
	/**
	 * Search SKOS concept by URI
	 * @param uri URI
	 * @param vocabulary Target vocabulary
	 * @return Matching concept
	 */
	public SKOSConcept searchByURI(String uriPrefix, String id){
		System.out.println("Search by URI:");
		SKOSConcept c = searcher.searchConceptByURI(uriPrefix, id);
		
		if (c != null){
			List<String> alt = c.getAltLabels();
			System.out.println("PrefLabel: " + c.getPrefLabel());
			for (String a : alt) {
			  System.out.println("\t altLabel: " + a);
			}
			System.out.println("\t Origin: " + server.getOrigin(c.getQName()));
			System.out.println("\t SKOS Format: \n" + c.getSKOSFormat());
		}
		System.out.println("");
		return c;
	}
	
	/**
	 * Search SKOS concept children by URI
	 * @param uri URI
	 * @param vocabulary Target vocabulary
	 * @return Matching concept
	 */
	public TreeMap<String,QName> searchChildrenByURI(String uriPrefix, String id)
	{
		SKOSConcept con = searcher.searchConceptByURI(uriPrefix, id);
		TreeMap<String,QName> children = searcher.searchChildrenByURI(uriPrefix, id);
		if (children != null){
			for (String c : children.keySet()) {
				System.out.println("prefLabel: " + c);
			}
		}
		System.out.println("");
		return children;
	}
  
	/**
	 * Retrieve the statistics for each configured vocabulary/scheme
	 */
	public void getSchemeStatistics()
	{
		TreeMap<String, SKOSScheme> vocabularies = server.getSKOSSchemas();
		Set<String> keys = vocabularies.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			SKOSScheme voc = vocabularies.get(it.next());
			System.out.println("Vocabulary: " + voc.getName());
			System.out.println("\t Long name: " + voc.getLongName());
			System.out.println("\t Number of concepts: " + voc.getNumberOfConcepts());
			System.out.println("\t Number of relations: "+ voc.getNumberOfRelations());
			System.out.println("\t Date: " + voc.getLastDate());
			System.out.println("\t Size: " + voc.getSubAlphaIndex("a").size());
			System.out.println("\t Top concepts: "+ voc.getTopConceptIndex().size() + "\n");
		}
	}
}