package Servicios;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.TrabajoDTO;
import Utilidades.Alerta;
import Utilidades.Correo;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;

public class ImplementacionInteraccionTipos implements InterfaceInteraccionTipos {

	@Override
	public boolean CrearTipo(TipoTrabajoDTO tipo, HttpServletRequest request) {
		implementacionCRUD acciones = new implementacionCRUD();
		if(acciones.InsertarTipoDeTrabajo(tipo)) {
			Alerta.Alerta(request, "Se inserto correctamente el tipo de trabajo ya se puede usar", "success");
			return true;
		}
		else {
			Alerta.Alerta(request, "Hubo un error a la hora de insertar el tipo", "error");
			return false;
		}
		
	}

}
