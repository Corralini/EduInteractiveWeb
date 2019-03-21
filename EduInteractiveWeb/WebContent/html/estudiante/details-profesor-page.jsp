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
        <p>Ano nacimiento: <%=profesor.getAnoNacimiento() %></p>
        <p>Pais: <%=paisProfesor.getNombrePais() %></p>
        <p>Genero: <%=generoProfesor.getGenero() %></p>
        <p>Precio: <%=profesor.getPrecioSesion() %></p>
        <p>Puntuacion: <%=profesor.getPuntuacion() %></p>
        <p>Descripcion:</p>
        <p><%=profesor.getDescripcion() %></p>
        <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><button id="disponibilidad" class="aceptbtn">Disponibilidad</button></a>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>