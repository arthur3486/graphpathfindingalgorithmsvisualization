package com.arthurivanets.graphalgorithmsvisualization.util;

import java.util.HashMap;
import java.util.Map.Entry;

public class TextUtils {

	
	
	
	/**
	 * 
	 * <br>
	 * 		Determines whether the given String is empty.
	 * 		Strings consistent of just white spaces are considered as empty, as well.
	 * <br>
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(String text) {
		return (text == null || text.trim().length() == 0);
	}
	
	
	
	
	public static String packParameters(HashMap<String, String> parameters) {
		if(parameters == null || parameters.size() == 0) {
			return null;
		}
		
		StringBuilder parameterStringBuilder = new StringBuilder();
		
		for(Entry<String, String> entry : parameters.entrySet()) {
			parameterStringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append(";");
		}
		
		return parameterStringBuilder.toString();
	}
	
	
	
	
	public static void unpackParameters(HashMap<String, String> outParams, String parameterString) {
		if(outParams == null || isEmpty(parameterString)) {
			return;
		}
		
		String[] keyValuePairs = parameterString.split(";");
		String[] keyValuePair;
		
		int itemCount = keyValuePairs.length;
		
		for(int i = 0; i < itemCount; i++) {
			keyValuePair = keyValuePairs[i].split("=");
			
			if(keyValuePair != null && keyValuePair.length >= 2) {
				outParams.put(keyValuePair[0], keyValuePair[1]);
			}
		}
	}
	
	
	
	
	public static HashMap<String, String> unpackParameters(String parameterString) {
		HashMap<String, String> parameters = new HashMap<String, String>();
		unpackParameters(parameters, parameterString);
		
		return parameters;
	}
	
	
	

}
