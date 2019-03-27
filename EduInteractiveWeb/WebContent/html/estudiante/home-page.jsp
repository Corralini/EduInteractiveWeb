<%@ page import="java.util.List" %>
<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>

<%
	List<Profesor> profesores = (List<Profesor>) request.getAttribute(AttributeNames.PROFESOR);
	List<Sesion> sesiones = (List<Sesion>) request.getAttribute(AttributeNames.SESIONES);
	Sesion sesion = (Sesion) request.getAttribute(AttributeNames.SESION);
	Profesor profesor = (Profesor) request.getAttribute(AttributeNames.TEACHER);
%>

<%
	if(sesion != null && profesor!=null){%>
		<div id="sesionDetails">
        <p>Sesion con: <%=ParameterUtils.makeName(profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()) %></p>
        <p>el día: <%=ParameterUtils.dateBuilder(sesion.getFechaSesion()) %></p>
        <%
        	valores.clear();
        	valores.put(ParameterNames.ACTION, Actions.CANCEL_SESION);
        	valores.put(ParameterNames.ID_SESION, sesion.getIdSesion().toString());
        %>
        <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button class="cancelbtn" id="cancelarSesion">Cancelar</button></a>
    </div>
<%
	}
%>
<%if (sesiones !=null && !sesiones.isEmpty()){ %>
	<div id="sesiones">
		<%
			for(Sesion s: sesiones){ 
				
			valores.clear();
			valores.put(ParameterNames.ACTION, Actions.START_SESION);
			valores.put(ParameterNames.ID_SESION, s.getIdSesion().toString());
		%>
		
        	<%if(ParameterUtils.dateComparator(new Date(), s.getFechaSesion())){ %>
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores) %>" target="_blank">Sesion <%=s.getIdSesion() %></a>
        		<%
					valores.clear();
        			valores.put(ParameterNames.ID_SESION, s.getIdSesion().toString());
				 %>
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.HOME_ESTUDIANTE, valores)%>" id="linkDetail">Más Detalles </a> <br><br>
        	<%
        		} else{
        	%>
        		<a class="isDisabled" href="#" target="_blank">Sesion <%=s.getIdSesion() %></a>
        		<%
					valores.clear();
        			valores.put(ParameterNames.ID_SESION, s.getIdSesion().toString());
				 %>
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.HOME_ESTUDIANTE, valores)%>" id="linkDetail">Más Detalles </a> <br><br>
        	
        	<%
        		}
        	%>
        <%
        	} 
        %>
    </div>
<%}else{ %>
	<p>No tienes sesiones disponibles, por favor, contrate una</p>
<%} %>
<div id="profesoresRecomendados">
        <%	
    	valores.clear();
    		for(Profesor p: profesores) { 
    			
        		valores.put(ParameterNames.ACTION, Actions.DETALLE_PROFESOR);
    	    	valores.put(ParameterNames.ID_PROFESOR, p.getIdProfesor().toString());
    	%>
         <div class="resultadoRecomendado">
            <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2()) %></a>
            <p class="puntuacion"><%=p.getPuntuacion() %>/10</p>
            <p class="precio"><%=p.getPrecioSesion()%></p>
            <%
            	valores.put(ParameterNames.ACTION, Actions.BUSCAR_HORARIOS);
            %>
            <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button id="dispo" class="aceptbtn">Disponibilidad</button></a>
        </div>
        <%
        } 
        %>
</div>
<%@ include file="/html/estudiante/common/footer.jsp"%>