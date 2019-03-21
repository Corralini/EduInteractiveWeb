<%@ page import="java.util.List" %>
<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<%
	List<Profesor> profesores = (List<Profesor>) request.getAttribute(AttributeNames.RESULTADOS);
	List<NivelIngles> niveles = (List<NivelIngles>) request.getAttribute(AttributeNames.NIVELES);
	List<Dia> dias = (List<Dia>) request.getAttribute(AttributeNames.DIAS);
%>
<div id="criteria">
        <form class="Criterios" action="<%=ControllerPaths.ESTUDIANTE%>">
        	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.SEARCH%>">
            <label for="<%=ParameterNames.NIVEL_INGLES%>"><b>Nivel</b></label>
            <select name="<%=ParameterNames.NIVEL_INGLES%>">
                <option value=" ">Cualquiera</option>
                <%
					for (NivelIngles i: niveles) {
						%>
						<option value="<%=i.getIdNivel()%>"><%=i.getNivel()%></option>
						<%
					}				
				%>
            </select>
            <label for="<%=ParameterNames.GENERO%>"><b>Genero</b></label>
            <select name="<%=ParameterNames.GENERO%>">
            	<option value=" ">Cualquiera</option>
            	<option value="H">Hombre</option>
            	<option value="M">Mujer</option>
            	<option value="O">Otro</option>
            </select>
            <label for="<%=ParameterNames.PUNTUACION%>"><b>Puntuación</b></label>
            <input type="text" min="0" max="10" name="<%=ParameterNames.PUNTUACION%>">
            <label for="<%=ParameterNames.PRECIO%>"><b>Precio Mínimo</b></label>
            <input type="number" name="<%=ParameterNames.PRECIO%>"><br>
            <label for="<%=ParameterNames.PRECIO_MAX%>"><b>Precio Máximo</b></label>
            <input type="number" name="<%=ParameterNames.PRECIO_MAX%>">
            <label for="<%=ParameterNames.DIA%>"><b>Dia</b></label>
            <select name="<%=ParameterNames.DIA%>">
                <option value=" ">Cualquiera</option>
                <%
					for (Dia d: dias) {
						%>
						<option value="<%=d.getIdDia()%>"><%=d.getDia()%></option>
						<%
					}				
				%>
            </select>
            <button id="submitCriteria" type="submit" class="aceptbtn">Buscar</button>
        </form>
        <hr id="separadorCriteria">
    </div>
    <!--Mostrar resultado-->
    <div id="resultados">
    	<%	
    		valores.clear();
    		valores.put(ParameterNames.ACTION, Actions.DETALLE_PROFESOR);
    		for(Profesor p: profesores) { 
    	    	valores.put(ParameterNames.ID_PROFESOR, p.getIdProfesor().toString());
    	%>
        <div class="resultado">
            <a href="<%=ParameterUtils.URLBuilder(ControllerPaths.ESTUDIANTE, valores)%>"><%=ParameterUtils.makeName(p.getNombre(), p.getApellido1(), p.getApellido2()) %></a>
            <p class="puntuacion"><%=p.getPuntuacion() %>/10</p>
            <p class="precio"><%=p.getPrecioSesion()%></p>
            <button id="dispo" class="aceptbtn">Disponibilidad</button>
        </div>
        <%
        } 
        %>
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>