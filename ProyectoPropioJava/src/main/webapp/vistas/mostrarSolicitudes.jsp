<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.SolicitudDTO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="Utilidades.Alerta" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="Utilidades.Escritura" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="icon" href="img/logoPNG.png" type="image/jpg">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fontawesome/css/all.min.css">
    <link rel="stylesheet" href="css/templatemo-style.css">
	<link rel="stylesheet" href="css/tabla.css">
	
</head>
<body>
<%
String accesoSesion = "0";
try {
    accesoSesion = session.getAttribute("acceso").toString();
    if (accesoSesion.equals("2") || accesoSesion.equals("3")) {
    	Alerta.Alerta(request, "No puede acceder a este lugar de la web", "warning");
    	Escritura.EscribirFichero("Un usuario o empleado intento entrar a la administracion");
    	response.sendRedirect("home.jsp");
    	return;
    }

} catch (Exception e) {
    Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
    response.sendRedirect("../index.jsp");
    return;
}
Escritura.EscribirFichero("Se accedio al panel de mostrar las solicitudes");
System.out.println(accesoSesion);
implementacionCRUD acciones=new implementacionCRUD();
//Si modifico el usuario que se actualice
UsuarioDTO user =acciones.SeleccionarUsuario("Select/"+session.getAttribute("usuario"));
//Convierto la imagen
String base64Image = Base64.getEncoder().encodeToString(user.getFoto());

//Coloca la cadena Base64 en el alcance de la solicitud
request.setAttribute("base64Image", base64Image);
session.setAttribute("imagen", base64Image);

%>
	<!-- Lógica de JavaScript para mostrar la alerta -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11/dist/sweetalert2.all.min.js"></script>
<script>
//Obtiene los atributos desde la sesión
var mensaje = '<%= session.getAttribute("mensajeAlerta") %>';
var tipo = '<%= session.getAttribute("tipoAlerta") %>';
    document.addEventListener('DOMContentLoaded', function () {
        // Lógica para mostrar la alerta con SweetAlert2
        if (mensaje !== null && tipo !== null && mensaje !== 'null' && tipo !== 'null') {
        	console.log('Mensaje:', mensaje, 'Tipo:', tipo);
            Swal.fire({
                icon: tipo,
                title: mensaje,
                confirmButtonText: 'OK'
            });
            <%session.setAttribute("mensajeAlerta","null");
            session.setAttribute("tipoAlerta","null"); %>
        }
    });
</script>
    <!-- Page Loader -->
    <div id="loader-wrapper">
        <div id="loader"></div>

        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>

    </div>
    <nav class="navbar navbar-expand-lg">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img class="logo" src="img/logo.png" alt="Imagen de Usuario">
                <span class="text-white">Menu</span>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav ml-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link nav-link-1" aria-current="page" href="home.jsp">Menu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link nav-link-2" href="modificarPerfil.jsp">
                            <div class="user-info-container d-flex align-items-center">
                                <img class="rounded-circle user-avatar" src="data:image/jpeg;base64,${base64Image}" alt="Imagen de Usuario">
                                <span class="ml-2 text-white">Perfil</span>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="tm-hero d-flex justify-content-center align-items-center" data-parallax="scroll" data-image-src="img/hero.jpg">
        <form class="d-flex tm-search-form">
            <div class="user-info-container2 d-flex align-items-center">
                <h2 class="text-white">Solicitudes De <%=user.getNombreUsuario() %></h2>
            </div>
        </form>
    </div>
	<%
	
	List <SolicitudDTO> solicitudes=acciones.SeleccionarTodasSolicitudes();
	List <SolicitudDTO> pendientes=new ArrayList <SolicitudDTO> ();
	List <SolicitudDTO> finalizados=new ArrayList <SolicitudDTO> ();
	for(int i=0;i<solicitudes.size();i++){
		if(solicitudes.get(i).getCliente().getIdUsuario()!=user.getIdUsuario()){
			solicitudes.remove(i);
		}
		else{
			if(solicitudes.get(i).isEstado()){
				finalizados.add(solicitudes.get(i));
			}
			else{
				pendientes.add(solicitudes.get(i));
			}
		}
	}
	
	%>
	<div class="container-fluid">
    <div class="row">
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item">
                        <a class="nav-link" onclick="mostrarTabla('tabla1')">Solicitudes Pendientes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" onclick="mostrarTabla('tabla2')">Solicitudes Finalizadas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="crearSolicitud.jsp" >Crear Solicitud</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>

    <div class="row justify-content-center">
        <div class="col-12 col-md-8 table-container">
            <table id="tabla1" class="table">
                <caption class="text-center text-light">Solicitudes Pendientes</caption>
                <thead>
                    <tr>
                        <th>Descripcion</th>
                        <th>Fecha De Solicitud</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(SolicitudDTO so:pendientes){ 
                  	  SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                      String fechaFormateada = sdf.format(so.getFechaSolicitud().getTime());
                    %>
                        <tr>
                            <td class="text-center"><%=so.getDescripcion() %></td>
                            <td class="text-center"><%=fechaFormateada %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <table id="tabla2" class="table" style="display: none;">
                <caption class="text-center text-light">Solicitudes Finalizadas</caption>
                <thead>
                    <tr>
                        <th>Descripcion</th>
                        <th>Fecha De Solicitud</th>
                    </tr>
                </thead>
                <tbody>
                    <% for(SolicitudDTO so:finalizados){ 
                    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String fechaFormateada = sdf.format(so.getFechaSolicitud().getTime());
                    %>
                        <tr>
                            <td class="text-center"><%=so.getDescripcion() %></td>
                            <td class="text-center"><%=fechaFormateada %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script>
	function mostrarTabla(tablaId) {
	 // Oculta todas las tablas
	 document.querySelectorAll('table').forEach(function(tabla) {
	  tabla.style.display = 'none';
	   });

	 // Muestra la tabla seleccionada
	  document.getElementById(tablaId).style.display = 'table';
	    }
</script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
<script>
  function confirmarBorrado(idUsuario) {
	  console.log('formBorrarUsuario'+idUsuario);
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, borrar usuario'
    }).then((result) => {
      if (result.isConfirmed) {
        // Si el usuario confirma, entonces enviamos el formulario
        document.getElementById('formBorrarUsuario'+idUsuario).submit();
      }
    });
  }
</script>
    
    <script src="js/plugins.js"></script>
    <script>
        $(window).on("load", function() {
            $('body').addClass('loaded');
        });
    </script>
</body>
</html>