<%@page import="java.util.List"%>
<%@page import="java.util.Map, com.eduinteractive.web.model.ErrorManager"%>
<%@page import="com.educorp.eduinteractive.ecommerce.model.*" %>

<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	ErrorManager errors = (ErrorManager) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new ErrorManager();
	Date date = (Date) request.getAttribute(AttributeNames.DATE_CONTRATACION);
	Integer idProfesor = (Integer) request.getAttribute(AttributeNames.PROFESOR);
	Map<Horario, Hora> resultados = (Map<Horario, Hora>) request.getAttribute(AttributeNames.RESULTADOS);
	valores.clear();
	valores.put(ParameterNames.ACTION, Actions.CONTRATAR_SESION);
	valores.put(ParameterNames.FECHA_SESION, ParameterUtils.dateBuilder(date));
%>

<div id="horarioSearch">
	<form action="<%=ControllerPaths.ESTUDIANTE%>">
		<input type="hidden" name="<%=ParameterNames.ID_PROFESOR%>" value="<%=idProfesor%>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR_HORARIOS%>">
		<%@ include file="/html/estudiante/common/action-errors.jsp" %>
		<%
			parameterErrors = errors.getErrors(ParameterNames.FECHA);
			for (String error: parameterErrors) {
				%><li><%=error%></li>
		<%	}
			%>
		<input type="date" name="<%=ParameterNames.FECHA%>">
		<button id="submitCriteria" type="submit" class="aceptbtn">Buscar</button>
		</form>
</div>
<% 
if(resultados != null && !resultados.isEmpty()){ %>
<div id="resultadoHorarios">
	<p>Hemos encontrado <%=resultados.size()%> resultados</p>
	<%
		for (Horario horario: resultados.keySet()){ 
			valores.put(ParameterNames.ID_HORARIO, horario.getIdHorario().toString());
	    	   %>
		
			<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=resultados.get(horario).getHora()%></a>
     		<%
		}
	 %>
</div>
<%} %>
<%@ include file="/html/estudiante/common/footer.jsp"%>