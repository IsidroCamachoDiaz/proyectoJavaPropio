package Controladores;



import java.io.IOException;
import java.net.URLEncoder;

import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * @author Equipo Lentos
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
		//Lammos al metodo de encriptar
		 Encriptado nc = new Encriptado();
			//Crearmos el DTO con los paraemos pasados y usando el metodos de encriptar
			UsuarioDTO usuario = new UsuarioDTO(
					request.getParameter("correoUsuario"),nc.EncriptarContra(request.getParameter("contraseniaUsuario")));	
			//USamos la implementacion
			ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
			// Redirigir a la vista JSP
			
				try {
					
					//Comprobamos si esta bien el usuario
					if(cosa.IniciarSesion(usuario,request)) {
						response.sendRedirect("vistas/home.jsp");							
					}
					else {
						response.sendRedirect("index.jsp");
					}
				} catch (IOException e) {
					Alerta.Alerta(request,"Hubo Un Error Intentalo Mas Tarde","error");
					e.printStackTrace();
				}
	 }
}
