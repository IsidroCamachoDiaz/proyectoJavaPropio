package Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrabajoDTO {
	
	//Atributos

	@JsonProperty("id_trabajo")
	private int id_trabajo;
	
	@JsonProperty("descripcion_trabajo")
	private String descripcion;
	
	@JsonProperty("estado_trabajo")
	private boolean estado;
	
	@JsonProperty("horas_trabajo")
	private int horas;
	
	@JsonProperty("id_incidencia")
	private IncidenciaDTO incidencia;
	
	@JsonProperty("id_tipo_incidencia")
	private TipoTrabajoDTO tipoIncidencia;
	
	//Constructores

	public TrabajoDTO(String descripcion, boolean estado, int horas, IncidenciaDTO incidencia,
			TipoTrabajoDTO tipoIncidencia) {
		super();
		this.descripcion = descripcion;
		this.estado = estado;
		this.horas = horas;
		this.incidencia = incidencia;
		this.tipoIncidencia = tipoIncidencia;
	}

	public TrabajoDTO(int id_trabajo, String descripcion, boolean estado, int horas, IncidenciaDTO incidencia,
			TipoTrabajoDTO tipoIncidencia) {
		super();
		this.id_trabajo = id_trabajo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.horas = horas;
		this.incidencia = incidencia;
		this.tipoIncidencia = tipoIncidencia;
	}
	
	//Geters y Seters
	
	public TrabajoDTO() {
		super();
	}

	public int getId_trabajo() {
		return id_trabajo;
	}

	public void setId_trabajo(int id_trabajo) {
		this.id_trabajo = id_trabajo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public IncidenciaDTO getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(IncidenciaDTO incidencia) {
		this.incidencia = incidencia;
	}

	public TipoTrabajoDTO getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(TipoTrabajoDTO tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}
	
	
	
}
