<%@ include file="/html/recoveryUser/common/head.jsp"%>
<%@ include file="/html/recoveryUser/common/superior.jsp"%>
<div id="searchEmail">
	<form action="<%=ControllerPaths.ESTUDIANTE%>">
			<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH_ACCOUNT%>">
			<label for="<%=ParameterNames.EMAIL%>"><b><fmt:message key = "email" bundle="${messages}"/></b></label>
			<br>
			<input type="text" name="<%=ParameterNames.EMAIL%>">
	<button id="sendEmail" type="submit" class="aceptbtn"><fmt:message key = "continuar" bundle="${messages}"/></button>
	</form>
</div>
<%@ include file="/html/recoveryUser/common/footer.jsp"%>