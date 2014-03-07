package edu.utah.bmi.ibiomes.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.connection.IRODSSession;
import org.irods.jargon.core.connection.auth.AuthResponse;
import org.irods.jargon.core.exception.AuthenticationException;
import org.irods.jargon.core.exception.JargonException;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.IRODSAccessObjectFactoryImpl;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.core.pub.UserGroupAO;
import org.irods.jargon.core.query.RodsGenQueryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.utah.bmi.ibiomes.web.domain.Method;
import edu.utah.bmi.ibiomes.web.domain.Simulation;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollection;
import edu.utah.bmi.ibiomes.pub.IBIOMESCollectionList;
import edu.utah.bmi.ibiomes.pub.IBIOMESExperimentAO;
import edu.utah.bmi.ibiomes.pub.UserGroupListMetadata;
import edu.utah.bmi.ibiomes.pub.UserGroupMetadata;
import edu.utah.bmi.ibiomes.pub.UserListMetadata;
import edu.utah.bmi.ibiomes.pub.UserMetadata;
import edu.utah.bmi.ibiomes.search.IBIOMESExperimentSearch;
import edu.utah.bmi.ibiomes.web.IBIOMESResponse;

@Controller
public class UserService
{
	@Autowired(required = true)  
    private HttpServletRequest request; 
    public HttpServletRequest getRequest() {
        return request;  
    }
    
    @Autowired(required = true)  
    private IRODSAccessObjectFactory irodsAccessObjectFactory;
	public IRODSAccessObjectFactory getIrodsAccessObjectFactory() {
		return irodsAccessObjectFactory;
	}
	
