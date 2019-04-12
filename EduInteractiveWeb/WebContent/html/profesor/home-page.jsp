<%@ page import="java.util.List" %>
<%@ include file="/html/profesor/common/head.jsp"%>
<%@ include file="/html/profesor/common/superior.jsp"%>

<%
	List<Sesion> sesiones = (List<Sesion>) request.getAttribute(AttributeNames.SESIONES);
	Sesion sesion = (Sesion) request.getAttribute(AttributeNames.SESION);
	Estudiante estudiante = (Estudiante) request.getAttribute(AttributeNames.ESTUDIANTE);
%>

<%
	if(sesion != null && estudiante!=null){%>
		<div id="sesionDetails">
        <p>Sesion con: <%=ParameterUtils.makeName(estudiante.getNombre(), estudiante.getApellido1(), estudiante.getApellido2()) %></p>
        <p>el d�a: <%=ParameterUtils.dateBuilder(sesion.getFechaSesion()) %></p>
        <%
        	valores.clear();
        	valores.put(ParameterNames.ACTION, Actions.CANCEL_SESION);
        	valores.put(ParameterNames.ID_SESION, sesion.getIdSesion().toString());
        %>
        <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores)%>"><button class="cancelbtn" id="cancelarSesion">Cancelar</button></a>
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
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores) %>" target="_blank">Sesion <%=s.getIdSesion() %></a>
        		<%
					valores.clear();
        			valores.put(ParameterNames.ID_SESION, s.getIdSesion().toString());
				 %>
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.HOME_PROFESOR, valores)%>" id="linkDetail">M�s Detalles </a> <br><br>
        	<%
        		} else{
        	%>
        		<a class="isDisabled" href="#" target="_blank">Sesion <%=s.getIdSesion() %></a>
        		<%
					valores.clear();
        			valores.put(ParameterNames.ID_SESION, s.getIdSesion().toString());
				 %>
        		<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.HOME_PROFESOR, valores)%>" id="linkDetail">M�s Detalles </a> <br><br>
        	
        	<%
        		}
        	%>
        <%
        	} 
        %>
    </div>
<%}else{ %>
	<p>No tienes sesiones disponibles</p>
<%} %>

<%@ include file="/html/profesor/common/footer.jsp"%>