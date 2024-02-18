package Servicios;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TrabajoDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.Escritura;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;

public class ImplementacionInteraccionIncedencias implements InterfaceInteraccionIncidencias {

	@Override
	public boolean CrearIncidencia(SolicitudDTO solicitud, HttpServletRequest request) {
		//Declaramos lo que necesitemos
		 implementacionCRUD acciones=new implementacionCRUD();
		 //Insertamos la solicitud		
		return acciones.InsertarSolicitud(solicitud);
	}

	@Override
	public boolean FinalizarIncidencia(IncidenciaDTO incidencia, HttpServletRequest request) {
		//Se declara lo que se necesite
		Properties seguridad = new Properties();
		implementacionCRUD acciones=new implementacionCRUD();
		
		//Declaramos el propertiesd de los datos del correo
		try {
			seguridad.load(ImplementacionInteraccionIncedencias.class.getResourceAsStream("/Utilidades/parametros.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Correo co= new Correo();
		//Se crea un booleano para ver que cada trabajo se termino
		boolean terminado=true;
		
		//Se va comprobando que todos los trabajos esten terminados
		for(TrabajoDTO tra:incidencia.getTrabajosConIncidencias()) {
			if(tra.isEstado()==false) {
				terminado=false;
			}
		}
		
		//Si esta terminado se actualiza los campos
		if(terminado) {
			incidencia.setFecha_fin(Calendar.getInstance());
			incidencia.setEstado(true);
			incidencia.getSolicitud().setEstado(true);
			
			//Se manda un correo para avisar al usuario
			String mensaje=co.MensajeCorreoConfirmacionTerminada(incidencia.getSolicitud().getCliente().getNombreUsuario());
			
			//Se comprueba si se envio bien
			if(co.EnviarMensaje(mensaje, incidencia.getSolicitud().getCliente().getEmailUsuario(),
					true,"Tu Incidencia Ha Sido Resuelta",seguridad.getProperty("correo"),true)) {
				
				//Volvemos aponer la lista como nulo para que no de problemas
				incidencia.setTrabajosConIncidencias(null);
				
				//Se actualiza la incidencia
				acciones.ActualizarIncidencia(incidencia);
				Escritura.EscribirFichero("Un usuario finalizo una incidencia");
				return true;
			}
			//Si no se pudo finalizar se avisa al usuario
			else {
				Escritura.EscribirFichero("Se termino una incidencia pero no se le pudo enviar el correo al usuario");
				Alerta.Alerta(request, "No se pudo Enviar El correo al Usuario", "error");
				return false;
			}
		}
		//Si hay trabajos pendientes se avisa al empleado
		else {
			Alerta.Alerta(request, "La incidencia no se puede terminar porque hay tareas pendientes con esta incidencia", "error");
			Escritura.EscribirFichero("Un usuario usuario intento finalizar una incidencia pero la incidencia tienen trabajos pendientes");
			return false;
		}
		
	}
	@Override
	public boolean CrearTrabajo(TrabajoDTO trabajo, HttpServletRequest request) {
		//Declaramos lo que necesitemos
		 implementacionCRUD acciones=new implementacionCRUD();
		 //Insertamos el trabajo	
		return acciones.InsertarTrabajo(trabajo);
	}

}
