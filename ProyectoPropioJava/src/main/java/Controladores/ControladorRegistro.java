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
				String contrasenia2=nc.EncriptarContra(request.getParameter("contrasenia2Usuario"));
				UsuarioDTO usuario = new UsuarioDTO(request.getParameter("nombreUsuario"),
						request.getParameter("telefonoUsuario"),
						request.getParameter("correoUsuario"),
						nc.EncriptarContra(request.getParameter("contraseniaUsuario")));
				
				if(!contrasenia2.equals(usuario.getClaveUsuario())) {
					Alerta.Alerta(request,"La primera contraseña no coincide con la segunda","error");
					response.sendRedirect("index.html");
				}
				
				ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
				
				// Redirigir a la vista JSP
				
				try {
					String url = "vistas/home.jsp?dni=" + URLEncoder.encode(request.getParameter("dniUsuario"), "UTF-8");
					//Comprobamos si esta bien el usuario
					if(cosa.RegistrarUsuario(usuario)) {

						//response.sendRedirect("vistas/home.jsp");
						session.setAttribute("usuario",usuario);
						session.setAttribute("acceso","1");
						Alerta.Alerta(request,"Usurario Creado Correctamente","success");
						response.sendRedirect("index.html");
					}
					else {
						Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
						response.sendRedirect("index.html");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	

		}
}
