package Controladores;



import java.io.IOException;
import java.net.URLEncoder;

import Dtos.UsuarioDTO;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Utilidades.Alerta;
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
			 	HttpSession session = request.getSession();
				
				UsuarioDTO usuario = new UsuarioDTO();
				
				ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
				
				// Redirigir a la vista JSP
				
				try {
					String url = "vistas/home.jsp?dni=" + URLEncoder.encode(request.getParameter("dniUsuario"), "UTF-8");
					//Comprobamos si esta bien el usuario
					if(cosa.RegistrarUsuario(usuario)) {

						//response.sendRedirect("vistas/home.jsp");
						session.setAttribute("usuario",usuario);
						session.setAttribute("acceso","1");
						response.sendRedirect(url);
					}
					else {
						Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
						response.sendRedirect("login.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	

		}
}
