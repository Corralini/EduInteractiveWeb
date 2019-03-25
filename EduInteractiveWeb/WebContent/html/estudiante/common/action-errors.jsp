<%@ page import="java.util.List" %>
<%@ page import="com.eduinteractive.web.controller.*" %>
<%
	List<String> parameterErrors = errors.getErrors(ParameterNames.ACTION);
	for (String error: parameterErrors) {
			%><p><%=error%></p>
	<%
	}
%>