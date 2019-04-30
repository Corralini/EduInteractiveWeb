<%@ include file="/html/recoveryProfesor/common/head.jsp"%>
<%@ include file="/html/recoveryProfesor/common/superior.jsp"%>
<%
	Integer idProfesor = (Integer) request.getAttribute(ParameterNames.ID_PROFESOR);
	Integer code = (Integer) request.getAttribute(ParameterNames.CODE);
%>

<div id="searchEmail">
	<form action="<%=ControllerPaths.PROFESOR%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SET_PASSWORD%>">
			<input type="hidden" name="<%=ParameterNames.ID_PROFESOR%>" value="<%=idProfesor%>">
			<input type="hidden" name="<%=ParameterNames.CODE%>" value="<%=code%>">
			<input type="text" name="<%=ParameterNames.PASSWORD%>">
			<input type="text" name="<%=ParameterNames.PSSWD_REPEAT%>">
			<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>

<%@ include file="/html/recoveryProfesor/common/footer.jsp"%>