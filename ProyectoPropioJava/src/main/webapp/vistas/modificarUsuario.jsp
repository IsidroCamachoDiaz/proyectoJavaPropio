<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="Utilidades.Alerta" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<%@ page import="Dtos.AccesoDTO" %>
<%@ page import="Utilidades.Escritura" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SystemRevive</title>
    <link rel="icon" href="img/logoPNG.png" type="image/jpg">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="fontawesome/css/all.min.css">
    <link rel="stylesheet" href="css/templatemo-style.css">
</head>
<body>
 <%
 String accesoSesion = "0";
 try {
	    accesoSesion = session.getAttribute("acceso").toString();
	    if (accesoSesion.equals("1") || accesoSesion.equals("2")) {
	    	Alerta.Alerta(request, "No puede acceder a este lugar de la web", "warning");
	    	Escritura.EscribirFichero("Un usuario o empleado intento entrar a la administracion");
	    	response.sendRedirect("home.jsp");
	    	return;
	    }

	} catch (Exception e) {
	    Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
	    Escritura.EscribirFichero("Una persona intento acceder sin haberse logueado");
	    response.sendRedirect("../index.jsp");
	    return;
	}
%>
<%
Escritura.EscribirFichero("Se accedio a modificar un usuario");
implementacionCRUD acciones=new implementacionCRUD();

String idUsuario = request.getParameter("id");

UsuarioDTO usuarioCambiar = acciones.SeleccionarUsuario("Select/"+idUsuario);

if(usuarioCambiar==null){
	Alerta.Alerta(request, "No se encontro al usuario", "warning");
	response.sendRedirect("home.jsp");
	return;
}

String fotoUsuario = Base64.getEncoder().encodeToString(usuarioCambiar.getFoto());

UsuarioDTO usuario =acciones.SeleccionarUsuario("Select/"+session.getAttribute("usuario"));

request.setAttribute("foto", fotoUsuario);
request.setAttribute("base64Image", session.getAttribute("imagen"));

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
            <a class="navbar-brand" href="home.jsp">
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
            <h2 class="text-white">Modificar Usuario <%=usuarioCambiar.getNombreUsuario() %></h2>
        </form>
    </div>

    <div class="container-fluid tm-container-content tm-mt-60">
        <div class="row mb-4">
            <h2 class="col-6 tm-text-primary text-white">
                Modifique lo que quiera de su usuario
            </h2>
        </div>
       <div class="row tm-mb-90">            
    <div class="col-xl-8 col-lg-7 col-md-6 col-sm-12 text-center">
        <img src="data:image/jpeg;base64,${foto}" alt="Image" class="img-fluid rounded">
    </div>
    <div class="col-xl-4 col-lg-5 col-md-6 col-sm-12">
        <div class="tm-bg-gray tm-video-details">
        <form action="../ControladorActualizarUsuario" method="post" enctype="multipart/form-data" id="formulario">
           
           <div class="form-group">
                <label for="nombre">Nombre:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<%=usuarioCambiar.getNombreUsuario() %>" required >
            </div>

            <!-- Campo de Número de Teléfono -->
            <div class="form-group">
                <label for="telefono">Número de Teléfono:</label>
                <input type="tel" class="form-control" id="telefono" name="telefono" pattern="[0-9]{9}" value="<%=usuarioCambiar.getTlfUsuario() %>" required>
            </div>
                       
			<label for="campo_select" class="form-label">Selecciona el acceso del usuario:</label>
		    <select class="form-select" id="acceso" name="acceso" required>
			<%
			List <AccesoDTO> accesos=acciones.SeleccionarTodosAccesos();		
			for(int i=0;i<accesos.size();i++){
				if(accesos.get(i).getIdAcceso()==usuarioCambiar.getAcceso().getIdAcceso()){
					%>
					<option value="<%=accesos.get(i).getIdAcceso() %>" selected><%=accesos.get(i).getCodigoAcceso() %></option>
					<%
				}
				else{
					%><option value="<%=accesos.get(i).getIdAcceso() %>"><%=accesos.get(i).getCodigoAcceso() %></option><%
				}
		    }
		    %>
		    </select>
			
            <!-- Campo de Subir Archivo (Imagen) -->
            <div class="form-group">
                <label for="imagen">Imagen de Perfil:</label>
                <input type="file" class="form-control-file" id="imagen" name="imagen" accept="image/*">
            </div>
            <input type="text" id="id" name="id" value="<%=String.valueOf(usuarioCambiar.getIdUsuario()) %>" style="display: none;" >
          
          <div class="mb-4 text-center">
                <button class="btn btn-primary tm-btn-big"  type="submit" >Modificar Usuario</button>
            </div>
          </form>
             
        </div>
    </div>
</div>

    </div> <!-- container-fluid, tm-container-content -->

    
    <script src="js/plugins.js"></script>
    <script>
        $(window).on("load", function() {
            $('body').addClass('loaded');
        });
    </script>
<script>
    var formModified = false;

    function setFormModified(modified) {
        formModified = modified;
    }

    function showConfirmation() {
        if (formModified) {
            Swal.fire({
                title: 'Si Modifica el correo tendrá que volver a verificar el correo. ¿Está seguro?',
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: 'Sí, enviar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Envía el formulario si la confirmación es positiva
                    document.getElementById("formulario").submit();
                } else {
                    // No hace nada si la confirmación es cancelada
                    Swal.fire('Envío cancelado', '', 'info');
                }
            });
        } else {
            // El formulario no ha sido modificado, no se muestra la alerta
            document.getElementById("formulario2").submit();
        }
    }
</script>
</body>
</html>