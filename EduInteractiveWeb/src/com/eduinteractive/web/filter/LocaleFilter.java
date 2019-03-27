package com.eduinteractive.web.filter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eduinteractive.web.controller.ConstantsValues;
import com.eduinteractive.web.utils.CookieManager;
import com.eduinteractive.web.utils.LocaleManager;
import com.eduinteractive.web.utils.SessionManager;

/**
 * Filtro para inicializacion del Locale
 */
// En servlet 3.0 la anotacion no provee manera para establecer orden de filtros
// @WebFilter(filterName = "LocaleFilter", urlPatterns = {"/*"})
public class LocaleFilter implements Filter {

	private static Logger logger = LogManager.getLogger(LocaleFilter.class.getName());
  	

	
    public LocaleFilter() {       
    }

	public void init(FilterConfig cfg) throws ServletException {
		// Habitualmente la configuracion de los parametros
		// de un filtro se hace en el web.xml.

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		
		Locale locale = (Locale) SessionManager.get(httpRequest, ConstantsValues.USER_LOCALE);
		
		if (locale == null) {
			// No hay Locale configurado para esta sesion.
			
			// Primero intentamos inicializar el locale de cookie.
			Cookie cookieLocale = CookieManager.getCookie(httpRequest, ConstantsValues.USER_LOCALE);
			if (cookieLocale!=null) {
				locale = new Locale(cookieLocale.getValue());
				if (logger.isDebugEnabled()) {
					logger.debug("Locale initialized from cookie: "+cookieLocale.getValue());
				}				
			} else {
				// En ultimo término, a modo de "por defecto", inicializamos a partir 
				// del header Accept-Language de la request. 
				// Más info: https://www.w3.org/International/questions/qa-accept-lang-locales
				locale = getLocale(httpRequest);
			}
			if (locale==null) {
				// En ultimo caso, el primero de los soportados como opcion por defecto.
				locale = LocaleManager.getDefault();
				logger.warn("Using default locale: "+locale);			
			}

			SessionManager.set(httpRequest, ConstantsValues.USER_LOCALE, locale);			
			CookieManager.addCookie(httpResponse, ConstantsValues.USER_LOCALE, locale.toString(), "/", 365*24*60*60);
		}
		
		// Continuar la invocacion de la cadena de responsabilidad.
		// Solamente no se invocaría si el filtro determinase otro 
		// como por ejemplo en el caso de un filtro de autenticación.
		chain.doFilter(request, response);		
	
	}


	protected Locale getLocale(HttpServletRequest httpRequest) {
			
		String acceptLanguageHeader = httpRequest.getHeader("Accept-Language");
		
		// Miramos cuales de los lenguajes establecidos por el usuario en su navegador
		// son soportados por nuesetra web
		List<Locale> matchedLocales = LocaleManager.getMatchedLocales(acceptLanguageHeader);
		
		Locale locale = null;
		if (matchedLocales.size()>0) {
			locale = matchedLocales.get(0);
			if (logger.isDebugEnabled()) {
				logger.debug("Matched "+matchedLocales.size()+" locales. Selected: "+locale);
			}
		} else {
			logger.warn("No matched locale: "+acceptLanguageHeader);
		}
		return locale;
		       
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
