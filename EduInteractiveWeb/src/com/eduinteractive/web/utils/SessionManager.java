package com.eduinteractive.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionManager {

	public static Object get(HttpServletRequest request, String attName) {
		return getSession(request).getAttribute(attName);
	}

	public static void set(HttpServletRequest request, String attName, Object attValue) {
		getSession(request).setAttribute(attName, attValue);
	}
			
	/**
	 * Método factoria de la sesión, y sus objetos mandatorios.
	 * @param request
	 * @return
	 */
	protected static HttpSession getSession(HttpServletRequest request) {
		// La inicializacion de la sesion se invoca siempre desde InitSessionFilter.		
		// para evitar efectos como: https://guneetsahai.com/2008/08/09/request-getsession-false-always-returning-a-session/
		// Con page session false en la practica la sesion no se inicializa si la
		// primera URL ejecutada es de una JSP.
				
		HttpSession httpSession = request.getSession(false);
		// *if (httpSession.)
		return httpSession;

	}
	
}
