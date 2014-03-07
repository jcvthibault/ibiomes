package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.domain.AvuData;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionAO;
import edu.utah.bmi.ibiomes.pub.IBIOMESFile;
import edu.utah.bmi.ibiomes.pub.IBIOMESFileAO;
import edu.utah.bmi.ibiomes.metadata.MetadataAVU;
import edu.utah.bmi.ibiomes.metadata.MetadataAVUList;

import java.util.Enumeration;
import java.util.List;

/**
 * Search simulations in iRODS file system
 * @author Julien Thibault
 *
 */
public class UpdateMD extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	private static final String TO_REMOVE = "unknown";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			String target = (String)request.getParameter("dispatchto");
			
			MetadataAVUList updatedMetadata = new MetadataAVUList();
			//retrieve list of metadata pairs to add
			Enumeration<String> attributes = request.getParameterNames();
			while (attributes.hasMoreElements())
			{
				String attr = attributes.nextElement();
				if (!attr.equals("uri") && !attr.equals("dispatchto") && !attr.equals("x") && !attr.equals("y")){
					String value = request.getParameter(attr);
					if (value == null || value.length()==0 || value.equals("null"))
						value = TO_REMOVE;
					updatedMetadata.add(new MetadataAVU(attr, value));
				}
			}
			
			String uri = request.getParameter("uri");

			IBIOMESCollectionAO  ibiomesCollAO = new IBIOMESCollectionAO(irodsAccessObjectFactory, irodsAccount);
			IBIOMESFileAO  ibiomesFileAO = new IBIOMESFileAO(irodsAccessObjectFactory, irodsAccount);
			IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile file = factory.instanceIRODSFile(uri);
			
			//if file is DIRECTORY (collection)
			if (file.isDirectory())
			{
				//retrieve metadata for the current collection
				IBIOMESCollection coll = ibiomesCollAO.getCollectionFromPath(uri);
				MetadataAVUList existingMetadata = coll.getMetadata();
				
				CollectionAO collAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				
				List<String> keys = updatedMetadata.getAttributes();
				for (String attribute : keys)
				{
					List<String> mdList = updatedMetadata.getValues(attribute);
					String value = mdList.get(0);
					
					boolean hasValue = (value.compareTo(TO_REMOVE)!=0);
					boolean exists = false;
					
					//get old value (if any)
					String oldValue = "";
					if (existingMetadata.containsAttribute(attribute)){
						exists = true;
						oldValue = existingMetadata.getValue(attribute);
					}
					
					//if the old metadata need to be removed
					if (exists){
						collAO.deleteAVUMetadata(uri, AvuData.instance(attribute, oldValue, ""));
					}
					//if need to add new value
					if (hasValue){
						collAO.addAVUMetadata(uri, AvuData.instance(attribute, value, ""));
					}
				}
				if (target == null || target.length()==0)
					target = "/editcollection.do?uri="+uri;
			}
			else
			{
				//retrieve metadata for the current file
				IBIOMESFile ibiomesFile = ibiomesFileAO.getFileByPath(uri);
				MetadataAVUList existingMetadata = ibiomesFile.getMetadata();
				
				DataObjectAO dataAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				
				List<String> keys = updatedMetadata.getAttributes();
				for (String attribute : keys)
				{
					List<String> mdList = updatedMetadata.getValues(attribute);
					String value = mdList.get(0);
					
					boolean hasValue = (value.compareTo(TO_REMOVE)!=0);
					boolean exists = false;
					
					//get old value (if any)
					String oldValue = "";
					if (existingMetadata.containsAttribute(attribute)){
						exists = true;
						oldValue = existingMetadata.getValue(attribute);
					}
					
					//if the old metadata need to be removed
					if (exists){
						dataAO.deleteAVUMetadata(uri, AvuData.instance(attribute, oldValue, ""));
					}
					//if need to add new value
					if (hasValue){
						dataAO.addAVUMetadata(uri, AvuData.instance(attribute, value, ""));
					}
				}
				if (target == null || target.length()==0)
					target = "/editfile.do?uri="+uri;
			}
			
			ModelAndView mav = new ModelAndView(target);
			mav.addObject("message", "Metadata successfully updated");
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
