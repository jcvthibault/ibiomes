package edu.utah.bmi.ibiomes.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.irods.jargon.core.connection.IRODSAccount;
import org.irods.jargon.core.protovalues.UserTypeEnum;
import org.irods.jargon.core.pub.IRODSAccessObjectFactory;
import org.irods.jargon.core.pub.UserAO;
import org.irods.jargon.core.pub.UserGroupAO;
import org.irods.jargon.core.pub.domain.User;
import org.irods.jargon.core.pub.domain.UserGroup;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


public class UserProfileController extends AbstractController {

	private IRODSAccessObjectFactory irodsAccessObjectFactory;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		//check authentication
		IRODSAccount irodsAccount = (IRODSAccount)session.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if (irodsAccount != null)
		{
			String username = request.getParameter("name");
			
			ModelAndView mav = new ModelAndView();
			
			//get user list
			UserAO userAO = irodsAccessObjectFactory.getUserAO(irodsAccount);
			UserGroupAO userGroupAO = irodsAccessObjectFactory.getUserGroupAO(irodsAccount);
			
			//get user/group
			User user = userAO.findByName(username);
			
			//check if this is a group or a user
			if (!user.getUserType().equals(UserTypeEnum.RODS_GROUP))
			{	
				mav.addObject("user", user);
				mav.setViewName("user.jsp");
			}
			else 
			{
				//get group members
				List<User> groupUsers = userGroupAO.listUserGroupMembers(username);
				mav.addObject("groupUserList", groupUsers);
				//get user group info
				UserGroup group = userGroupAO.findByName(username);
				mav.addObject("group", group);
				mav.setViewName("usergroup.jsp");
			}
			return mav;
		}
		else 
		{
			if (session!=null)
				session.invalidate();
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
