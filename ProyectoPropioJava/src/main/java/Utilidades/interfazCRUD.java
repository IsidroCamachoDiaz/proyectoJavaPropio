package Utilidades;

import Dtos.AccesoDTO;
import Dtos.IncidenciaDTO;
import Dtos.SolicitudDTO;
import Dtos.TipoTrabajoDTO;
import Dtos.TokenDTO;
import Dtos.TrabajoDTO;
import Dtos.UsuarioDTO;
import java.util.List;

/**
 * Esta interfaz define operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para diversas entidades
 * como Usuario, Acceso, Incidencia, Solicitud, TipoTrabajo, Token y Trabajo a través de una API REST.
 *@author Isidro Camacho Diaz
 */
public interface interfazCRUD {
	/**
	 * Realiza una solicitud GET para seleccionar un usuario por su identificador.
	 *
	 * @param url Identificador del usuario a seleccionar.
	 * @return Objeto UsuarioDTO correspondiente al identificador proporcionado.
	 */
	public UsuarioDTO SeleccionarUsuario(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar un acceso por su identificador.
	 *
	 * @param url Identificador del acceso a seleccionar.
	 * @return Objeto AccesoDTO correspondiente al identificador proporcionado.
	 */
	
	public AccesoDTO SeleccionarAcceso(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar una incidencia por su identificador.
	 *
	 * @param url Identificador de la incidencia a seleccionar.
	 * @return Objeto IncidenciaDTO correspondiente al identificador proporcionado.
	 */
	
	public IncidenciaDTO SeleccionarIncidencia(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar una solicitud por su identificador.
	 *
	 * @param url Identificador de la solicitud a seleccionar.
	 * @return Objeto SolicitudDTO correspondiente al identificador proporcionado.
	 */
	
	public SolicitudDTO SeleccionarSolicitud(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar un tipo de trabajo por su identificador.
	 *
	 * @param url Identificador del tipo de trabajo a seleccionar.
	 * @return Objeto TipoTrabajoDTO correspondiente al identificador proporcionado.
	 */
	
	public TipoTrabajoDTO SeleccionarTipoDeTrabajo(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar un token por su identificador.
	 *
	 * @param url Identificador del token a seleccionar.
	 * @return Objeto TokenDTO correspondiente al identificador proporcionado.
	 */
	
	public TokenDTO SeleccionarToken(String url);
	
	/**
	 * Realiza una solicitud GET para seleccionar un trabajo por su identificador.
	 *
	 * @param url Identificador del trabajo a seleccionar.
	 * @return Objeto TrabajoDTO correspondiente al identificador proporcionado.
	 */
	
	public TrabajoDTO SeleccionarTrabajo(String url);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo usuario en la base de datos.
	 *
	 * @param usuarioMeter Objeto UsuarioDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarUsuario(UsuarioDTO usuarioMeter);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo usuario como administrador en la base de datos.
	 *
	 * @param usuarioMeter Objeto UsuarioDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	public boolean InsertarUsuarioAdministrador(UsuarioDTO usuarioMeter);
	
	/**
	 * Realiza una solicitud POST para insertar una nueva incidencia en la base de datos.
	 *
	 * @param incidenciaMeter Objeto IncidenciaDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarIncidencia(IncidenciaDTO incidenciaMeter);
	
	/**
	 * Realiza una solicitud POST para insertar una nueva solicitud en la base de datos.
	 *
	 * @param solicitudMeter Objeto SolicitudDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarSolicitud(SolicitudDTO solicitudMeter);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo tipo de trabajo en la base de datos.
	 *
	 * @param tipoTrabajoMeter Objeto TipoTrabajoDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarTipoDeTrabajo(TipoTrabajoDTO tipoTrabajoMeter);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo token en la base de datos.
	 *
	 * @param tokenMeter Objeto TokenDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarToken(TokenDTO tokenMeter);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo trabajo en la base de datos.
	 *
	 * @param trabajoMeter Objeto TrabajoDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarTrabajo(TrabajoDTO trabajoMeter);
	
	/**
	 * Realiza una solicitud POST para insertar un nuevo acceso en la base de datos.
	 *
	 * @param accesoMeter Objeto AccesoDTO que se desea insertar.
	 * @return true si la operación de inserción fue exitosa, false de lo contrario.
	 */
	
	public boolean InsertarAcceso(AccesoDTO accesoMeter);
	
	/**
	 * Realiza una solicitud DELETE para eliminar un acceso de la base de datos.
	 *
	 * @param url Identificador del acceso que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarAcceso(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar una incidencia de la base de datos.
	 *
	 * @param url Identificador de la incidencia que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarIncidencia(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar una solicitud de la base de datos.
	 *
	 * @param url Identificador de la solicitud que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarSolicitud(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar un tipo de trabajo de la base de datos.
	 *
	 * @param url Identificador del tipo de trabajo que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarTipoDeTrabajo(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar un token de la base de datos.
	 *
	 * @param url Identificador del token que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarToken(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar un trabajo de la base de datos.
	 *
	 * @param url Identificador del trabajo que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarTrabajo(String url);
	
	/**
	 * Realiza una solicitud DELETE para eliminar un usuario de la base de datos.
	 *
	 * @param url Identificador del usuario que se desea eliminar.
	 * @return true si la operación de eliminación fue exitosa, false de lo contrario.
	 */
	
	public boolean EliminarUsuario(String url);
	
	/**
	 * Realiza una solicitud PUT para actualizar una incidencia en la base de datos.
	 *
	 * @param nuevaIncidencia Objeto IncidenciaDTO con los nuevos datos.
	 * @return true si la operación de actualización fue exitosa, false de lo contrario.
	 */
	
	public boolean ActualizarIncidencia(IncidenciaDTO nuevaIncidencia);
	
	/**
	 * Realiza una solicitud PUT para actualizar una solicitud en la base de datos.
	 *
	 * @param nuevaSolicitud Objeto SolicitudDTO con los nuevos datos.
	 * @return true si la operación de actualización fue exitosa, false de lo contrario.
	 */
	
	public boolean ActualizarSolicitud(SolicitudDTO nuevaSolicitud);
	
	/**
	 * Realiza una solicitud PUT para actualizar un tipo de trabajo en la base de datos.
	 *
	 * @param nuevoTipoTrabajo Objeto TipoTrabajoDTO con los nuevos datos.
	 * @return true si la operación de actualización fue exitosa, false de lo contrario.
	 */
	
	public boolean ActualizarTipoDeTrabajo(TipoTrabajoDTO nuevoTipoTrabajo);
	
	/**
	 * Realiza una solicitud PUT para actualizar un trabajo en la base de datos.
	 *
	 * @param nuevoTrabajo Objeto TrabajoDTO con los nuevos datos.
	 * @return true si la operación de actualización fue exitosa, false de lo contrario.
	 */
	
	public boolean ActualizarTrabajo(TrabajoDTO nuevoTrabajo);
	
	/**
	 * Realiza una solicitud PUT para actualizar un usuario en la base de datos.
	 *
	 * @param nuevoUsuario Objeto UsuarioDTO con los nuevos datos.
	 * @return true si la operación de actualización fue exitosa, false de lo contrario.
	 */
	
	public boolean ActualizarUsuario(UsuarioDTO nuevoUsuario);
	
	/**
	 * Realiza una solicitud GET para seleccionar todos los usuarios.
	 *
	 * @return Lista de objetos UsuarioDTO.
	 */
	
	public List<UsuarioDTO> SeleccionarTodosUsuarios();
	
	/**
	 * Realiza una solicitud GET para seleccionar todas las incidencias.
	 *
	 * @return Lista de objetos IncidenciaDTO.
	 */
	
	public List<IncidenciaDTO> SeleccionarTodasIncidencias();
	
	/**
	 * Realiza una solicitud GET para seleccionar todas las solicitudes.
	 *
	 * @return Lista de objetos SolicitudDTO.
	 */
	
	public List<SolicitudDTO> SeleccionarTodasSolicitudes();
	
	/**
	 * Realiza una solicitud GET para seleccionar todos los tipos de trabajo.
	 *
	 * @return Lista de objetos TipoTrabajoDTO.
	 */

	public List<TipoTrabajoDTO> SeleccionarTodosTiposDeTrabajo();
	
	/**
	 * Realiza una solicitud GET para seleccionar todos los trabajos.
	 *
	 * @return Lista de objetos TrabajoDTO.
	 */
	
	public List<TrabajoDTO> SeleccionarTodosTrabajos();
	
	/**
	 * Realiza una solicitud GET para seleccionar todos los tokens.
	 *
	 * @return Lista de objetos TokenDTO.
	 */
	
	public List<TokenDTO> SeleccionarTodosTokens();
	
	/**
	 * Realiza una solicitud GET para seleccionar todos los accesos.
	 *
	 * @return Lista de objetos AccesoDTO.
	 */
	
	public List<AccesoDTO> SeleccionarTodosAccesos();
}
