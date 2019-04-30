<%@ include file="/html/recoveryUser/common/head.jsp"%>
<%@ include file="/html/recoveryUser/common/superior.jsp"%>
<%
	Integer idEstudiante = (Integer) request.getAttribute(ParameterNames.ID_ESTUDIANTE);
%>

<div id="searchEmail">
	<form action="<%=ControllerPaths.ESTUDIANTE%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHECK_CODE%>">
			<input type="hidden" name="<%=ParameterNames.ID_ESTUDIANTE%>" value="<%=idEstudiante%>">
			<input type="number" name="<%=ParameterNames.CODE%>">
	<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>

<%@ include file="/html/recoveryUser/common/footer.jsp"%>