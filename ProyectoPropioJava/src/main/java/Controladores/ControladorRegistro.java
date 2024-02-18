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
import Utilidades.ComprobacionImagen;
import Utilidades.Encriptado;
import Utilidades.Escritura;
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
			 //Declaramos loq ue necesitemos
			 	Encriptado nc = new Encriptado();
			 	HttpSession session = request.getSession();
				
			 	//Creamos el usuario
				UsuarioDTO usuario = new UsuarioDTO(request.getParameter("nombreUsuario"),
						request.getParameter("telefonoUsuario"),
						request.getParameter("correoUsuario"),
						nc.EncriptarContra(request.getParameter("contraseniaUsuario")));
				
				//Cojemos la imagen
				 Part filePart = request.getPart("imagenUsuario");
				 
				 ComprobacionImagen com=new ComprobacionImagen();
				 //Comprobamos si ha metido una foto
				  if (filePart.getSize() > 0) {
					  
					  if(com.esArchivoImagen(filePart)) {
						  InputStream fileContent = filePart.getInputStream();
					        // Convertir InputStream a byte[]
					        usuario.setFoto(fileContent.readAllBytes());
					  }
					  else {
						  try (InputStream defaultImageStream = getClass().getResourceAsStream("/user.png")) {
						        if (defaultImageStream != null) {
						            // Convertir InputStream a byte[]
						            usuario.setFoto(defaultImageStream.readAllBytes());
						        } 
						    } catch (IOException e) {
						        // Manejar la excepción de entrada/salida si es necesario
						        e.printStackTrace();
						    }
					  }
				        
				  }
				  //Si no lo ha metido se pone una por defcto
				  else {
					    // El usuario no subió un archivo, proporcionar un archivo predefinido
					    try (InputStream defaultImageStream = getClass().getResourceAsStream("/user.png")) {
					        if (defaultImageStream != null) {
					            // Convertir InputStream a byte[]
					            usuario.setFoto(defaultImageStream.readAllBytes());
					        } 
					    } catch (IOException e) {
					        // Manejar la excepción de entrada/salida si es necesario
					        e.printStackTrace();
					    }
					}
			
				ImplentacionIntereaccionUsuario cosa = new ImplentacionIntereaccionUsuario();
				
				
				try {
					//Comprobamos si esta bien el usuario
					if(cosa.RegistrarUsuario(usuario,request)) {
						Alerta.Alerta(request,"Se ha creado el Usuario Correctamente se le ha enviando un correo para activar la cuenta","success");
						response.sendRedirect("index.jsp");
						Escritura.EscribirFichero("Un usuario se registro en la web y s ele mando un correo de alta");
					}
					//Si no se pudo se avisa al usuario
					else {
						Escritura.EscribirFichero("Un usuario quizo registrarse en la web pero nos e pudo insertar");
						response.sendRedirect("index.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
					response.sendRedirect("index.html");
					Escritura.EscribirFichero("Hubo un error en regsitar usuario "+e.getLocalizedMessage());
				}
				
		 }catch(Exception e) {
			 Escritura.EscribirFichero("Hubo un error en regsitar usuario "+e.getLocalizedMessage());
			 System.out.println("[ERROR-ControladorRegistro-doPost] Se produjo un error en el metodo post al insertar al usuario. | "+e);
			}
		 	
		}
}
