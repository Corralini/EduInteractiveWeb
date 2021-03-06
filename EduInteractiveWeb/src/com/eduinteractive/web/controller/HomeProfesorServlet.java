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
import com.educorp.eduinteractive.ecommerce.service.impl.EstudianteServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.SesionServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.EstudianteService;
import com.educorp.eduinteractive.ecommerce.service.spi.SesionServices;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.ValidationUtils;

/**
 * Servlet implementation class HomeProfesorServlet
 */
@WebServlet("/homeProfesor")
public class HomeProfesorServlet extends HttpServlet {
	private Logger logger = LogManager.getLogger(HomeEstudianteServlet.class);
	private SesionServices sesionServices = null;
	private EstudianteService estudianteService = null;
	
    public HomeProfesorServlet() {
        super();
        sesionServices = new SesionServicesImpl();
		estudianteService = new EstudianteServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Profesor profesor = new Profesor();
		String idSesion = request.getParameter(ParameterNames.ID_SESION);
		if(idSesion != null && !idSesion.isEmpty()) {
			Integer sesionId = ValidationUtils.intValidator(idSesion);
			if(sesionId!=null) {
				try {
					Sesion sesion = sesionServices.findById(sesionId);
					Estudiante estudiante = estudianteService.findById(sesion.getIdEstudiante());
					if(sesion != null) {
						request.setAttribute(AttributeNames.SESION, sesion);
						request.setAttribute(AttributeNames.ESTUDIANTE, estudiante);
					}
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
		}
		profesor = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
		List<Sesion> sesiones = new ArrayList<Sesion>();
		boolean redirect = false;
		String target = null;
		try {
			sesiones = sesionServices.findByCalendario(profesor.getIdProfesor());
			request.setAttribute(AttributeNames.SESIONES, sesiones);
			target = ViewPaths.HOME_PROFESOR;
			redirect = false;
		}catch (DataException e) {
			logger.warn(e.getMessage(), e);
			target = ViewPaths.PRE_INICIO;
			redirect = true;
		}
		RedirectOrForwardUtils.redirectOrForward(request, response, redirect, target);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
