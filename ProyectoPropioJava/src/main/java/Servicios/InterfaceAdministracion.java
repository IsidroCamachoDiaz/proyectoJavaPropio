package Servicios;

import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface InterfaceAdministracion {
	public boolean CambiarAcceso(UsuarioDTO usuario,HttpServletRequest request,String idAcceso);
	
	public boolean CrearUsuario(UsuarioDTO usu,HttpServletRequest request);
}
