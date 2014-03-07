package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

import java.util.List;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class UpdateTopologyFromFile extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			String fileRelPath = request.getParameter("TOPOLOGY_FILE_PATH");
			String uri = request.getParameter("uri");
			String target = (String)request.getParameter("dispatchto");
			
			if (target == null || target.length()==0)
				target = "/editcollection.do?uri=" + uri;
			
			CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
			
			/*IRODSFileFactory factory = fs.getIRODSFileFactory(account);
			IRODSFile topofile = factory.instanceIRODSFile(uri + "/" + fileRelPath);*/

			IBIOMESCollectionAO  ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESFileAO  ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			//get reference to collection
			IBIOMESCollection collection = ibiomesCollAO.getCollectionFromPath(uri);
			MetadataAVUList existingMetadata = collection.getMetadata();
			//get reference to topology file
			IBIOMESFile topofile = ibiomesFileAO.getFileByPath(uri + "/" + fileRelPath);
			
			//get topology-specific metadata only
			MetadataAVUList newMetadata = topofile.getMetadata().getTopologyMetadata();
			
			List<String> keys = newMetadata.getAttributes();
			for (String attribute : keys)
			{
				List<String> newValues = newMetadata.getValues(attribute);
				
				boolean exists = false;
				
				//get old value (if any)
				List<String> oldValues = null;
				if (existingMetadata.containsAttribute(attribute)){
					exists = true;
					oldValues = existingMetadata.getValues(attribute);
				}
				
				//if the old metadata need to be removed
				if (exists){
					for (String oldValue : oldValues){
						collAO.deleteAVUMetadata(uri, AvuData.instance(attribute, oldValue, ""));
					}
				}
				//if need to add new value
				for (String newValue : newValues){
					collAO.addAVUMetadata(uri, AvuData.instance(attribute, newValue, ""));
				}
			}
			ModelAndView mav = new ModelAndView(target);
			mav.addObject("message", "Metadata successfully updated.");
			return mav;
		}
		else
		{
			ModelAndView mav = new ModelAndView("index.do");
			return mav;
		}
	}
	
	 /**
	  * @return the irodsAccessObjectFactory
	  */
	 public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	 }

	 /**
	 * @param irodsAccessObjectFactory
	 *            the irodsAccessObjectFactory to set
	 */
	 public void setIrodsAccessObjectFactory(
			IRODSAccessObjectFactory irodsAccessObjectFactory) {
		 this.irodsAccessObjectFactory = irodsAccessObjectFactory;
	 }
}
		