	/**
	 * Retrieve list of iBIOMES users.
	 * @param usertype User type (admins, users, or groups)
	 * @return User list
	 * @throws JargonException 
	 * @throws IOException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/users", method = RequestMethod.GET)
	@ResponseBody
	public UserListMetadata getUsers() throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
		UserListMetadata users = new UserListMetadata(userAO.findAll());
		return users;
	}

	/**
	 * Retrieve info for particular iBIOMES user.
	 * @param headers
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/users/{username}", method = RequestMethod.GET)
	@ResponseBody
	public UserMetadata getUser(
			@PathVariable("username") String username) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
		UserMetadata user = new UserMetadata(userAO.findByName(username));
		return user;
	}
	
	/**
	 * Retrieve list of latest uploads for iBIOMES user.
	 * @param headers
	 * @param username
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/services/users/{username}/uploads", method = RequestMethod.GET)
	@ResponseBody
	public List<Simulation> getUserUploads(
			@PathVariable("username") String username,
			@RequestParam(value="nRows", required=false, defaultValue="12") int nRows) throws Exception
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		
		List<Simulation> simulations = new ArrayList<Simulation>();
		IBIOMESExperimentAO eao = new IBIOMESExperimentAO(irodsAccessObjectFactory, irodsAccount);
		
		IBIOMESExperimentSearch search = new IBIOMESExperimentSearch( irodsAccessObjectFactory, irodsAccount);
      	search.setNumberOfRowsRequested(12);
      	search.setOrderBy(RodsGenQueryEnum.COL_COLL_CREATE_TIME);
      	search.setOwnerUsername(username);

		List<String> collectionPaths = search.executeAndClose();
		
		if (collectionPaths != null && collectionPaths.size()>0)
		{
			//get metadata
			IBIOMESCollectionList collections = eao.getExperimentsFromPath(collectionPaths);
			
			for (IBIOMESCollection c : collections)
			{
				//populate pojo with simulation info
				Simulation sim = new Simulation(c);
				//get abbreviation for method instead of full string
				String method = sim.getMethod().getName();
				if (method == null){
					method = "?";
				}
				else if (method.equals(Method.METHOD_MD) 
						|| method.equals(Method.METHOD_LANGEVIN_DYNAMICS)){
					method = "MD";
				}
				else if (method.equals(Method.METHOD_REMD)){
					method = "REMD";
				}
				else if (method.equals(Method.METHOD_QM)){
					method = "QM";
				}
				else if (method.equals(Method.METHOD_QUANTUM_MD)){
					method = "QMD";
				}
				else if (method.equals(Method.METHOD_QMMM)){
					method = "QM/MM";
				}
				sim.getMethod().setName(method);
				simulations.add(sim);
			}
		}
		
		return simulations;
	}
	
	/**
	 * Retrieve group memberships for given user.
	 * @param headers
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/users/{username}/usergroups", method = RequestMethod.GET)
	@ResponseBody
	public UserGroupListMetadata getUserMemberships(
			@PathVariable("username") String username) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserGroupAO userGroupAO = irodsAccessObjectFactory.getUserGroupAO(irodsAccount);
		UserGroupListMetadata groups = new UserGroupListMetadata(userGroupAO.findUserGroupsForUser(username));
		return groups;
	}
	
	/**
	 * Retrieve list of user groups.
	 * @param headers
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/usergroups", method = RequestMethod.GET)
	@ResponseBody
	public UserGroupListMetadata getUserGroups() throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserGroupAO userGroupAO = irodsAccessObjectFactory.getUserGroupAO(irodsAccount);
		UserGroupListMetadata groups = new UserGroupListMetadata(userGroupAO.findAll());
		return groups;
	}
	
	/**
	 * Retrieve list of user groups.
	 * @param headers
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/usergroups/{groupname}", method = RequestMethod.GET)
	@ResponseBody
	public UserGroupMetadata getUserGroup(
			@PathVariable("groupname") String groupname) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserGroupAO userGroupAO = irodsAccessObjectFactory.getUserGroupAO(irodsAccount);
		UserGroupMetadata user = new UserGroupMetadata(userGroupAO.findByName(groupname));

		return user;
	}
	
	/**
	 * Retrieve list of user groups.
	 * @param headers
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/usergroups/{groupname}/users", method = RequestMethod.GET)
	@ResponseBody
	public UserListMetadata getUserGroupMembers(
			@PathVariable("groupname") String groupname) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserGroupAO userGroupAO = irodsAccessObjectFactory.getUserGroupAO(irodsAccount);
		UserListMetadata users = new UserListMetadata(userGroupAO.listUserGroupMembers(groupname));
		return users;
	}
	
	/**
	 * Update user password.
	 * @param headers
	 * @param username
	 * @return
	 * @throws IOException
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/users/{username}/updatepwd", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse updateUserPassword(
			@PathVariable("username") String username,
			@RequestParam("oldPwd") String oldPwd,
			@RequestParam("newPwd") String newPwd) throws JargonException, IOException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
		try{
			userAO.changeAUserPasswordByThatUser(username, oldPwd, newPwd);

			//create new iRODS account object to reflect changes
			IRODSAccount newAccount = IRODSAccount.instance(
					irodsAccount.getHost(),
					irodsAccount.getPort(),
					irodsAccount.getUserName(), 
					newPwd,
					irodsAccount.getHomeDirectory(), 
					irodsAccount.getZone(), 
					irodsAccount.getDefaultStorageResource());
			
			IRODSSession session = irodsAccessObjectFactory.getIrodsSession();
			
			//TODO UPDATE SESSION INFO!!! OR SOEMTHING WRONG HERE...
			
			AuthResponse authResponse = null;
			IRODSAccessObjectFactory accessAO = IRODSAccessObjectFactoryImpl.instance(session);
			try {
				request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
				authResponse = accessAO.authenticateIRODSAccount(newAccount);
				session.currentConnection(authResponse.getAuthenticatedIRODSAccount());
				request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", authResponse.getAuthenticatedIRODSAccount());
			} 
			catch(JargonException e) {
				e.printStackTrace();
				return new IBIOMESResponse(false, "Cannot update session information. Exception: " + e.getLocalizedMessage(), null);
			}
			
		} catch(AuthenticationException exc) {
			return new IBIOMESResponse(false, "Current password is not correct", null);
		}
		
		return new IBIOMESResponse(true, "Password successfully updated", null);
	}
	
	/**
	 * Create temporary user password.
	 * @return
	 * @throws JargonException 
	 */
	@RequestMapping(value = "/services/user/getTmpPassword", method = RequestMethod.GET)
	@ResponseBody
	public IBIOMESResponse getTemporaryPassword() throws JargonException
	{
		IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if (irodsAccount.isAnonymousAccount()){
			return new IBIOMESResponse(true, "", "");
		}
		else {
			UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
			try{
				String password = userAO.getTemporaryPasswordForConnectedUser();
				return new IBIOMESResponse(true, "", password);
			} catch(AuthenticationException exc) {
				return new IBIOMESResponse(false, "Could not generate temporary password for user '"+irodsAccount.getUserName()+"'", null);
			}
		}
	}
}
