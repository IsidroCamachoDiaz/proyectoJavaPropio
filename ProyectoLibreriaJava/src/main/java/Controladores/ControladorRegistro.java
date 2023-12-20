package Controladores;



import Dtos.UsuarioDTO;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Utilidades.Encriptado;
import Servicios.ImplentacionIntereaccionUsuario;
public class ControladorRegistro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 
	 protected void doGet(HttpServletRequest request, HttpServletResponse response){
		 try
		 {
			 response.getWriter().append("Served at: ").append(request.getContextPath());
		 }catch(Exception e) {

				System.out.println("[ERROR-ControladorRegistro-doGet] Se produjo un error en el metodo get");
			}
		
		}
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
			
		 try {
			 	Encriptado nc = new Encriptado();
				
				UsuarioDTO usuario = new UsuarioDTO(
						request.getParameter("dniUsuario"),
						request.getParameter("nombreUsuario"),
						request.getParameter("apellidosUsuario"),
						request.getParameter("tlfUsuario"),
						request.getParameter("email"),
						nc.EncriptarContra(request.getParameter("password"))
						);
				
				ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
				
				if(cosa.RegistrarUsuario(usuario)) {
					response.sendRedirect("vistas/home.jsp");
				}
				else 
					response.sendRedirect("login.jsp");
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	

		}
}
