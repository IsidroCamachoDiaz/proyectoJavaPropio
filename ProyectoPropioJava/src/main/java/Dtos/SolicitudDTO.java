package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SolicitudDTO {
	
	 @JsonProperty("id_solicitud")
	    private int idSolicitud;
	 
	 @JsonProperty("descripcion")
	 private String descripcion;
		
	 @JsonProperty("estado")
		private boolean estado;
		
	 @JsonProperty("fch_limite")
		private Calendar fechaSolicitud;
	 
	 @JsonProperty("usuario_solicitud")
	 private UsuarioDTO usuarioSolicitud;
	 
	 @JsonProperty("incidencia")
	 private IncidenciaDTO incidenciaSolicitud;

	 /**
	  * Constructor para crear un objeto SolicitudDTO con todos los atributos.
	  * 
	  * @param descripcion           Descripción de la solicitud.
	  * @param estado                Estado de la solicitud.
	  * @param fechaSolicitud        Fecha de la solicitud.
	  * @param usuario               Objeto UsuarioDTO asociado a la solicitud.
	  */
	 public SolicitudDTO(String descripcion, boolean estado, Calendar fechaSolicitud, UsuarioDTO usuario) {
	     super();
	     this.descripcion = descripcion;
	     this.estado = estado;
	     this.fechaSolicitud = fechaSolicitud;
	     this.usuarioSolicitud = usuario;
	 }

	 /**
	  * Constructor para crear un objeto SolicitudDTO con algunos atributos.
	  * 
	  * @param idSolicitud           Identificador único de la solicitud.
	  * @param descripcion           Descripción de la solicitud.
	  * @param estado                Estado de la solicitud.
	  * @param fechaSolicitud        Fecha de la solicitud.
	  */
	 public SolicitudDTO(int idSolicitud, String descripcion, boolean estado, Calendar fechaSolicitud) {
	     super();
	     this.idSolicitud = idSolicitud;
	     this.descripcion = descripcion;
	     this.estado = estado;
	     this.fechaSolicitud = fechaSolicitud;
	 }

	 /**
	  * Constructor predeterminado para crear un objeto SolicitudDTO sin atributos.
	  */
	 public SolicitudDTO() {
	     super();
	 }

	 /**
	  * Obtiene el identificador único de la solicitud.
	  * 
	  * @return Identificador único de la solicitud.
	  */
	 public int getIdSolicitud() {
	     return idSolicitud;
	 }

	 /**
	  * Establece el identificador único de la solicitud.
	  * 
	  * @param idSolicitud Nuevo identificador único de la solicitud.
	  */
	 public void setIdSolicitud(int idSolicitud) {
	     this.idSolicitud = idSolicitud;
	 }

	 /**
	  * Obtiene la descripción de la solicitud.
	  * 
	  * @return Descripción de la solicitud.
	  */
	 public String getDescripcion() {
	     return descripcion;
	 }

	 /**
	  * Establece la descripción de la solicitud.
	  * 
	  * @param descripcion Nueva descripción de la solicitud.
	  */
	 public void setDescripcion(String descripcion) {
	     this.descripcion = descripcion;
	 }

	 /**
	  * Verifica el estado de la solicitud.
	  * 
	  * @return true si la solicitud está en un estado activo, false de lo contrario.
	  */
	 public boolean isEstado() {
	     return estado;
	 }

	 /**
	  * Establece el estado de la solicitud.
	  * 
	  * @param estado Nuevo estado de la solicitud.
	  */
	 public void setEstado(boolean estado) {
	     this.estado = estado;
	 }

	 /**
	  * Obtiene la fecha de la solicitud.
	  * 
	  * @return Fecha de la solicitud.
	  */
	 public Calendar getFechaSolicitud() {
	     return fechaSolicitud;
	 }

	 /**
	  * Establece la fecha de la solicitud.
	  * 
	  * @param fechaSolicitud Nueva fecha de la solicitud.
	  */
	 public void setFechaSolicitud(Calendar fechaSolicitud) {
	     this.fechaSolicitud = fechaSolicitud;
	 }

	 /**
	  * Obtiene el objeto UsuarioDTO asociado a la solicitud.
	  * 
	  * @return Objeto UsuarioDTO asociado a la solicitud.
	  */
	 public UsuarioDTO getUsuarioSolicitud() {
	     return usuarioSolicitud;
	 }

	 /**
	  * Establece el objeto UsuarioDTO asociado a la solicitud.
	  * 
	  * @param usuarioSolicitud Nuevo objeto UsuarioDTO asociado a la solicitud.
	  */
	 public void setUsuarioSolicitud(UsuarioDTO usuarioSolicitud) {
	     this.usuarioSolicitud = usuarioSolicitud;
	 }

	 /**
	  * Obtiene el objeto IncidenciaDTO asociado a la solicitud.
	  * 
	  * @return Objeto IncidenciaDTO asociado a la solicitud.
	  */
	 public IncidenciaDTO getIncidenciaSolicitud() {
	     return incidenciaSolicitud;
	 }

	 /**
	  * Establece el objeto IncidenciaDTO asociado a la solicitud.
	  * 
	  * @param incidenciaSolicitud Nuevo objeto IncidenciaDTO asociado a la solicitud.
	  */
	 public void setIncidenciaSolicitud(IncidenciaDTO incidenciaSolicitud) {
	     this.incidenciaSolicitud = incidenciaSolicitud;
	 }

	 /**
	  * Representación en cadena del objeto SolicitudDTO.
	  * 
	  * @return Cadena que representa el objeto SolicitudDTO.
	  */
	 @Override
	 public String toString() {
	     return "SolicitudDTO [idSolicitud=" + idSolicitud + ", descripcion=" + descripcion + ", estado=" + estado
	             + ", fechaSolicitud=" + fechaSolicitud + ", usuarioSolicitud=" + usuarioSolicitud
	             + ", incidenciaSolicitud=" + incidenciaSolicitud + "]";
	 }

	
	 

}
