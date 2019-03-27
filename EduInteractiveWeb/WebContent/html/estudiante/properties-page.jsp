<%@ include file="/html/estudiante/common/head.jsp"%>
<%@ include file="/html/estudiante/common/superior.jsp"%>
<div class="tab">
            <button class="tablinks" onclick="openCity(event, 'Preferences')" id="defaultOpen">Preferences</button>
            <button class="tablinks" onclick="openCity(event, 'Privacy')">Privacy</button>
          </div>
          
          <div id="Preferences" class="tabcontent">
            <h3>Preferences</h3>
            <p>Cambiar idioma de la página</p>
            <form>  
                <select name="idioma">
                    <option value="en">inglés</option>
                    <option value="es">Español</option>
                </select>
                <button type="submit" class="aceptbtn">Confirmar</button>
            </form>
          </div>
          
          <div id="Privacy" class="tabcontent">
            <h3>Privacy</h3>
            <form>
                <label for="currentPsswd">Contraseña actual</label>
                <input type="text" name="currentPsswd"><br><br>
                <label for="newPsswd">Nueva contraseña</label>
                <input type="text" name="newPsswd"><br><br>
                <label for="newPasswdRepeat">Repite la nueva Contraseña</label>
                <input type="text" name="newPasswdRepeat">
                <button type="submit" class="aceptbtn">Cambiar</button>
            </form>
            
    </div>
<%@ include file="/html/estudiante/common/footer.jsp"%>