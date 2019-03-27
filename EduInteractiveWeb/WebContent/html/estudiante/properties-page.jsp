<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<div class="tab">
            <button class="tablinks" onclick="openCity(event, 'Preferences')" id="defaultOpen">Preferences</button>
            <button class="tablinks" onclick="openCity(event, 'Privacy')">Privacy</button>
          </div>
          
          <div id="Preferences" class="tabcontent">
            <h3>Preferences</h3>
            <p>Cambiar idioma de la p�gina</p>
            <form action="<%=ControllerPaths.ESTUDIANTE%>" method="post"> 
            	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHANGE_LOCALE%>">
                <select name="<%=ParameterNames.LOCALE%>">
                    <option value="en">Ingl�s</option>
                    <option value="es">Espa�ol</option>
                </select>
                <button type="submit" class="aceptbtn">Confirmar</button>
            </form>
          </div>
          
          <div id="Privacy" class="tabcontent">
            <h3>Privacy</h3>
            <form action="<%=ControllerPaths.ESTUDIANTE%>" method="post">
            	<input type="hidden" name="<%=ParameterNames.ACTION%>" value="<%=Actions.CHANGE_PSSWD%>">
                <label for="<%=ParameterNames.PASSWORD%>">Contrase�a actual</label>
                <input type="password" name="<%=ParameterNames.PASSWORD%>"><br><br>
                <label for="<%=ParameterNames.NEW_PASSWD%>">Nueva contrase�a</label>
                <input type="password" name="<%=ParameterNames.NEW_PASSWD%>"><br><br>
                <label for="<%=ParameterNames.PSSWD_REPEAT%>">Repite la nueva Contrase�a</label>
                <input type="password" name="<%=ParameterNames.PSSWD_REPEAT%>">
                <button type="submit" class="aceptbtn">Cambiar</button>
            </form>
            
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>