package Servicios;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownServiceException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TokenDTO;
import Dtos.UsuarioDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.Escritura;
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
			//Declaramos todo lo que necesitemos
				
				UsuarioDTO usuarioBD;
		        HttpSession session = request.getSession();
		        implementacionCRUD acciones = new implementacionCRUD();
		        
		        //Cogemos el usuario de la base de datos
		        try {
				usuarioBD =acciones.SeleccionarUsuario("SelectCorreo/"+user.getEmailUsuario());
				if(usuarioBD==null) {
					Escritura.EscribirFichero("No se encontro al usuario");
					throw new RuntimeException();
				}
		        }catch(RuntimeException e) {
		        	Escritura.EscribirFichero("Se busco a un usuario pero no puso bien los datos");
		        	Alerta.Alerta(request,"El Correo y/o contraseña es incorrecto", "error");
		        	return false;
		        }
				//Si no es igual lo manda al login con aviso
	            if(user.getClaveUsuario().equals(usuarioBD.getClaveUsuario())) {
	            	//Comprobamos is verifico la cuenta
	            	if(!usuarioBD.isAlta()) {
	            		Escritura.EscribirFichero("Un usuario a intentado logearse pero no esta dado de alta");
	            		Alerta.Alerta(request,"El usuario no esta dado de alta en la web", "error");
	            		return false;
	            	}
	            	//Comprobamos si esta dado de baja
	            	else if(usuarioBD.getFechaBaja()!=null) {
	            		Escritura.EscribirFichero("Un usuario a intentado darse de alta pero suc uenta esta dada de baja");
	            		Alerta.Alerta(request,"Su cuenta ha sido dada de baja gracias por confiar en nosotros", "info");
	            		return false;
	            	}
	            	//Es un usuario valido
	            	else {
	            		//Asignamos el usuario y el control de acceso de vada usuario
	                	session.setAttribute("usuario",usuarioBD.getIdUsuario());
	            		if(usuarioBD.getAcceso().getCodigoAcceso().equals("Usuario")) {
	            			session.setAttribute("acceso","1");
	            		}
	            		else if(usuarioBD.getAcceso().getCodigoAcceso().equals("Empleado")) {
	            			session.setAttribute("acceso","2");
	            		}
	            		else {
	            			session.setAttribute("acceso","3");
	            		}
	            		
	            		Escritura.EscribirFichero("Un usuario se logeo bien en la web");
	            		return true;
	            	}
	            }
	            //El usuario no puso bien los datos al logearse
	            else {
	            	Escritura.EscribirFichero("Un usuario intento logearse en la aplicacion pero no puso bien los valores");
					Alerta.Alerta(request,"El Correo y/o Contraseña son incorrectos","error");
	            	return false;
	            }
		}catch(Exception e) {
			Alerta.Alerta(request,"Hubo un error intentelo mas tarde","error");
			Escritura.EscribirFichero("Hubo un error en el login "+e.getLocalizedMessage());
			System.out.println(e.getLocalizedMessage());
			return false;
		}
	}
	
	@Override
	public boolean RegistrarUsuario(UsuarioDTO usu,HttpServletRequest request) {
		
		try{
			//Declaramos lo que necesitemos
			implementacionCRUD acciones = new implementacionCRUD();
			
			//Creamos un usuario para buscar si el correo que se regsitre esta asociado a un cuenta
			UsuarioDTO usuarioSiHay =acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
			
			//Si no es nulo existe una cuenta
			if(usuarioSiHay!=null) {
				Alerta.Alerta(request, "Ya existe una cuenta con ese correo", "error");
				Escritura.EscribirFichero("Una persona intento registarse pero ya hay una cuenta con ese correo");
				return false;
			}
			//SI no exite se inserta el usuario con los datos
			else {
				acciones.InsertarUsuario(usu);
	            
				//Cogemos el usuario por el correo y lo tenemos con el id
	            UsuarioDTO usuId=acciones.SeleccionarUsuario("SelectCorreo/"+usu.getEmailUsuario());
	            
	            Correo correo=new Correo();
	            //Comprobamos si se envia bien el correo de alta
	            if(correo.EnviarCorreoToken(usuId)) {
	            	Escritura.EscribirFichero("Un usuario creo bien una cuenta y se le envio el correo de alta");
	            	return true;
	            }
	            //Si no se envia bien se le avisa al usuario
	            else {
	            	Escritura.EscribirFichero("Un usuario intento registrase pero no se le pudo enviar el correo");
	            	return false;
	            }
			}	            
			}catch (IllegalStateException e) {
				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Ya esta uso el metodo para insertar el usuario. |"+e);
			}catch (NullPointerException e) {
				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Hay un componente nulo. |"+e);
			}catch (SecurityException e) {
				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] El gerente de seguridad para indicar una violación de seguridad. |"+e);
			}catch (IndexOutOfBoundsException e) {
				Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-RegistrarUsuario] Algun tipo (como una matriz, una cadena o un vector) está fuera de rango. |"+e);
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
				Escritura.EscribirFichero("Un usuario olvido la contraseña pero no puso un correo qu no esta asociada a njinguna cuenta");
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
				 Escritura.EscribirFichero("Un usuario olvido la contraseña y se le envio un correo para que la cambie");
				 return true;
			 }
			 //Si no se manda bien el correo se le avisa al usuario
			 else {
				 Alerta.Alerta(request, "Hubo un erroe intentelo mas tarde", "error");
				 Escritura.EscribirFichero("Un usuario olvido la contraseña pero no se le pudo mandar el correo");
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
			Escritura.EscribirFichero("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El .properties es nulo. |"+e);
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
	            
	            //Le añado uno hora del UTC
	            fechaAnterior.add(Calendar.HOUR_OF_DAY, 1);
	            
	            //Comprobamos si paso el tiempo para cambiar la contraseña
	            if(actual.after(fechaAnterior)) {
	            	Escritura.EscribirFichero("Un usuario quiso modificar una contraseña pero se le paso el tiempo para cambiarla");
	            	Alerta.Alerta(request,"Paso el Tiempo de cambiar contraseña","error");
	            }
	            // Verificar si se obtuvo el usuario
	            if (usuarioActual != null) {
	                // Actualizar la contraseña en el objeto UsuarioDTO
	                usuarioActual.setClaveUsuario(clave1);
	                
	                // Realizar la actualización en el sistema externo
	                Escritura.EscribirFichero("Un usuario cambio la contraseña correctamente");
	                return acciones.ActualizarUsuario(usuarioActual);
	            } else {
	                System.out.println("No se pudo obtener el usuario para actualizar la contraseña.");
	                Alerta.Alerta(request,"No se pudo obtener el usuario para actualizar la contraseña. ","error");
	                Escritura.EscribirFichero("Un usuario intento cambiar la co ntraseña pero no se encontro su usuario");
	                return false;
	            }

	        } catch (Exception e) {
	            // Manejar cualquier excepción
	            e.printStackTrace();
	            Alerta.Alerta(request,"Hubo un erro intenlo de nuevo mas tarde","error");
	            Escritura.EscribirFichero("Hubo un error en cambiar la contraseña "+e.getLocalizedMessage());
	            return false;
	        }
	}

	@Override
	public boolean eliminarUsuario(UsuarioDTO usu, HttpServletRequest request) {
		//Declaramos loq ue necesitemos
		implementacionCRUD acciones = new implementacionCRUD();
		
		//Cogemos tambien todos los tokens y filtramos por las suyas
		List <TokenDTO> tokens= acciones.SeleccionarTodosTokens();
		tokens= tokens.stream()
		        .filter(t -> t.getId_usuario().getIdUsuario() == usu.getIdUsuario())
		        .collect(Collectors.toList());
		
		//Comprobamos que tipo de acceso tiene el usuario
		if(usu.getAcceso().getCodigoAcceso().equals("Administrador")||usu.getAcceso().getCodigoAcceso().equals("Empleado")) {
			List <IncidenciaDTO> incidencias = acciones.SeleccionarTodasIncidencias();
			
			//Filtramos por las de le usuario
			incidencias=incidencias.stream()
	                .filter(incidencia -> incidencia.getEmpleado().getIdUsuario() == usu.getIdUsuario())
	                .collect(Collectors.toList());
			//Comprobamos si esta vacio
			if(incidencias.isEmpty()) {
				//Si no tiene borramos todo lo que tengamos del usuario
				for(int i=0;i<tokens.size();i++) {
					acciones.EliminarToken(String.valueOf(tokens.get(i).getIdToken()));
				}
				

				acciones.EliminarUsuario(String.valueOf(usu.getIdUsuario()));				
				Alerta.Alerta(request, "Se elimino Totalmente al usuario", "success");
				Escritura.EscribirFichero("Se elimino un usuario por completo "+usu.getNombreUsuario());
				return true;
			}
			//Si es usuario
			else {
				usu.setFechaBaja(Calendar.getInstance());
				acciones.ActualizarUsuario(usu);
				for(IncidenciaDTO in:incidencias) {
					if(in.isEstado()==false) {
					in.setEmpleado(null);
					acciones.ActualizarIncidencia(in);
					}
				}
				Alerta.Alerta(request, "Se dio de Baja al Usuario", "success");
				Escritura.EscribirFichero("Un usuario se dio de baja en la web "+usu.getNombreUsuario());
				return true;
			}
		}
		else {
			//Si es usuario cogemos todas las solicitudes y las filtramos por las suyas
			List <SolicitudDTO> solicitudes=acciones.SeleccionarTodasSolicitudes();
			
			solicitudes = solicitudes.stream()
	                .filter(solicitud -> solicitud.getCliente().getIdUsuario() == usu.getIdUsuario())
	                .collect(Collectors.toList());
			
			//Comprobamos si tiene solicitudes o no
			if(solicitudes.isEmpty()) {
				//Si no tiene borramos todo lo que tengamos del usuario
				for(int i=0;i<tokens.size();i++) {
					acciones.EliminarToken(String.valueOf(tokens.get(i).getIdToken()));
				}
				
				acciones.EliminarUsuario(String.valueOf(usu.getIdUsuario()));
				
				Alerta.Alerta(request, "Se elimino Totalmente al usuario", "success");
				Escritura.EscribirFichero("Se elimino un usuario por completo "+usu.getNombreUsuario());
				return true;
			}
			//Si tiene solicitudes lo damos de baja y actualizamos el usuario
			else {
				usu.setFechaBaja(Calendar.getInstance());
				acciones.ActualizarUsuario(usu);
				Alerta.Alerta(request, "Se dio de Baja al Usuario", "success");
				Escritura.EscribirFichero("Un usuario se dio de baja en la web "+usu.getNombreUsuario());
				return true;
			}
		}
	}


}

