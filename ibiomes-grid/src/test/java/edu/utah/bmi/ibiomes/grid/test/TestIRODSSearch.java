package edu.utah.bmi.ibiomes.grid.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSGenQueryExecutor;
import org.irods.jargon.core.query.AbstractIRODSGenQuery;
import org.irods.jargon.core.query.GenQueryBuilderException;
import org.irods.jargon.core.query.IRODSGenQueryBuilder;
import org.irods.jargon.core.query.IRODSGenQueryFromBuilder;
import org.irods.jargon.core.query.IRODSQueryResultRow;
import org.irods.jargon.core.query.IRODSQueryResultSetInterface;
import org.irods.jargon.core.query.JargonQueryException;
import org.irods.jargon.core.query.QueryConditionOperators;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.irods.jargon.core.query.TranslatedIRODSGenQuery;

import edu.utah.bmi.ibiomes.grid.test.common.TestIRODSConnector;
import edu.utah.bmi.ibiomes.metadata.BiosimMetadata;
import edu.utah.bmi.ibiomes.search.IBIOMESCollectionSearch;
import edu.utah.bmi.ibiomes.security.IRODSConnector;

public class TestIRODSSearch {

	/**
	 * @param args
	 * @throws GenQueryBuilderException 
	 * @throws JargonQueryException 
	 * @throws JargonException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws GenQueryBuilderException, JargonException, JargonQueryException, IOException {
		
		boolean isCaseInsensitive = true;
		String collectionName = "%";
		
		IRODSGenQueryExecutor irodsQueryExecutor;
		AbstractIRODSGenQuery irodsQuery;
		IRODSQueryResultSetInterface irodsResults;
		
		//connect to iBIOMES system
		IRODSConnector cnx = TestIRODSConnector.getTestConnector();
		IRODSAccessObjectFactory irodsAccessObjectFactory = cnx.getFileSystem().getIRODSAccessObjectFactory();
		IRODSAccount account = cnx.getAccount();
		
		irodsQueryExecutor = irodsAccessObjectFactory.getIRODSGenQueryExecutor(account);
				
		IRODSGenQueryBuilder queryBuilder = new IRODSGenQueryBuilder(true, isCaseInsensitive, null);
	
		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_NAME);
		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_OWNER_NAME);
		queryBuilder.addSelectAsGenQueryValue(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
		
		//queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_COLL_ATTR_NAME , QueryConditionOperators.LIKE ,BiosimMetadata.IBIOMES_FILE_TYPE );
		//queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_META_COLL_ATTR_VALUE, QueryConditionOperators.LIKE , "%" + BiosimMetadata.FILE_COLLECTION);
		
		queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_OWNER_NAME, QueryConditionOperators.LIKE, "jthibault");

		queryBuilder.addConditionAsGenQueryField(RodsGenQueryEnum.COL_COLL_NAME, QueryConditionOperators.LIKE, collectionName);
		
		IRODSGenQueryFromBuilder query = queryBuilder.exportIRODSQueryFromBuilder(12);
		TranslatedIRODSGenQuery trQuery = query.convertToTranslatedIRODSGenQuery();

		irodsQuery = trQuery.getIrodsQuery();
		
		irodsResults = irodsQueryExecutor.executeIRODSQuery(irodsQuery, 0);
		List<IRODSQueryResultRow> rows = irodsResults.getResults();
		for (IRODSQueryResultRow row : rows){
			String path = row.getColumn(RodsGenQueryEnum.COL_COLL_NAME.getName());
			System.out.println(path);
		}
		
		//IBIOMESCollectionSearch collectionSearch = new IBIOMESCollectionSearch(irodsAccessObjectFactory, acc);
	}

}
