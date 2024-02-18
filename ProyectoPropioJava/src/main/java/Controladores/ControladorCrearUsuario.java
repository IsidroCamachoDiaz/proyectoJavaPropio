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
import Servicios.ImplementacionAdministracion;
import Servicios.ImplentacionIntereaccionUsuario;

@MultipartConfig
public class ControladorCrearUsuario extends HttpServlet {
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
			 //Declaramos lo que necesitemos
			 	Encriptado nc = new Encriptado();
			 	HttpSession session = request.getSession();
				
			 	//Creamos el usuario
				UsuarioDTO usuario = new UsuarioDTO(request.getParameter("nombre"),
						request.getParameter("telefono"),
						request.getParameter("correo"),
						nc.EncriptarContra(request.getParameter("contrasenia")));
				
				//Cogemos la imagenn
				 Part filePart = request.getPart("imagen");
				 //Damos de alta
				 usuario.setAlta(true);
				 
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
				  
				  //Declaramos la implementacion
				  ImplementacionAdministracion cosa = new ImplementacionAdministracion();
				
				// Redirigir a la vista JSP
				
				try {
					//Comprobamos si esta bien el usuario
					if(cosa.CrearUsuario(usuario, request)) {
						Alerta.Alerta(request,"Se ha creado el Usuario Correctamente ya puede acceder a la cuenta","success");
						response.sendRedirect("vistas/crearUsuario.jsp");
						Escritura.EscribirFichero("Se creo un usuario: "+usuario.getNombreUsuario());
					}
					//Si no se creo bien avsiamos al usuario
					else {
						Escritura.EscribirFichero("Un usuario quiso crear un usuario pero no se pudo insertar");
						response.sendRedirect("index.jsp");
					}
				} catch (IOException e) {
					e.printStackTrace();
					response.sendRedirect("vistas/crearUsuario.jsp");
					Escritura.EscribirFichero("Hubo un error en crear usuario "+e.getLocalizedMessage());
				}
				
		 }catch(Exception e) {
			 try {
				response.sendRedirect("vistas/crearUsuario.jsp");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			 Escritura.EscribirFichero("Hubo un error en crear usuario "+e.getLocalizedMessage());
			}
		 	
		}
}
