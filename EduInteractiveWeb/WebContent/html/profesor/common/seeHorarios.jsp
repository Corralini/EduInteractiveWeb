
<%@page import="com.eduinteractive.web.controller.AttributeNames,java.util.List,
java.util.Map"%>
<%
	Map<String, List<String>> horarios = (Map<String, List<String>>) request.getAttribute(AttributeNames.PRINT_HORARIOS);
%>
<%
	if(horarios != null){ 
		if(!horarios.isEmpty()){
%>

<div id="horariosProfesor">
	<%
		for(String dia: horarios.keySet()){
	%>
		<p><%=dia %></p>
		<ul>
			<%for(String hora: horarios.get(dia)){%>
				<li><%=hora %></li>
		</ul>
	<%
			}
		}
	%>
</div>
		<%
		}else{
		%>
	<p id="horariosProfesor">Todav�a no has agregado ning�n horario, mientras no lo hagas los estudiantes no podr�n encontrarte</p>
		<%
		}	
	}
%>