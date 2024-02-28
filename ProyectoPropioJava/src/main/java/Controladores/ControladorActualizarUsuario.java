package Controladores;

import java.io.IOException;
import java.io.InputStream;

import Dtos.UsuarioDTO;
import Servicios.ImplementacionAdministracion;
import Servicios.ImplentacionIntereaccionUsuario;
import Utilidades.Alerta;
import Utilidades.ComprobacionImagen;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

//Aqui va el controlador es para el formulario para restablecer enviar el correo
@MultipartConfig
public class ControladorActualizarUsuario extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 * Metodo que se usa en el formulario para coger los valores
	 * y comprobarlos para actualizar el usuario
	 * @param request
	 * @param response
	 * 
	 * */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
    	try {
    	//Declaramos loq ue encesitemos
    	boolean cambio=false;
    	
    	//Cogemos los valores del fomulario
        String idUsuario=request.getParameter("id");
	 	String nombre=request.getParameter("nombre");
	 	String telefono=request.getParameter("telefono");
	 	String acceso=request.getParameter("acceso");
	 	
	 	//Comprobamos que no hay valores nulos
	 	if(nombre==null||nombre.equals("")||telefono.equals("")||telefono==null) {
	 		response.sendRedirect("vistas/home.jsp");
			Escritura.EscribirFichero("Un usuario puso campos vacios");
			Alerta.Alerta(request, "No puede poner campos vacios", "warning");
			return;
	 	}
	 	
	    ImplementacionAdministracion inter = new ImplementacionAdministracion();
	 	
	 	implementacionCRUD acciones = new implementacionCRUD();
	 	
	 	//Cogemos el usuario que se quiere cambiar
	 	UsuarioDTO usuarioCambiar = acciones.SeleccionarUsuario("Select/"+idUsuario);
	 	
	 	//Comprobamos si se encontro
	 	if(usuarioCambiar==null) {
	 		Alerta.Alerta(request, "No se encontro al usuario", "error");
			response.sendRedirect("vistas/home.jsp");
			Escritura.EscribirFichero("Un administrador quiso modificar un usuario pero no se encontro el usuario");
			return;
	 	}
	 	
	 	//Comprobamos si tiene campos diferentes
	 	if(!nombre.equals(usuarioCambiar.getNombreUsuario())||!telefono.equals(usuarioCambiar.getTlfUsuario())
	 			||!acceso.equals(String.valueOf(usuarioCambiar.getAcceso().getIdAcceso()))) {
	 		
	 		//Comprobamos si es el acceso
	 		if(!acceso.equals(String.valueOf(usuarioCambiar.getAcceso().getIdAcceso()))) {
	 			//Comprobamos si se cambia bien
	 			if(inter.CambiarAcceso(usuarioCambiar, request, acceso)) {
	 				Escritura.EscribirFichero("Se cambio el acceso al usuario: "+usuarioCambiar.getNombreUsuario());
	 			}
	 			//Si no se puede cambiar bien se avisa al usuario
	 			else {	 				
	 				Escritura.EscribirFichero("Un administrador quiso cambiar un acceso pero no se pudo cambiar");
	 				response.sendRedirect("vistas/home.jsp");
	 				return;
	 			}
	 		}
	 		//Se cambia el resto de campo
	 		usuarioCambiar.setNombreUsuario(nombre);
	 		usuarioCambiar.setTlfUsuario(telefono);
	 		cambio=true;
	 		
	 	}
	 	//Cojo la imagen
        Part filePart;
			filePart = request.getPart("imagen");
			
			ComprobacionImagen com=new ComprobacionImagen();
			
			 //Comprobamos si ha metido una foto
			  if (filePart.getSize() > 0) {
				  
				  if(com.esArchivoImagen(filePart)) {
					  InputStream fileContent = filePart.getInputStream();
				        // Convertir InputStream a byte[]
				        usuarioCambiar.setFoto(fileContent.readAllBytes());
				        cambio=true;
				  }
				  //Si no metio algo que sea una foto se pone una por defecto
				  else {
					  try (InputStream defaultImageStream = getClass().getResourceAsStream("/user.png")) {
					        if (defaultImageStream != null) {
					            // Convertir InputStream a byte[]
					            usuarioCambiar.setFoto(defaultImageStream.readAllBytes());
					            cambio=true;
					        } 
					    } catch (IOException e) {
					        // Manejar la excepción de entrada/salida si es necesario
					        e.printStackTrace();
					    }
				  }
			        
			  }
			
			//Comprobamos se se cambio campos
			if(cambio) {
				acciones.ActualizarUsuario(usuarioCambiar);
				Escritura.EscribirFichero("Un administrador modifico al usuario: "+usuarioCambiar.getNombreUsuario());
				Alerta.Alerta(request, "Se modifico correctamente el usuario "+usuarioCambiar.getNombreUsuario(), "success");
				response.sendRedirect("vistas/gestionUsuarios.jsp");
			}
			//Si no ha cambiado se le avisa
			else {
				Escritura.EscribirFichero("Un administrador quiso cambiar a un usuario pero no cambiar ningun dato");
				Alerta.Alerta(request, "No hizo ninguna modificacion", "warning");
				response.sendRedirect("vistas/gestionUsuarios.jsp");
			}	
        
    	}catch (IOException e) {
			e.printStackTrace();
			Escritura.EscribirFichero("Hubo un error en modificar usuario "+e.getLocalizedMessage());
		}catch (ServletException e) {
			e.printStackTrace();
			Escritura.EscribirFichero("Hubo un error en modificar usuario "+e.getLocalizedMessage());
		}
    	catch(Exception e) {
    		Escritura.EscribirFichero("Hubo un error en modificar usuario "+e.getLocalizedMessage());
    	}
    }

}
