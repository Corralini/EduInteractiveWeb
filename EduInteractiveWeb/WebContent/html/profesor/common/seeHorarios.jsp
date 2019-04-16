
<%@page import="com.eduinteractive.web.controller.AttributeNames,java.util.List,
java.util.Map"%>
<%
	Map<String, List<String>> horarios = (Map<String, List<String>>) request.getAttribute(AttributeNames.PRINT_HORARIOS);
%>

<div>
	<%
		for(String dia: horarios.keySet()){
	%>
		<p><%=dia %></p>
			<%for(String hora: horarios.get(dia)){%>
				<ul></ul>
	<%
			}
		}
	%>
</div>