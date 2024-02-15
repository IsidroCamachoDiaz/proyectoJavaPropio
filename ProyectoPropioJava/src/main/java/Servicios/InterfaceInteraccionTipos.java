package Servicios;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;
/**
 * La interfaz InterfaceInteraccionIncidencias proporciona métodos para interactuar con las incidencias.
 * Permite la inserción y finalización de incidencias en el sistema.
 *
 * @since 7/2/24
 * @author Isidro Camacho Diaz
 */
public interface InterfaceInteraccionTipos {
	 /**
     * Método insertar las incidencias
     *
     * @param request para mostrar las laertas
     * @param solicitud que se insertara junto a la incidencia
     * @param incidencia que se creara en la base de datos
     * @return true si se creo bien y false si hubo algun problema
     * @since 7/2/24
     */
	public boolean CrearTipo(TipoTrabajoDTO tipo,HttpServletRequest request);
	
}
