package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.educorp.eduinteractive.ecommerce.dao.service.Results;
import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.exceptions.DuplicateInstanceException;
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
import com.educorp.eduinteractive.ecommerce.service.criteria.ProfesorCriteria;
import com.educorp.eduinteractive.ecommerce.service.impl.DiaServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.EstudianteServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.GeneroServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HoraServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.HorarioServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.NivelInglesServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.PaisServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.SesionServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.DiaServices;
import com.educorp.eduinteractive.ecommerce.service.spi.EstudianteService;
import com.educorp.eduinteractive.ecommerce.service.spi.GeneroService;
import com.educorp.eduinteractive.ecommerce.service.spi.HoraServices;
import com.educorp.eduinteractive.ecommerce.service.spi.HorarioService;
import com.educorp.eduinteractive.ecommerce.service.spi.NivelInglesServices;
import com.educorp.eduinteractive.ecommerce.service.spi.PaisServices;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.educorp.eduinteractive.ecommerce.service.spi.SesionServices;
import com.educorp.eduinteractive.exceptions.PasswordEncryptionUtil;
import com.eduinteractive.web.config.ConfigurationManager;
import com.eduinteractive.web.config.ConfigurationParameterNames;
import com.eduinteractive.web.model.ErrorCodes;
import com.eduinteractive.web.model.ErrorManager;
import com.eduinteractive.web.utils.CookieManager;
import com.eduinteractive.web.utils.LocaleManager;
import com.eduinteractive.web.utils.ParameterUtils;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.ValidationUtils;
import com.eduinteractive.web.utils.WebUtils;
import com.mysql.cj.util.StringUtils;

/**
 * Servlet implementation class EstudianteServlet
 */
