package Servicios;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.mail.IllegalWriteException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Dtos.TokenDTO;
import Dtos.UsuarioDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;






/**
 * Clase para implentar la interfez de register,login del usuario que implenta de la interfaz
 * 
 */

public class ImplentacionIntereaccionUsuario implements InterfaceIntereccionUsuario {
	
	public boolean IniciarSesion(UsuarioDTO user,HttpServletRequest request) {
		
		
		try {
			//Se le pasa la url
				
				UsuarioDTO usuarioBD;
		        HttpSession session = request.getSession();
		        implementacionCRUD acciones = new implementacionCRUD();
		        
		        //Cogemos el usuario de la base de datos
		        try {
				usuarioBD =acciones.SeleccionarUsuario("SelectCorreo/"+user.getEmailUsuario());
				if(usuarioBD==null) {
					throw new RuntimeException();
				}
		        }catch(RuntimeException e) {
		        	Alerta.Alerta(request,"El Correo y/o contraseña es incorrecto", "error");
		        	return false;
		        }
				//Si no es igual lo manda al login con aviso
	            if(user.getClaveUsuario().equals(usuarioBD.getClaveUsuario())) {
	            	//Comprobamos is verifico la cuenta
	            	if(usuarioBD.getAcceso().getCodigoAcceso().equals("Pendiente")) {
	            		Alerta.Alerta(request,"El usuario no esta dado de alta en la web", "error");
	            		return false;
	            	}else {
	            		//Asignamos el usuario y el control de acceso de vada usuario
	                	session.setAttribute("usuario",usuarioBD);
	            		if(usuarioBD.getAcceso().getCodigoAcceso().equals("Usuario")) {
	            			session.setAttribute("acceso","1");
	            		}
	            		else if(usuarioBD.getAcceso().getCodigoAcceso().equals("Empleado")) {
	            			session.setAttribute("acceso","2");
	            		}
	            		else {
	            			session.setAttribute("acceso","3");
	            		}
	            		return true;
	            	}
	            }
	            else {
					Alerta.Alerta(request,"El DNI y/o Clave son incorrectos","error");
	            	return false;
	            }
		}catch(Exception e) {
			Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
	
	@Override
	public boolean RegistrarUsuario(UsuarioDTO usu,HttpServletRequest request) {
		
		try{
			implementacionCRUD acciones = new implementacionCRUD();
			UsuarioDTO usuarioSiHay =acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
			if(usuarioSiHay!=null) {
				Alerta.Alerta(request, "Ya existe una cuenta con ese correo", "error");
				return false;
			}
			else {
				acciones.InsertarUsuario(usu);
	            
	            UsuarioDTO usuId=acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
	            Correo correo=new Correo();
	            if(correo.EnviarCorreoToken(usuId)) {
	            	return true;
	            }
	            else {
	            	return false;
	            }
			}	            
			}catch (IllegalStateException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
			}catch (NullPointerException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
			}catch (SecurityException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
			}catch (IndexOutOfBoundsException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Algun tipo (como una matriz, una cadena o un vector) está fuera de rango. |"+e);
			}
			
	return false;
	}
	@Override
	public boolean OlvidarClaveUsuario(HttpServletRequest request,String correo) {
		boolean ok=false;
		try {
			//Se crea un objeto properties para coger los valores
			Properties seguridad = new Properties();
			implementacionCRUD acciones = new implementacionCRUD();
			//Se cargar los valores 
			seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));
			//Aqui tiene que ir el buscar el usuario por el correo
			UsuarioDTO usuarioCorreo=acciones.SeleccionarUsuario("SelectCorreo/"+correo);
			//Se comprueba si encontro el usuario
			if(usuarioCorreo.getNombreUsuario()==null||usuarioCorreo.getNombreUsuario().equals("")||usuarioCorreo.getClaveUsuario()==null) {
				Alerta.Alerta(null, "Este correo no esta asociado a niguna cuenta", "error");
				return false;
			}
			//Se crea el token 
			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();
			//Aqui generas la fecha limite con un tiempo de 10 minutos
			TokenDTO tokenMandarBD= new TokenDTO();
			tokenMandarBD.setFch_limite(new GregorianCalendar());
			tokenMandarBD.getFch_limite().add(Calendar.MINUTE, 10);
			tokenMandarBD.setToken(token);
			tokenMandarBD.setId_usuario(usuarioCorreo);
			//Aqui una vez encuentre el usuario insertas el token generado arriba y 
			//lo inserta con la fecha limite, id_Usuario
			Correo correo2= new Correo();
			 String mensaje=correo2.MensajeCorreo(token);
			 ok=correo2.EnviarMensaje(mensaje,correo,true,"Recuperar Contraseña",seguridad.getProperty("correo"),true);
			//Meter en un if si el usuario se encontro que haga el resto (tiene que llegar hasta ok=EnviarMensaje...)
			 if(ok) {
				 acciones.InsertarToken(tokenMandarBD);
				 return true;
			 }
			 else {
				 Alerta.Alerta(request, "Hubo un erroe intentelo mas tarde", "error");
				 return false;
			 }
		}catch(IOException e)
		{
			 Alerta.Alerta(request, "Hubo un erroe intentelo mas tarde", "error");
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se pudo leer el .properties. |"+e);
			return false;
		}
		catch(NullPointerException e)
		{
			 Alerta.Alerta(request, "Hubo un erroe intentelo mas tarde", "error");
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El .properties es nulo. |"+e);
			return false;
		}
	}
	
	@Override
	public boolean actualizarContrasena(HttpServletRequest request,TokenDTO token, String clave1) {
		 try {
       	  		implementacionCRUD acciones= new implementacionCRUD();
	            // Obtener el usuario actual
	            UsuarioDTO usuarioActual =acciones.SeleccionarUsuario("Select/"+token.getId_usuario().getIdUsuario());
	            //Cogemos la fecha actual
	            Calendar actual = new GregorianCalendar();
	            //Cogeos la fecha limite
	            Calendar fechaAnterior = token.getFch_limite();
	            //LE añado uno hora del UTC
	            fechaAnterior.add(Calendar.HOUR_OF_DAY, 1);
	            if(actual.after(fechaAnterior)) {
	            	Alerta.Alerta(request,"Paso el Tiempo de cambiar contraseña","error");
	            }
	            // Verificar si se obtuvo el usuario
	            if (usuarioActual != null) {
	                // Actualizar la contraseña en el objeto UsuarioDTO
	                usuarioActual.setClaveUsuario(clave1);
	                // Realizar la actualización en el sistema externo
	                return acciones.ActualizarUsuario(usuarioActual);
	            } else {
	                System.out.println("No se pudo obtener el usuario para actualizar la contraseña.");
	                Alerta.Alerta(request,"No se pudo obtener el usuario para actualizar la contraseña. ","error");
	                return false;
	            }

	        } catch (Exception e) {
	            // Manejar cualquier excepción
	            e.printStackTrace();
	            Alerta.Alerta(request,"Hubo un erro intenlo de nuevo mas tarde","error");
	            return false;
	        }
	}


}

