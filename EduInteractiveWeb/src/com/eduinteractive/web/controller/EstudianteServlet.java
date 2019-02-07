package com.eduinteractive.web.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.exceptions.DuplicateInstanceException;
import com.educorp.eduinteractive.ecommerce.exceptions.MailException;
import com.educorp.eduinteractive.ecommerce.model.Estudiante;
import com.educorp.eduinteractive.ecommerce.service.impl.EstudianteServiceImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.EstudianteService;
import com.eduinteractive.web.utils.ParameterNames;
import com.eduinteractive.web.utils.ParameterUtils;
import com.mysql.cj.util.StringUtils;


@WebServlet("/estudiante")
public class EstudianteServlet extends HttpServlet {

	private Logger logger = LogManager.getLogger();
	private EstudianteService estudianteService = null;
	private Estudiante e = null;


	public EstudianteServlet() {
		super();
		estudianteService = new EstudianteServiceImpl();
		e = new Estudiante();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Writer out = response.getWriter();

		if(logger.isDebugEnabled()) {
			logger.debug(ParameterUtils.print(request.getParameterMap()));
		}
		
		String action = ParameterNames.ACTION;

		if (action == ParameterNames.SIGNUP) {

			String email = request.getParameter(ParameterNames.EMAIL);
			String psswd = request.getParameter(ParameterNames.PSSWD);
			String nombre = request.getParameter(ParameterNames.NOMBRE);
			String apellido1 = request.getParameter(ParameterNames.APELLIDO1);
			String apellido2 = request.getParameter(ParameterNames.APELLIDO2);
			String anoNac = request.getParameter(ParameterNames.ANO_NACIMIENTO);
			String genero = request.getParameter(ParameterNames.ID_GENERO);
			String pais = request.getParameter(ParameterNames.ID_PAIS);
			String acertadas = request.getParameter(ParameterNames.ACERTADAS);

			try {
				if(!StringUtils.isEmptyOrWhitespaceOnly(email)) {
					email = email.trim();
				}else{
					out.append("El campo email es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(psswd)) {
					psswd = psswd.trim();
				}else{
					out.append("El campo psswd es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(nombre)) {
					nombre = nombre.trim();
				}else{
					out.append("El campo nombre es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(apellido1)) {
					apellido1 = apellido1.trim();
				}else{
					out.append("El campo apellido1 es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(apellido2)) {
					apellido2 = apellido2.trim();
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(anoNac)) {
					anoNac = anoNac.trim();
				}else{
					out.append("El campo anoNac es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(genero)) {
					genero = genero.trim();
				}else{
					out.append("El campo genero es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(pais)) {
					pais = pais.trim();
				}else{
					out.append("El campo pais es obligatorio");
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(acertadas)) {
					acertadas = acertadas.trim();
				}else{
					out.append("El campo acertadas es obligatorio");
				}


				Integer anoNacimiento = Integer.parseInt(anoNac);
				int acertadasTest = Integer.parseInt(acertadas);


				e.setEmail(email);
				e.setNombre(nombre);
				e.setApellido1(apellido1);
				if(apellido2 != null || apellido2 != "") {
					e.setApellido2(apellido2);
				}
				e.setEmail(email);
				e.setIdGenero(genero);
				e.setAnoNacimiento(anoNacimiento);
				e.setIdPais(pais);
				e.setPsswd(psswd);

				estudianteService.signUp(e, acertadasTest);

			} catch (NumberFormatException e) {
				out.append("Hemos teneido un problema");
				e.printStackTrace();
			} catch (MailException e1) {
				out.append("Hemos tenido algún problema enviando el correo");
				e1.printStackTrace();
			} catch (DuplicateInstanceException e1) {
				out.append("El estudiante ya existe");
				e1.printStackTrace();
			} catch (DataException e1) {
				out.append("Ups! Hemos tenido algun problema con el servidor");
				e1.printStackTrace();
			}
		}else if (action == ParameterNames.LOGIN) {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
