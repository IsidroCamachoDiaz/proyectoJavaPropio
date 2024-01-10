// Esta función se llama cuando se intenta validar una entrada, como la entrada de un usuario en un formulario.
function validarEntrada(event, tipoForm) {
	var keycode;

	// Utilizamos el parámetro event directamente para obtener el código de tecla
	if (event)
		keycode = event.which || event.keyCode;
	else
		return true;

	// Comprobar si el código de tecla está fuera del rango de números y letras ASCII
	if ((keycode < 48 || keycode > 57) && (keycode < 64 || keycode > 90) && (keycode < 97 || keycode > 122)) {

		// Si el código de tecla no está en el rango y no es un carácter especial permitido, muestra un error y bloquea la entrada.
		if (keycode != 8 && keycode != 13 && keycode != 27 && keycode != 0 && keycode != 32 &&
			keycode != 225 && keycode != 233 && keycode != 237 && keycode != 243 && keycode != 250 &&
			keycode != 193 && keycode != 201 && keycode != 205 && keycode != 211 && keycode != 218 && keycode != 46 && keycode != 241) {

			// Mostrar mensaje de error según el tipo de formulario
			switch (tipoForm) {
				case 0:
					document.getElementById("error2").innerHTML = 'Sólo puedes introducir letras y números.';
					break;
				case 1:
					document.getElementById("error1").innerHTML = 'Sólo puedes introducir letras y números.';
					break;
				case 2:
					document.getElementById("resultado").innerHTML = 'Sólo puedes introducir letras y números.';
					break;
			}
			return false; // Evitar que el carácter se introduzca

		} else if (keycode == 13) {
			// Realizar acciones específicas según el tipo de formulario para cuando se introduzca Enter
			event.preventDefault();
			switch (tipoForm) {
				case 0:
					enviarLogin();
					break;
				case 1:
					enviarRegistrar();
					break;
				case 2:
					enviarRecuperar();
					break;
			}
		} else {
			return true; // Permitir caracteres especiales (teclas de control, Enter, etc.)
		}
	} else {
		return true; // Permitir letras y números
	}
}

// Función para manejar el envío del formulario de inicio de sesión
function enviarLogin() {
	// Obtener los datos del formulario de inicio de sesión
	datos = document.login;

	// Verificar si los campos de DNI y contraseña están vacíos
	if (datos.dniUsuario.value == '' || datos.password.value == '')
		// Mostrar mensaje de error si hay campos vacíos
		document.getElementById("error2").innerHTML = 'Tienes que rellenar todos los campos.';
	// Verificar el formato del DNI utilizando una expresión regular
	else if (!/^\d{8}[A-Za-z]$/.test(datos.dniUsuario.value))
		document.getElementById("error2").innerHTML = 'El DNI no tiene un formato válido.';
	// Enviar el formulario si todas las validaciones pasan
	else datos.submit();
}

// Función para manejar el envío del formulario de registro
function enviarRegistrar() {
	// Obtener los datos del formulario de registro
	datos = document.registro;

	if (datos.nombreUsuario.value == '' || datos.apellidosUsuario.value == '' || datos.dniUsuario.value == '' || datos.tlfUsuario.value == '' || datos.password.value == '' || datos.email.value == '')
		document.getElementById("error1").innerHTML = 'Tienes que rellenar todos los campos.';
	else if (!/^(?=.*\d)[A-Za-z\d]{7,}$/.test(datos.password.value))
		document.getElementById("error1").innerHTML = 'La contraseña debe tener al menos un número y 7 caracteres.';
	else if (!/^\d{8}[A-Za-z]$/.test(datos.dniUsuario.value))
		document.getElementById("error1").innerHTML = 'El DNI no tiene un formato válido.';
	else if (!/.*@.*/.test(datos.email.value))
		document.getElementById("error1").innerHTML = 'El email debe contener "@".';
	else if (!/^\d{9}$/.test(datos.tlfUsuario.value))
		document.getElementById("error1").innerHTML = 'El teléfono debe contener 9 dígitos.';
	else
		datos.submit();
}

// Función para manejar el envío del formulario de recuperación de contraseña por correo
function enviarRecuperar() {
	datos = document.recuperaClave;

	if (datos.emailUsuario.value == '') {
		document.getElementById("resultado").innerHTML = 'Tienes que indicar un email.';
	} else if (!/.*@.*/.test(datos.emailUsuario.value)) {
		document.getElementById("resultado").innerHTML = 'El email debe contener "@".';
	}
	else {
		datos.submit();
		document.getElementById("resultado").innerHTML = "Se le ha enviado un correo con la recuperación de la contraseña.";
	}
}
