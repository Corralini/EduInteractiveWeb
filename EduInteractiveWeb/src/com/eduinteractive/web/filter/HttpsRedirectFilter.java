package com.eduinteractive.web.filter;

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
/**
 * Asegura que todos los usuarios, tanto profesores como alumnos 
 * acceden por SSL.
 * Además de por obligación legal (datos personales), lo requiere
 * la libreria Appear.inc.
 * @author Corral
 *
 */
public class HttpsRedirectFilter implements Filter {

	private static Logger logger = LogManager.getLogger(HttpsRedirectFilter.class);
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws java.io.IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		String getProtocol = req.getScheme();
		String getDomain = req.getServerName();
		//Integer.toString(req.getServerPort());
		String getPort = "8443";

		if (getProtocol.toLowerCase().equals("http")) {

			// Set response content type
			// response.setContentType("text/html");

			// New location to be redirected
			String httpsPath = "https" + "://" + getDomain + ":" + getPort
					+ uri;
			logger.debug("Redirecting secure path: {}", httpsPath);
			// String site = new String(httpsPath);
//			res.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
//			res.setHeader("Location", httpsPath);
			res.sendRedirect(httpsPath);
		}else {

		// Pass request back down the filter chain
		chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}


