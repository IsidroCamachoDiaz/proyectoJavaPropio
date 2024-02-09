<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Dtos.SolicitudDTO" %>
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
    if (accesoSesion.equals("1")) {
    	Alerta.Alerta(request, "No puede acceder a este lugar de la web", "warning");
    	Escritura.EscribirFichero("Un usuario intento entrar a las incidencias");
    	response.sendRedirect("home.jsp");
    	return;
    }

} catch (Exception e) {
    Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
    response.sendRedirect("../index.jsp");
    return;
}
Escritura.EscribirFichero("Se accedio al panel de mostrar las incidencias");
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
                <h2 class="text-white">Incidencias</h2>
            </div>
        </form>
    </div>
	<%
	
	List <IncidenciaDTO> incidencias=acciones.SeleccionarTodasIncidencias();
	List <IncidenciaDTO> mias=new ArrayList <IncidenciaDTO> ();
	List <IncidenciaDTO> sinAsignar=new ArrayList <IncidenciaDTO> ();
	List <IncidenciaDTO> finalizadas=new ArrayList <IncidenciaDTO> ();
	
	for(IncidenciaDTO in:incidencias ){
		if(in.getEmpleado()==null){
			sinAsignar.add(in);
		}
		else if(in.isEstado()==true){
			finalizadas.add(in);
		}
		else if(in.getEmpleado().getIdUsuario()==user.getIdUsuario()){
			mias.add(in);
		}
	}
	
	%>
	<div class="container-fluid">
    <div class="row">
        
    </div>

    <div class="row justify-content-center">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">
                <ul class="navbar-nav mx-auto">
                    <li class="nav-item">
                        <a class="nav-link" onclick="mostrarTabla('tabla1')">Incidencias Propias</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" onclick="mostrarTabla('tabla2')">Incidencias Sin Asignar</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" onclick="mostrarTabla('tabla3')">Incidencias Finalizadas</a>
                    </li>
                </ul>
            </div>
        </nav>
        <div class="col-12 col-md-12 table-container">
            <table id="tabla1" class="table">
                <caption class="text-center text-light">Incidencias Propias</caption>
                <thead>
                    <tr>
                        <th>Descripcion Del Cliente</th>
                        <th>Descripcion Tecnica</th>
                        <th>Horas Acumuladas</th>
                        <th>Coste Hasta El Momento</th>
                        <th>Fecha De Inicio</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <% for(IncidenciaDTO in:mias){ 
                    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String fechaFormateada = sdf.format(in.getFecha_inicio().getTime());
                    %>
                        <tr>
                            <td class="text-center"><%=in.getDescripcion_usuario() %></td>
                            <%if(in.getDescripcion_tecnica()==null||in.getDescripcion_tecnica().equals("")){ %>
                            <td class="text-danger text-center">No puso Descripcion Tecnica</td>
                            <%}else{ %>
                            <td class="text-center"><%=in.getDescripcion_tecnica() %></td>
                            <%} %>
                            <td class="text-center"><%=in.getHoras() %></td>
                            <td class="text-center"><%=in.getCoste() %></td>
                           	<td class="text-center"><%=fechaFormateada %></td>
                           	<td  class="text-center">
	                       <form action="./ControladorFinalizarIncidencia" method="post">
	                            <input type="hidden" name="id" value="<%=user.getIdUsuario() %>">
						        <input type="hidden" name="idI" value="<%=in.getId_incidencia() %>">
						        <button type="submit" class="btn btn-info" >Finalizar Incidencia</button>	
						  </form>	
						 			      		     
                        	</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <table id="tabla2" class="table" style="display: none;">
                <caption class="text-center text-light">Incidencias Sin Asignar</caption>
                <thead>
                    <tr>
                        <th>Descripcion Del Cliente</th>
                        <th>Horas Acumuladas</th>
                        <th>Coste Hasta El Momento</th>
                         <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
 					<% for(IncidenciaDTO in:sinAsignar){ 
                    %>
                        <tr>
                            <td class="text-center"><%=in.getDescripcion_usuario() %></td>
                            <td class="text-center"><%=in.getHoras() %></td>
                            <td class="text-center"><%=in.getCoste() %></td>
                            <td  class="text-center">
	                       <form action="./ControladorAsignarIncidencia" method="post">
	                            <input type="hidden" name="id" value="<%=user.getIdUsuario() %>">
						        <input type="hidden" name="idI" value="<%=in.getId_incidencia() %>">
						        <button type="submit" class="btn btn-info" >Asignar Incidencia</button>	
						  </form>	
						 			      		     
                        	</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

            <table id="tabla3" class="table" style="display: none;">
                <caption class="text-center text-light">Incidencias Finalizadas</caption>
                <thead>
                    <tr>
                        <th>Descripcion Del Cliente</th>
                        <th>Descripcion Tecnica</th>
                        <th>Horas Acumuladas</th>
                        <th>Coste</th>
                        <th>Fecha De Inicio</th>
                        <th>Fecha Fin</th>
                        <th>Empleado De La Incidencia</th>
                    </tr>
                </thead>
                <tbody>
					<% for(IncidenciaDTO in:finalizadas){ 
                    	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        String fechaInicio = sdf.format(in.getFecha_inicio().getTime());
                        String fechaFin = sdf.format(in.getFecha_fin().getTime());
                    %>
                        <tr>
                            <td class="text-center"><%=in.getDescripcion_usuario() %></td>
                            <td class="text-center"><%=in.getDescripcion_tecnica() %></td>
                            <td class="text-center"><%=in.getHoras() %></td>
                            <td class="text-center"><%=in.getCoste() %></td>
                           	<td class="text-center"><%=fechaInicio %></td>
                           	<td class="text-center"><%=fechaFin %></td>
                           	<td class="text-center"><%=in.getEmpleado().getNombreUsuario() %></td>                           	                        	
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