@WebServlet("/estudiante")
public class EstudianteServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(EstudianteServlet.class);
	private static int pageSize = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGE_SIZE_DEFAULT)); 

	private static int pagingPageCount = Integer.valueOf(
			ConfigurationManager.getInstance().getParameter(
					ConfigurationParameterNames.RESULTS_PAGING_PAGE_COUNT)); 
	//Mis servicios
	private EstudianteService estudianteService = null;
	private NivelInglesServices nivelServices = null;
	private ProfesorService profesorService = null;
	private DiaServices diaServices = null;
	private PaisServices paisServices = null;
	private GeneroService generoServices = null;
	private SesionServices sesionServices = null;
	private HorarioService horarioServices = null;
	private HoraServices horaServices = null;

	public EstudianteServlet() {
		super();
		estudianteService = new EstudianteServiceImpl();
		nivelServices = new NivelInglesServicesImpl();
		profesorService = new ProfesorServicesImpl();
		diaServices = new DiaServicesImpl();
		paisServices = new PaisServicesImpl();
		generoServices = new GeneroServiceImpl();
		sesionServices = new SesionServicesImpl();
		horarioServices = new HorarioServicesImpl();
		horaServices = new HoraServicesImpl();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = ParameterUtils.trimmer(request.getParameter(ParameterNames.ACTION));
		String idioma = SessionManager.get(request,ConstantsValues.USER_LOCALE).toString().substring(0,2).toUpperCase();

		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}
		boolean redirect = false;
		ErrorManager errors = new ErrorManager(); 
		String target = null;
		if (Actions.LOGIN.equalsIgnoreCase(action)) {
			// Recuperacion
			String email = request.getParameter(ParameterNames.EMAIL);
			String password = request.getParameter(ParameterNames.PASSWORD);

			email = ParameterUtils.trimmer(email);

			if (StringUtils.isEmptyOrWhitespaceOnly(email)) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}

			if (StringUtils.isEmptyOrWhitespaceOnly(password)) {
				errors.add(ParameterNames.PASSWORD,ErrorCodes.MANDATORY_PARAMETER);
			}

			Estudiante estudiante = null;
			if (!errors.hasErrors()) {
				try {
					estudiante = estudianteService.login(email, password);
				}catch(DataException e) {
					errors.add(ParameterNames.ACTION,ErrorCodes.AUTHENTICATION_ERROR);
				}
			}

			if (errors.hasErrors() || estudiante == null) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}

				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PRE_INICIO;				
			} else {
				if(logger.isDebugEnabled()) {
					logger.info("Estudiante " + estudiante.getEmail() + " autenticado");
				}
				SessionManager.set(request, SessionAttributeNames.USUARIO, estudiante);		
				target = ViewPaths.PRE_HOME_ESTUDIANTE;	
				redirect = true;
			}

		}else if(Actions.PRESIGNIN.equalsIgnoreCase(action)){
			// Recuperacion de parametros
			String email = request.getParameter(ParameterNames.EMAIL);
			String nombre = request.getParameter(ParameterNames.NOMBRE);
			String apellido1 = request.getParameter(ParameterNames.APELLIDO1);
			String apellido2 = request.getParameter(ParameterNames.APELLIDO2);
			String psswd = request.getParameter(ParameterNames.PASSWORD);
			String psswdRepetida = request.getParameter(ParameterNames.PSSWD_REPEAT);
			String genero = request.getParameter(ParameterNames.GENERO);
			String pais = request.getParameter(ParameterNames.PAIS);
			String anoNac = request.getParameter(ParameterNames.YEAR);

			//Validacion, la limpieza tiene lugar en los ValidationUtils

			email = ValidationUtils.emailValidator(email);
			if(email == null) {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
			}

			nombre = ValidationUtils.stringOnlyLettersValidator(nombre, true);
			if(nombre == null) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.MANDATORY_PARAMETER);
			}

			apellido1 = ValidationUtils.stringOnlyLettersValidator(apellido1, false);
			if(apellido1 == null) {
				errors.add(ParameterNames.APELLIDO1, ErrorCodes.MANDATORY_PARAMETER);
			}
			if(!StringUtils.isEmptyOrWhitespaceOnly(apellido2) && apellido2 != null) {
				apellido2 = ValidationUtils.stringOnlyLettersValidator(apellido2, false);
			}

			psswd = ValidationUtils.passwordValidator(psswd, psswdRepetida);
			if(psswd == null) {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}

			genero = ValidationUtils.stringOnlyLettersValidator(genero, false);
			if(genero == null) {
				errors.add(ParameterNames.GENERO, ErrorCodes.MANDATORY_PARAMETER);
			}

			pais = ValidationUtils.stringOnlyLettersValidator(pais, false);
			if(pais == null) {
				errors.add(ParameterNames.PAIS, ErrorCodes.MANDATORY_PARAMETER);
			}

			Integer anoNacimiento = ValidationUtils.intValidator(anoNac);

			if(anoNacimiento == null) {
				errors.add(ParameterNames.YEAR, ErrorCodes.MANDATORY_PARAMETER);
			}

			Estudiante estudiante = new Estudiante();

			if (!errors.hasErrors()) {
				estudiante.setEmail(email);
				estudiante.setNombre(nombre);
				estudiante.setApellido1(apellido1);
				estudiante.setApellido2(apellido2);
				estudiante.setPsswd(psswd);
				estudiante.setIdGenero(genero);
				estudiante.setIdPais(pais);
				estudiante.setAnoNacimiento(anoNacimiento);
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Registro fallido: {}", errors);
				}				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PRE_INICIO;				
			} else {			
				if (logger.isDebugEnabled()) {
					logger.info("Estudiante "+estudiante.getEmail()+" pre-registrado.");
				}				
				SessionManager.set(request, SessionAttributeNames.USUARIO, estudiante);						
				target = ViewPaths.TEST;					
			}
		}else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.set(request, SessionAttributeNames.USUARIO, null);
			target = ViewPaths.PRE_INICIO;
			redirect = true;
		}else if(Actions.SIGNIN.equalsIgnoreCase(action)) {
			Estudiante estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
			//resultados test

			List <String> respuestas = new ArrayList<String>();
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_1));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_2));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_3));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_4));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_5));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_6));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_7));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_8));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_9));
			respuestas.add(request.getParameter(ParameterNames.PREGUNTA_10));


			if(logger.isInfoEnabled()) {
				logger.info("Estudiante: {}", estudiante);
			}

			int acertadas = ParameterUtils.getAcertadas(respuestas);

			if(logger.isInfoEnabled()) logger.info("Acertadas: {}", acertadas);
			if (!errors.hasErrors()) {
				try {
					estudiante = estudianteService.signUp(estudiante, acertadas);
				} catch (MailException e) {
					errors.add(Actions.SIGNIN, ErrorCodes.MAIL_ERROR);
				} catch (DuplicateInstanceException e) {
					errors.add(Actions.SIGNIN, ErrorCodes.USER_REPEAT);
				} catch (DataException e) {
					errors.add(Actions.SIGNIN, ErrorCodes.SIGNIN_ERROR);
				}
			}

			if (estudiante == null) {
				errors.add(ParameterNames.ACTION,ErrorCodes.SIGNIN_ERROR);
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.TEST;				
			} else {			
				if (logger.isDebugEnabled()) {
					logger.info("Usuario "+estudiante.getEmail()+" registrado.");
				}				
				SessionManager.set(request, SessionAttributeNames.USUARIO, estudiante);						
				target = ViewPaths.PRE_HOME_ESTUDIANTE;					
				redirect = true;
			}

		}else if(Actions.SEARCH.equalsIgnoreCase(action)){
			ProfesorCriteria profesorSearch = new ProfesorCriteria();
			if(request.getParameter(ParameterNames.SEARCH_BOX) != null) {
				String[] nombreCompleto = request.getParameter(ParameterNames.SEARCH_BOX)
						.split(ConstantsValues.WHITE_SPACE);
				String nombre = ConstantsValues.WHITE_SPACE;
				String apellido1 = ConstantsValues.WHITE_SPACE;
				String apellido2 = ConstantsValues.WHITE_SPACE;
				nombre = ConstantsValues.WHITE_SPACE;
				apellido1 = ConstantsValues.WHITE_SPACE;
				apellido2 = ConstantsValues.WHITE_SPACE;
				for(int i = 0; i<nombreCompleto.length; i++) {
					if(i == 0) {
						nombre = nombreCompleto[i];
					}
					if(i == 1) {
						apellido1 = nombreCompleto[i];
					}
					if(i == 2) {
						apellido2 = nombreCompleto[i];
					}
				}

				if(nombre != null && !StringUtils.isEmptyOrWhitespaceOnly(nombre)) {
					nombre = ValidationUtils.stringOnlyLettersValidator(nombre, false);
					profesorSearch.setNombre(nombre);
				}

				if(apellido1 != null && !StringUtils.isEmptyOrWhitespaceOnly(apellido1)) {
					apellido1 = ValidationUtils.stringOnlyLettersValidator(apellido1, false);
					profesorSearch.setApellido1(apellido1);
				}

				if(apellido2 != null && !StringUtils.isEmptyOrWhitespaceOnly(apellido2)) {
					apellido2 = ValidationUtils.stringOnlyLettersValidator(apellido1, false);
					profesorSearch.setApellido2(apellido2);
				}
				request.setAttribute(AttributeNames.SEARCH_BOX, nombreCompleto);
			}
			if(request.getParameter(ParameterNames.NIVEL_INGLES) != null) {
				String nivel = request.getParameter(ParameterNames.NIVEL_INGLES);
				Integer nivelIngles = ValidationUtils.intValidator(nivel);
				if (nivelIngles != null) {
					profesorSearch.setIdNivel(nivelIngles);
					request.setAttribute(AttributeNames.NIVEL_INGLES_INPUT, nivelIngles);
				}

			}
			if(request.getParameter(ParameterNames.GENERO) != null) {
				String genero = request.getParameter(ParameterNames.GENERO);
				if (genero != null && !StringUtils.isEmptyOrWhitespaceOnly(genero)) {
					genero = ValidationUtils.stringOnlyLettersValidator(genero, false);
					profesorSearch.setIdGenero(genero);
					request.setAttribute(AttributeNames.GENERO, genero);
				}
			}
			if(request.getParameter(ParameterNames.PUNTUACION) != null) {
				String puntuacion = request.getParameter(ParameterNames.PUNTUACION);
				Double rate = ValidationUtils.doubleValidator(puntuacion);
				if(logger.isDebugEnabled()) logger.debug(rate);
				if (rate != null) {
					profesorSearch.setPuntuacion(rate);
					request.setAttribute(AttributeNames.PUNTUACION, rate);
				}
			}
			if(request.getParameter(ParameterNames.PRECIO) != null) {
				String precioMin = request.getParameter(ParameterNames.PRECIO);
				Double precioMinimo = ValidationUtils.doubleValidator(precioMin);
				if (precioMinimo != null) {
					profesorSearch.setPrecioSesion(precioMinimo);
					request.setAttribute(AttributeNames.PRECIO, precioMinimo);
				}
			}
			if(request.getParameter(ParameterNames.PRECIO_MAX) != null) {
				String precioMax = request.getParameter(ParameterNames.PRECIO_MAX);
				Double precioMaximo = ValidationUtils.doubleValidator(precioMax);
				if(precioMaximo != null) {
					profesorSearch.setPrecioSesionHasta(precioMaximo);
					request.setAttribute(AttributeNames.PRECIO_MAX, precioMaximo);
				}
			}
			if(request.getParameter(ParameterNames.DIA) != null) {
				String dia = request.getParameter(ParameterNames.DIA);
				Integer diaSesion = ValidationUtils.intValidator(dia);
				if (diaSesion != null) {
					profesorSearch.setDiaSesion(diaSesion);
					request.setAttribute(AttributeNames.DIA, diaSesion);
				}
			}


			Results<Profesor> results = new Results<Profesor>();
			List<Profesor> resultados = new ArrayList<Profesor>();
			List<NivelIngles> niveles = new ArrayList<NivelIngles>();
			List<Dia> dias = new ArrayList<Dia>();
			int page = WebUtils.
					getPageNumber(request.getParameter(ParameterNames.PAGE), 1);
			int totalPages = 0;
			int firstPagedPage = 0;
			int lastPagedPage = 0;
			try {

				results = profesorService.findByCriteria(profesorSearch, (page-1)*pageSize+1, pageSize);
				niveles = nivelServices.findAll();
				dias = diaServices.findAll();
				totalPages = (int) Math.ceil((double)results.getResultadosTotales()/(double)pageSize);
				firstPagedPage = Math.max(1, page-pagingPageCount);
				lastPagedPage = Math.min(totalPages, page+pagingPageCount);

			} catch (DataException e) {
				errors.add(ParameterNames.ACTION,ErrorCodes.SEARCH_ERROR);
			}
			resultados = results.getResultados();
			request.setAttribute(AttributeNames.RESULTADOS, resultados);
			request.setAttribute(AttributeNames.NIVELES, niveles);
			request.setAttribute(AttributeNames.DIAS, dias);
			request.setAttribute(ParameterNames.PAGE, page);
			request.setAttribute(AttributeNames.TOTAL_PAGES, totalPages);
			request.setAttribute(AttributeNames.FIRST_PAGED_PAGES, firstPagedPage);
			request.setAttribute(AttributeNames.LAST_PAGED_PAGES, lastPagedPage);
			target = ViewPaths.SEARCH_TEACHER;

		}else if (Actions.DETALLE_ESTUDIANTE.equalsIgnoreCase(action)){
			Estudiante estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
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
				logger.debug("Pais estudiante --> {}", paisEstudiante);
				logger.debug("Genero estudiante --> {}", generoEstudiante);
				logger.debug("Nivel estudiante --> {}", nivelEstudiante);
			}

			request.setAttribute(AttributeNames.PAISES, paisEstudiante);
			request.setAttribute(AttributeNames.GENERO, generoEstudiante);
			request.setAttribute(AttributeNames.NIVELES, nivelEstudiante);
			target = ViewPaths.DETAILS_ESTUDIANTE;
			redirect = false;
		}else if(Actions.DETALLE_PROFESOR.equalsIgnoreCase(action)) {
			String profesorId = request.getParameter(ParameterNames.ID_PROFESOR);
			Pais paisProfesor = new Pais();
			Genero generoProfesor = new Genero();
			Profesor profesor = new Profesor();
			Integer idProfesor = ValidationUtils.intValidator(profesorId);
			if(idProfesor != null) {
				try {
					profesor = profesorService.findById(idProfesor);
					paisProfesor = paisServices.findById(profesor.getIdPais(), idioma);
					generoProfesor = generoServices.findById(profesor.getIdGenero());
				} catch (DataException e) {
					logger.warn(e.getMessage(), e);
				}
			}

			request.setAttribute(AttributeNames.PROFESOR, profesor);
			request.setAttribute(AttributeNames.PAISES, paisProfesor);
			request.setAttribute(AttributeNames.GENERO, generoProfesor);
			target = ViewPaths.DETAILS_PROFESOR;
			redirect=false;

		}else if(Actions.BUSCAR_HORARIOS.equalsIgnoreCase(action)){
			String id = request.getParameter(ParameterNames.ID_PROFESOR);
			String fecha = request.getParameter(ParameterNames.FECHA);
			Integer idProfesor = null;
			Results<Horario> horariosResults = new Results<Horario>();
			Map<Horario, Hora> resultados = new HashMap<Horario, Hora>();
			Date date = new Date();
			if(!StringUtils.isEmptyOrWhitespaceOnly(id)) idProfesor = ValidationUtils.intValidator(id);
			if(fecha!=null) {

				if(!StringUtils.isEmptyOrWhitespaceOnly(fecha)) date = ValidationUtils.dateValidator(fecha);
				if(new Date().after(date)) errors.add(ParameterNames.FECHA, ErrorCodes.FECHA_INVALID);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.set(Calendar.HOUR_OF_DAY, 12);
				date = calendar.getTime();
				if(!errors.hasErrors()) {
					try {
						horariosResults = horarioServices.findByFecha(idProfesor, date, 1, 10);
						for(Horario h: horariosResults.getResultados()) {
							resultados.put(h, horaServices.findById(h.getIdHora()));
						}
					} catch (DataException e) {
						errors.add(ParameterNames.RESULTADOS, ErrorCodes.SEARCH_ERROR);
					}
				}
			}
			request.setAttribute(AttributeNames.DATE_CONTRATACION, date);
			request.setAttribute(AttributeNames.PROFESOR, idProfesor);
			request.setAttribute(AttributeNames.RESULTADOS, resultados);
			request.setAttribute(AttributeNames.ERRORS, errors);

			target = ViewPaths.BUSQUEDA_HORARIOS;
			redirect = false;
		}else if(Actions.CONTRATAR_SESION.equalsIgnoreCase(action)){
			Estudiante estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
			String idHorarioStr = ParameterUtils.trimmer(request.getParameter(ParameterNames.ID_HORARIO));
			String fechaSesionStr = ParameterUtils.trimmer(request.getParameter(ParameterNames.FECHA_SESION));
			Horario h = new Horario();
			Integer idHorario = null;
			Date fechaSesion = new Date();
			if(idHorarioStr != null) {
				idHorario = ValidationUtils.intValidator(idHorarioStr);
			}
			if(fechaSesionStr !=null) {
				fechaSesion = ValidationUtils.dateValidator(fechaSesionStr);
			}
			//fechaSesion = ParameterUtils.nextDay(fechaSesion);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaSesion);
			// TODO Pendiente arreglar fuso horario BD
			calendar.set(Calendar.HOUR_OF_DAY, 12);

			fechaSesion = calendar.getTime();
			try {
				h = horarioServices.findById(idHorario);
				sesionServices.create(h, fechaSesion, estudiante.getIdEstudiante());

			}catch(DataException | MailException e) {
				errors.add(Actions.CONTRATAR_SESION, ErrorCodes.SEARCH_ERROR);
			}
			target = ViewPaths.PRE_HOME_ESTUDIANTE;
			redirect = true;
		}else if(Actions.START_SESION.equalsIgnoreCase(action)){
			String idSesion = request.getParameter(ParameterNames.ID_SESION);
			Integer sesionId = null;
			Boolean puntuar =Boolean.FALSE;
			if(idSesion != null) {
				sesionId = ValidationUtils.intValidator(idSesion);
			}
			try {
				Sesion sesion = sesionServices.findById(sesionId);
				if(sesion.getFechaInicio() == null && sesion.getFechaFin() != null) {
					sesionServices.empezarSesion(sesion);
				}

			} catch (DataException e) {

				e.printStackTrace();
			}
			request.setAttribute(ParameterNames.PUNTUADO, puntuar);
			request.setAttribute(ParameterNames.ID_SESION, sesionId);
			target = ViewPaths.VIDEO_CALL_ESTUDIANTE;
			redirect = false;
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

			target = ViewPaths.PRE_HOME_ESTUDIANTE;
			redirect = true;
		}else if(Actions.CHANGE_PSSWD.equalsIgnoreCase(action)){
			Estudiante estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
			String currentPasswd = request.getParameter(ParameterNames.PASSWORD);
			if(!StringUtils.isEmptyOrWhitespaceOnly(currentPasswd)) {
				currentPasswd = ParameterUtils.trimmer(currentPasswd);
			}else {
				errors.add(ParameterNames.PASSWORD, ErrorCodes.MANDATORY_PARAMETER);
			}
			String newPasswd = request.getParameter(ParameterNames.NEW_PASSWD);
			if(!StringUtils.isEmptyOrWhitespaceOnly(newPasswd)) {
				newPasswd = ParameterUtils.trimmer(newPasswd);
			}else {
				errors.add(ParameterNames.NEW_PASSWD, ErrorCodes.MANDATORY_PARAMETER);
			}
			String newPasswdRepeat = request.getParameter(ParameterNames.PSSWD_REPEAT);
			if(!StringUtils.isEmptyOrWhitespaceOnly(newPasswdRepeat)) {
				newPasswdRepeat = ParameterUtils.trimmer(newPasswdRepeat);
			}else {
				errors.add(ParameterNames.PSSWD_REPEAT, ErrorCodes.MANDATORY_PARAMETER);
			}

			if(!PasswordEncryptionUtil.checkPassword(currentPasswd, estudiante.getPsswd()))
				errors.add(ParameterNames.PASSWORD, ErrorCodes.PSSWD_NOT_MATCH);
			newPasswd = ValidationUtils.passwordValidator(newPasswd, newPasswdRepeat);
			if(newPasswd == null)
				errors.add(ParameterNames.NEW_PASSWD, ErrorCodes.MANDATORY_PARAMETER);

			if(!errors.hasErrors()) {
				Estudiante estudianteUpdate = new Estudiante();
				estudianteUpdate.setIdEstudiante(estudiante.getIdEstudiante());
				estudianteUpdate.setEmail(estudiante.getEmail());
				estudianteUpdate.setPsswd(newPasswd);
				try {
					estudianteService.update(estudianteUpdate);
				} catch (DataException e) {
					e.printStackTrace();
				}
			}
			if(errors.hasErrors() || estudiante == null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Cambio de contraseña fallido: {}", errors);
				}

				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PROPERTIES_ESTUDIANTE;	
			}else {
				if(logger.isDebugEnabled()) {
					logger.info("Estudiante {} cambio psswd: {}", estudiante.getEmail(), newPasswd==null);
				}
				target = ViewPaths.PROPERTIES_ESTUDIANTE;
				redirect = true;
			}
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

			target = ViewPaths.PROPERTIES_ESTUDIANTE;
			redirect = true;
		}else if(Actions.PUNTUAR.equalsIgnoreCase(action)){
			String idSesionStr = request.getParameter(ParameterNames.ID_SESION);
			Estudiante estudiante = (Estudiante) SessionManager.get(request, SessionAttributeNames.USUARIO);
			String puntuarStr = request.getParameter(ParameterNames.PUNTUADO);
			String puntuacionStr = request.getParameter(ParameterNames.PUNTUACION);
			Boolean puntuar = ValidationUtils.booleanValidator(puntuarStr, false, false);
			Double puntuacion = ValidationUtils.doubleValidator(puntuacionStr);
			Integer idSesion = ValidationUtils.intValidator(idSesionStr);
			if(puntuar != null && puntuar && puntuacion != null && idSesion != null) {
				Profesor profesor;
				Sesion sesion;
				try {
					sesion = sesionServices.findById(idSesion);
					profesor = profesorService.findById(sesion.getIdProfesor());
					estudianteService.puntuarProfesor(profesor, estudiante, puntuacion);
				} catch (DataException e) {
					logger.warn(e.getMessage(), e);
				}
			}
			redirect = Boolean.FALSE;
			target = ViewPaths.VIDEO_CALL_ESTUDIANTE;
			request.setAttribute(ParameterNames.PUNTUADO, puntuar);
		}else if(Actions.SEARCH_ACCOUNT.equalsIgnoreCase(action)){
			String email = request.getParameter(ParameterNames.EMAIL);
			email = ValidationUtils.emailValidator(email);
			if(email != null) {
				try {
					Estudiante estudiante = estudianteService.findByEmailToRecovery(email);
					if(estudiante != null) {
						request.setAttribute(ParameterNames.EMAIL, email);
						redirect = Boolean.FALSE;
						target = ViewPaths.RECOVERY_CODE;
					}
				} catch (MailException | DataException e) {
					errors.add(ParameterNames.ACTION, ErrorCodes.MAIL_ERROR);
				}
			}else {
				errors.add(ParameterNames.EMAIL, ErrorCodes.MANDATORY_PARAMETER);
				redirect = Boolean.FALSE;
				target = ViewPaths.RECOVERY_ACCOUNT;
			}
		}else if(Actions.CHECK_CODE.equalsIgnoreCase(action)){
			String estudianteIdStr = request.getParameter(ParameterNames.ID_ESTUDIANTE);
			String codeStr = request.getParameter(ParameterNames.CODE);
			Integer code = ValidationUtils.intValidator(codeStr);
			Integer estudianteId = ValidationUtils.intValidator(estudianteIdStr);
			if(estudianteId != null && code != null) {
				try {
					Estudiante estudiante = estudianteService.findById(estudianteId);
					if(estudiante != null) {
						if(estudiante.getCodigoDeRecuperacion().equals(code))
						request.setAttribute(ParameterNames.ID_ESTUDIANTE, estudiante.getIdEstudiante());
						request.setAttribute(ParameterNames.CODE, code);
						redirect = Boolean.FALSE;
						target = ViewPaths.SET_PASSWORD;
					}
				} catch (DataException e) {
					logger.info(e.getMessage(), e);
					errors.add(ParameterNames.ACTION, ErrorCodes.MAIL_ERROR);
				}
			}else {
				errors.add(ParameterNames.CODE, ErrorCodes.MANDATORY_PARAMETER);
				redirect = Boolean.FALSE;
				target = ViewPaths.RECOVERY_CODE;
			}
		}else if(Actions.SET_PASSWORD.equals(action)){
			String estudianteIdStr = request.getParameter(ParameterNames.ID_ESTUDIANTE);
			String codeStr = request.getParameter(ParameterNames.CODE);
			String psswd = request.getParameter(ParameterNames.PASSWORD);
			String repeatPsswd = request.getParameter(ParameterNames.PSSWD_REPEAT);
			Integer estudianteId = ValidationUtils.intValidator(estudianteIdStr);
			Integer code = ValidationUtils.intValidator(codeStr);
			psswd = ValidationUtils.passwordValidator(psswd, repeatPsswd);
			if(estudianteId != null && code != null && psswd != null) {
				Estudiante estudiante;
				try {
					estudiante = estudianteService.findById(estudianteId);
					estudianteService.cambiarContra(code,estudiante.getEmail(), psswd);
					SessionManager.set(request, SessionAttributeNames.USUARIO, estudiante);
					redirect = Boolean.TRUE;
					target = ViewPaths.HOME_ESTUDIANTE;
				} catch (DataException e) {
					logger.info(e.getMessage(), e);
				}
			}else {
				errors.add(ParameterNames.PASSWORD, ParameterNames.PASSWORD);
				redirect = Boolean.FALSE;
				target = ViewPaths.SET_PASSWORD;
			}
		}else{
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
