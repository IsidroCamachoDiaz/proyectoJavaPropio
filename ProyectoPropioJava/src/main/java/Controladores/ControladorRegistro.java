package Controladores;



import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import Dtos.UsuarioDTO;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import Utilidades.Alerta;
import Utilidades.Encriptado;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
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
				
				UsuarioDTO usuario = new UsuarioDTO(request.getParameter("nombreUsuario"),
						request.getParameter("telefonoUsuario"),
						request.getParameter("correoUsuario"),
						nc.EncriptarContra(request.getParameter("contraseniaUsuario")));
				
				 Part filePart = request.getPart("imagenUsuario");
				 
				  if (filePart.getSize() > 0) {
				        InputStream fileContent = filePart.getInputStream();

				        // Convertir InputStream a byte[]
				        usuario.setFoto(fileContent.readAllBytes());
				  }
			
				ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
				
				// Redirigir a la vista JSP
				
				try {
					//Comprobamos si esta bien el usuario
					if(cosa.RegistrarUsuario(usuario)) {

						//response.sendRedirect("vistas/home.jsp");
						session.setAttribute("usuario",usuario);
						session.setAttribute("acceso","1");
						Alerta.Alerta(request,"Se ha creado el Usuario Correctamente se le ha enviando un correo para activar la cuenta","success");
						response.sendRedirect("index.jsp");
					}
					else {
						Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
						response.sendRedirect("index.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
					response.sendRedirect("index.html");
				}
				
		 }catch(Exception e) {
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	

		}
}
