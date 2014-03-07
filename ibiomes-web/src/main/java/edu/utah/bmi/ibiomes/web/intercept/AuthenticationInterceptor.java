package edu.utah.bmi.ibiomes.web.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.irods.jargon.core.connection.IRODSAccount;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Deprecated
@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
	{
		String uri = request.getRequestURI();
		if (!uri.endsWith("login.do") && !uri.endsWith("logout.do"))
		{
			IRODSAccount irodsAccount = (IRODSAccount) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			if (irodsAccount == null) {
				response.sendRedirect("login.jsp");
				return false;
			}
		}
		return true;
	}
}