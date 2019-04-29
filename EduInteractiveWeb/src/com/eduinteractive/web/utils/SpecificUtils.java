package com.eduinteractive.web.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.model.Dia;
import com.educorp.eduinteractive.ecommerce.model.Hora;
import com.educorp.eduinteractive.ecommerce.model.Horario;
import com.educorp.eduinteractive.ecommerce.model.Sesion;
import com.educorp.eduinteractive.ecommerce.service.impl.DiaServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HoraServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.SesionServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.DiaServices;
import com.educorp.eduinteractive.ecommerce.service.spi.HoraServices;
import com.educorp.eduinteractive.ecommerce.service.spi.SesionServices;
import com.eduinteractive.web.controller.ConstantsValues;

public class SpecificUtils {

	private static Logger logger = LogManager.getLogger(SpecificUtils.class);

	/**
	 * Metodo utilidad para tener los horarios listos para mostrar en web
	 * @param horarios
	 * @return
	 */
	public static Map<String, List<String>> printHorario (List<Horario> horarios){
		DiaServices diaServices = new DiaServicesImpl();
		HoraServices horaServices = new HoraServicesImpl();
		Map<String, List<String>> sortedHorarios = new HashMap<String, List<String>>();
		Map<Integer, List<Integer>> sortedHorariosCopy = new HashMap<Integer, List<Integer>>();
		Dia dia = null;
		Hora hora = null;

		//Recorremos los horarios de la lista

		for(Horario h: horarios) {
			try {
				dia = diaServices.findById(h.getIdDia());
				hora = horaServices.findById(h.getIdHora());
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}

			if(sortedHorariosCopy.containsKey(h.getIdDia())) {
				//Insertamos los datos nuevos en el que trabajamos
				sortedHorariosCopy.get(h.getIdDia()).add(h.getIdHora());

				//Insertamos los datos en el mapa limpio
				sortedHorarios.get(dia.getDia()).add(hora.getHora());
			}else {
				//Como la key no existe...
				List<Integer> toAddCopy = new ArrayList<Integer>();
				List<String> toAdd = new ArrayList<String>();
				toAddCopy.add(h.getIdHora());
				sortedHorariosCopy.put(h.getIdDia(), toAddCopy);
				//insertamos los datos en el mapa limpio
				toAdd.add(hora.getHora());
				sortedHorarios.put(dia.getDia(), toAdd);
			}
		}

		return sortedHorarios;
	}

	/**
	 * 
	 * Método que comprueba el estado de la sesión
	 * Si el estado es Solicitada o Aceptada mira la fecha de sesión con la actual
	 * Si la fecha actual es antes de la sesión establece la sesión como Cancelada
	 * 
	 * @param sesiones
	 * @return
	 */

	public static List<Sesion> checkStatus (List<Sesion> sesionesOrig){

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		
		Date currentDate = calendar.getTime();
		
		SesionServices sesionServices = new SesionServicesImpl();

		List<Sesion> sesiones = new ArrayList<Sesion>();
		sesiones.addAll(sesionesOrig);

		for(Sesion sesion: sesionesOrig) {
			calendar.setTime(sesion.getFechaSesion());
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			sesion.setFechaSesion(calendar.getTime());
			
			if(ConstantsValues.SESION_ACEPTADA.equalsIgnoreCase(sesion.getIdEstado())) {
				if(!(sesion.getFechaSesion().compareTo(currentDate) == 0) 
						&& ParameterUtils.nextDay(sesion.getFechaSesion()).compareTo(currentDate) < 0 ) {
					try {
						sesionServices.cambiarEstado(sesion, ConstantsValues.SESION_CANCELADA);
						sesiones.remove(sesion);
					} catch (DataException e) {
						logger.warn(e.getMessage(), e);
					}

				}else if(sesion.getFechaFin() != null && currentDate.compareTo(sesion.getFechaFin()) < 0) {
					try {
						sesionServices.cambiarEstado(sesion, ConstantsValues.SESION_TERMINADA);
						sesiones.remove(sesion);
					} catch (DataException e) {
						logger.warn(e.getMessage(), e);
					}
					
				}
			}else if(ConstantsValues.SESION_SOLICITADA.equalsIgnoreCase(sesion.getIdEstado())) {
				if(sesion.getFechaSesion().compareTo(currentDate) < 0 
						|| sesion.getFechaSesion().compareTo(currentDate) == 0) {
					try {
						sesionServices.cambiarEstado(sesion, ConstantsValues.SESION_CANCELADA);
						sesiones.remove(sesion);
					} catch (DataException e) {
						logger.warn(e.getMessage(), e);
					}
				}
			}
		}

		return sesiones;
	}

}
