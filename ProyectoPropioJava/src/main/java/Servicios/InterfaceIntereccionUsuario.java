package Servicios;

import Dtos.TokenDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interfaz para la interacción con operaciones relacionadas con el usuario, como registro e inicio de sesión.
 */
public interface InterfaceIntereccionUsuario {

    /**
     * Método para iniciar sesión de un usuario.
     *
     * @param usu     Objeto UsuarioDTO que contiene la información del usuario.
     * @param request HttpServletRequest proporciona información sobre la solicitud HTTP.
     * @return true si la sesión se inicia con éxito, false de lo contrario.
     * @since 23/1/24
     */
    public boolean IniciarSesion(UsuarioDTO usu, HttpServletRequest request);

    /**
     * Método para registrar un nuevo usuario.
     *
     * @param usu     Objeto UsuarioDTO que contiene la información del usuario a registrar.
     * @param request HttpServletRequest proporciona información sobre la solicitud HTTP.
     * @return true si el usuario se registra con éxito, false de lo contrario.
     * @since 24/1/24
     */
    public boolean RegistrarUsuario(UsuarioDTO usu, HttpServletRequest request);

    /**
     * Método para enviar un recordatorio de clave a un usuario.
     *
     * @param request HttpServletRequest proporciona información sobre la solicitud HTTP.
     * @param correo  Correo electrónico del usuario al que se enviará el recordatorio.
     * @return true si el recordatorio se envía con éxito, false de lo contrario.
     * @since 28/1/24
     */
    public boolean OlvidarClaveUsuario(HttpServletRequest request, String correo);

    /**
     * Método para restablecer la contraseña de un usuario.
     *
     * @param request    HttpServletRequest proporciona información sobre la solicitud HTTP.
     * @param token      TokenDTO que representa el token asociado al proceso de restablecimiento de contraseña.
     * @param nuevaContrasena Nueva contraseña que se establecerá para el usuario.
     * @return true si la contraseña se restablece con éxito, false de lo contrario.
     * @since 28/1/24
     */
    public boolean actualizarContrasena(HttpServletRequest request, TokenDTO token, String nuevaContrasena);
}