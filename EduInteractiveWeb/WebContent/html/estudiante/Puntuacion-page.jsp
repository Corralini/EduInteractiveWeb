<%@ page import="java.util.List" %>
<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	Integer idSesion = (Integer) request.getAttribute(ParameterNames.ID_SESION);
%>
	<from action="<%=ControllerPaths.ESTUDIANTE %>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PUNTUAR%>">
		<input type="hidden" name="<%=ParameterNames.ID_SESION%>" value="<%=idSesion%>">
		<input type="number" min="0" max="100" name="<%=ParameterNames.PUNTUACION%>">
		<button type="submit" class="aceptbtn">Puntuar</button>
	</from>

<%@ include file="/html/estudiante/common/footer.jsp"%>