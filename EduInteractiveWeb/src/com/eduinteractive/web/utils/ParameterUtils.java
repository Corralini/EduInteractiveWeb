package com.eduinteractive.web.utils;

import java.util.List;
import java.util.Map;

import com.eduinteractive.web.controller.ConstantsValues;
import com.eduinteractive.web.controller.ParameterNames;

public class ParameterUtils {

	public static final String print(Map <String, String[]> parameters) {
		StringBuilder sb = new StringBuilder();
		String [] values = null;
		for(String s : parameters.keySet()) {
			sb.append (s + " values{");
			values = parameters.get(s);
			for(int i = 0; i<values.length-1; i++) {
				sb.append(values[i] ).append(", ");
			}
			sb.append(values[values.length-1]);
			sb.append ("}");
		}
		return sb.toString();
	}

	public static String trimmer(String param) {
		return param.trim();
	}

	public static Integer getAcertadas(List<String> respuestas) {
		
		int acertadas = 0;
		
		for(int i = 0; i<10; i++) {
			String respuesta = respuestas.get(i);
			if((i == 2 || i == 3) && respuesta.equalsIgnoreCase(ParameterNames.OPCION_A)) acertadas++;
			if((i == 0 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8) && respuesta.equalsIgnoreCase(ParameterNames.OPCION_B)) acertadas++;
			if((i == 1 || i == 9) && respuesta.equalsIgnoreCase(ParameterNames.OPCION_D)) acertadas++;
		}
		
		return acertadas;
	}
	
	public static String makeName (String nombre, String apellido1, String apellido2) {
		StringBuilder nombreCompleto = new StringBuilder();
		nombreCompleto.append(nombre);
		nombreCompleto.append(ConstantsValues.WHITE_SPACE);
		nombreCompleto.append(apellido1);
		if(apellido2 != null) {
			nombreCompleto.append(ConstantsValues.WHITE_SPACE);
			nombreCompleto.append(apellido2);
		}
		return nombreCompleto.toString();
	}
}
