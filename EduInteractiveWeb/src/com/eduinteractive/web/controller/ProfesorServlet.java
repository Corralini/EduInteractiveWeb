package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.model.Genero;
import com.educorp.eduinteractive.ecommerce.model.Horario;
import com.educorp.eduinteractive.ecommerce.model.NivelIngles;
import com.educorp.eduinteractive.ecommerce.model.Pais;
import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.service.impl.GeneroServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HorarioServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.NivelInglesServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.PaisServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.GeneroService;
import com.educorp.eduinteractive.ecommerce.service.spi.HorarioService;
import com.educorp.eduinteractive.ecommerce.service.spi.NivelInglesServices;
import com.educorp.eduinteractive.ecommerce.service.spi.PaisServices;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.eduinteractive.web.model.ErrorCodes;
import com.eduinteractive.web.model.ErrorManager;
import com.eduinteractive.web.utils.ParameterUtils;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.SpecificUtils;
import com.mysql.cj.util.StringUtils;

/**
 * Servlet implementation class ProfesorServlet
 */
@WebServlet("/profesor")
public class ProfesorServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(ProfesorServlet.class);
	
	private ProfesorService profesorService = null;
	private PaisServices paisServices = null;
	private GeneroService generoServices = null;
	private NivelInglesServices nivelServices = null;
	private HorarioService horarioServices = null;
	

	public ProfesorServlet() {
		super();
		profesorService = new ProfesorServicesImpl();
		paisServices = new PaisServicesImpl();
		generoServices = new GeneroServiceImpl();
		nivelServices = new NivelInglesServicesImpl();
		horarioServices = new HorarioServicesImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter(ParameterNames.ACTION);
		String idioma = SessionManager.get(request,ConstantsValues.USER_LOCALE).toString().substring(0,2).toUpperCase();
		boolean redirect = false;
		ErrorManager errors = new ErrorManager(); 
		String target = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		if(Actions.LOGIN.equalsIgnoreCase(action)) {
			String email = request.getParameter(ParameterNames.EMAIL);
			String password = request.getParameter(ParameterNames.PASSWORD);

			email = ParameterUtils.trimmer(email);

			if (StringUtils.isEmptyOrWhitespaceOnly(email)) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}
			
			password = ParameterUtils.trimmer(password);
			
			if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
				errors.add(ParameterNames.PASSWORD,ErrorCodes.MANDATORY_PARAMETER);
			}

			Profesor profesor = null;
			if (!errors.hasErrors()) {
				try {
					profesor = profesorService.login(email, password);
				}catch(DataException e) {
					errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);
				}
			}

			if (errors.hasErrors() || profesor == null) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}

				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PRE_INICIO;				
			} else {
				if(logger.isDebugEnabled()) {
					logger.info("Profesor " + profesor.getEmail() + " autenticado");
				}
				SessionManager.set(request, SessionAttributeNames.USUARIO, profesor);		
				target = ViewPaths.PRE_HOME_PROFESOR;	
				redirect = true;
			}
		}else if(Actions.DETALLE_PROFESOR.equalsIgnoreCase(action)){
			Profesor estudiante = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
			Pais paisEstudiante = new Pais();
			Genero generoEstudiante = new Genero();
			NivelIngles nivelEstudiante = new NivelIngles();
			try {
				paisEstudiante = paisServices.findById(estudiante.getIdPais(), idioma);
				generoEstudiante = generoServices.findById(estudiante.getIdGenero());
				nivelEstudiante = nivelServices.findById(estudiante.getIdNivel());
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}
			if(logger.isDebugEnabled()) {
				logger.debug("Pais profesor --> {}", paisEstudiante);
				logger.debug("Genero profesor --> {}", generoEstudiante);
				logger.debug("Nivel profesor --> {}", nivelEstudiante);
			}

			request.setAttribute(AttributeNames.PAISES, paisEstudiante);
			request.setAttribute(AttributeNames.GENERO, generoEstudiante);
			request.setAttribute(AttributeNames.NIVELES, nivelEstudiante);
			target = ViewPaths.DETAILS_PROFESOR_AS_PROFESOR;
			redirect = false;
		}else if(Actions.BUSCAR_HORARIOS.equals(action)){
			Profesor profesor = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
			List<Horario> horarios = new ArrayList<Horario>();
			try {
				horarios = horarioServices.findByProfesor(profesor.getIdProfesor());
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}
			
			Map<String, List<String>> horariosPrint = SpecificUtils.printHorario(horarios);
			request.setAttribute(AttributeNames.PRINT_HORARIOS, horariosPrint);
			
		}else {
			logger.error("Action desconocida");
			target =ViewPaths.PRE_INICIO;
			redirect = true;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Redirect status: {}", redirect);
		}
		RedirectOrForwardUtils.redirectOrForward(request, response, redirect, target);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}	
}
