package com.eduinteractive.web.utils;

import com.mysql.cj.util.StringUtils;

public class ValidationUtils {
	

	public static Integer intValidator(String parameter) {
		try {
			if(!StringUtils.isEmptyOrWhitespaceOnly(parameter)) {
				return Integer.parseInt(ParameterUtils.trimmer(parameter));
				
			}else {
				return null;
			}
		}catch (NumberFormatException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
