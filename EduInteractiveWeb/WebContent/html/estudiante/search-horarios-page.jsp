<%@page import="java.util.List"%>
<%@page import="java.util.Map, com.eduinteractive.web.model.ErrorManager"%>
<%@page import="com.educorp.eduinteractive.ecommerce.dao.service.*" %> %>

<%@ include file="/html/estudiante/common/head.jsp"%>
<%
	ErrorManager errors = (ErrorManager) request.getAttribute(AttributeNames.ERRORS);
	if (errors == null) errors = new ErrorManager();
	
	Results<Horario> horarios = (Results<Horario>) request.getAttribute(AttributeNames.HORARIOS);
	List<Horario> resultados = horarios.getResultados();
%>

<div id="horarioSearch">
	<form action="<%=ControllerPaths.ESTUDIANTE%>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.BUSCAR_HORARIOS%>">
		<%@ include file="/html/estudiante/common/action-errors.jsp" %>
		<%
			parameterErrors = errors.getErrors(ParameterNames.FECHA);
			for (String error: parameterErrors) {
				%><li><%=error%></li>
		<%	}
			%>
		<input type="date" name="<%=ParameterNames.FECHA%>">
		<button type="submit">Buscar</button>
	</form>
</div>
<div id="resultadoHorarios">
	<p>Hemos encontrado <%=horarios.getResultadosTotales()%> resultados</p>
	<%
		for(Horario h: resultados){ %>
			<a><%=h.getIdHora()%></a>
		<%
		}
	 %>
</div>
<%@ include file="/html/estudiante/common/footer.jsp"%>