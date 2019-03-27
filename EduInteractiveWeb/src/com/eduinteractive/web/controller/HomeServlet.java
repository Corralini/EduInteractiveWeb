package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.model.Estudiante;
import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.model.Sesion;
import com.educorp.eduinteractive.ecommerce.service.criteria.ProfesorCriteria;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.SesionServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.educorp.eduinteractive.ecommerce.service.spi.SesionServices;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.ValidationUtils;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private Logger logger = LogManager.getLogger(HomeServlet.class);
	private SesionServices sesionServices = null;
	private ProfesorService profesorServices = null;
	public HomeServlet() {
		super();
		sesionServices = new SesionServicesImpl();
		profesorServices = new ProfesorServicesImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Estudiante estudiante = new Estudiante();
		String idSesion = request.getParameter(ParameterNames.ID_SESION);
		if(idSesion != null && !idSesion.isEmpty()) {
			Integer sesionId = ValidationUtils.intValidator(idSesion);
			if(sesionId!=null) {
				try {
					Sesion sesion = sesionServices.findById(sesionId);
					Profesor profesor = profesorServices.findById(sesion.getIdProfesor());
					if(sesion != null) {
						request.setAttribute(AttributeNames.SESION, sesion);
						request.setAttribute(AttributeNames.TEACHER, profesor);
					}
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
		}
		estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
		List<Sesion> sesiones = new ArrayList<Sesion>();
		List<Profesor> profesores = new ArrayList<Profesor>();
		boolean redirect = false;
		String target = null;
		ProfesorCriteria profesorCriteria = new ProfesorCriteria();
		try {
			sesiones = sesionServices.findByCalendario(estudiante.getIdEstudiante());
			profesores = profesorServices.findByCriteria(profesorCriteria, 1, 3).getResultados();
		}catch (DataException e) {
			e.printStackTrace();
		}

		request.setAttribute(AttributeNames.SESIONES, sesiones);
		request.setAttribute(AttributeNames.PROFESOR, profesores);
		target = ViewPaths.HOME_ESTUDIANTE;
		redirect = false;
		RedirectOrForwardUtils.redirectOrForward(request, response, redirect, target);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
