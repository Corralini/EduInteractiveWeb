<%@ include file="/html/recoveryUser/common/head.jsp"%>
<%@ include file="/html/recoveryUser/common/superior.jsp"%>
<%
	Integer idEstudiante = (Integer) request.getAttribute(ParameterNames.ID_ESTUDIANTE);
	Integer code = (Integer) request.getAttribute(ParameterNames.CODE);
%>

<div id="searchEmail">
	<form action="<%=ControllerPaths.ESTUDIANTE%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SET_PASSWORD%>">
			<input type="hidden" name="<%=ParameterNames.ID_ESTUDIANTE%>" value="<%=idEstudiante%>">
			<input type="hidden" name="<%=ParameterNames.CODE%>" value="<%=code%>">
			<label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswd" bundle="${messages}"/></b></label>
			<br>
			<input type="password" name="<%=ParameterNames.PASSWORD%>"> <br>
			<br>
			<label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswdRepeat" bundle="${messages}"/></b></label>
			<br>
			<input type="password" name="<%=ParameterNames.PSSWD_REPEAT%>">
			<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>

<%@ include file="/html/recoveryUser/common/footer.jsp"%>