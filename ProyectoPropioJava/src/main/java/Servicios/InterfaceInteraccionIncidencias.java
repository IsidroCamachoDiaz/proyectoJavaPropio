package Servicios;

import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TrabajoDTO;
import Dtos.UsuarioDTO;
import jakarta.servlet.http.HttpServletRequest;
/**
 * La interfaz InterfaceInteraccionIncidencias proporciona métodos para interactuar con las incidencias.
 * Permite la inserción y finalización de incidencias en el sistema.
 *
 * @since 7/2/24
 * @author Isidro Camacho Diaz
 */
public interface InterfaceInteraccionIncidencias {
	 /**
     * Método insertar las incidencias
     *
     * @param request para mostrar las laertas
     * @param solicitud que se insertara junto a la incidencia
     * @return true si se creo bien y false si hubo algun problema
     * @since 7/2/24
     */
	public boolean CrearIncidencia(SolicitudDTO solicitud,HttpServletRequest request);
	
	/**
     * Método para finalizar una incidencia
     *
     * @param request para mostrar las laertas
     * @param incidencia que se va a finalizar
     * @return true si se finalizo bien y false si hubo algun problema
     * @since 9/2/24
     */	
	public boolean FinalizarIncidencia(IncidenciaDTO incidencia,HttpServletRequest request);
	
	/**
     * Método para insertar trabajos en la base de datos
     *
     * @param request para mostrar las alertas
     * @param Trabjo para meter en la BD
     * @return true si se creo bien y false si hubo algun problema
     * @since 18/2/24
     */	
	public boolean CrearTrabajo(TrabajoDTO trabajo, HttpServletRequest request);
	
	/**
     * Método para finalizar los trabajos asignados de la incdencia
     *
     * @param request para mostrar las alertas
     * @param Trabjo para finalizar
     * @return true si se finalizo bien y false si hubo algun problema
     * @since 19/2/24
     */	
	public boolean FinalizarTrabajo(TrabajoDTO trabajo, HttpServletRequest request);
}
