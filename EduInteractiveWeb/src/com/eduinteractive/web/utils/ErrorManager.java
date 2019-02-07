package com.eduinteractive.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Alejandro Corral
 *
 */

public class ErrorManager {

	private Map<String,List<String>> errors = null;
	private List<String> errorCodes = null;

	public ErrorManager() {
		errors = new HashMap<String, List<String>>();
		errorCodes = new ArrayList <String>();
		errors.put(ParameterNames.ANO_NACIMIENTO, errorCodes);
		errors.put(ParameterNames.APELLIDO1, errorCodes);
		errors.put(ParameterNames.APELLIDO2, errorCodes);
		errors.put(ParameterNames.DESCRIPCION, errorCodes);
		errors.put(ParameterNames.EMAIL, errorCodes);
		errors.put(ParameterNames.FECHA_HORARIO, errorCodes);
		errors.put(ParameterNames.ID, errorCodes);
		errors.put(ParameterNames.ID_DIA, errorCodes);
		errors.put(ParameterNames.ID_GENERO, errorCodes);
		errors.put(ParameterNames.ID_HORA, errorCodes);
		errors.put(ParameterNames.ID_NIVEL, errorCodes);
		errors.put(ParameterNames.ID_PAIS, errorCodes);
		errors.put(ParameterNames.NOMBRE, errorCodes);
		errors.put(ParameterNames.PRECIO, errorCodes);
		errors.put(ParameterNames.PRECIO_HASTA, errorCodes);
		errors.put(ParameterNames.PSSWD, errorCodes);
		errors.put(ParameterNames.URL_DOCUMENT, errorCodes);
	} 

	public void addError (String parameter, String errorCode) {

		List<String> errores = errors.get(parameter);
		if (errores==null) {
			errores = new ArrayList<String> ();
			errores.add(errorCode);
		}
		errors.put(parameter, errores);	
		
		
	}

	public boolean hasError () {

		int count = 0;
		for(String s : errors.keySet()) {
			errorCodes = errors.get(s);
			for(String string: errorCodes) {
				if(string != "") {
					count ++;
				}
			}
			
		}

		if(count == 0) {
			return false;
		}else {
			return true;
		}

	}
}
