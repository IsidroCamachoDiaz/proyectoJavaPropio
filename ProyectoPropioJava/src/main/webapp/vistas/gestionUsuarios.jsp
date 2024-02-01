<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Dtos.UsuarioDTO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.util.Base64" %>
<%@ page import="java.util.List" %>
<%@ page import="Utilidades.implementacionCRUD" %>
<%@ page import="Utilidades.Alerta" %>
<%@ page import="java.util.stream.Collectors" %>
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
	 <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
</head>
<body>
<%
String accesoSesion = "0";
try {
    accesoSesion = session.getAttribute("acceso").toString();
    if (accesoSesion.equals("1") || accesoSesion.equals("2")) {
    	Alerta.Alerta(request, "No puede acceder a este lugar de la web", "warning");
    	response.sendRedirect("home.jsp");
    	return;
    }

} catch (Exception e) {
    Alerta.Alerta(request, "No ha iniciado Sesión en la web", "error");
    response.sendRedirect("../index.jsp");
    return;
}
System.out.println(accesoSesion);
implementacionCRUD acciones=new implementacionCRUD();
//Si modifico el usuario que se actualice
UsuarioDTO user =(UsuarioDTO) session.getAttribute("usuario");
//Selecciono el nuevi
user = acciones.SeleccionarUsuario("Select/"+user.getIdUsuario());
//Convierto la imagen
String base64Image = Base64.getEncoder().encodeToString(user.getFoto());

//Coloca la cadena Base64 en el alcance de la solicitud
request.setAttribute("base64Image", base64Image);
session.setAttribute("imagen", base64Image);

%>

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
                <h2 class="text-white">Administracion De Usuarios</h2>
            </div>
        </form>
    </div>
	<%
	
	List <UsuarioDTO> usuarios=acciones.SeleccionarTodosUsuarios();
	for(int i=0;i<usuarios.size();i++){
		if(usuarios.get(i).getIdUsuario()==user.getIdUsuario()){
			usuarios.remove(i);
			break;
		}
	}
	%>
	<div class="container-fluid">
		<div class="row">
			<div class="col-12 table-container">
				<table class="table mt-3"  style="background-color: #A875E8; color: #ffffff;">
				    <thead>
				      <tr>
				      	<th>Foto</th>
				      	<th>Correo</th>
				        <th>Nombre de Usuario</th>
				        <th>Telefono</th>
				        <th>Nivel de Acceso</th>
				        <th>Acciones</th>
				      </tr>
				    </thead>
				    <tbody>
				    <%
				    for(UsuarioDTO usuarioVer : usuarios){
				    	String foto = Base64.getEncoder().encodeToString(usuarioVer.getFoto());
				    	request.setAttribute("foto",foto);
				    %>
				      <tr>
				        <td> <img class="rounded-circleVer user-avatarVer" src="data:image/jpeg;base64,${foto}" alt="Imagen de Usuario"></td>
				        <td class="text-center"><%=usuarioVer.getEmailUsuario() %></td>
				        <td class="text-center"><%=usuarioVer.getNombreUsuario() %></td>
				        <td class="text-center"><%=usuarioVer.getTlfUsuario() %></td>
				        <td class="text-center"><%=usuarioVer.getAcceso().getCodigoAcceso()%></td>
				         <td>
				         <form action="./ControladorEliminarUsuario" method="post" id="formBorrarUsuario<%=usuarioVer.getIdUsuario()%>">
					        <input type="hidden" name="id" value="<%=usuarioVer.getIdUsuario()%>">
					      </form>
					      <button class="btn btn-danger"  onclick="confirmarBorrado('<%=usuarioVer.getIdUsuario()%>')">Borrar Usuario</button>
					       <a href="https://www.youtube.com/" target="_blank">
					        	<button  class="btn btn-success" type="button">Modificar Usuario</button>
					      	</a>				        			        
				         </td>
				      </tr>
					<%
				    }
					%>
					 <tr>
					 <td colspan="6" class="text-center">
					 Crear Usuario
					 </td>
					 </tr>
				    </tbody>
		  		</table>
			</div>
		</div>
	</div>
    
    <script src="js/plugins.js"></script>
    <script>
        $(window).on("load", function() {
            $('body').addClass('loaded');
        });
    </script>
    
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
</body>
</html>