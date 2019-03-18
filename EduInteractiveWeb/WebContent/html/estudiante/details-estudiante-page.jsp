<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<div id="detallesEstudiante">
        <p>Nombre Completo: <%=ParameterUtils.makeName(e.getNombre(), e.getApellido1(), e.getApellido2()) %></p>
        <p>Ano nacimiento: <%=e.getAnoNacimiento() %></p>
        <p>Pais: <%=e.getIdPais() %></p>
        <p>Genero</p>
        <p>Nivel Inglés</p>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>