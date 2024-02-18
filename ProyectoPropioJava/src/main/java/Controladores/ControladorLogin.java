package Controladores;



import java.io.IOException;
import java.net.URLEncoder;

import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import Utilidades.Escritura;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author Isidro Camacho Diaz
 */
public class ControladorLogin extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para Iniciar Sesion y enviar al home
	 * @param request
	 * @param response
	 * 
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
			//Declaramos loq ue necesitemos
			 Encriptado nc = new Encriptado();
		 
			//Crearmos el DTO con los paraemos pasados y usando el metodos de encriptar
			UsuarioDTO usuario = new UsuarioDTO(
					request.getParameter("correoUsuario"),nc.EncriptarContra(request.getParameter("contraseniaUsuario")));	
			
			//Usamos la implementacion
			ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
			
				try {
					
					//Comprobamos si esta bien el usuario
					if(cosa.IniciarSesion(usuario,request)) {
						Escritura.EscribirFichero("Un usuario inicio sesion en la web");
						response.sendRedirect("vistas/home.jsp");							
					}
					//Si no se inicciar bien se devuelve al index
					else {
						Escritura.EscribirFichero("Un usuario quizo iniciar sesion pero no pudo");
						response.sendRedirect("index.jsp");
					}
				} catch (Exception e) {
					Escritura.EscribirFichero("Hubo un error en inicar sesion "+e.getLocalizedMessage());
					e.printStackTrace();
				}
	 }
}
