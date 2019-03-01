<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <div id="signUpEstudiante" class="modal">
            <span onclick="document.getElementById('signUpEstudiante').style.display='none'" class="close"
                title="Close Modal">&times;</span>
            <form class="modal-content" action="/action_page.php">
                <div class="container">
                    <h1>Soy Estudiante</h1>
                    <p>Por favor, rellene este formulario para crear una cuenta</p>
                    <hr>
                    <label for="email"><b>Email</b></label>
                    <input type="text" placeholder="alguien@ejemplo.com" name="email" required>

                    <label for="nombre"><b>Nombre</b></label>
                    <input type="text" placeholder="Nombre" name="nombre" required>

                    <label for="apellido1"><b>Primer Apellido</b></label>
                    <input type="text" placeholder="Primer Apellido" name="apellido1" required>

                    <label for="apellido2"><b>Segundo Apellido</b></label>
                    <input type="text" placeholder="Segundo Apellido" name="apellido2">

                    <label for="pswd"><b>Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="pswd" required>

                    <label for="pswd-repeat"><b>Vuelva a introducir la Contraseña</b></label>
                    <input type="password" placeholder="Contraseña" name="pswd-repeat" required>

                    <label for="genero"><b>Genero</b></label>
                    <select name="genero" id="generoEstudiante">
                        <option value="H">Hombre</option>
                        <option value="M">Mujer</option>
                        <option value="O">Otro</option>
                    </select>
                    <br>
                    <br>
                    <label for="pais"><b>Pais</b></label>
                    <select name="pais" id="paisEstudiante">
                        <option value="es">España</option>
                        <option value="fr">Francia</option>
                    </select>
                    <br>
                    <br>
                    <label for="year"><b>Año nacimiento</b></label>
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