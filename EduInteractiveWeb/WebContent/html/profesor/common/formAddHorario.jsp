
<%@page import="com.eduinteractive.web.controller.AttributeNames,java.util.List,
java.util.Map"%>
<%
	List<Hora> horas = (List<Hora>) request.getAttribute(AttributeNames.HORAS);
	List<Dia> dias = (List<Dia>)request.getAttribute(AttributeNames.DIAS);
%> 
<%if(horas != null && dias != null){ %>
<div id="horariosProfesor">

	<form action="<%=ControllerPaths.PROFESOR%>">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.ADD_HORARIO%>">
		<select name="<%=ParameterNames.DIA%>">
			<%for(Dia d: dias){ %>
				<option value="<%=d.getIdDia() %>"><%=d.getDia() %></option>
			<%} %>
		</select>
		
		<select name="<%=ParameterNames.HORA%>">
			<%for(Hora h: horas){ %>
				<option value="<%=h.getIdHora() %>"><%=h.getHora() %></option>
			<%} %>
		</select>
		
		<button id="submitCriteria" type="submit" style="
    width: 94px;
    height: 52px;
    top: 20px;
		" class="aceptbtn">Añadir Horario</button>
		</form>
</div>
<%
}
%>