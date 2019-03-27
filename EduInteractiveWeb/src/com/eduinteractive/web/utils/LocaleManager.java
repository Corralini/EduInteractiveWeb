package com.eduinteractive.web.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.eduinteractive.web.config.ConfigurationManager;
import com.eduinteractive.web.config.ConfigurationParameterNames;


public class LocaleManager {
	
	private static Logger logger = LogManager.getLogger(LocaleManager.class.getName());
	
	// Locales soportados por esta web. 
	private static List<Locale> supportedLocales = null;
	static {

		// Cargamos los Locale configurados
		supportedLocales = new ArrayList<Locale>();
		String[] supportedLocaleNames= 
				ConfigurationManager.getInstance().getParameter(ConfigurationParameterNames.SUPPORTED_LOCALES).split(",");

		// Lista de locales admitidos
        Locale locale = null;
        for (String s: supportedLocaleNames) {
        	locale = Locale.forLanguageTag(s);
        	supportedLocales.add(locale);
        	if (logger.isDebugEnabled()) {
        		logger.debug("Registered locale "+locale);
        	}
        }
        
        if (supportedLocales.size()==0) {
        	logger.fatal("No Locale configured");
        	System.exit(0); // ...
        }
                
	}
	
	public static Locale getDefault() {
		return supportedLocales.get(0);
	}
	
	public static List<Locale> getSupportedLocales() {
		return supportedLocales;
	}
	
	/**
	 * Metodo utilidad para transformar y filtrar Locales 
	 * desde un String con formato tipo valores del header Accept-Language,
	 * como por ejemplo: "en-US;q=1.0,en-GB;q=0.5,fr-FR;q=0.0"
	 * 
	 * Más info: 
     * https://docs.oracle.com/javase/tutorial/i18n/locale/matching.html
	 * 
hector.ledo.doval
	 */
	public static List<Locale> getMatchedLocales(String ranges) {
		List<Locale.LanguageRange> languageRanges = Locale.LanguageRange.parse(ranges);		
		// Now filter the Locale objects, returning any matches
		List<Locale> results = Locale.filter(languageRanges,getSupportedLocales());
		return results;
	}
}
