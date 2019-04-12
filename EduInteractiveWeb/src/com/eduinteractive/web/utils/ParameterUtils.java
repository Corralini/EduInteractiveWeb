package com.eduinteractive.web.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eduinteractive.web.controller.ConstantsValues;
import com.eduinteractive.web.controller.ParameterNames;
import com.mysql.cj.util.StringUtils;

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
		if(param != null) {
		return param.trim();
		}else {
			return null;
		}
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

	/**
	 * 
	 * Metodo utilidad para construir URL con parametros
	 * 
	 * 
	 * @param url
	 * @param valores
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	
	public static String URLBuilder (String url, Map<String, String> valores) throws UnsupportedEncodingException {
		int cont = 1;
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(url);
		for(String mapKey: valores.keySet()) {
			if(cont == 1) urlBuilder.append(ConstantsValues.QUESTION_MARK);
			urlBuilder.append(URLEncoder.encode(trimmer(mapKey), ConstantsValues.ENCODING))
				.append(ConstantsValues.EQUAL)
				.append(URLEncoder.encode(trimmer(valores.get(mapKey)), ConstantsValues.ENCODING));
			if(cont != valores.size()) {
				urlBuilder.append(ConstantsValues.AMPERSAND_URL);
			}
			cont++;
		}
		return urlBuilder.toString();
	}
	/**
	 * 
	 * metodo utilidad para crear una string de la fecha con este formato (yyyy-mm-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String dateBuilder (Date date) {
		StringBuilder sb = new StringBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		sb.append(c.get(Calendar.YEAR)).append(ConstantsValues.HYPHEN);
		if(c.get(Calendar.MONTH) < 10 ) {
			sb.append("0").append(c.get(Calendar.MONTH)+1).append(ConstantsValues.HYPHEN);
		}else {
			sb.append(c.get(Calendar.MONTH)+1).append(ConstantsValues.HYPHEN);
		}
		if(c.get(Calendar.DAY_OF_MONTH) < 10) {
			sb.append("0").append(c.get(Calendar.DAY_OF_MONTH));
		}else {
			sb.append(c.get(Calendar.DAY_OF_MONTH));
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * metodo utilidad para incrementar una fecha en un dia
	 * 
	 * @param date
	 * @return
	 */
	public static Date nextDay (Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, 1);
		
		return c.getTime();
	}
	
	/**
	 * 
	 * metodo para comparar una fecha por dia, mes y año
	 * si un objeto Date o ambos == null returns false
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean dateComparator(Date date1, Date date2) {
		
		if(date1 == null || date2 == null) {
			return false;
		}
		
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		
		Integer dia1 = calendar1.get(Calendar.DAY_OF_MONTH);
		Integer dia2 = calendar2.get(Calendar.DAY_OF_MONTH);
		
		if(dia1.equals(dia2)) {
			Integer mes1 = calendar1.get(Calendar.MONTH);
			Integer mes2 = calendar2.get(Calendar.MONTH);
			if(mes1.equals(mes2)) {
				Integer ano1 = calendar1.get(Calendar.YEAR);
				Integer ano2 = calendar2.get(Calendar.YEAR);
				if(ano1.equals(ano2)) {
					return true;
				}
			}
		}
		return false;	
	}
	/**
	 * 
	 * Divide una cadena por un carácter o una cadena
	 * Si falla retorna null
	 * 
	 * @param string
	 * @param forSplit
	 * @return
	 */
	public static String[] split(String string, String forSplit) {
		String[] splitString = null;
		if(string != null && !StringUtils.isEmptyOrWhitespaceOnly(string)) {
			splitString = string.split(forSplit);
		}
		return splitString;
	}
	
}
