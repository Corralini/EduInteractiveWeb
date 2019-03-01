package com.eduinteractive.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RedirectOrForwardUtils {

	private static Logger logger = LogManager.getLogger(RedirectOrForwardUtils.class); 
	
	public static void redirectOrForward (HttpServletRequest request,
										HttpServletResponse response,
										boolean redirect, String target) throws IOException, ServletException {
		if (redirect) {
			logger.info("Redirecting to "+target);
			response.sendRedirect(target);
		} else {
			logger.info("Forwarding to "+ request.getContextPath() +  target);
			request.getRequestDispatcher(target).forward(request, response);
		}
	}
	
}
