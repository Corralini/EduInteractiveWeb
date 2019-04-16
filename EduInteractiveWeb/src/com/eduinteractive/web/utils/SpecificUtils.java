package com.eduinteractive.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.model.Dia;
import com.educorp.eduinteractive.ecommerce.model.Hora;
import com.educorp.eduinteractive.ecommerce.model.Horario;
import com.educorp.eduinteractive.ecommerce.service.impl.DiaServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HoraServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.DiaServices;
import com.educorp.eduinteractive.ecommerce.service.spi.HoraServices;

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

}
