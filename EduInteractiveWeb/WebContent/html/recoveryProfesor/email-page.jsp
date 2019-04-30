<%@ include file="/html/recoveryProfesor/common/head.jsp"%>
<%@ include file="/html/recoveryProfesor/common/superior.jsp"%>
<div id="searchEmail">
	<form action="<%=ControllerPaths.PROFESOR%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH_ACCOUNT%>">
			<input type="text" name="<%=ParameterNames.EMAIL%>">
	<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>
<%@ include file="/html/recoveryProfesor/common/footer.jsp"%>