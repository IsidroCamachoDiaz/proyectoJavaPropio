<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="https://kit.fontawesome.com/64d58efce2.js"></script>
	<link rel="stylesheet" href="vistas/common/css/style.css">
	<title>Gestor de Biblioteca</title>
</head>
<body>
<div class="container">
		<div class="forms-container">
			<div class="signin-signup">
<form action="./ControladorRestablecer" method="post" class="sign-in-form" name="login">
					<h2 class="title">Restablecer contraseña</h2>
					<div class="input-field">
					<i class="fas fa-lock"></i> <input type="text" name="clave1"
							onkeypress="return validarEntrada(event, 0)" placeholder="Contraseña" />
					</div>
					<div class="input-field">
						<i class="fas fa-lock"></i> <input type="password" name="clave2"
							onkeypress="return validarEntrada(event, 0)" placeholder="Repite la contraseña" />
					</div>
					<input type="hidden" name="tk" value="<%= request.getParameter("tk") %>">
					<input type="button" value="Enviar" class="btn solid" onclick="enviarLogin()" />
					<h4 id="error2"></h4>
	</form>
	</div>
	</div>
	</div>
	<div class="panels-container">
			<div class="panel left-panel">
				<div class="content">
					<h3>¿Has recordado la contraseña?</h3>
					<p>Vuelve para iniciar sesión.</p>
					<button class="btn transparent" id="sign-up-btn" onclick="location.href='login.jsp';">
						Iniciar sesión
					</button>
				</div>
				<img src="vistas/common/img/man.png" class="image" alt="" />
			</div>
	</div>		
</body>
</html>