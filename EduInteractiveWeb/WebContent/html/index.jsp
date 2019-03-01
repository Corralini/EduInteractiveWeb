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

<% 
	List<Pais> paises = (List<Pais>) request.getAttribute(AttributeNames.PAISES);
	List<Idioma> idiomas = (List<Idioma>) request.getAttribute(AttributeNames.IDIOMA);
	List<NivelIngles> niveles = (List<NivelIngles>) request.getAttribute(AttributeNames.NIVELES);
%>

<body>
    <div id="header">
        <h1 id="nombre">EduInteractive</h1>
        <div id="logIn">
            <button onclick="document.getElementById('login').style.display='block'" type="button"
                class="button">Autentícate</button>
        </div>
    </div>
    <div id="body" style="text-align: center;">
        <h1>Bienvenido a EduInteractive</h1>

        <div id="registroHome">
            <h3 style="width: 200px;position: relative;left: 43%;">Regístrate ahora!</h3>
            <button onclick="document.getElementById('signUpProfesor').style.display='block'" type="button" onclick=""
                class="button" style="width: 250px; text-align: center;">Soy Profesor</button>
            <button onclick="document.getElementById('signUpEstudiante').style.display='block'" type="button" onclick=""
                class="button" style="background-color:#88a394; width: 250px; text-align: center;">Soy
                Estudiante</button>
        </div>
        <!-- Sign in Estudiante-->

        <div id="signUpEstudiante" class="modal">
            <span onclick="document.getElementById('signUpEstudiante').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
            <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PRESIGNIN%>">
                <div class="container">
                    <h1>Soy Estudiante</h1>
                    <p>Por favor, rellene este formulario para crear una cuenta</p>
                    <hr>
                    <label for="<%=ParameterNames.EMAIL%>"><b>Email</b></label>
                    <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                    <label for="<%=ParameterNames.NOMBRE%>"><b>Nombre</b></label>
                    <input type="text" placeholder="Nombre" name="<%=ParameterNames.NOMBRE%>" required>

                    <label for="<%=ParameterNames.APELLIDO1%>"><b>Primer Apellido</b></label>
                    <input type="text" placeholder="Primer Apellido" name="<%=ParameterNames.APELLIDO1%>" required>

                    <label for="<%=ParameterNames.APELLIDO2%>"><b>Segundo Apellido</b></label>
                    <input type="text" placeholder="Segundo Apellido" name="<%=ParameterNames.APELLIDO2%>">

                    <label for="<%=ParameterNames.PASSWORD%>"><b>Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>

                    <label for="<%=ParameterNames.PSSWD_REPEAT%>"><b>Vuelva a introducir la Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PSSWD_REPEAT%>" required>

                    <label for="<%=ParameterNames.GENERO%>"><b>Genero</b></label>
                    <select name="<%=ParameterNames.GENERO%>" id="generoEstudiante">
                        <option value="H">Hombre</option>
                        <option value="M">Mujer</option>
                        <option value="O">Otro</option>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PAIS%>"><b>Pais</b></label>
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
                    <label for="<%=ParameterNames.YEAR%>"><b>Año nacimiento</b></label>
                    <input id="yearEstudiante" name="year" type="number" min="1900" max="2099" step="1" value="2000" />
                    <p>Creando una cuenta aceptas nuestros <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>

                    <div class="clearfix">
                        <button type="button" onclick="document.getElementById('signUpEstudiante').style.display='none'"
                            class="cancelbtn">Cancel</button>
                        <button type="submit" class="cancelbtn" style="background-color: #38761d">Hacer test de nivel</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- Sign in Profesor-->

        <div id="signUpProfesor" class="modal">
            <span onclick="document.getElementById('signUpProfesor').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>">
            <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.PRESIGNIN%>" method="post">
                <div class="container">
                    <h1>Soy Profesor</h1>
                    <p>Por favor, rellene este formulario para crear una cuenta</p>
                    <hr>
                    <label for="<%=ParameterNames.EMAIL%>"><b>Email</b></label>
                    <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                    <label for="<%=ParameterNames.NOMBRE%>"><b>Nombre</b></label>
                    <input type="text" placeholder="Nombre" name="<%=ParameterNames.NOMBRE%>" required>

                    <label for="<%=ParameterNames.APELLIDO1%>"><b>Primer Apellido</b></label>
                    <input type="text" placeholder="Primer Apellido" name="<%=ParameterNames.APELLIDO1%>" required>

                    <label for="<%=ParameterNames.APELLIDO2%>"><b>Segundo Apellido</b></label>
                    <input type="text" placeholder="Segundo Apellido" name="<%=ParameterNames.APELLIDO2%>">

                    <label for="<%=ParameterNames.PASSWORD%>"><b>Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>

                    <label for="<%=ParameterNames.PSSWD_REPEAT%>"><b>Vuelva a introducir la Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PSSWD_REPEAT%>" required>

                    <label for="<%=ParameterNames.GENERO%>"><b>Genero</b></label>
                    <select name="<%=ParameterNames.GENERO%>" id="generoProfesor">
                        <option value="H">Hombre</option>
                        <option value="M">Mujer</option>
                        <option value="O">Otro</option>
                    </select>
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PAIS%>"><b>Pais</b></label>
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
                    <label for="<%=ParameterNames.YEAR%>"><b>Año nacimiento</b></label>
                    <input id="yearProfesor" name="<%=ParameterNames.YEAR%>" type="number" min="1900" max="2099" step="1" value="2000" />
                    <br>
                    <br>
                    <label for="<%=ParameterNames.PRECIO%>"><b>Precio</b></label>
                    <input id="<%=ParameterNames.PRECIO%>" name="precio" type="number" min="0" step="0.5" value="5" />
                    <br>
                    <br>
                    <label for="<%=ParameterNames.IDIOMA%>"><b>Idioma</b></label>
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
                    <label for="<%=ParameterNames.NIVEL_INGLES%>"><b>Nivel mínimo</b></label>
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
                    <label for="<%=ParameterNames.DESCRIPCION%>"><b>Descripción</b></label>
                    <textarea id="<%=ParameterNames.DESCRIPCION%>" name="descripcion" rows="4" cols="50" maxlength="255" placeholder="Breve Descripción"></textarea>

                    <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.
                    </p>

                    <div class="clearfix">
                        <button type="button" onclick="document.getElementById('signUpProfesor').style.display='none'"
                            class="cancelbtn">Cancel</button>
                        <button type="submit" class="cancelbtn" style="background-color: #38761d">Sign Up</button>
                    </div>
                </div>
            </form>
        </div>

        
        <!-- Login (Estudiante y profesor) -->

        <div id="login" class="modal">
            <span onclick="document.getElementById('login').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <button class="tablink" onclick="openPage('estudiante', this, 'white')">Estudiante</button>
            <button class="tablink" onclick="openPage('profesor', this, 'white')">Profesor</button>
            
            <div id="estudiante" class="tabcontent">



                <form class="modal-content" action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
                    <div class="container">

                        <h1>LogIn</h1>
                        <hr>
                        <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
                        <label for="<%=ParameterNames.EMAIL%>"><b>Email</b></label>
                        <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                        <label for="<%=ParameterNames.PASSWORD%>"><b>Contraseña</b></label>
                        <input type="<%=ParameterNames.PASSWORD%>" placeholder="Contraseña" name="pswd" required>

                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('login').style.display='none'"
                                class="cancelbtn">Cancel</button>
                            <button type="submit" class="cancelbtn" style="background-color: #38761d">LogIn</button>
                        </div>
                    </div>
                </form>
            </div>

            <div id="profesor" class="tabcontent">
                <form class="modal-content" action="<%=ControllerPaths.PROFESOR%>">
                    <div class="container">

                        <h1>LogIn</h1>
                        <hr>
                        <input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.LOGIN%>">
                        <label for="<%=ParameterNames.EMAIL%>"><b>Email</b></label>
                        <input type="text" placeholder="alguien@ejemplo.com" name="<%=ParameterNames.EMAIL%>" required>

                        <label for="<%=ParameterNames.PASSWORD%>"><b>Contraseña</b></label>
                        <input type="password" placeholder="Contraseña" name="<%=ParameterNames.PASSWORD%>" required>

                        <div class="clearfix">
                            <button type="button" onclick="document.getElementById('login').style.display='none'"
                                class="cancelbtn">Cancel</button>
                            <button type="submit" class="cancelbtn" style="background-color: #38761d">LogIn</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

    </div>
</body>

</html>