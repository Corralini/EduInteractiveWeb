
package com.eduinteractive.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.model.Usuario;
import com.eduinteractive.web.controller.RedirectOrForwardUtils;
import com.eduinteractive.web.controller.SessionAttributeNames;
import com.eduinteractive.web.controller.ViewPaths;
import com.eduinteractive.web.utils.SessionManager;

public class UserFilter implements Filter {
	private static Logger logger = LogManager.getLogger(UserFilter.class.getName());
	  
    public UserFilter() {       
    }

	public void init(FilterConfig cfg) throws ServletException {
		// Habitualmente se configuran en el web.xml´
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String target = null;

        Usuario user = (Usuario) SessionManager.get(httpRequest, SessionAttributeNames.USUARIO);
        
        if (user!=null) {
        	if (user instanceof Profesor && ((Profesor) user).getAceptado() == 0) {
        			target = ViewPaths.TEACHER_NOT_ACTIVATE;
        	}
        } else {        	
        	target = ViewPaths.PRE_INICIO;
        	logger.warn("Redirigiendo a "+ target +" "+request.getRemoteHost());
        	RedirectOrForwardUtils.redirectOrForward(httpRequest, httpResponse, true, target);
        }
        
        if(target != null) {
        	RedirectOrForwardUtils.redirectOrForward(httpRequest, httpResponse, true, target);
        }else {
        	chain.doFilter(request, response);
        }
    }

	@Override
	public void destroy() {
		
		
	}

}
