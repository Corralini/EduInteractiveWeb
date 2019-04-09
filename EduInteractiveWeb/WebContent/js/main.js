
var modal = document.getElementById('signUpEstudiante');
var modal2 = document.getElementById('signUpProfesor');
var ids = new Array("home", "profesores", "estudiante");

window.onclick = function (event) {
  if (event.target == modal || event.target == modal2) {
    modal.style.display = "none";
    modal2.style.display = "none";
  }
}

function openPage(pageName, elmnt, color) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablink");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].style.backgroundColor = "";
  }
  document.getElementById(pageName).style.display = "block";
  elmnt.style.backgroundColor = color;
}

function getFocus(id) {
  var elemento = document.getElementById(id);
  removeFocus();
  elemento.setAttribute("class", "active");
}

function removeFocus() {

  var elemento;
  var id;

  for (var i = 0; i < ids.length; i++) {
    id = ids[i];
    elemento = document.getElementById(id);
    if(elemento != null) elemento.removeAttribute("class");

  }
}

function openCity(evt, cityName) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(cityName).style.display = "block";
  evt.currentTarget.className += " active";
}
console.log(document.getElementById("defaultOpen"));
// Get the element with id="defaultOpen" and click on it
document.getElementById("defaultOpen").click();