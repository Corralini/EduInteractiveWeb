<%@ page import="java.util.List" %>
<%@ page import="com.educorp.eduinteractive.ecommerce.model.*"%>
<%@ page import="com.eduinteractive.web.controller.*" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EduInteractive</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/css/main.css">
    <link rel="icon" href="<%=request.getContextPath()%>/img/logo.ico">
    <script src="<%=request.getContextPath()%>/js/main.js"></script>
</head>
<body>
	<form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
		<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SIGNIN%>">
		<input type="number" min="0" max="10" maxlength="2" name="<%=ParameterNames.ACERTADAS %>">
		<button type="submit">Registrarse</button>
	</form>
</body>
</html>