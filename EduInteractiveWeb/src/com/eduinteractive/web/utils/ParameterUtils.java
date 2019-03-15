package com.eduinteractive.web.utils;

import java.util.List;
import java.util.Map;

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
	
}
