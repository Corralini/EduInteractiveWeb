<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	Profesor profesor = (Profesor) request.getAttribute(AttributeNames.PROFESOR);
	Pais paisProfesor = (Pais) request.getAttribute(AttributeNames.PAISES);
	Genero generoProfesor = (Genero) request.getAttribute(AttributeNames.GENERO);
	valores.clear();
    valores.put(ParameterNames.ACTION, Actions.BUSCAR_HORARIOS);
    valores.put(ParameterNames.ID_PROFESOR, profesor.getIdProfesor().toString());
%>
<div id="detallesProfesor">
        <p><b><%=ParameterUtils.makeName(profesor.getNombre(), profesor.getApellido1(), profesor.getApellido2()) %></b></p>
        <p><fmt:message key = "details.anoNac" bundle="${messages}"/> <%=profesor.getAnoNacimiento() %></p>
        <p><fmt:message key = "details.pais" bundle="${messages}"/> <%=paisProfesor.getNombrePais() %></p>
        <p><fmt:message key = "details.genero" bundle="${messages}"/> <%=generoProfesor.getGenero() %></p>
        <p><fmt:message key = "details.precio" bundle="${messages}"/> <%=profesor.getPrecioSesion() %></p>
        <p><fmt:message key = "details.puntuacion" bundle="${messages}"/> <%=profesor.getPuntuacion() %></p>
        <p><fmt:message key = "details.descripcion" bundle="${messages}"/></p>
        <p><%=profesor.getDescripcion() %></p>
        <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button id="disponibilidad" class="aceptbtn"><fmt:message key = "disponibilidad" bundle="${messages}"/></button></a>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>