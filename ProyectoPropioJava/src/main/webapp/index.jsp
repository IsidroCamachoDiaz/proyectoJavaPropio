<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!-- Coding by CodingNepal | www.codingnepalweb.com-->
<html lang="en" dir="ltr">
  <head>
    <meta charset="UTF-8">
    <title> Login and Registration Form in HTML & CSS | CodingLab </title>
    <link rel="stylesheet" href="style.css">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   </head>
<body>
	<!-- L�gica de JavaScript para mostrar la alerta -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
<script>
//Obtiene los atributos desde la sesi�n
var mensaje = '<%= session.getAttribute("mensajeAlerta") %>';
var tipo = '<%= session.getAttribute("tipoAlerta") %>';
    document.addEventListener('DOMContentLoaded', function () {
        // L�gica para mostrar la alerta con SweetAlert2
        if (mensaje !== null && tipo !== null && mensaje !== 'null' && tipo !== 'null') {
        	console.log('Mensaje:', mensaje, 'Tipo:', tipo);
            Swal.fire({
                icon: tipo,
                title: mensaje,
                confirmButtonText: 'OK'
            });
        }
    });
</script>
  <div class="container">
    <input type="checkbox" id="flip">
    <div class="cover">
      <div class="front">
        <img src="prueba2.jpg" alt="">
        <div class="text">
          <span class="text-1">Bienvenido</span>
          <span class="text-2">De Nuevo</span>
        </div>
      </div>
      <div class="back">
        <!--<img class="backImg" src="images/backImg.jpg" alt="">-->
        <div class="text">
          <span class="text-1">Complete miles of journey <br> with one step</span>
          <span class="text-2">Let's get started</span>
        </div>
      </div>
    </div>
    <div class="forms">
        <div class="form-content">
          <div class="login-form">
            <div class="title">Iniciar Sesion</div>
          <form action="#">
            <div class="input-boxes">
              <div class="input-box">
                <i class="fas fa-envelope"></i>
                <input type="text" placeholder="Introduzca Su Email" required>
              </div>
              <div class="input-box">
                <i class="fas fa-lock"></i>
                <input type="password" placeholder="Introduzca Su Contrase�a" required>
              </div>
              <div class="text"><a href="Contrasenia.html">Olvidaste La Contrase�a?</a></div>
              <div class="button input-box">
                <input type="submit" value="Iniciar Sesion">
              </div>
              <div class="text sign-up-text">No Tienes Cuenta? <label for="flip">Resgistrate Ahora</label></div>
            </div>
        </form>
      </div>
        <div class="signup-form">
          <div class="title">Registrarse</div>
        <form action="./ControladorRegistro" method="post" enctype="multipart/form-data">
            <div class="input-boxes">
              <div class="input-box">
                <i class="fas fa-user"></i>
                <input type="text" placeholder="Introduzca Un Nombre" required name="nombreUsuario">
              </div>
              <div class="input-box">
                <i class="fas fa-envelope"></i>
                <input type="text" placeholder="Introduzca Un Email" required name="correoUsuario">
              </div>
              <div class="input-box">
                <i class="fas fa-phone"></i>
                <input type="text" placeholder="Introduzca Un Numero De Telefono" required name="telefonoUsuario">
              </div>
              <div class="input-box">
                <i class="fas fa-lock"></i>
                <input type="password" placeholder="Introduzca Una Contrase�a" required name="contraseniaUsuario">
              </div>
              <div class="input-box">
                <i class="fas fa-images"></i>
                <input type="file" placeholder="Introduzca Una Contrase�a" required name="imagenUsuario">
              </div>
              <div class="button input-box">
                <input type="submit" value="Registrarse">
              </div>
              <div class="text sign-up-text">Tienes Cuenta? <label for="flip">Iniciar Sesion Ahora</label></div>
            </div>
      </form>
    </div>
    </div>
    </div>
  </div>
</body>
</html>