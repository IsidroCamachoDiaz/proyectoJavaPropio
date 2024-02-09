package Servicios;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface InterfaceInteraccionIncidencias {
	public boolean CrearIncidencia(SolicitudDTO solicitud,IncidenciaDTO incidencia,HttpServletRequest request);
	
	public boolean FinalizarIncidencia(IncidenciaDTO incidencia,HttpServletRequest request);
}
