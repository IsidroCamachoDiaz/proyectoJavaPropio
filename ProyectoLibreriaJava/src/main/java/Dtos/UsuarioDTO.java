package Dtos;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Clase de Usuario (DTO) 
 *  
 * @author El Equipazo de los Lentos
 * 
 * @param idUsuarioDto id del Usuario
 * @param dniUsuarioDto DNI del usuario
 * @param nombreUsuarioDto Nombre del Usuario
 * @param apellidosUsuarioDto Apellido del Usuario
 * @param tlfUsuarioDto telefono del Usuario
 * @param emailUsuarioDto Email del usuario
 * @param claveUsuarioDto contraseña del usuario
 * @param estaBloqueadoUsuarioDto Estado del usuario, si esta bloqueado o no
 * @param fchFinBloqueoDto fecha del bloqueo
 * @param fchAltaUsuarioDto fecha de alta del usuario
 * @param fchBajaUsuarioDto fecha de baja del usuario
 * @param acceso indica el acceso del usuario
 */
public class UsuarioDTO {
	
	@JsonProperty("idUsuario")
	private long idUsuarioDto;
	
	@JsonProperty("dniUsuario")
	private String dniUsuarioDto;
	
	@JsonProperty("nombreUsuario")
	private String nombreUsuarioDto;
	
	@JsonProperty("apellidosUsuario")
	private String apellidosUsuarioDto;
	
	@JsonProperty("tlfUsuario")
	private String tlfUsuarioDto;
	
	@JsonProperty("emailUsuario")
	private String emailUsuarioDto;
	
	@JsonProperty("claveUsuario")
	private String claveUsuarioDto;
	
	@JsonProperty("estaBloqueadoUsuario")
	private boolean estaBloqueadoUsuarioDto;
	
	@JsonProperty("fchFinBloqueo")
	private Calendar fchFinBloqueoDto;
	
	@JsonProperty("fchAltaUsuario")
	private Calendar fchAltaUsuarioDto;
	
	@JsonProperty("fchBajaUsuario")
	private Calendar fchBajaUsuarioDto;

	@JsonProperty("acceso")
	private AccesoDTO acceso;

   
	/**
	 * Constructor vacío por defecto
	 */
	public UsuarioDTO() {
		super();
	}

	/**
	 * Constructor con trodos los campos 
	 * 
	 * @param dniUsuario dni del usuario
	 * @param nombreUsuario nombre del usuario
	 * @param apellidosUsuario apellido del usuario
	 * @param tlfUsuario telefono del usuario 
	 * @param emailUsuario email del usuario
	 * @param claveUsuario contraseña del usuario
	 * @param estaBloqueadoUsuario estado del bloqueo (true/false)
	 * @param fchFinBloqueo fecha de bloqueo del usuario
	 * @param fchAltaUsuario fecha de alta del usuario
	 * @param fchBajaUsuario fecha de baja del usuario
	 */
	public UsuarioDTO(String dniUsuario, String nombreUsuario, String apellidosUsuario, String tlfUsuario,
			String emailUsuario, String claveUsuario, boolean estaBloqueadoUsuario, Calendar fchFinBloqueo,
			Calendar fchAltaUsuario, Calendar fchBajaUsuario) {
		super();
		this.dniUsuarioDto = dniUsuario;
		this.nombreUsuarioDto = nombreUsuario;
		this.apellidosUsuarioDto = apellidosUsuario;
		this.tlfUsuarioDto = tlfUsuario;
		this.emailUsuarioDto = emailUsuario;
		this.claveUsuarioDto = claveUsuario;
		this.estaBloqueadoUsuarioDto = estaBloqueadoUsuario;
		this.fchFinBloqueoDto = fchFinBloqueo;
		this.fchAltaUsuarioDto = fchAltaUsuario;
		this.fchBajaUsuarioDto = fchBajaUsuario;
	}
	
	/**
	 *  Contructor con los campod dni, nombre, apellido, tlf, email, clave
	 *  
	 * @param dniUsuario dni del usuario
	 * @param nombreUsuario nombre del usuario
	 * @param apellidosUsuario apellido del usuario
	 * @param tlfUsuario telefono del usuario 
	 * @param emailUsuario email del usuario
	 * @param claveUsuario contraseña del usuario
	 */
	public UsuarioDTO(String dniUsuario, String nombreUsuario, String apellidosUsuario, String tlfUsuario,
			String emailUsuario, String claveUsuario) {
		super();
		this.dniUsuarioDto = dniUsuario;
		this.nombreUsuarioDto = nombreUsuario;
		this.apellidosUsuarioDto = apellidosUsuario;
		this.tlfUsuarioDto = tlfUsuario;
		this.emailUsuarioDto = emailUsuario;
		this.claveUsuarioDto = claveUsuario;	
		
	}
	
	public UsuarioDTO(String dniUsuarioDto, String claveUsuarioDto) {
		super();
		this.dniUsuarioDto = dniUsuarioDto;
		this.claveUsuarioDto = claveUsuarioDto;
	}

