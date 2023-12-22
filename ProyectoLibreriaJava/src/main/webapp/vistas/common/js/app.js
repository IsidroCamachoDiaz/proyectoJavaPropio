// Container
const container = document.querySelector(".container");
// Botones de iniciar sesión y registrarse que aparecen en los paneles laterales
const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");

// Recupera contraseña
const enlaceRecuperaClave = document.getElementById("enlaceRecuperaClave"); // El enlace "¿Has olvidado la contraseña?"
const panelLogin = document.getElementById("panel-login"); // El panel que sale en la izquierda cuando estamso en el login
const formularioRegistro = document.getElementById("formularioRegistrarse"); // Formulario de registrarse
const formularioRecuperaClave = document.getElementById("formularioRecuperaClave"); // Formulario de recupera contraseña
const imagenLogin = document.getElementById("imagenLogin"); // Imagen que aparece en el panel cuando estamos en login

// Método que cambiará la clase del container para cambiar entre login y register y recuperar contraseña
sign_up_btn.addEventListener("click", () => {
  container.classList.add("sign-up-mode");

  // Cambiamos el texto del h3 del panelLogin
  panelLogin.getElementsByTagName("h3")[0].innerHTML = "¿Ya tienes una cuenta?";
  // Cambiamos el texto del p del panelLogin
  panelLogin.getElementsByTagName("p")[0].innerHTML = "Descubre el mundo de la lectura.";

  // Ahora ocultaremos el formulario de recupera contraseña y mostraremos el formulario de registro
  formularioRecuperaClave.style.display = "none";
  setTimeout(function () {
    // Mostrar el formulario de registro
    formularioRegistro.style.display = "";
  }, 1000);

  // Cambiamos la imagen que aparece en el panel, para eso usamos src y cambiamos la foto del src
  imagenLogin.src = "vistas/common/img/woman.png";
});

// Método que se accionará al pulsar el botón iniciar sesión
sign_in_btn.addEventListener("click", () => {
  container.classList.remove("sign-up-mode");
});

// Método que se accionará al pulsar sobre el enlace de "¿Has olvidado la contraseña?"
enlaceRecuperaClave.addEventListener("click", () => {
  container.classList.add("sign-up-mode");

  // Cambiamos el texto del h3 del panelLogin
  panelLogin.getElementsByTagName("h3")[0].innerHTML = "¿Recordaste tu contraseña?";
  // Cambiamos el texto del p y lo ponemos vacio
  panelLogin.getElementsByTagName("p")[0].innerHTML = "";

  // Ahora ocultaremos el formulario de registro y mostraremos el formulario de recupera contraseña
  formularioRegistro.style.display = "none";
  setTimeout(function () {
    // Mostrar el formulario de recupera contraseña
    formularioRecuperaClave.style.display = "";
  }, 1000);

  // Cambiamos la imagen para eso usamos src y cambiamos la foto del src
  imagenLogin.src = "vistas/common/img/woman2.png";
})