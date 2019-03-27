package com.eduinteractive.web.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utilidades para el manejo de cookies (documentación mejorable).
 * 
 * @version 0.2
 */
public class CookieManager {

	/**
	 * Busca una cookie por su nombre (case insensitive). 
	 * @return null si no la encuentra.
	 */
	public static final Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();		
		if (cookies!=null) {
			for (Cookie c: cookies) {
				if (c.getName().equalsIgnoreCase(name)) {
					return c;
				}
			}
		}
		return null;    
	}

	/**
	 * Añade una cookie a la response.
	 */
    public static final void addCookie(HttpServletResponse response, String name, String value, String path, int timeToLive) {		
		Cookie c = new Cookie(name, value);		
		c.setMaxAge(timeToLive);
		c.setPath(path);
		response.addCookie(c);	
    }

    /**
     * Commodity method para "eliminar" una cookie.
     */
    public static final void removeCookie(HttpServletResponse response, String name, String path) {
    	addCookie(response, name, null, path, 0);
    }
}
