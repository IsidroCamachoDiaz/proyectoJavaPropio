package Utilidades;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;

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

import Dtos.TokenDTO;
import Dtos.UsuarioDTO;
import Servicios.ImplentacionIntereaccionUsuario;

public class Correo {

	public boolean EnviarCorreoToken(UsuarioDTO usuario) {
		implementacionCRUD acciones = new implementacionCRUD();
		 try {
				Properties seguridad = new Properties();
				seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));
				//Aqui tiene que ir el buscar el usuario por el correo
				//Meter en un if si el usuario se encontro que haga el resto (tiene que llegar hasta ok=EnviarMensaje...)
				
				//Aqui generas la fecha limite con un tiempo de 10 minutos
				//Aqui una vez encuentre el usuario insertas el token generado arriba y 
				//lo inserta con la fecha limite, id_Usuario
				
				UUID uuid = UUID.randomUUID();
				String token = uuid.toString();
				//Fecha Limite
				Calendar fechaLimite=Calendar.getInstance();
				fechaLimite.add(Calendar.MINUTE, 10);
				
				//Creo token
				TokenDTO tk= new TokenDTO(token,fechaLimite,usuario);
				if(acciones.InsertarToken(tk)) {
					String mensaje=MensajeCorreoAlta(token);
					EnviarMensaje(mensaje,usuario.getEmailUsuario(),true,"Solicitud De Alta",seguridad.getProperty("correo"),true);
					return true;
				}
				else {
				return false;
				}
				 
			}catch(IOException e)
			{
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se pudo leer el .properties. |"+e);
				return false;
			}
			catch(NullPointerException e)
			{
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El .properties es nulo. |"+e);
				return false;
			}	
	}
	
	/**
	 * Metodo donde crea el cuerpo del correo
	 * @param token
	 * @param direccion
	 * @return String
	 */
	public String MensajeCorreo(String token, String direccion) {
	    return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
	            + "    <p style=\"color: white;\">Se ha enviado una solicitud para restablecer la contraseña. Si no has solicitado esto, cambia tu contraseña de inmediato.</p>\r\n"
	            + "    <p style=\"color: white;\">Si has solicitado el restablecimiento de contraseña, por favor haz clic en el siguiente enlace:</p>\r\n"
	            + "    <a href=\"" + direccion + "?tk=" + token + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Restablecer contraseña</button></a>\r\n"
	            + "    <p style=\"color: white;\">Gracias por confiar en nosotros.</p>\r\n"
	            + "</div>";
	}
	
	public String MensajeCorreoAlta(String direccion) {
	    return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
	            + "    <p style=\"color: white;\">Bienvenido a nuestra plataforma. Tu cuenta ha sido creada con éxito.</p>\r\n"
	            + "    <p style=\"color: white;\">Para comenzar a utilizar nuestros servicios, por favor, haz clic en el siguiente enlace:</p>\r\n"
	            + "    <a href=\"http://localhost:8081/ProyectoGetPa/altaHecha.jsp?tk=" + direccion + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Activar cuenta</button></a>\r\n"
	            + "    <p style=\"color: white;\">Gracias por unirte a nosotros.</p>\r\n"
	            + "</div>";
	}
	
	/**
	 * Metodo para enviar correo al usuario introducido
	 * @param body
	 * @param to
	 * @param html
	 * @param subject
	 * @param frommail
	 * @param cco
	 * @return boolean
	 */
	public boolean EnviarMensaje(String body, String to, boolean html, String subject,String frommail, boolean cco)  {
		boolean resultado = true;
		Transport tp = null;
		try {
			Properties seguridad = new Properties();
			seguridad.load(
					ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));

			// Parametros de coneccion con un correo de ionos
			String host = "smtp.ionos.es";
			String miLogin = seguridad.getProperty("usuarioCorreo");
			String miPassword = seguridad.getProperty("contraCorreo");
			InternetAddress addressFrom;
			InternetAddress[] addressReply = new InternetAddress[1];

			// Obtener propiedades del sistema
			Properties properties = System.getProperties();

			// Configurar servidor de correo
			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.port", "587");
			properties.setProperty("mail.smtp.user", miLogin);
			properties.setProperty("mail.smtp.auth", "true");

			// Obtenga el objeto de sesión predeterminado.
			Session session2 = Session.getInstance(properties);
			
			//Configura los parametros para conectarse
			Message msg = new MimeMessage(session2);
			
			//La direccion de quien lo envia
			addressFrom = new InternetAddress("'SystemRevive' <" + seguridad.getProperty("correo") + ">");
			msg.setFrom(addressFrom);
			
			//A quien envia el correo
			addressReply[0] = new InternetAddress(frommail);
			msg.setReplyTo(addressReply);
			InternetAddress addressTo = new InternetAddress();
			addressTo = new InternetAddress(to);

			InternetAddress[] addressToOK = new InternetAddress[1];
			addressToOK[0] = addressTo;
			//Establece la dirreccion a quien lo envia
			msg.setRecipients(RecipientType.TO, addressToOK);
			if (cco)
				msg.addRecipients(RecipientType.BCC, addressReply);
			//Establece el asunto de este mensaje.
			msg.setSubject(subject);
			//Construye el body
			if (html) {
				body = body;
				msg.setContent(body, "text/html; charset=ISO-8859-1");
			} else {
				msg.setContent(body, "text/plain; charset=ISO-8859-1");
			}
			//Envia el mensaje
			tp = session2.getTransport("smtp");
			tp.connect((String) properties.get("mail.smtp.user"), miPassword);
			tp.sendMessage(msg, msg.getAllRecipients());

		} catch (AddressException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje]  Falló en el análisis. |" + e);
			resultado = false;
		} catch (SecurityException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje] No se permite el acceso a la propiedad. |"+ e);

		} catch (NullPointerException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-EnviarMensaje] El .properties es nulo. |" + e);
			resultado = false;
		} catch (SendFailedException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] La direccion es no valida|" + e);
			resultado = false;
		} catch (IllegalWriteException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se admite la modificación de los valores existentes en la implementación subyacente. |"+ e);
			resultado = false;
		} catch (IllegalStateException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] El mensaje se intenta optener de una carpeta READ_ONLY. |"+ e);
			resultado = false;
		} catch (NoSuchProviderException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se encuentra el proveedor para el protocolo dado. |"+ e);
			resultado = false;
		} catch (IOException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] No se pudo leer el .properties. |"+ e);
			resultado = false;
		}catch (MessagingException e) {
			System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] Se produjo un fallo. |" + e);
			resultado = false;
		} finally {
			try {
				tp.close();
			} catch (MessagingException e) {
				System.err.println("[ERROR-ImplentacionIntereaccionUsuario-OlvidarClaveUsuario] Error al cerrar. |" + e);
				resultado = false;
			}
		}
		return resultado;
	}
}
