package edu.utah.bmi.ibiomes.grid.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;
import edu.utah.bmi.ibiomes.pub.set.ExperimentSetSqlConnector;
import edu.utah.bmi.ibiomes.pub.set.IBIOMESExperimentSet;

public class ExperimentSetSQLTest {
	
	private final Logger logger = Logger.getLogger(ExperimentSetSQLTest.class);
	
	@Test
	public void testExperimentSetSQL() throws Exception
	{
		/*MetadataAVUList metadata = null;
		String user = null;
		List<IBIOMESExperimentSet> sets = null;

		ApplicationContext context = new ClassPathXmlApplicationContext("spring-test.xml");
		ExperimentSetSqlConnector sql = (ExperimentSetSqlConnector)context.getBean("experimentSetSqlConnector");
		
		user = "user1";
		
		long id = sql.createExperimentSet(user, "set_1", "Set 1", true);
		logger.info("Created set with ID '" + id + "'");
		
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_1_user1","value_1_user1","unit_1_user1"));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_2_user1","value_2_user1","unit_2_user1"));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_3_user1","value_3_user1",""));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_4_user1","value_4_user1"));
		
		metadata = new MetadataAVUList();
		metadata.add(new MetadataAVU("avu_1_user1","value_1_user1","unit_1_user1"));
		metadata.add(new MetadataAVU("avu_2_user1","value_2","unit_2_user1"));
		metadata.add(new MetadataAVU("avu_3_user1","value_3_user1",""));
		metadata.add(new MetadataAVU("avu_5_user1","value_5_user1"));
		
		id = sql.createExperimentSet(user, "set_2", "Set 2", true);
		logger.info("Created set with ID '" + id + "'");
		
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_21_user1","value_21_user1","unit_21_user1"));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_22_user1","value_22_user1","unit_22_user1"));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_23_user1","value_23_user1",""));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_24_user1","value_24_user1"));
		
		sets = sql.getExperimentSetsForUser(user);
		String setsStr = "";
		for (IBIOMESExperimentSet set : sets){
			setsStr += "["+set.getOwner()+"]" + set.getName() + " (" + set.getDescription() + ")\n";
		}
		logger.info("Sets for user '" + user + "'\n" + setsStr);

		//delete
		sql.deleteExperimentSet(id, user);
		logger.info("Deleted set with ID '" + id + "'");
		
		
		user = "user2";
		
		id = sql.createExperimentSet(user, "set_2", "Set 2", true);
		logger.info("Created set with ID '" + id + "'");
		
		sql.addExperimentSetMetadata(id, user,new MetadataAVU("avu_1_user2","value_1_user2","unit_1_user2"));
		sql.addExperimentSetMetadata(id, user,new MetadataAVU("avu_2_user2","value_2_user2","unit_2_user2"));
		sql.addExperimentSetMetadata(id, user,new MetadataAVU("avu_3_user2","value_3_user2",""));
		sql.addExperimentSetMetadata(id, user, new MetadataAVU("avu_4_user2","value_4_user2"));
		
		sets = sql.getExperimentSetsForUser(user);
		setsStr = "";
		for (IBIOMESExperimentSet set : sets){
			setsStr += "[" + set.getId() + "]["+set.getOwner()+"]" + set.getName() + " (" + set.getDescription() + ")\n";
		}
		logger.info("Sets for user '" + user + "'\n" + setsStr);
				
		//delete
		sql.deleteExperimentSet(id, user);
		logger.info("Deleted set with ID '" + id + "'");
		
		sets = sql.getExperimentSetsForUser(user);
		setsStr = "";
		for (IBIOMESExperimentSet set : sets){
			setsStr += "["+set.getOwner()+"]" + set.getName() + " (" + set.getDescription() + ")\n";
		}
		logger.info("Sets for user '" + user + "'\n" + setsStr);*/
	}
}
