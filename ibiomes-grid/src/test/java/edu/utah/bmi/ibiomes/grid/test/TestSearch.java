package edu.utah.bmi.ibiomes.grid.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.query.AVUQueryElement;
import org.irods.jargon.core.query.AVUQueryOperatorEnum;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.irods.jargon.core.query.AVUQueryElement.AVUQueryPart;

import edu.utah.bmi.ibiomes.experiment.comp.qm.QMParameterSet;
import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.metadata.FileMetadata;
import edu.utah.bmi.ibiomes.metadata.MethodMetadata;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESFileSearch;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

public class TestSearch {
	
	private IRODSAccount account;
	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	private IBIOMESCollectionAO collectionAO;
	private IBIOMESFileAO fileAO;
	
	public TestSearch(IRODSAccount account, IRODSAccessObjectFactory irodsAccessObjectFactory){
		this.account = account;
		this.irodsAccessObjectFactory = irodsAccessObjectFactory;
		this.fileAO = new IBIOMESFileAO(irodsAccessObjectFactory, account);
		this.collectionAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, account);
	}
	
	public void runSearchTests() throws Exception
	{	
		IBIOMESFileSearch filesearch = null;
		List<String> files = null;
		
		ArrayList<AVUQueryElement> avus;
		
		
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES from AVU");
		System.out.println("=====================================================================");
		
		avus = new ArrayList<AVUQueryElement>();
		/*avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, FileMetadata.FILE_FORMAT));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "PDB"));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, TopologyMetadata.RESIDUE_CHAIN));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "%U8U%"));*/
		
		
		filesearch = new IBIOMESFileSearch(irodsAccessObjectFactory, account);
		filesearch.setAvuConditions(avus);
		filesearch.setNumberOfRowsRequested(10);
		filesearch.setOwnerUsername(account.getUserName());
		files = filesearch.executeAndClose();
		
		dumpFiles(files);

		
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES from name");
		System.out.println("=====================================================================");
		
		filesearch = new IBIOMESFileSearch(irodsAccessObjectFactory, account);
		filesearch.setNumberOfRowsRequested(10);
		filesearch.setFilename("%output%");
		files = filesearch.executeAndClose();
		
		dumpFiles(files);

		
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES from directory (search)");
		System.out.println("=====================================================================");
		
		filesearch = new IBIOMESFileSearch(irodsAccessObjectFactory, account);
		filesearch.setNumberOfRowsRequested(10);
		filesearch.setDirectory("/ibiomesZone/home/test/test1");
		files = filesearch.executeAndClose();
		
		dumpFiles(files);

				
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES from SIZE");
		System.out.println("=====================================================================");
		
		filesearch = new IBIOMESFileSearch(irodsAccessObjectFactory, account);
		filesearch.setNumberOfRowsRequested(10);
		filesearch.setSizeMin(3000000);
		files = filesearch.executeAndClose();

		dumpFiles(files);

		
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES UNDER");
		System.out.println("=====================================================================");
		
		List<AVUQueryElement> avuQuery = new ArrayList<AVUQueryElement>();
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS));
		avuQuery.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, FileMetadata.FILE_CLASS_ANALYSIS));
		
		//find files at first level
		IBIOMESFileSearch search = new IBIOMESFileSearch( irodsAccessObjectFactory, account);
      	search.setAvuConditions(avuQuery);
      	search.setNumberOfRowsRequested(12);
      	search.setDirectory("/ibiomesZone/home/jthibault/1BIV");
      	files = search.executeAndClose();
      			
      	dumpFiles(files);
		
		
		System.out.println("=====================================================================");
		System.out.println("              FIND FILES from DATE");
		System.out.println("=====================================================================");
		
		filesearch = new IBIOMESFileSearch(irodsAccessObjectFactory, account);
		filesearch.setNumberOfRowsRequested(20);
		Date date = new Date();
		filesearch.setCreationDateMax(date.getTime());
		files = filesearch.executeAndClose();

		dumpFiles(files);
		      	
		System.out.println("=====================================================================");
		System.out.println("              FIND COLLECTIONS WITH KEYWORDS");
		System.out.println("=====================================================================");
		
		avus = new ArrayList<AVUQueryElement>();
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, "%"));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "RNA"));
		
		IBIOMESExperimentSearch collectionSearch = new IBIOMESExperimentSearch(irodsAccessObjectFactory, account);
		collectionSearch.setNumberOfRowsRequested(20);
		collectionSearch.setAvuConditions(avus);
		List<String> colls = collectionSearch.executeAndClose();

		dumpCollections(colls);
		
		System.out.println("=====================================================================");
		System.out.println("              FIND COLLECTIONS by OWNER");
		System.out.println("=====================================================================");
		avus = new ArrayList<AVUQueryElement>();
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.EQUAL, MethodMetadata.COMPUTATIONAL_METHOD_NAME));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE,  "Stochastic dynamics"));
		
		collectionSearch = new IBIOMESExperimentSearch(irodsAccessObjectFactory, account);
		collectionSearch.setNumberOfRowsRequested(12);
		collectionSearch.setAvuConditions(avus);
		collectionSearch.setOrderBy(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
		collectionSearch.setAscendant(false);
		collectionSearch.setOwnerUsername("jthibault");
		colls = collectionSearch.executeAndClose();

		dumpCollections(colls);
		
		System.out.println("=====================================================================");
		System.out.println("              FIND COLLECTIONS");
		System.out.println("=====================================================================");
		
		avus = new ArrayList<AVUQueryElement>();
		//avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, "%"));
		//avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "RNA"));
		
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, MethodMetadata.CALCULATION));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, QMParameterSet.CALCULATION_FREQUENCY));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, MethodMetadata.CALCULATION));
		avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, QMParameterSet.QM_GEOMETRY_OPTIMIZATION));
		
		collectionSearch = new IBIOMESExperimentSearch(irodsAccessObjectFactory, account);
		collectionSearch.setNumberOfRowsRequested(20);
		collectionSearch.setAvuConditions(avus);
		collectionSearch.setOrderBy(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
		collectionSearch.setAscendant(false);
		colls = collectionSearch.executeAndClose();

		dumpCollections(colls);
				
		System.out.println("=====================================================================");
		System.out.println("              FIND COLLECTIONS with paging ");
		System.out.println("=====================================================================");
		
		avus = new ArrayList<AVUQueryElement>();
		//avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.ATTRIBUTE, AVUQueryOperatorEnum.LIKE, "%"));
		//avus.add(AVUQueryElement.instanceForValueQuery(AVUQueryPart.VALUE, AVUQueryOperatorEnum.LIKE, "RNA"));
		
		int nRowsWanted = 5;
		collectionSearch = new IBIOMESExperimentSearch(irodsAccessObjectFactory, account);
		collectionSearch.setNumberOfRowsRequested(nRowsWanted);
		collectionSearch.setAvuConditions(avus);
		colls = collectionSearch.execute();
		System.out.println("Number of matches: " + collectionSearch.getTotalNumberOfRecords());
		
		System.out.println("[Page 1]");
		for (String res : colls){
			System.out.println("\t" + res);
		}
		
		int i = 2;
		int offset = 0;
		while (collectionSearch.hasMoreResults())
		{
			offset += nRowsWanted;
			colls = collectionSearch.executeWithStart(offset);
			System.out.println("[Page "+(i)+"]");
			for (String res : colls){
				System.out.println("\t" + res);
			}
			i++;
		}
		
		colls = collectionSearch.executeWithStart(nRowsWanted);
		System.out.println("[Back to page 2]");
		for (String res : colls){
			System.out.println("\t" + res);
		}
		
		colls = collectionSearch.executeWithStart(0);
		System.out.println("[Back to page 1]");
		for (String res : colls){
			System.out.println("\t" + res);
		}
		
		collectionSearch.closeSearch();
	}
	
	public static void main(String[] args) throws Exception
	{
		//get connection to iRODS
		IRODSConnector cnx = TestIRODSConnector.getTestConnector();
		//IRODSConnector cnx = TestIRODSConnector.getAnonymousAccess();
		IRODSAccessObjectFactory irodsAccessObjectFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
		
		TestSearch tests = new TestSearch(cnx.getAccount(), irodsAccessObjectFactory);
		
		long startTime = System.currentTimeMillis();
		
		tests.runSearchTests();
		
		long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime-startTime));
	}
	
	private void dumpCollections(List<String> colls) throws JargonException, JargonQueryException{
		System.out.println("Matching collections:");
		for (int c=0; c<colls.size();c++){
			System.out.println((c+1) + ". " + colls.get(c));
		}
		collectionAO.getCollectionsFromPath(colls);
	}
	
	private void dumpFiles(List<String> files) throws JargonException, JargonQueryException{
		System.out.println("Matching collections:");
		for (int c=0; c<files.size();c++){
			System.out.println((c+1) + ". " + files.get(c));
		}
		fileAO.getFilesFromPath(files);
	}
}
