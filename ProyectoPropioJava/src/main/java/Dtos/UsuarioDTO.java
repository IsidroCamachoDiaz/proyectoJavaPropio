package Dtos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class UsuarioDTO implements Serializable {
	
	@JsonProperty("id_usuario")
	private int idUsuario;
	
	@JsonProperty("nombre")
	private String nombreUsuario;
	
	@JsonProperty("telefono")
	private String tlfUsuario;
	
	@JsonProperty("correo")
	private String emailUsuario;
	
	@JsonProperty("contrasenia")
	private String claveUsuario;
	
	@JsonProperty("foto")
	private byte[] foto;
	
	@JsonProperty("alta")
	private boolean alta;
	
	@JsonProperty("fecha_baja")
	private Calendar fechaBaja;

	@JsonProperty("acceso")
	private AccesoDTO acceso;
	
	@JsonProperty("tokens_usuario")
	private List<TokenDTO> listaTokensUsuario;
	
	@JsonProperty("solicitudes_usuario")
	private List <SolicitudDTO> solicitudesUsuario;
	

	//Construtores
   
	/**
	 * Constructor vacío por defecto
	 */
	public UsuarioDTO() {
		super();
	}


	/**
	 * Constructor para crear un objeto UsuarioDTO con todos los atributos.
	 * 
	 * @param idUsuario      Identificador único del usuario.
	 * @param nombreUsuario  Nombre del usuario.
	 * @param tlfUsuario     Número de teléfono del usuario.
	 * @param emailUsuario   Dirección de correo electrónico del usuario.
	 * @param claveUsuario   Clave o contraseña del usuario.
	 */
	public UsuarioDTO(int idUsuario, String nombreUsuario, String tlfUsuario, String emailUsuario, String claveUsuario,boolean alta) {
	    super();
	    this.idUsuario = idUsuario;
	    this.nombreUsuario = nombreUsuario;
	    this.tlfUsuario = tlfUsuario;
	    this.emailUsuario = emailUsuario;
	    this.claveUsuario = claveUsuario;
	    this.alta=alta;
	}

	/**
	 * Constructor para crear un objeto UsuarioDTO con algunos atributos.
	 * 
	 * @param nombreUsuario  Nombre del usuario.
	 * @param tlfUsuario     Número de teléfono del usuario.
	 * @param emailUsuario   Dirección de correo electrónico del usuario.
	 * @param claveUsuario   Clave o contraseña del usuario.
	 */
	public UsuarioDTO(String nombreUsuario, String tlfUsuario, String emailUsuario, String claveUsuario) {
	    super();
	    this.nombreUsuario = nombreUsuario;
	    this.tlfUsuario = tlfUsuario;
	    this.emailUsuario = emailUsuario;
	    this.claveUsuario = claveUsuario;
	}

	/**
	 * Constructor para crear un objeto UsuarioDTO con los atributos mínimos necesarios.
	 * 
	 * @param emailUsuario   Dirección de correo electrónico del usuario.
	 * @param claveUsuario   Clave o contraseña del usuario.
	 */
	public UsuarioDTO(String emailUsuario, String claveUsuario) {
	    super();
	    this.emailUsuario = emailUsuario;
	    this.claveUsuario = claveUsuario;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return Nombre del usuario.
	 */
	public String getNombreUsuario() {
	    return nombreUsuario;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param nombreUsuario Nuevo nombre del usuario.
	 */
	public void setNombreUsuario(String nombreUsuario) {
	    this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Obtiene el número de teléfono del usuario.
	 * 
	 * @return Número de teléfono del usuario.
	 */
	public String getTlfUsuario() {
	    return tlfUsuario;
	}

	/**
	 * Establece el número de teléfono del usuario.
	 * 
	 * @param tlfUsuario Nuevo número de teléfono del usuario.
	 */
	public void setTlfUsuario(String tlfUsuario) {
	    this.tlfUsuario = tlfUsuario;
	}

	/**
	 * Obtiene la dirección de correo electrónico del usuario.
	 * 
	 * @return Dirección de correo electrónico del usuario.
	 */
	public String getEmailUsuario() {
	    return emailUsuario;
	}

	/**
	 * Establece la dirección de correo electrónico del usuario.
	 * 
	 * @param emailUsuario Nueva dirección de correo electrónico del usuario.
	 */
	public void setEmailUsuario(String emailUsuario) {
	    this.emailUsuario = emailUsuario;
	}

	/**
	 * Obtiene la clave o contraseña del usuario.
	 * 
	 * @return Clave o contraseña del usuario.
	 */
	public String getClaveUsuario() {
	    return claveUsuario;
	}

	/**
	 * Establece la clave o contraseña del usuario.
	 * 
	 * @param claveUsuario Nueva clave o contraseña del usuario.
	 */
	public void setClaveUsuario(String claveUsuario) {
	    this.claveUsuario = claveUsuario;
	}

	/**
	 * Obtiene el objeto AccesoDTO asociado al usuario.
	 * 
	 * @return Objeto AccesoDTO asociado al usuario.
	 */
	public AccesoDTO getAcceso() {
	    return acceso;
	}

	
	/**
	 * Obtiene la representación en bytes de la foto asociada a este objeto.
	 *
	 * @return Un array de bytes que representa la foto.
	 */
	public byte[] getFoto() {
	    return foto;
	}

	/**
	 * Establece la representación en bytes de la foto para asociarla a este objeto.
	 *
	 * @param foto Un array de bytes que representa la foto a establecer.
	 */
	public void setFoto(byte[] foto) {
	    this.foto = foto;
	}

	
	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public void setAcceso(AccesoDTO acceso) {
		this.acceso = acceso;
	}

	

	public boolean isAlta() {
		return alta;
	}


	public void setAlta(boolean alta) {
		this.alta = alta;
	}


	public Calendar getFechaBaja() {
		return fechaBaja;
	}


	public void setFechaBaja(Calendar fechaBaja) {
		this.fechaBaja = fechaBaja;
	}


	/**
	 * Representación en cadena del objeto UsuarioDTO.
	 * 
	 * @return Cadena que representa el objeto UsuarioDTO.
	 */
	@Override
	public String toString() {
	    return "UsuarioDTO [idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", tlfUsuario=" + tlfUsuario
	            + ", emailUsuario=" + emailUsuario + ", claveUsuario=" + claveUsuario + ", acceso=" + acceso
	            + ", listaTokensUsuario=" + listaTokensUsuario + "]";
	}


}