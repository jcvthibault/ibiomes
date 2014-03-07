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

package edu.utah.bmi.ibiomes.catalog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * Wrapper around Lucene dictionary
 * @author Julien Thibault - University of Utah, BMI
 *
 */
public class LuceneDictionary {

	private IndexSearcher searcher;
    private String lookupFieldName;
    private IndexReader indexReader;
    private Analyzer analyzer;
    
    private int maxNumHits;

    /**
     * Instantiate new Lucene dictionary
     * @param indexFile Lucene index file
     * @param lookupFieldName Field used for the lookup
     * @param maxHits Maximum number of hits to return
     * @throws Exception
     */
    public LuceneDictionary(File indexFile, String lookupFieldName, int maxHits) throws IOException
    { 
    	if (indexFile.exists()){
			this.indexReader = DirectoryReader.open(FSDirectory.open(indexFile));
			this.searcher = new IndexSearcher(this.indexReader);
			this.analyzer = new KeywordAnalyzer();
	        this.lookupFieldName = lookupFieldName;
	        this.maxNumHits = maxHits;
		}
    	else throw new IOException("Lucene index not found at '"+ indexFile.getAbsolutePath() +"'");
    }
    
    /**
     * Lookup a term in the dictionary and return only exact matches
     * @param term Term to lookup
     * @return List of hits
     * @throws Exception
     */
    public List<Document> lookupExactTerm(String term) throws Exception
    {
    	List<Document> docs = new ArrayList<Document>();
    	
    	//create query
    	QueryParser parser = new QueryParser(Version.LUCENE_41, this.lookupFieldName, this.analyzer);
    	Query query = parser.parse(term);
		
    	//search
    	ScoreDoc[] hits = this.searcher.search( query, null, this.maxNumHits).scoreDocs;
        for (int i = 0; i < hits.length; i++)
        {
			Document hitDoc = this.searcher.doc(hits[i].doc);
			docs.add(hitDoc);
        }
        return docs;
    }
    
    /**
     * Close Lucene index
     * @throws IOException
     */
    public void closeSearch() throws IOException{
    	this.indexReader.close();
    }
    
    /**
     * Return the number of entries in the index
     * @return Number of entries
     */
    public int getNumberOfEntries(){
    	return this.indexReader.numDocs();
    }
}

