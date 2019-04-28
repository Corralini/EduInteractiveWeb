<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	Pais paisEstudiante = (Pais) request.getAttribute(AttributeNames.PAISES);
	Genero generoEstudiante = (Genero) request.getAttribute(AttributeNames.GENERO);
	NivelIngles nivelIngles = (NivelIngles) request.getAttribute(AttributeNames.NIVELES);
%>
<div id="detallesEstudiante">
        <p><fmt:message key = "details.nombre" bundle="${messages}"/> <%=ParameterUtils.makeName(e.getNombre(), e.getApellido1(), e.getApellido2()) %></p>
        <p><fmt:message key = "details.anoNac" bundle="${messages}"/> <%=e.getAnoNacimiento() %></p>
        <p><fmt:message key = "details.pais" bundle="${messages}"/> <%=paisEstudiante.getNombrePais() %></p>
        <p><fmt:message key = "details.genero" bundle="${messages}"/> <%=generoEstudiante.getGenero() %> </p>
        <p><fmt:message key = "details.nivelIngles" bundle="${messages}"/> <%= nivelIngles.getNivel()%> </p>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>