<%@ include file="/html/recoveryProfesor/common/head.jsp"%>
<%@ include file="/html/recoveryProfesor/common/superior.jsp"%>
<%
	Integer idProfesor = (Integer) request.getAttribute(ParameterNames.ID_PROFESOR);
%>

<div id="searchEmail">
	<form action="<%=ControllerPaths.PROFESOR%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHECK_CODE%>">
			<input type="hidden" name="<%=ParameterNames.ID_PROFESOR%>" value="<%=idProfesor%>">
			<input type="number" name="<%=ParameterNames.CODE%>">
	<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>

<%@ include file="/html/recoveryProfesor/common/footer.jsp"%>