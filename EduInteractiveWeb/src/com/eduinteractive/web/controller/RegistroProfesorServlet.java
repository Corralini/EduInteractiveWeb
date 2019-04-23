package com.eduinteractive.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.exceptions.MailException;
import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.eduinteractive.web.model.ErrorCodes;
import com.eduinteractive.web.model.ErrorManager;
import com.eduinteractive.web.utils.SessionManager;
import com.eduinteractive.web.utils.ValidationUtils;
import com.mysql.cj.util.StringUtils;

/**
 * Servlet implementation class RegistroProfesorServlet
 */
@WebServlet("/registroTeacher")
public class RegistroProfesorServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(RegistroProfesorServlet.class);
       // files
		// location to store file uploaded
    	private static final String UPLOAD_DIRECTORY = "EduInteractive/Files";
 
    	// upload settings
    	private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    	private static final int MAX_FILE_SIZE      = 1024 * 1024 * 4; // 4MB
    	private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 5; // 5MB
    	
    	private ProfesorService profesorService = null;
    	
    	public RegistroProfesorServlet() {
        super();
        profesorService = new ProfesorServicesImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			ErrorManager errors = new ErrorManager();
			String target = null;
		
			//parámetros
			String email = null;
			String nombre = null;
			String apellido1 = null;
			String apellido2 = null;
			String psswd = null;
			String psswdRepetida = null;
			String genero = null;
			String pais = null;
			String anoNac = null;
			String precio = null;
			String idiomaProfesor = null;
			String nivelMin = null;
			String description = null;
			
			if (!ServletFileUpload.isMultipartContent(request)) {
	            // if not, we stop here
	            logger.warn("Error: Form must has enctype=multipart/form-data.");
	            return;
	        }
			
			// configures upload settings
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        // sets memory threshold - beyond which files are stored in disk
	        factory.setSizeThreshold(MEMORY_THRESHOLD);
	        // sets temporary location to store files
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
	 
	        ServletFileUpload upload = new ServletFileUpload(factory);
	         
	        // sets maximum size of upload file
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	         
	        // sets maximum size of request (include file + form data)
	        upload.setSizeMax(MAX_REQUEST_SIZE);
	 
	        // constructs the directory path to store upload file
	        // this path is relative to application's directory
	        String uploadPath = getServletContext().getRealPath("")
	                + File.separator + UPLOAD_DIRECTORY;
	         
	        // creates the directory if it does not exist
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }
	        List<FileItem> formItems = new ArrayList<FileItem>();
	     // parses the request's content to extract file data
	        try {
				formItems = upload.parseRequest(request);
				//recuperamos parámetros
				email = formItems.get(1).getString();
				nombre = formItems.get(2).getString();
				apellido1 = formItems.get(3).getString();
				apellido2 = formItems.get(4).getString();
				psswd = formItems.get(5).getString();
				psswdRepetida = formItems.get(6).getString();
				genero = formItems.get(7).getString();
				pais = formItems.get(8).getString();
				anoNac = formItems.get(9).getString();
				precio = formItems.get(10).getString();
				idiomaProfesor = formItems.get(11).getString();
				nivelMin = formItems.get(12).getString();
				description = formItems.get(13).getString();
			} catch (FileUploadException e) {
				logger.warn(e.getMessage(), e);
			}
			
			

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
			
			if(apellido2 != null && !StringUtils.isEmptyOrWhitespaceOnly(apellido2)) {
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
			
			Double precioSesion = ValidationUtils.doubleValidator(precio);
			
			if(precioSesion == null) {
				errors.add(ParameterNames.PRECIO, ErrorCodes.MANDATORY_PARAMETER);
			}
			
			idiomaProfesor = ValidationUtils.stringOnlyLettersValidator(idiomaProfesor, false);
			if(idiomaProfesor == null) {
				errors.add(ParameterNames.IDIOMA, ErrorCodes.MANDATORY_PARAMETER);
			}
			
			Integer nivelEstudiante = ValidationUtils.intValidator(nivelMin);
			if(nivelEstudiante == null) {
				errors.add(ParameterNames.NIVEL_INGLES, ErrorCodes.MANDATORY_PARAMETER);
			}
			
			
			Profesor profesor = new Profesor();

			if (!errors.hasErrors()) {
				profesor.setEmail(email);
				profesor.setNombre(nombre);
				profesor.setApellido1(apellido1);
				profesor.setApellido2(apellido2);
				profesor.setPsswd(psswd);
				profesor.setIdGenero(genero);
				profesor.setIdPais(pais);
				profesor.setAnoNacimiento(anoNacimiento);
				profesor.setPrecioSesion(precioSesion);
				profesor.setIdIdioma(idiomaProfesor);
				profesor.setIdNivel(nivelEstudiante);
				profesor.setDescripcion(description);
				
				if (!formItems.get(14).isFormField()) {
                    String fileName = new File(profesor.getEmail().concat(formItems.get(14).getName())).getName();
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);

                    try {
						formItems.get(14).write(storeFile);
					} catch (Exception e) {
						logger.warn(e.getMessage(), e);
					}
                    logger.debug("Upload has been done successfully!");
                }
				
				try {
					profesorService.signUp(profesor);
				} catch (MailException | DataException e) {
					logger.warn(e.getMessage(), e);
				}
			}

			if (errors.hasErrors()) {	
				if (logger.isDebugEnabled()) {
					logger.debug("Registro fallido: {}", errors);
				}				
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.PRE_INICIO;				
			} else {			
				if (logger.isDebugEnabled()) {
					logger.info("Profesor "+profesor.getEmail()+" registrado.");
				}				
				SessionManager.set(request, SessionAttributeNames.USUARIO, profesor);						
				target = ViewPaths.TEACHER_NOT_ACTIVATE;					
			}
			RedirectOrForwardUtils.redirectOrForward(request, response, true, target);
		}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
