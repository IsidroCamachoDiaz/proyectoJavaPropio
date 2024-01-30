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

/**
 * Clase que proporciona funciones relacionadas con el envío de correos electrónicos,
 * especialmente en el contexto de interacción con usuarios, como el envío de tokens y mensajes de bienvenida.
 */
public class Correo {

    /**
     * Envía un correo electrónico con un token para el proceso de restablecimiento de contraseña.
     *
     * @param usuario Objeto UsuarioDTO que contiene la información del usuario.
     * @return true si el correo se envía con éxito, false de lo contrario.
     */
    public boolean EnviarCorreoToken(UsuarioDTO usuario) {
        // Implementación de acciones CRUD
        implementacionCRUD acciones = new implementacionCRUD();
        try {
            // Cargar propiedades de seguridad desde el archivo de propiedades
            Properties seguridad = new Properties();
            seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));

            // Generar un token único
            UUID uuid = UUID.randomUUID();
            String token = uuid.toString();

            // Establecer fecha límite (10 minutos desde ahora)
            Calendar fechaLimite = Calendar.getInstance();
            fechaLimite.add(Calendar.MINUTE, 10);

            // Crear objeto TokenDTO con el token, fecha límite y usuario
            TokenDTO tk = new TokenDTO(token, fechaLimite, usuario);

