package com.eduinteractive.web.utils;

import java.util.Map;

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

}
