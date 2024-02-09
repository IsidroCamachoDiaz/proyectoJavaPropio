package Servicios;

import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface InterfaceAdministracion {
	
	/**
     * Método para cambiar el acceso a un usuario
     *
     * @param request para mostrar las alertas
     * @param usuario que se le va a cambiar el acceso
     * @param idAcceso para cogerlo en la base de datos y ponerselo a usuario
     * @return true si se cambio bien y false si hubo algun problema
     * @since 6/2/24
     */
	public boolean CambiarAcceso(UsuarioDTO usuario,HttpServletRequest request,String idAcceso);
	
	/**
     * Método para crear un usuario
     *
     * @param request para mostrar las alertas
     * @param usuario que se va crear en la base de datos
     * @return true si se creo bien y false si hubo algun problema
     * @since 7/2/24
     */
	public boolean CrearUsuario(UsuarioDTO usu,HttpServletRequest request);
}
