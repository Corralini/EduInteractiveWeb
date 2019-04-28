<%@ include file="/html/profesor/common/head.jsp"%>
<%@ include file="/html/profesor/common/superior.jsp"%>
<%
	Pais paisProfesor = (Pais) request.getAttribute(AttributeNames.PAISES);
	Genero generoProfesor = (Genero) request.getAttribute(AttributeNames.GENERO);
%>
<div id="detallesProfesor">
        <p><b><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2()) %></b></p>
        <p><fmt:message key="details.anoNac" bundle="${messages}"/> <%=p.getAnoNacimiento() %></p>
        <p><fmt:message key="details.pais" bundle="${messages}"/> <%=paisProfesor.getNombrePais() %></p>
        <p><fmt:message key="details.genero" bundle="${messages}"/> <%=generoProfesor.getGenero() %></p>
        <p><fmt:message key="details.precio" bundle="${messages}"/> <%=p.getPrecioSesion() %></p>
        <p><fmt:message key="details.puntuacion" bundle="${messages}"/> <%=p.getPuntuacion() %></p>
        <p><fmt:message key="details.descripcion" bundle="${messages}"/></p>
        <p><%=p.getDescripcion() %></p>
    </div>
    <%
    	valores.clear();
    valores.put(ParameterNames.ACTION, Actions.SEE_DOCUMENT);
    %>
  	<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores)%>" target="_blank"><fmt:message key="details.archivoSubido" bundle="${messages}"/></a>
<%@ include file="/html/profesor/common/footer.jsp"%>