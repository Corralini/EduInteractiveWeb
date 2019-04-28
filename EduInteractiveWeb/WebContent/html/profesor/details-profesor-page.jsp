<%@ include file="/html/profesor/common/head.jsp"%>
<%@ include file="/html/profesor/common/superior.jsp"%>
<%
	Pais paisProfesor = (Pais) request.getAttribute(AttributeNames.PAISES);
	Genero generoProfesor = (Genero) request.getAttribute(AttributeNames.GENERO);
%>
<div id="detallesProfesor">
        <p><b><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2()) %></b></p>
        <p>Ano nacimiento: <%=p.getAnoNacimiento() %></p>
        <p>Pais: <%=paisProfesor.getNombrePais() %></p>
        <p>Genero: <%=generoProfesor.getGenero() %></p>
        <p>Precio: <%=p.getPrecioSesion() %></p>
        <p>Puntuacion: <%=p.getPuntuacion() %></p>
        <p>Descripcion:</p>
        <p><%=p.getDescripcion() %></p>
    </div>
    <%
    	valores.clear();
    valores.put(ParameterNames.ACTION, Actions.SEE_DOCUMENT);
    %>
  	<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores)%>" target="_blank">Ver archivo subido</a>
<%@ include file="/html/profesor/common/footer.jsp"%>