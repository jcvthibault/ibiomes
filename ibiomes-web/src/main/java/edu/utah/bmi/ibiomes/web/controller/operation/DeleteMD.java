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

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Remove metadata from iBIOMES file/collection.
 * @author Julien Thibault
 *
 */
public class DeleteMD extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		
		HttpSession session = request.getSession(true);
		
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount != null)
		{
			String uri = request.getParameter("uri");
			
			//retrieve list of metadata pairs to delete
			Enumeration<String> attributes = request.getParameterNames();
			ArrayList<AvuData> metadataPairs = new ArrayList<AvuData>();
			while (attributes.hasMoreElements()){
				String attr = attributes.nextElement();
				if (attr.compareTo("uri")!=0 && attr.compareTo("dispatchto")!=0){
					metadataPairs.add(new AvuData(attr, request.getParameter(attr), ""));
				}
			}
			//get target
			String target = (String)request.getParameter("dispatchto");
			
			IRODSFileFactory factory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
			IRODSFile file = factory.instanceIRODSFile(uri);
			
			if (file.isDirectory()){
				CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
				//delete metadata
				for (AvuData avu : metadataPairs){
					cAO.deleteAVUMetadata(uri, avu);
				}
				if (target == null || target.length()==0)
					target = "/editcollection.html?uri="+uri;
			}
			else{
				DataObjectAO data = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
				//delete metadata
				for (AvuData avu : metadataPairs){
					data.deleteAVUMetadata(uri, avu);
				}
				if (target == null || target.length()==0)
					target = "/editfile.do?uri="+uri;
			}			
			
			ModelAndView mav = new ModelAndView(target);
			mav.addObject("message", "Metadata successfully removed.");
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
