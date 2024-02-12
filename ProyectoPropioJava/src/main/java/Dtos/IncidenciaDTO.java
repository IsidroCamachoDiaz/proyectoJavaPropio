package Dtos;

import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase que representa una incidencia con sus atributos y métodos asociados.
 * Esta clase es un Data Transfer Object (DTO) para transferir información de incidencias.
 * 
 * @author Isidro Camacho Diaz
 * 
 * @param id_incidencia Identificador de la incidencia
 * @param descripcion_usuario Descripción de la incidencia proporcionada por el usuario
 * @param descripcion_tecnica Descripción técnica de la incidencia
 * @param horas Horas dedicadas a resolver la incidencia
 * @param coste Coste asociado a la incidencia
 * @param estado Estado de la incidencia (resuelta o no)
 * @param fecha_inicio Fecha de inicio de la incidencia
 * @param fecha_fin Fecha de finalización de la incidencia
 * @param solicitud DTO de la solicitud asociada a la incidencia
 * @param empleado DTO del usuario empleado asociado a la incidencia
 * @param trabajosConIncidencias Lista de trabajos con incidencias asociadas a la incidencia
 */ 
public class IncidenciaDTO {
	@JsonProperty("id_incidencia")
	private int id_incidencia;
	
	@JsonProperty("descripcion_usuario")
	private String descripcion_usuario;
	
	@JsonProperty("descripcion_tecnica")
	private String descripcion_tecnica;
	
	@JsonProperty("horas")
	private int horas;
	
	@JsonProperty("coste")
	private float coste;
	
	@JsonProperty("estado")
	private boolean estado;
	
	@JsonProperty("fecha_inicio")
	private Calendar fecha_inicio;
	
	@JsonProperty("fecha_fin")
	private Calendar fecha_fin;
	
	@JsonProperty("solicitud")
	@JsonIgnoreProperties(value={"incidenciaSolicitud"},allowSetters=true)
	private SolicitudDTO solicitud;
	
	@JsonProperty("empleado")
	@JsonIgnoreProperties(value={"incidencias_empleado"},allowSetters=true)
	private UsuarioDTO empleado;
	
	@JsonProperty("incidencia")
	List<TrabajoDTO> trabajosConIncidencias;
	
	//Geters y Seters
	
		public int getId_incidencia() {
			return id_incidencia;
		}

		public void setId_incidencia(int id_incidencia) {
			this.id_incidencia = id_incidencia;
		}



		public int getHoras() {
			return horas;
		}

		public void setHoras(int horas) {
			this.horas = horas;
		}

		public float getCoste() {
			return coste;
		}

		public void setCoste(float coste) {
			this.coste = coste;
		}

		public Calendar getFecha_inicio() {
			return fecha_inicio;
		}

		public void setFecha_inicio(Calendar fecha_inicio) {
			this.fecha_inicio = fecha_inicio;
		}

		public Calendar getFecha_fin() {
			return fecha_fin;
		}

		public void setFecha_fin(Calendar fecha_fin) {
			this.fecha_fin = fecha_fin;
		}

		public UsuarioDTO getEmpleado() {
			return empleado;
		}

		public void setEmpleado(UsuarioDTO empleado) {
			this.empleado = empleado;
		}

		public SolicitudDTO getSolicitud() {
			return solicitud;
		}

		public void setSolicitud(SolicitudDTO solicitud) {
			this.solicitud = solicitud;
		}
		public String getDescripcion_usuario() {
			return descripcion_usuario;
		}

		public void setDescripcion_usuario(String descripcion_usuario) {
			this.descripcion_usuario = descripcion_usuario;
		}

		public String getDescripcion_tecnica() {
			return descripcion_tecnica;
		}

		public void setDescripcion_tecnica(String descripcion_tecnica) {
			this.descripcion_tecnica = descripcion_tecnica;
		}

		public boolean isEstado() {
			return estado;
		}

		public void setEstado(boolean estado) {
			this.estado = estado;
		}

		public List<TrabajoDTO> getTrabajosConIncidencias() {
			return trabajosConIncidencias;
		}

		public void setTrabajosConIncidencias(List<TrabajoDTO> trabajosConIncidencias) {
			this.trabajosConIncidencias = trabajosConIncidencias;
		}
		
		
		// Constructores

		/**
		 * Constructor predeterminado para crear un objeto IncidenciaDTO sin atributos.
		 */
		public IncidenciaDTO() {
		    super();
		}

		/**
		 * Constructor para crear un objeto IncidenciaDTO con todos los atributos.
		 * 
		 * @param id_incidencia Identificador único de la incidencia.
		 * @param descripcion_usuario Descripción proporcionada por el usuario.
		 * @param descripcion_tecnica Descripción técnica de la incidencia.
		 * @param horas Horas dedicadas a resolver la incidencia.
		 * @param coste Coste asociado a la incidencia.
		 * @param estado Estado de la incidencia (resuelta o no).
		 * @param fecha_inicio Fecha de inicio de la incidencia.
		 * @param fecha_fin Fecha de finalización de la incidencia.
		 */
		public IncidenciaDTO(int id_incidencia, String descripcion_usuario, String descripcion_tecnica, int horas,
		        float coste, boolean estado, Calendar fecha_inicio, Calendar fecha_fin) {
		    super();
		    this.id_incidencia = id_incidencia;
		    this.descripcion_usuario = descripcion_usuario;
		    this.descripcion_tecnica = descripcion_tecnica;
		    this.horas = horas;
		    this.coste = coste;
		    this.estado = estado;
		    this.fecha_inicio = fecha_inicio;
		    this.fecha_fin = fecha_fin;
		}

		/**
		 * Constructor para crear un objeto IncidenciaDTO con algunos atributos.
		 * 
		 * @param descripcion_usuario Descripción proporcionada por el usuario.
		 * @param descripcion_tecnica Descripción técnica de la incidencia.
		 * @param horas Horas dedicadas a resolver la incidencia.
		 * @param coste Coste asociado a la incidencia.
		 * @param estado Estado de la incidencia (resuelta o no).
		 * @param fecha_inicio Fecha de inicio de la incidencia.
		 * @param fecha_fin Fecha de finalización de la incidencia.
		 */
		public IncidenciaDTO(String descripcion_usuario, String descripcion_tecnica, int horas, float coste, boolean estado,
		        Calendar fecha_inicio, Calendar fecha_fin) {
		    super();
		    this.descripcion_usuario = descripcion_usuario;
		    this.descripcion_tecnica = descripcion_tecnica;
		    this.horas = horas;
		    this.coste = coste;
		    this.estado = estado;
		    this.fecha_inicio = fecha_inicio;
		    this.fecha_fin = fecha_fin;
		}

		/**
		 * Constructor para crear un objeto IncidenciaDTO con algunos atributos.
		 * 
		 * @param descripcion_usuario Descripción proporcionada por el usuario.
		 * @param estado Estado de la incidencia (resuelta o no).
		 * @param solicitud Solicitud asociada a la incidencia.
		 */
		public IncidenciaDTO(String descripcion_usuario, boolean estado, SolicitudDTO solicitud) {
		    super();
		    this.descripcion_usuario = descripcion_usuario;
		    this.estado = estado;
		    this.solicitud = solicitud;
		}

		
		
		
		
}
