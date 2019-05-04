<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<div id="videoCall">
	<iframe src="https://appr.tc/r/eduinteractiveroom1" width="500" height="500" frameborder="1" allow="geolocation; microphone; camera"></iframe>
</div>
<%
	Boolean puntuado = (Boolean) request.getAttribute(ParameterNames.PUNTUADO);
	Integer idSesion = (Integer) request.getAttribute(ParameterNames.ID_SESION);
	int newPuntuado = (int)(Math.random() * 10 + 1);
%>
	<%if(puntuado != null && !puntuado.booleanValue()){ %>
		<div id="puntuacion">
			<form action="<%=ControllerPaths.ESTUDIANTE%>">
				<input type="hidden" name="<%=ParameterNames.ID_SESION%>" value="<%=idSesion%>">
				<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PUNTUAR%>">
				<input type="hidden" name="<%=ParameterNames.PUNTUADO%>" value="<%=newPuntuado%>">
				<input style="float: left;" type="number" min="0" step="0.5" value="5" name="<%=ParameterNames.PUNTUACION%>">
		<button id="puntuarBtn" type="submit" class="aceptbtn"><fmt:message key = "puntuar" bundle="${messages}"/></button>
		</form>
</div>
	<%} %>
<%@ include file="/html/estudiante/common/footer.jsp"%>