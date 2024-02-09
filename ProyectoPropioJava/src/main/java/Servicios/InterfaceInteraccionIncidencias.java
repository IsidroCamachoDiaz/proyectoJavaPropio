package Servicios;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface InterfaceInteraccionIncidencias {
	 /**
     * Método insertar las incidencias
     *
     * @param request para mostrar las laertas
     * @param solicitud que se insertara junto a la incidencia
     * @param incidencia que se creara en la base de datos
     * @return true si se creo bien y false si hubo algun problema
     * @since 7/2/24
     */
	public boolean CrearIncidencia(SolicitudDTO solicitud,IncidenciaDTO incidencia,HttpServletRequest request);
	
	/**
     * Método para finalizar una incidencia
     *
     * @param request para mostrar las laertas
     * @param incidencia que se va a finalizar
     * @return true si se finalizo bien y false si hubo algun problema
     * @since 9/2/24
     */	
	public boolean FinalizarIncidencia(IncidenciaDTO incidencia,HttpServletRequest request);
}
