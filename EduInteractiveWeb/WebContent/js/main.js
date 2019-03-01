
var modal = document.getElementById('signUpEstudiante');
var modal2 = document.getElementById('signUpProfesor');

window.onclick = function(event) {
  if (event.target == modal || event.target == modal2 ) {
    modal.style.display = "none";
    modal2.style.display = "none";
  }
}

function openPage(pageName,elmnt,color) {
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
  