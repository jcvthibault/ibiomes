package edu.utah.bmi.ibiomes.web.controller.operation;

import javax.servlet.http.*;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.protovalues.FilePermissionEnum;
import org.irods.jargon.core.pub.CollectionAO;
import org.irods.jargon.core.pub.DataObjectAO;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.io.IRODSFile;
import org.irods.jargon.core.pub.io.IRODSFileFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * Update permissions (ACL)
 * @author Julien Thibault
 *
 */
public class UpdateACL extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	public static final String INHERIT = "INHERIT";
	public static final String NO_INHERIT = "NO_INHERIT";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try{
			HttpSession session = request.getSession(true);
			
			//check authentication
			IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount != null)
			{
				String uri = request.getParameter("uri");
				String target = (String)request.getParameter("dispatchto");
				
				String[] aclUsers = request.getParameterValues("userACL");
				String aclPermission = request.getParameter("permissionACL");
				String aclRecursive = request.getParameter("recursiveACL");
				
				IRODSFileFactory fileFactory = irodsAccessObjectFactory.getIRODSFileFactory(irodsAccount);
				IRODSFile iFile = fileFactory.instanceIRODSFile(uri);
				
				boolean recursive = false;
				if (aclRecursive != null && aclRecursive.equalsIgnoreCase("yes"))
					recursive = true;
				
				// if its a collection
				if (iFile.isDirectory()){
				
					CollectionAO cAO = irodsAccessObjectFactory.getCollectionAO(irodsAccount);
					
					if (aclUsers != null && aclUsers.length>0 && aclPermission != null && aclPermission.length()>0){
						
						if (aclPermission.equals(FilePermissionEnum.READ.toString())){
							for (int i=0; i < aclUsers.length; i++){
								cAO.setAccessPermissionRead(irodsAccount.getZone(), uri, aclUsers[i], recursive);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.WRITE.toString())){
							for (int i=0; i < aclUsers.length; i++){
								cAO.setAccessPermissionWrite(irodsAccount.getZone(), uri, aclUsers[i], recursive);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.OWN.toString())){
							for (int i=0; i < aclUsers.length; i++){
								cAO.setAccessPermissionOwn(irodsAccount.getZone(), uri, aclUsers[i], recursive);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.NONE.toString())){
							for (int i=0; i < aclUsers.length; i++){
								cAO.removeAccessPermissionForUser(irodsAccount.getZone(), uri, aclUsers[i], recursive);
							}
						}
					}
					else if (aclPermission != null && aclPermission.equals(INHERIT)){
						cAO.setAccessPermissionInherit(irodsAccount.getZone(), uri, recursive);
						
					}
					else if (aclPermission != null && aclPermission.equals(NO_INHERIT)){
						cAO.setAccessPermissionToNotInherit(irodsAccount.getZone(), uri, recursive);
					}
					if (target == null || target.length()==0)
						target = "/editcollection.do?uri=" + uri;
				}
				else // file ACL
				{
					DataObjectAO dAO = irodsAccessObjectFactory.getDataObjectAO(irodsAccount);
					
					if (aclUsers != null && aclUsers.length>0 && aclPermission != null && aclPermission.length()>0){
						
						if (aclPermission.equals(FilePermissionEnum.READ.name())){
							for (int i=0; i < aclUsers.length; i++){
								dAO.setAccessPermissionRead(null, uri, aclUsers[i]);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.WRITE.name())){
							for (int i=0; i < aclUsers.length; i++){
								dAO.setAccessPermissionWrite(null, uri, aclUsers[i]);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.OWN.name())){
							for (int i=0; i < aclUsers.length; i++){
								dAO.setAccessPermissionOwn(null, uri, aclUsers[i]);
							}
						}
						else if (aclPermission.equals(FilePermissionEnum.NONE.toString())){
							for (int i=0; i < aclUsers.length; i++){
								dAO.removeAccessPermissionsForUser(irodsAccount.getZone(), uri, aclUsers[i]);
							}
						}
					}
					if (target == null || target.length()==0)
						target = "/editfile.do?uri=" + uri;
				}
				ModelAndView mav = new ModelAndView(target);
				mav.addObject("message", "Permissions were successfully updated.");
				return mav;
			}
			else 
			{
				ModelAndView mav = new ModelAndView("index.do");
				return mav;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			ModelAndView mav = new ModelAndView("/error.do");
			mav.addObject("exception", e);
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
