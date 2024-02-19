<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.TrabajoDTO" %>
<%@ page import="Dtos.IncidenciaDTO" %>
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
    <title>Mostrar Trabajos</title>
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
    if (accesoSesion.equals("1")) {
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
                <span class="text-white">SystemRevive</span>
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
                <h2 class="text-white">Trabajos De <%=user.getNombreUsuario() %></h2>
            </div>
        </form>
    </div>
	<%
	
	List <IncidenciaDTO> incidencias=acciones.SeleccionarTodasIncidencias();
	
	List <IncidenciaDTO> incidenciasPendientes=new ArrayList <IncidenciaDTO> ();
	
	for (int i = 0; i < incidencias.size(); i++) {
	    if (incidencias.get(i).getEmpleado() == null) {
	    	
	    } else if (incidencias.get(i).getFecha_fin() == null && incidencias.get(i).getEmpleado().getIdUsuario() == user.getIdUsuario()) {
	        incidenciasPendientes.add(incidencias.get(i));
	    }
	}
	
	//Cogemos todos los trabajos y los filtramos por los de la incidencia
	List <TrabajoDTO> trabajos = acciones.SeleccionarTodosTrabajos();
	for(IncidenciaDTO in:incidenciasPendientes){
		in.setTrabajosConIncidencias(new ArrayList <TrabajoDTO>());
		for(TrabajoDTO tra:trabajos){
			if(tra.getIncidencia().getId_incidencia()==in.getId_incidencia()){
				in.getTrabajosConIncidencias().add(tra);
			}
		}
	}
	%>
	<div class="container-fluid">

    <div class="row justify-content-center">
        <div class="col-12 col-md-8 table-container">
            
			<div class="row">
			
			<% 			
			for(IncidenciaDTO incidencia : incidenciasPendientes) { %>
			 <div class="col-12"></div>
				    <div class="col-12">
				 		<br>
				       <table class="table">
                <caption class="text-center text-light">Incidencia 
                			<%if(incidencia.getDescripcion_tecnica()==null||incidencia.getDescripcion_tecnica().equals("")){ %>
                           		No puso Descripcion Tecnica
                            <%}else{ %>
                            <%=incidencia.getDescripcion_tecnica() %>
                            <%} %>
                            </caption>
                <thead>
                    <tr>
                        <th>Descripcion Del Trabajo</th>
                        <th>Horas Del Trabajo</th>
                        <th>Estado</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% 
                	if(incidencia.getTrabajosConIncidencias()==null||incidencia.getTrabajosConIncidencias().size()==0){
                		%>
                		<td colspan="4" class="text-center" >No hay Ningun Trabajo Asignado</td>
                		<%
                	}
                	else{
                		for(TrabajoDTO t:incidencia.getTrabajosConIncidencias()){
                			String estado="En Proceso";
                			if(t.isEstado()==true){
                				estado="Termindado";
                			}
                		%>
                		                        <tr>
                            <td class="text-center"><%=t.getDescripcion() %></td>
                            <td class="text-center"><%=t.getHoras() %></td>
                            <td class="text-center"><%=estado %></td>
                           	<td class="text-center">
                           	<form action="../ControladorFinalizarTrabajo" method="post" id="formulario">
					        <input type="hidden" name="idT" value="<%=t.getId_trabajo() %>">
					        <button  class="btn btn-success text-center" type="submit">Finalizar Trabajo</button>
					      	</form>                    	                          	
                           	</td>
                        </tr>
                		<%
                		}
                	}
                    %>

                </tbody>
            </table>
            		<a href="crearTrabajo.jsp?idI=<%=incidencia.getId_incidencia() %>">
					      <button  class="btn btn-success text-center" type="button">Crear Trabajo</button>
					  </a>
				    </div>
				     <div class="col-12"></div>
				<% } %>
			</div>        
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