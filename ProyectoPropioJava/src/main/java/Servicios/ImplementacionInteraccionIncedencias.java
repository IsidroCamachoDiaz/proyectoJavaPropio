package Servicios;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Utilidades.implementacionCRUD;
import jakarta.servlet.http.HttpServletRequest;

public class ImplementacionInteraccionIncedencias implements InterfaceInteraccionIncidencias {

	@Override
	public boolean CrearIncidencia(SolicitudDTO solicitud, IncidenciaDTO incidencia, HttpServletRequest request) {
		 implementacionCRUD acciones=new implementacionCRUD();
		return acciones.InsertarSolicitud(solicitud);
	}

}
