<%@page import="com.eduinteractive.web.utils.ParameterUtils"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.educorp.eduinteractive.ecommerce.model.*"%>
<%@ page import="com.eduinteractive.web.controller.*" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>EduInteractive</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/css/index.css">
    <link rel="icon" href="<%=request.getContextPath()%>/img/logo.ico">
    <link href="https://fonts.googleapis.com/css?family=Libre+Baskerville" rel="stylesheet">
    <script src="<%=request.getContextPath()%>/js/main.js"></script>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>

<% 
	List<Pais> paises = (List<Pais>) request.getAttribute(AttributeNames.PAISES);
	List<Idioma> idiomas = (List<Idioma>) request.getAttribute(AttributeNames.IDIOMA);
	List<NivelIngles> niveles = (List<NivelIngles>) request.getAttribute(AttributeNames.NIVELES);
%>

<body>
<fmt:setLocale value = "${sessionScope['user-locale']}" scope="session"/>
<fmt:setBundle basename = "resources.Messages" var="messages" scope="session"/>
    <div id="header">
        <h1 id="nombre">EduInteractive</h1>
        <div id="logIn">
            <button onclick="document.getElementById('login').style.display='block'" type="button"
                class="button"><fmt:message key = "autenticate" bundle="${messages}"/></button>
        </div>
    </div>
    <div id="body" style="text-align: center;">
        <h1><fmt:message key = "bienvenida" bundle="${messages}"/></h1>
		<img src="<%=request.getContextPath()%>/img/indexbackground.jpg" alt="foto">
        <div id="registroHome">
            <h3 style="width: 200px;position: relative;left: 43%;"><fmt:message key = "registrate" bundle="${messages}"/></h3>
            <button onclick="document.getElementById('signUpProfesor').style.display='block'" type="button" onclick=""
                class="button" style="width: 250px; text-align: center;"><fmt:message key = "soyProfesor" bundle="${messages}"/></button>
            <button onclick="document.getElementById('signUpEstudiante').style.display='block'" type="button" onclick=""
                class="button" style="background-color:#80ff00; width: 250px; text-align: center;"><fmt:message key = "soyEstudiante" bundle="${messages}"/></button>
        </div>
        <!-- Sign in Estudiante-->

        <div id="signUpEstudiante" class="modal">
            <span onclick="document.getElementById('signUpEstudiante').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
            <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PRESIGNIN%>">
                <div class="container">
                    <h1><fmt:message key = "soyEstudiante" bundle="${messages}"/></h1>
                    <p><fmt:message key = "rellenadoForm" bundle="${messages}"/></p>
                    <hr>
                    <label for="<%=ParameterNames.EMAIL%>"><b><fmt:message key = "email" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                    <label for="<%=ParameterNames.NOMBRE%>"><b><fmt:message key = "nombre" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Nombre" name="<%=ParameterNames.NOMBRE%>" required>

                    <label for="<%=ParameterNames.APELLIDO1%>"><b><fmt:message key = "apellido1" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Primer Apellido" name="<%=ParameterNames.APELLIDO1%>" required>

                    <label for="<%=ParameterNames.APELLIDO2%>"><b><fmt:message key = "apellido2" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Segundo Apellido" name="<%=ParameterNames.APELLIDO2%>">

                    <label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswd" bundle="${messages}"/></b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>

                    <label for="<%=ParameterNames.PSSWD_REPEAT%>"><b><fmt:message key = "psswdRepeat" bundle="${messages}"/></b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PSSWD_REPEAT%>" required>

                    <label for="<%=ParameterNames.GENERO%>"><b><fmt:message key = "genero" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.GENERO%>" id="generoEstudiante">
                        <option value="H"><fmt:message key = "hombre" bundle="${messages}"/></option>
                        <option value="M"><fmt:message key = "mujer" bundle="${messages}"/></option>
                        <option value="O"><fmt:message key = "otro" bundle="${messages}"/></option>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PAIS%>"><b><fmt:message key = "pais" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.PAIS%>" id="paisEstudiante">
                        <%
					for (Pais p: paises) {
						%>
						<option value="<%=p.getIdPais()%>"><%=p.getNombrePais()%></option>
						<%
					}				
				%>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.YEAR%>"><b><fmt:message key = "anoNac" bundle="${messages}"/></b></label>
                    <input id="yearEstudiante" name="year" type="number" min="1900" max="2099" step="1" value="2000" />
                    <p><fmt:message key = "privacidad" bundle="${messages}"/> <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

                    <div class="clearfix">
                        <button type="button" onclick="document.getElementById('signUpEstudiante').style.display='none'"
                            class="cancelbtn"><fmt:message key = "cancelar" bundle="${messages}"/></button>
                        <button type="submit" class="cancelbtn2" ><fmt:message key = "doTestLevel" bundle="${messages}"/></button>
                    </div>
                </div>
            </form>
        </div>

        <!-- Sign in Profesor-->

        <div id="signUpProfesor" class="modal">
            <span onclick="document.getElementById('signUpProfesor').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <form class="modal-content" enctype="multipart/form-data" action="<%=ControllerPaths.PROFESOR_REGISTRO%>" method="post">
            <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SIGNIN%>">
                <div class="container">
                    <h1><fmt:message key = "soyProfesor" bundle="${messages}"/></h1>
                    <p><fmt:message key = "rellenadoForm" bundle="${messages}"/></p>
                    <hr>
                    <label for="<%=ParameterNames.EMAIL%>"><b><fmt:message key = "email" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                    <label for="<%=ParameterNames.NOMBRE%>"><b><fmt:message key = "nombre" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Nombre" name="<%=ParameterNames.NOMBRE%>" required>

                    <label for="<%=ParameterNames.APELLIDO1%>"><b><fmt:message key = "apellido1" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Primer Apellido" name="<%=ParameterNames.APELLIDO1%>" required>

                    <label for="<%=ParameterNames.APELLIDO2%>"><b><fmt:message key = "apellido2" bundle="${messages}"/></b></label>
                    <input type="text" placeholder="Segundo Apellido" name="<%=ParameterNames.APELLIDO2%>">

                    <label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswd" bundle="${messages}"/></b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>

                    <label for="<%=ParameterNames.PSSWD_REPEAT%>"><b><fmt:message key = "psswdRepeat" bundle="${messages}"/></b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PSSWD_REPEAT%>" required>

                    <label for="<%=ParameterNames.GENERO%>"><b><fmt:message key = "genero" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.GENERO%>" id="generoProfesor">
                        <option value="H"><fmt:message key = "hombre" bundle="${messages}"/></option>
                        <option value="M"><fmt:message key = "mujer" bundle="${messages}"/></option>
                        <option value="O"><fmt:message key = "otro" bundle="${messages}"/></option>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PAIS%>"><b><fmt:message key = "pais" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.PAIS%>" id="paisProfesor">
                         <%
					for (Pais p: paises) {
						%>
						<option value="<%=p.getIdPais()%>"><%=p.getNombrePais()%></option>
						<%
					}				
				%>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.YEAR%>"><b><fmt:message key = "anoNac" bundle="${messages}"/></b></label>
                    <input id="yearProfesor" name="<%=ParameterNames.YEAR%>" type="number" min="1900" max="2099" step="1" value="2000" />
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PRECIO%>"><b><fmt:message key = "precio" bundle="${messages}"/></b></label>
                    <input id="<%=ParameterNames.PRECIO%>" name="precio" type="number" min="0" step="0.5" value="5" />
                    <br>
                    <br>
                    <label for="<%=ParameterNames.IDIOMA%>"><b><fmt:message key = "idioma" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.IDIOMA%>" id="idiomaProfesor">
                        <%
					for (Idioma i: idiomas) {
						%>
						<option value="<%=i.getIdIdioma()%>"><%=i.getIdioma()%></option>
						<%
					}				
				%>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.NIVEL_INGLES%>"><b>N<fmt:message key = "nivelMin" bundle="${messages}"/></b></label>
                    <select name="<%=ParameterNames.NIVEL_INGLES%>" id="idiomaEstudianteClase">
                        <%
					for (NivelIngles i: niveles) {
						%>
						<option value="<%=i.getIdNivel()%>"><%=i.getNivel()%></option>
						<%
					}				
				%>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.DESCRIPCION%>"><b><fmt:message key = "descripcion" bundle="${messages}"/></b></label>
                    <textarea id="<%=ParameterNames.DESCRIPCION%>" name="<%=ParameterNames.DESCRIPCION%>" rows="4" cols="50" maxlength="255" placeholder="Breve Descripción"></textarea>
					<label for="<%=ParameterNames.UP_FILE%>"><b><fmt:message key = "documento" bundle="${messages}"/></b></label>
					<input type="file" name="<%=ParameterNames.UP_FILE%>">
                    <p><fmt:message key = "privacidad" bundle="${messages}"/> <a href="#" style="color:dodgerblue">Terms & Privacy</a>.
                    </p>

                    <div class="clearfix">
                        <button type="button" onclick="document.getElementById('signUpProfesor').style.display='none'"
                            class="cancelbtn"><fmt:message key = "cancelar" bundle="${messages}"/></button>
                        <button type="submit" class="cancelbtn2" style="background-color: #38761d"><fmt:message key = "usuario.registrar" bundle="${messages}"/></button>
                    </div>
                </div>
            </form>
        </div>

        
        <!-- Login (Estudiante y profesor) -->

        <div id="login" class="modal">
            <span onclick="document.getElementById('login').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <button class="tablink" onclick="openPage('estudiante', this, 'white')"><fmt:message key = "estudiante" bundle="${messages}"/></button>
            <button class="tablink" onclick="openPage('profesor', this, 'white')"><fmt:message key = "profesor" bundle="${messages}"/></button>
            
            <div id="estudiante" class="tabcontent">



                <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
                    <div class="container">

                        <h1><fmt:message key = "login" bundle="${messages}"/></h1>
                        <hr>
                        <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
                        <label for="<%=ParameterNames.EMAIL%>"><b><fmt:message key = "email" bundle="${messages}"/></b></label>
                        <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                        <label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswd" bundle="${messages}"/></b></label>
                        <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>
						<%Map<String, String> valores = new HashMap<String, String>(); 
							valores.put(ParameterNames.ACTION, Actions.GO_RECOVERY);
						%>
						<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>">Recuperar Cuenta</a>
                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('login').style.display='none'"
                                class="cancelbtn"><fmt:message key = "cancelar" bundle="${messages}"/></button>
                            <button type="submit" class="cancelbtn2" style="background-color: #38761d"><fmt:message key = "login" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
            </div>

            <div id="profesor" class="tabcontent">
                <form class="modal-content" action="<%=ControllerPaths.PROFESOR%>">
                    <div class="container">

                        <h1><fmt:message key = "login" bundle="${messages}"/></h1>
                        <hr>
                        <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
                        <label for="<%=ParameterNames.EMAIL%>"><b><fmt:message key = "email" bundle="${messages}"/></b></label>
                        <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                        <label for="<%=ParameterNames.PASSWORD%>"><b><fmt:message key = "psswd" bundle="${messages}"/></b></label>
                        <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>
						<a href="<%=ParameterUtils.URLBuilder(ControllerPaths.PROFESOR, valores)%>">Recuperar Cuenta</a>
                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('login').style.display='none'"
                                class="cancelbtn"><fmt:message key = "cancelar" bundle="${messages}"/></button>
                            <button type="submit" class="cancelbtn2" style="background-color: #38761d"><fmt:message key = "login" bundle="${messages}"/></button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</body>

</html>