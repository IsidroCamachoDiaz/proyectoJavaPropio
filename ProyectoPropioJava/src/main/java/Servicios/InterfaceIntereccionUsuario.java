package Servicios;

import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Clase para el nombramiento de los metodos relacionado con el usuario register,login
 */

public interface InterfaceIntereccionUsuario {
	
	/**
	 * Metodo para registrar a los usuarios
	 * @param UsuarioDTO Objetos usu
	 * @param ASMP-17/11/23
	 */
	public boolean IniciarSesion(UsuarioDTO usu,HttpServletRequest request);
	/**
	 * Metodo para registrar a los usuarios
	 * @param UsuarioDTO Objetos usu
	 * @param ASMP-17/11/23
	 */
	public boolean RegistrarUsuario(UsuarioDTO usu,HttpServletRequest request);
	/**
	 * Metodo para registrar a los usuarios
	 * @param String correo
	 * @param ASMP-30/11/23
	 */
	public boolean OlvidarClaveUsuario(String correo);
	
	/**
	 * Metodo para restablcer la contraseña
	 * @param idUsuario
	 * @param nuevaContrasena
	 * @return
	 */
	public boolean actualizarContrasena( String token, String clave1);
	
}
