package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.exceptions.DuplicateInstanceException;
import com.educorp.eduinteractive.ecommerce.exceptions.MailException;
import com.educorp.eduinteractive.ecommerce.model.Estudiante;
import com.educorp.eduinteractive.ecommerce.service.impl.EstudianteServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.PaisServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.EstudianteService;
import com.educorp.eduinteractive.ecommerce.service.spi.PaisServices;
import com.eduinteractive.web.model.ErrorCodes;
import com.eduinteractive.web.model.ErrorManager;
import com.eduinteractive.web.utils.ParameterUtils;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.ValidationUtils;

/**
 * Servlet implementation class EstudianteServlet
 */
@WebServlet("/estudiante")
public class EstudianteServlet extends HttpServlet {

	private static Logger logger = LogManager.getLogger(EstudianteServlet.class);
	private EstudianteService estudianteService = null;
	private PaisServices paisServices = null;

	public EstudianteServlet() {
		super();
		estudianteService = new EstudianteServiceImpl();
		paisServices = new PaisServicesImpl();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ParameterNames.ACTION);

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
			logger.debug(email); 	// borrar esta traza
			logger.debug(password); // borrar esta traza

			if (StringUtils.isEmpty(email)) {
				errors.add(ParameterNames.EMAIL,ErrorCodes.MANDATORY_PARAMETER);
			}

			if (StringUtils.isEmpty(password)) {
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

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}

				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PRE_INICIO;				
			} else {
				if(logger.isDebugEnabled()) {
					logger.info("Estudiante " + estudiante.getEmail() + " autenticado");
				}
				SessionManager.set(request, SessionAttributeNames.ESTUDIANTE, estudiante);		
				target = request.getContextPath() + ViewPaths.HOME_ESTUDIANTE;	
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
			
			apellido2 = ValidationUtils.stringOnlyLettersValidator(apellido2, false);
			
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
				SessionManager.set(request, SessionAttributeNames.ESTUDIANTE, estudiante);						
				target = ViewPaths.TEST;					
			}
		}else if (Actions.LOGOUT.equalsIgnoreCase(action)) {
			SessionManager.set(request, SessionAttributeNames.ESTUDIANTE, null);
			target = request.getContextPath() + ViewPaths.PRE_INICIO;
			redirect = true;
		}else if(Actions.SIGNIN.equalsIgnoreCase(action)) {
			Estudiante estudiante = (Estudiante) SessionManager.get(request, AttributeNames.ESTUDIANTE);
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
				SessionManager.set(request, SessionAttributeNames.ESTUDIANTE, estudiante);						
				target = request.getContextPath() + ViewPaths.HOME_ESTUDIANTE;					
				redirect = true;
				if (logger.isDebugEnabled()) {
					logger.debug("Redirect status: ", redirect);
				}
			}
			
		}else {
			logger.error("Action desconocida");
			target = ViewPaths.PRE_INICIO;
		}

		RedirectOrForwardUtils.redirectOrForward(request, response, redirect, target);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
