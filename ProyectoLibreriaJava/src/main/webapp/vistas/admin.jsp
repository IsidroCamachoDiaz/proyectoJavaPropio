<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <!-- basic -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- mobile metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <!-- Titulo Pagina -->
    <title>Biblioteca</title>
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
    <!-- CSS -->
   <link rel="stylesheet" href="common/css/styleBienvenida.css">
</head>
<!-- body -->

<body class="main-layout">
    <!-- header -->
    <header>
      <!-- header inner -->
      <div class="header">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-4 col-sm-4">
              <div class="logo">
                <a href="home.jsp"><img src="common/img/logo.webp"  alt="logito" width="175" height="100"/></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
    <!-- end header inner -->
    <!-- end header -->
    <!-- banner -->
    <section class="banner_main">
      <div
        id="banner1"
        class="carousel slide banner_slide"
        data-ride="carousel"
      >
        <div class="carousel-inner">
          <div class="carousel-item active">
            <div class="container-fluid">
              <div class="carousel-caption">
                <div class="row">
                  <div class="col">
                    <table class="table tablaUsuarios" style="box-shadow: 0px 0px 10px 2px rgba(15, 152, 248, 0.3); margin-top: 50px;">
                      <thead>
                        <tr>
                          <th scope="col">Id</th>
                          <th scope="col">Nombre</th>
                          <th scope="col">Apellidos</th>
                          <th scope="col">Email</th>
                          <th scope="col"></th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row">1</th>
                          <td>Francisco</td>
                          <td>Gallego Dorado</td>
                          <td>noemail@.no</td>
                          <td>
                            <button
                            type="button"
                            class="btn btn-outline-danger"
                          >
                            Eliminar
                          </button>
                            <button
                              type="button"
                              class="btn btn-outline-primary editar"
                              onclick="alternarFormulario(1)"
                            >
                             Editar
                            </button>
                          </td>
                        </tr>
                      </tbody>
                      
                    </table>
                    <a
                    type="button"
                    style="float: left;"
                    class="btn btn-outline-primary"
                    href="home.jsp"
                  >
                    Volver
                    </a>
                    <!--En este div aparecen los datos del usuario-->
                    <div >
                        <form>
                            <table style="margin-left: 15%; display: none; " id="formularioContainer" >
                                <tr>
                                    <td><label for="nombre">Nombre:</label></td>
                                    <td><input type="text" id="nombre" /></td>
                                    <td><label for="apellido">Apellidos:</label></td>
                                    <td><input type="text" id="apellido" /></td>
                                </tr>
                                <tr>
                                    <td><label for="email">Email:</label></td>
                                    <td><input type="text" id="email" /></td>
                                    <td><label for="telefono">Telefono:</label></td>
                                    <td><input type="text" id="telefono" /></td>
                                </tr>
                                <tr>
                                    
                                </tr>
                                <tr>
                                    <td colspan="2" ><input type="submit" value="Guardar" /></td>
                                </tr>
                            </table>
                        </form>
                        
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- end banner -->
    <script>
      var formularioContainer = document.getElementById("formularioContainer");
      var botones = document.querySelectorAll("table button.editar");
      var nuevaFila; // Guardar una referencia a la nueva fila
      function alternarFormulario(id) {
        
        var fila = document.querySelector("tbody tr:nth-of-type(" + id + ")");
        var celdaAcciones = fila.querySelector("td:last-child");
        var boton = celdaAcciones.querySelector("button.editar");
        
        // Ocultar cualquier formulario visible y cambiar el texto de todos los botones a "Mostrar Formulario"
        console.log("Valor de boton:", document.querySelector("tbody tr:nth-of-type(" + id + ")"));
        if (boton.innerText === "Cerrar") {
          boton.innerText = "Editar";
        
          formularioContainer.style.display = "none";
          if (nuevaFila) {
            // Si existe una nueva fila, eliminarla
            nuevaFila.remove();
            nuevaFila = null; // Restablecer la referencia a la nueva fila
          }
          
        } else {
          botones.forEach(function (boton) {
            if (boton.innerText === "Cerrar") {
          boton.innerText = "Editar";
        
          formularioContainer.style.display = "none";
          if (nuevaFila) {
            // Si existe una nueva fila, eliminarla
            nuevaFila.remove();
            nuevaFila = null; // Restablecer la referencia a la nueva fila
          }}
          });
          console.log(boton.innerText)
          formularioContainer.style.display = "none";

          // Crear una nueva fila y añadir el formulario a esta fila
          nuevaFila = document.createElement("tr");
          var nuevaCelda = document.createElement("td");
          nuevaCelda.colSpan = 5; // Asegurarse de que la nueva celda se extienda a través de todas las columnas
          nuevaCelda.appendChild(formularioContainer);
          nuevaFila.appendChild(nuevaCelda);

          // Insertar la nueva fila debajo de la fila actual
          fila.parentNode.insertBefore(nuevaFila, fila.nextSibling);

          formularioContainer.style.display = "block";
          boton.innerText = "Cerrar";
        }
       
      }
     
    </script>
  </body>

</html>