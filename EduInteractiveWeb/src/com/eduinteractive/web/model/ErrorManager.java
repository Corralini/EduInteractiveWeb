package com.eduinteractive.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ErrorManager {

private static final List<String> EMPTY_LIST = new ArrayList<String>();
	
	private Map<String, List<String>> errorsMap = null;
	
	public ErrorManager() {
		errorsMap = new HashMap<String, List<String>>();
	}
	
	public void add(String parameter, String errorCode) {
		
		List<String> errors = errorsMap.get(parameter);
		
		if (errors==null) {
			errors = new ArrayList<String>();
			errorsMap.put(parameter, errors);
		}
		
		errors.add(errorCode);
	}
	
	public List<String> getErrors(String parameter) {
		List<String> parameterErrors = errorsMap.get(parameter);
		if (parameterErrors==null) {
			parameterErrors = EMPTY_LIST;
		}
		return parameterErrors;
	}
	
	public boolean hasErrors() {
		return !errorsMap.isEmpty();
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
