<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	Pais paisEstudiante = (Pais) request.getAttribute(AttributeNames.PAISES);
	Genero generoEstudiante = (Genero) request.getAttribute(AttributeNames.GENERO);
	NivelIngles nivelIngles = (NivelIngles) request.getAttribute(AttributeNames.NIVELES);
%>
<div id="detallesEstudiante">
        <p>Nombre Completo: <%=ParameterUtils.makeName(e.getNombre(), e.getApellido1(), e.getApellido2()) %></p>
        <p>Ano nacimiento: <%=e.getAnoNacimiento() %></p>
        <p>Pais: <%=paisEstudiante.getNombrePais() %></p>
        <p>Genero: <%=generoEstudiante.getGenero() %> </p>
        <p>Nivel Inglés: <%= nivelIngles.getNivel()%> </p>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>