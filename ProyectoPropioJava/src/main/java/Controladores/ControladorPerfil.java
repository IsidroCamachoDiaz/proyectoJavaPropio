package Controladores;

import java.io.IOException;
import java.io.InputStream;

import Dtos.AccesoDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.ComprobacionImagen;
import Utilidades.Correo;
import Utilidades.Encriptado;
import Utilidades.implementacionCRUD;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig
public class ControladorPerfil extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
		 	implementacionCRUD acciones = new implementacionCRUD ();
		 	Encriptado nc = new Encriptado();
	     	//Cogemos los valores del formulario

		 	String id=request.getParameter("id");
		 	String nombre=request.getParameter("nombre");
		 	String telefono=request.getParameter("telefono");
		 	String correo =request.getParameter("correo");
		 	String contrasenia =request.getParameter("contrasenia");
		 	//Creo una variable para  ver si ha modificado algun campo
		 	boolean cambio=false;
		 	//Busco al usuario para modificarle los campos
	        UsuarioDTO usuarioCambio=acciones.SeleccionarUsuario("Select/"+id);
	        //Compruebo cada campo y si lo modifica cambio el boleano
	        if(!usuarioCambio.getNombreUsuario().equals(nombre)) {
	        	usuarioCambio.setNombreUsuario(nombre);
	        	cambio=true;
	        }
	        if(!usuarioCambio.getTlfUsuario().equals(telefono)) {
	        	usuarioCambio.setTlfUsuario(telefono);
	        	cambio=true;
	        }
	        
	        if(!contrasenia.equals("")) {
	        	usuarioCambio.setClaveUsuario(nc.EncriptarContra(contrasenia));
	        	cambio=true;
	        }

	        //Cojo la imaegn
	        Part filePart;
			try {
				filePart = request.getPart("imagen");
				
				
				ComprobacionImagen com=new ComprobacionImagen();
				
				 //Comprobamos si ha metido una foto
				  if (filePart.getSize() > 0) {
					  
					  if(com.esArchivoImagen(filePart)) {
						  InputStream fileContent = filePart.getInputStream();
					        // Convertir InputStream a byte[]
					        usuarioCambio.setFoto(fileContent.readAllBytes());
					        cambio=true;
					  }
					  //Si no metio algo que sea una foto se pone una por defecto
					  else {
						  try (InputStream defaultImageStream = getClass().getResourceAsStream("/user.png")) {
						        if (defaultImageStream != null) {
						            // Convertir InputStream a byte[]
						            usuarioCambio.setFoto(defaultImageStream.readAllBytes());
						            cambio=true;
						        } 
						    } catch (IOException e) {
						        // Manejar la excepción de entrada/salida si es necesario
						        e.printStackTrace();
						    }
					  }
				        
				  }
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			}
			
			//Compruebo si cambio el email
			if(!usuarioCambio.getEmailUsuario().equals(correo)) {
				Correo correoAccion = new Correo();
				usuarioCambio.setEmailUsuario(correo);
				//Pongo nuevo acceso
				usuarioCambio.setAlta(false);
				//Envio el correo con el nuevo token
				correoAccion.EnviarCorreoToken(usuarioCambio);
				//Actualizamos
				acciones.ActualizarUsuario(usuarioCambio);
				try {
					response.sendRedirect("index.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Alerta.Alerta(request, "Le hemos enviado un correo a su nuevo Correo Comfirmelo", "warning");
				
			}
			//Si cambio lo actualizo el usuario y lo mando al home
			else if(cambio) {
				acciones.ActualizarUsuario(usuarioCambio);
				try {
					response.sendRedirect("vistas/home.jsp");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//Si no ha cambiado se le avisa
			else {
				Alerta.Alerta(request, "No hizo ninguna modificacion", "warning");
			}	        	             	        	    
	    }
}