	/**
	 * Devuelve el id del usuario
	 * @return id del usuario
	 */
	public long getIdUsuario() {
		return idUsuarioDto;
	}

	/**
	 * introduce un id al usuario
	 * @param idUsuario id del usuario
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuarioDto = idUsuario;
	}

	/**
	 * Devuelve el DNI del usuario
	 * @return DNI del usuario
	 */
	public String getDniUsuario() {
		return dniUsuarioDto;
	}

	/**
	 * Inserta un DNI al usuario
	 * @param dniUsuario dni del usuario
	 */
	public void setDniUsuario(String dniUsuario) {
		this.dniUsuarioDto = dniUsuario;
	}

	/**
	 * Devuelve el Nombre del Usuario 
	 * @return Nombre del usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuarioDto;
	}

	/**
	 * Inserta Nombre al Usuario
	 * @param nombreUsuario Nombre del usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuarioDto = nombreUsuario;
	}

	/**
	 * Devuelve el Apellido del usuario
	 * @return Apellido del usuario
	 */
	public String getApellidosUsuario() {
		return apellidosUsuarioDto;
	}

	/**
	 * Inserta apellido al Usuario
	 * @param apellidosUsuario Apellido del usuario
	 */
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuarioDto = apellidosUsuario;
	}

	/**
	 * Devuelve el Telefono usuario
	 * @return telefono del usuario
	 */
	public String getTlfUsuario() {
		return tlfUsuarioDto;
	}

	/**
	 * Inserta telefono al Usuario
	 * @param tlfUsuario telefono de usuario
	 */
	public void setTlfUsuario(String tlfUsuario) {
		this.tlfUsuarioDto = tlfUsuario;
	}

	/**
	 * Devuelve el Email al usuario
	 * @return Email
	 */
	public String getEmailUsuario() {
		return emailUsuarioDto;
	}

	/**
	 * Inserta el Email del Usuario
	 * @param emailUsuario Email usuario
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuarioDto = emailUsuario;
	}

	/**
	 * Devuelve la Contraseña del usuario
	 * @return Contraseña del usuario
	 */
	public String getClaveUsuario() {
		return claveUsuarioDto;
	}

	/**
	 * Inserta clave al usuario
	 * @param claveUsuario clave al usuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuarioDto = claveUsuario;
	}
	
	/**
	 * Devuelve el estado del usuario (si esta bloqueado false o no true)
	 * @return estado del usuario
	 */
	public boolean isEstaBloqueadoUsuario() {
		return estaBloqueadoUsuarioDto;
	}

	/**
	 * Inserta un estado al usuario (si esta bloqueado false o no true)
	 * @param estaBloqueadoUsuario estado del usuario
	 */
	public void setEstaBloqueadoUsuario(boolean estaBloqueadoUsuario) {
		this.estaBloqueadoUsuarioDto = estaBloqueadoUsuario;
	}

	/**
	 * Devuelve la fecha de bloqueo
	 * @return fecha de bloqueo
	 */
	public Calendar getFchFinBloqueo() {
		return fchFinBloqueoDto;
	}

	/**
	 * Inserta una fecha de bloqueo
	 * @param fchFinBloqueo fecha de bloqueo
	 */
	public void setFchFinBloqueo(Calendar fchFinBloqueo) {
		this.fchFinBloqueoDto = fchFinBloqueo;
	}

	/**
	 * Devuelve la Fecha de Alta del usuario
	 * @return fecha de alta
	 */
	public Calendar getFchAltaUsuario() {
		return fchAltaUsuarioDto;
	}

	/**
	 * Inserta una Fecha de Alta del usuario
	 * @param fchAltaUsuario fecha de alta
	 */
	public void setFchAltaUsuario(Calendar fchAltaUsuario) {
		this.fchAltaUsuarioDto = fchAltaUsuario;
	}

	/**
	 * Devuelve la fecha de baja del usuario
	 * @return
	 */
	public Calendar getFchBajaUsuario() {
		return fchBajaUsuarioDto;
	}

	/**
	 * Inserta una fecha de baja al usuario
	 * @param fchBajaUsuario fecha de baja
	 */
	public void setFchBajaUsuario(Calendar fchBajaUsuario) {
		this.fchBajaUsuarioDto = fchBajaUsuario;
	}
	
	/**
	 * String con todos los datos del Usuario
	 */
	@Override
	public String toString() {
		return "--- Datos Usuario ---\nidUsuario: " + idUsuarioDto + "\ndniUsuario: " + dniUsuarioDto + "\nnombreUsuario: " + nombreUsuarioDto
				+ "\napellidosUsuario: " + apellidosUsuarioDto + "\ntlfUsuario:" + tlfUsuarioDto + ", emailUsuario="
				+ emailUsuarioDto + "\nclaveUsuario: " + claveUsuarioDto + "\nestaBloqueadoUsuario: " + estaBloqueadoUsuarioDto
				+ "\nfchFinBloqueo: " + fchFinBloqueoDto + "\nfchAltaUsuario: " + fchAltaUsuarioDto + "\nfchBajaUsuario: "
				+ fchBajaUsuarioDto ;
	}
}