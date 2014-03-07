package edu.utah.bmi.ibiomes.web.listener;

import java.io.File;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import edu.utah.bmi.ibiomes.search.IBIOMESFileSearch;
import edu.utah.bmi.ibiomes.search.IBIOMESSearch;

/**
 * iBIOMES session listener. Automatically closes iRODS session if JSP session is invalidated or times out.
 * @author Julien Thibault, University of Utah
 *
 */
public class IBIOMESSessionListener implements HttpSessionListener {

	private static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent event)
	{
		activeSessions++;
		//System.out.println("[iBIOMES] JSP session created ("+ activeSessions +" active sessions)");
	}

	public void sessionDestroyed(HttpSessionEvent event) 
	{
		if(activeSessions > 0){ 
			activeSessions--;
			//System.out.println("[iBIOMES] JSP session destroyed("+ activeSessions +" active sessions)");
		}

		HttpSession session = event.getSession();
		
		//close possible iRODS connections
		try{
			IBIOMESFileSearch fileSearch = (IBIOMESFileSearch)session.getAttribute("SESSION_FILE_LIST");
			if (fileSearch!=null)
				fileSearch.closeSearch();
		} catch (Exception e){
			e.printStackTrace();
		}
		try{
			IBIOMESSearch search = (IBIOMESSearch)session.getAttribute("SEARCH_OBJECT");
			if (search!=null)
				search.closeSearch();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//remove temporary files
		String ibiomesPath = session.getServletContext().getRealPath("/");
		if (session != null)
		{
			File tempDir = new File(ibiomesPath + "/" + (String)session.getAttribute("USER_DIR"));
			File[] files = tempDir.listFiles();
			for (int f=0; f<files.length; f++){
				files[f].delete();
			}
			tempDir.delete();
			
			//close iRODS session
			session.removeAttribute("SPRING_SECURITY_CONTEXT");
		}		
		
	}
}
