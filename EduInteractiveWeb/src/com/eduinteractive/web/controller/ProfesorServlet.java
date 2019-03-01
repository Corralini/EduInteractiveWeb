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
import com.educorp.eduinteractive.ecommerce.model.Profesor;
import com.educorp.eduinteractive.ecommerce.service.criteria.ProfesorCriteria;
import com.educorp.eduinteractive.ecommerce.service.impl.ProfesorServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.ProfesorService;
import com.eduinteractive.web.util.Actions;
import com.eduinteractive.web.util.AttributeNames;
import com.eduinteractive.web.util.ErrorCodes;
import com.eduinteractive.web.util.ErrorManager;
import com.eduinteractive.web.util.ParameterNames;
import com.eduinteractive.web.util.ParameterUtils;
import com.eduinteractive.web.util.ViewPaths;

/**
 * Servlet implementation class ProfesorServlet
 */
@WebServlet("/profesor")
public class ProfesorServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(ProfesorServlet.class);
	private ProfesorService profesorService = null;
       
    
    public ProfesorServlet() {
        super();
        profesorService = new ProfesorServicesImpl();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ParameterNames.ACTION);
		if (logger.isDebugEnabled()) {
			logger.debug("Action {}: {}", action, ToStringBuilder.reflectionToString(request.getParameterMap()));
		}

		ErrorManager errors = new ErrorManager(); 
		String target = null;
		if (Actions.SEARCH.equalsIgnoreCase(action)) {

			// Recuperar parametros
			String nombre = request.getParameter(ParameterNames.NOMBRE);

			nombre = ParameterUtils.trimmer(nombre);
			
			if(StringUtils.isEmpty(nombre)) {
				errors.add(ParameterNames.NOMBRE, ErrorCodes.MANDATORY_PARAMETER);
			}
			ProfesorCriteria profesorCriteria = new ProfesorCriteria();
			profesorCriteria.setNombre(nombre);
			List<Profesor> resultados = new ArrayList<Profesor>();
			if(!errors.hasErrors()) {
				try {
				resultados = profesorService.findByCriteria(profesorCriteria);
				request.setAttribute(AttributeNames.RESULTADOS, resultados);
				}catch(DataException e) {
					errors.add(ParameterNames.ACTION, ErrorCodes.SEARCH_ERROR);
				}
			}else{
				if (logger.isDebugEnabled()) {
					logger.debug("Autenticacion fallida: {}", errors);
				}
							
				request.setAttribute(AttributeNames.ERRORS, errors);				
				target = ViewPaths.SEARCH;
			}
			
		} else {
			// Mmm...
			logger.error("Action desconocida");
			// target ?
		}
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
