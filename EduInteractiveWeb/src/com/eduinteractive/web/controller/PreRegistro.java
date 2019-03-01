package com.eduinteractive.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.educorp.eduinteractive.ecommerce.exceptions.DataException;
import com.educorp.eduinteractive.ecommerce.model.Idioma;
import com.educorp.eduinteractive.ecommerce.model.NivelIngles;
import com.educorp.eduinteractive.ecommerce.model.Pais;
import com.educorp.eduinteractive.ecommerce.service.impl.IdiomaServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.NivelInglesServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.impl.PaisServicesImpl;
import com.educorp.eduinteractive.ecommerce.service.spi.IdiomaServices;
import com.educorp.eduinteractive.ecommerce.service.spi.NivelInglesServices;
import com.educorp.eduinteractive.ecommerce.service.spi.PaisServices;

/**
 * Servlet implementation class PreRegistro
 */
@WebServlet("/inicio")
public class PreRegistro extends HttpServlet {
    
	private PaisServices paisService = null;
	private IdiomaServices idiomaServices = null;
	private NivelInglesServices nivelServices = null;
    public PreRegistro() {
        super();
        paisService = new PaisServicesImpl();
        idiomaServices = new IdiomaServicesImpl();
        nivelServices = new NivelInglesServicesImpl();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Pais> paises = new ArrayList<Pais>();
		List<Idioma> idioma = new ArrayList<Idioma>();
		List<NivelIngles> niveles = new ArrayList<NivelIngles>();
		try {
			paises = paisService.findByIdioma("es");
			idioma = idiomaServices.findAll();
			niveles = nivelServices.findAll();
		} catch (DataException e) {
			e.printStackTrace();
		}
		request.setAttribute(AttributeNames.PAISES, paises);
		request.setAttribute(AttributeNames.IDIOMA, idioma);
		request.setAttribute(AttributeNames.NIVELES, niveles);
		request.getRequestDispatcher(ViewPaths.INICIO).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