            // Insertar el token en la base de datos
            if (acciones.InsertarToken(tk)) {
                // Crear mensaje de correo
                String mensaje = MensajeCorreoAlta(token);
                // Enviar el mensaje al correo del usuario
                EnviarMensaje(mensaje, usuario.getEmailUsuario(), true, "Solicitud De Alta", seguridad.getProperty("correo"), true);
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            System.err.println("[ERROR-Correo-EnviarCorreoToken] No se pudo leer el .properties. |" + e);
            return false;
        } catch (NullPointerException e) {
            System.err.println("[ERROR-Correo-EnviarCorreoToken] El .properties es nulo. |" + e);
            return false;
        }
    }

    /**
     * Crea el cuerpo del correo para el proceso de restablecimiento de contraseña.
     *
     * @param token Token único generado para el restablecimiento de contraseña.
     * @return String que representa el cuerpo del correo.
     */
    public String MensajeCorreo(String token) {
        // Cuerpo del correo en formato HTML
        return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
                + "    <p style=\"color: white;\">Se ha enviado una solicitud para restablecer la contraseña. Si no has solicitado esto, cambia tu contraseña de inmediato.</p>\r\n"
                + "    <p style=\"color: white;\">Si has solicitado el restablecimiento de contraseña, por favor haz clic en el siguiente enlace:</p>\r\n"
                + "    <a href=\"http://localhost:8081/ProyectoGetPa/Modificar.jsp?tk=" + token + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Restablecer contraseña</button></a>\r\n"
                + "    <p style=\"color: white;\">Gracias por confiar en nosotros.</p>\r\n"
                + "</div>";
    }

    /**
     * Crea el cuerpo del correo para el mensaje de bienvenida y activación de cuenta.
     *
     * @param direccion Dirección para activar la cuenta.
     * @return String que representa el cuerpo del correo.
     */
    public String MensajeCorreoAlta(String direccion) {
        // Cuerpo del correo en formato HTML
        return "<div style=\"text-align: center; background-color: #7d2ae8; padding: 20px;\">\r\n"
                + "    <p style=\"color: white;\">Bienvenido a nuestra plataforma. Tu cuenta ha sido creada con éxito.</p>\r\n"
                + "    <p style=\"color: white;\">Para comenzar a utilizar nuestros servicios, por favor, haz clic en el siguiente enlace:</p>\r\n"
                + "    <a href=\"http://localhost:8081/ProyectoGetPa/altaHecha.jsp?tk=" + direccion + "\"><button style=\"background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px;\">Activar cuenta</button></a>\r\n"
                + "    <p style=\"color: white;\">Gracias por unirte a nosotros.</p>\r\n"
                + "</div>";
    }

    /**
     * Envía un mensaje de correo electrónico.
     *
     * @param body    Cuerpo del correo.
     * @param to      Dirección de correo del destinatario.
     * @param html    Indica si el cuerpo del correo está en formato HTML.
     * @param subject Asunto del correo.
     * @param frommail Dirección de correo del remitente.
     * @param cco     Indica si se utilizará copia oculta (CCO).
     * @return true si el correo se envía con éxito, false de lo contrario.
     */
    public boolean EnviarMensaje(String body, String to, boolean html, String subject, String frommail, boolean cco) {
        // Variable para almacenar el resultado del envío del correo
        boolean resultado = true;
        Transport tp = null;
        try {
            // Cargar propiedades de seguridad desde el archivo de propiedades
            Properties seguridad = new Properties();
            seguridad.load(ImplentacionIntereaccionUsuario.class.getResourceAsStream("/Utilidades/parametros.properties"));

            // Configuración del servidor de correo
            String host = "smtp.ionos.es";
            String miLogin = seguridad.getProperty("usuarioCorreo");
            String miPassword = seguridad.getProperty("contraCorreo");

            // Crear direcciones de correo
            InternetAddress addressFrom = new InternetAddress("'SystemRevive' <" + seguridad.getProperty("correo") + ">");
            InternetAddress[] addressReply = new InternetAddress[1];
            addressReply[0] = new InternetAddress(frommail);
            InternetAddress addressTo = new InternetAddress(to);
            InternetAddress[] addressToOK = new InternetAddress[1];
            addressToOK[0] = addressTo;

            // Propiedades del sistema
            Properties properties = System.getProperties();

            // Configurar servidor de correo
            properties.setProperty("mail.smtp.host", host);
            properties.setProperty("mail.smtp.starttls.enable", "true");
            properties.setProperty("mail.smtp.port", "587");
            properties.setProperty("mail.smtp.user", miLogin);
            properties.setProperty("mail.smtp.auth", "true");

            // Obtener objeto de sesión
            Session session2 = Session.getInstance(properties);

            // Configurar parámetros de conexión
            Message msg = new MimeMessage(session2);
            msg.setFrom(addressFrom);
            msg.setReplyTo(addressReply);
            msg.setRecipients(Message.RecipientType.TO, addressToOK);

            if (cco)
                msg.addRecipients(Message.RecipientType.BCC, addressReply);

            msg.setSubject(subject);

            if (html) {
                body = body;
                msg.setContent(body, "text/html; charset=ISO-8859-1");
            } else {
                msg.setContent(body, "text/plain; charset=ISO-8859-1");
            }

            // Enviar el mensaje
            tp = session2.getTransport("smtp");
            tp.connect((String) properties.get("mail.smtp.user"), miPassword);
            tp.sendMessage(msg, msg.getAllRecipients());

        } catch (AddressException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] Falló en el análisis. |" + e);
            resultado = false;
        } catch (SecurityException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] No se permite el acceso a la propiedad. |" + e);
        } catch (NullPointerException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] El .properties es nulo. |" + e);
            resultado = false;
        } catch (SendFailedException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] La dirección es no válida|" + e);
            resultado = false;
        } catch (IllegalWriteException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] No se admite la modificación de los valores existentes en la implementación subyacente. |" + e);
            resultado = false;
        } catch (IllegalStateException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] El mensaje se intenta obtener de una carpeta READ_ONLY. |" + e);
            resultado = false;
        } catch (NoSuchProviderException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] No se encuentra el proveedor para el protocolo dado. |" + e);
            resultado = false;
        } catch (IOException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] No se pudo leer el .properties. |" + e);
            resultado = false;
        } catch (MessagingException e) {
            System.err.println("[ERROR-Correo-EnviarMensaje] Se produjo un fallo. |" + e);
            resultado = false;
        } finally {
            try {
                tp.close();
            } catch (MessagingException e) {
                System.err.println("[ERROR-Correo-EnviarMensaje] Error al cerrar. |" + e);
                resultado = false;
            }
        }
        return resultado;
    }
}
