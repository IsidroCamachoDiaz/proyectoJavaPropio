package Servicios;

import Dtos.UsuarioDTO;

/**
 * Clase para el nombramiento de los metodos relacionado con el usuario register,login
 */

public interface InterfaceIntereccionUsuario {
	
	/**
	 * Metodo para registrar a los usuarios
	 * @param UsuarioDTO Objetos usu
	 * @param ASMP-17/11/23
	 */
	public boolean IniciarSesion(UsuarioDTO usu);
	/**
	 * Metodo para registrar a los usuarios
	 * @param UsuarioDTO Objetos usu
	 * @param ASMP-17/11/23
	 */
	public boolean RegistrarUsuario(UsuarioDTO usu);
	/**
	 * Metodo para registrar a los usuarios
	 * @param String correo
	 * @param ASMP-30/11/23
	 */
	public boolean OlvidarClaveUsuario(String correo);
	
}
