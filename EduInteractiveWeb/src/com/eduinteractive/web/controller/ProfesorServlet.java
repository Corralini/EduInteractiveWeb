package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import com.educorp.eduinteractive.ecommerce.exceptions.MailException;
import com.educorp.eduinteractive.ecommerce.model.Dia;
import com.educorp.eduinteractive.ecommerce.model.Estudiante;
import com.educorp.eduinteractive.ecommerce.model.Genero;
import com.educorp.eduinteractive.ecommerce.model.Hora;
import com.educorp.eduinteractive.ecommerce.model.Horario;
import com.educorp.eduinteractive.ecommerce.model.NivelIngles;
import com.educorp.eduinteractive.ecommerce.model.Pais;
import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.model.Sesion;
import com.educorp.eduinteractive.ecommerce.service.impl.DiaServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.GeneroServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HoraServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HorarioServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.NivelInglesServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.PaisServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.SesionServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.DiaServices;
import com.educorp.eduinteractive.ecommerce.service.spi.GeneroService;
import com.educorp.eduinteractive.ecommerce.service.spi.HoraServices;
import com.educorp.eduinteractive.ecommerce.service.spi.HorarioService;
import com.educorp.eduinteractive.ecommerce.service.spi.NivelInglesServices;
import com.educorp.eduinteractive.ecommerce.service.spi.PaisServices;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.educorp.eduinteractive.ecommerce.service.spi.SesionServices;
import com.eduinteractive.web.model.ErrorCodes;
import com.eduinteractive.web.model.ErrorManager;
import com.eduinteractive.web.utils.CookieManager;
import com.eduinteractive.web.utils.FileUtils;
import com.eduinteractive.web.utils.LocaleManager;
import com.eduinteractive.web.utils.ParameterUtils;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.SpecificUtils;
import com.eduinteractive.web.utils.ValidationUtils;
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
	private HoraServices horaServices = null;
	private DiaServices diaServices =  null;
	private SesionServices sesionServices = null;


	public ProfesorServlet() {
		super();
		profesorService = new ProfesorServicesImpl();
		paisServices = new PaisServicesImpl();
		generoServices = new GeneroServiceImpl();
		nivelServices = new NivelInglesServicesImpl();
		horarioServices = new HorarioServicesImpl();
		horaServices = new HoraServicesImpl();
		diaServices = new DiaServicesImpl();
		sesionServices = new SesionServicesImpl();
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
				if(profesor.getAceptado() != 0) {
					target = ViewPaths.PRE_HOME_PROFESOR;	
				}else {
					target=ViewPaths.TEACHER_NOT_ACTIVATE;
				}
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
		}else if(Actions.SEE_HORARIOS.equals(action)){
			Profesor profesor = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
			List<Horario> horarios = new ArrayList<Horario>();
			try {
				horarios = horarioServices.findByProfesor(profesor.getIdProfesor());
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}

			Map<String, List<String>> horariosPrint = SpecificUtils.printHorario(horarios);
			request.setAttribute(AttributeNames.PRINT_HORARIOS, horariosPrint);

			redirect = false;
			target = ViewPaths.SETTINGS_PROFESOR;
		}else if(Actions.PRE_ADD_HORARIO.equalsIgnoreCase(action)){
			List<Dia> dias = new ArrayList<Dia>();
			List<Hora> horas = new ArrayList<Hora>();

			try {
				dias = diaServices.findAll();
				horas = horaServices.findAll();
			} catch (DataException e) {
				logger.warn(e.getMessage(), e);
			}

			request.setAttribute(AttributeNames.DIAS, dias);
			request.setAttribute(AttributeNames.HORAS, horas);

			redirect = false;
			target = ViewPaths.SETTINGS_PROFESOR;

		}else if(Actions.ADD_HORARIO.equalsIgnoreCase(action)){
			Profesor p = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
			String dia = request.getParameter(ParameterNames.DIA);
			String hora = request.getParameter(ParameterNames.HORA);

			Integer idDia = ValidationUtils.intValidator(dia);
			if(idDia == null) {
				errors.add(ParameterNames.DIA, ErrorCodes.MANDATORY_PARAMETER);
			}

			Integer idHora = ValidationUtils.intValidator(hora);
			if(idHora == null) {
				errors.add(ParameterNames.HORA, ErrorCodes.MANDATORY_PARAMETER);
			}

			if(!errors.hasErrors()) {
				Horario h = new Horario();
				h.setIdProfesor(p.getIdProfesor());
				h.setIdDia(idDia);
				h.setIdHora(idHora);
				try {
					horarioServices.create(h);
				} catch (DataException e) {
					logger.warn(e.getMessage(), e);
					errors.add(Actions.ADD_HORARIO, "El horario ya existe o no se pudo añadir");
				}
			}

			if(errors.hasErrors()) {
				redirect = false;
			}else {
				redirect = true;
			}
			target = ViewPaths.SETTINGS_PROFESOR;

		}else if(Actions.ACEPT_SESION.equalsIgnoreCase(action)){
			String idSesion = request.getParameter(ParameterNames.ID_SESION);
			Integer sesionId = null;
			if(idSesion != null) {
				sesionId = ValidationUtils.intValidator(idSesion);
			}
			try {

				Sesion sesion = sesionServices.findById(sesionId);
				sesionServices.cambiarEstado(sesion, ConstantsValues.SESION_ACEPTADA);

			} catch (DataException e) {

				e.printStackTrace();
			}

			target = ViewPaths.PRE_HOME_PROFESOR;
			redirect = true;
		}else if(Actions.CANCEL_SESION.equalsIgnoreCase(action)){
			String idSesion = request.getParameter(ParameterNames.ID_SESION);
			Integer sesionId = null;
			if(idSesion != null) {
				sesionId = ValidationUtils.intValidator(idSesion);
			}
			try {

				Sesion sesion = sesionServices.findById(sesionId);
				sesionServices.cambiarEstado(sesion, ConstantsValues.SESION_CANCELADA);

			} catch (DataException e) {

				e.printStackTrace();
			}

			target = ViewPaths.PRE_HOME_PROFESOR;
			redirect = true;
		}else if(Actions.CHANGE_LOCALE.equalsIgnoreCase(action)){
			String localeName = request.getParameter(ParameterNames.LOCALE);
			List<Locale> results = LocaleManager.getMatchedLocales(localeName);
			Locale newLocale = null;
			if (results.size()>0) {
				newLocale = results.get(0);
			} else {
				logger.warn("Request locale not supported: "+localeName);
				newLocale = LocaleManager.getDefault();
			}

			SessionManager.set(request, ConstantsValues.USER_LOCALE, newLocale);			
			CookieManager.addCookie(response, ConstantsValues.USER_LOCALE, newLocale.toString(), "/", 365*24*60*60);

			if (logger.isDebugEnabled()) {
				logger.debug("Locale changed to "+newLocale);
			}

			target = ViewPaths.SETTINGS_PROFESOR;
			redirect = true;
		}else if(Actions.START_SESION.equalsIgnoreCase(action)){
			target = ViewPaths.VIDEO_CALL_PROFESOR;
			redirect = true;
		}else if(Actions.SEE_DOCUMENT.equalsIgnoreCase(action)){
			Profesor profesor = (Profesor) SessionManager.get(request, SessionAttributeNames.USUARIO);
			FileUtils.readDocument(response, ParameterUtils.getFileName(profesor.getEmail()));
			return;
		}else if(Actions.SEARCH_ACCOUNT.equalsIgnoreCase(action)){
			String email = request.getParameter(ParameterNames.EMAIL);
			email = ValidationUtils.emailValidator(email);
			if(email != null) {
				try {
					Profesor profesor = profesorService.findByEmailToRecovery(email);
					if(profesor != null) {
						request.setAttribute(ParameterNames.ID_PROFESOR, profesor.getIdProfesor());
						redirect = Boolean.FALSE;
						target = ViewPaths.RECOVERY_CODE_PROFESOR;
					}
				} catch (MailException | DataException e) {
					errors.add(ParameterNames.ACTION, ErrorCodes.MAIL_ERROR);
				}
			}else {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
				redirect = Boolean.FALSE;
				target = ViewPaths.RECOVERY_ACCOUNT_PROFESOR;
			}
		}else if(Actions.CHECK_CODE.equalsIgnoreCase(action)){
			String profesorIdStr = request.getParameter(ParameterNames.ID_PROFESOR);
			String codeStr = request.getParameter(ParameterNames.CODE);
			Integer code = ValidationUtils.intValidator(codeStr);
			Integer profesorId = ValidationUtils.intValidator(profesorIdStr);
			if(profesorId != null && code != null) {
				try {
					Profesor profesor = profesorService.findById(profesorId);
					if(profesor != null) {
						if(profesor.getCodigoDeRecuperacion().equals(code))
						request.setAttribute(ParameterNames.ID_PROFESOR, profesor.getIdProfesor());
						request.setAttribute(ParameterNames.CODE, code);
						redirect = Boolean.FALSE;
						target = ViewPaths.SET_PASSWORD_PROFESOR;
					}
				} catch (DataException e) {
					logger.info(e.getMessage(), e);
					errors.add(ParameterNames.ACTION, ErrorCodes.MAIL_ERROR);
				}
			}else {
				errors.add(ParameterNames.CODE, ErrorCodes.MANDATORY_PARAMETER);
				redirect = Boolean.FALSE;
				target = ViewPaths.RECOVERY_CODE_PROFESOR;
			}
		}else if(Actions.SET_PASSWORD.equals(action)){
			String profesorIdStr = request.getParameter(ParameterNames.ID_PROFESOR);
			String codeStr = request.getParameter(ParameterNames.CODE);
			String psswd = request.getParameter(ParameterNames.PASSWORD);
			String repeatPsswd = request.getParameter(ParameterNames.PSSWD_REPEAT);
			Integer profesorId = ValidationUtils.intValidator(profesorIdStr);
			Integer code = ValidationUtils.intValidator(codeStr);
			psswd = ValidationUtils.passwordValidator(psswd, repeatPsswd);
			if(profesorId != null && code != null && psswd != null) {
				Profesor profesor;
				try {
					profesor = profesorService.findById(profesorId);
					profesorService.cambiarContra(code,profesor.getEmail(), psswd);
					SessionManager.set(request, SessionAttributeNames.USUARIO, profesor);
					redirect = Boolean.TRUE;
					target = ViewPaths.PRE_HOME_PROFESOR;
				} catch (DataException e) {
					logger.info(e.getMessage(), e);
				}
			}else {
				errors.add(ParameterNames.PASSWORD, ParameterNames.PASSWORD);
				redirect = Boolean.FALSE;
				target = ViewPaths.SET_PASSWORD_PROFESOR;
			}
		}else if(Actions.GO_RECOVERY.equalsIgnoreCase(action)){
			redirect = true;
			target = ViewPaths.RECOVERY_ACCOUNT_PROFESOR;
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
