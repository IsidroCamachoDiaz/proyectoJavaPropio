package com.example.demo.Modelos;

import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
/**
 * Clase de Usuario(DAO) para traer los campos de la base de datos 
 * @author El Equipazo de los Lentos
 */
@Entity
@Table(name="usuarios")
/**
 * @param idUsuario Identificador del usuario
 * @param dniUsuario DNI del Usuario
 * @param nombreUsuario Nombre del usuario
 * @param apellidosUsuario Apellidos del Usuario
 * @param tlfUsuario Telefono del usuario
 * @param emailUsuario Email del Usuario
 * @param claveUsuario contraseña del usuario
 * @param estaBloqueadoUsuario Si el usuario esta bloqueado o no
 * @param fchFinBloqueo Fecha del Boqueo
 * @param fchAltaUsuario Fecha en la que el usuario se dio de alta
 * @param fchBajaUsuario Fecha en la que el usuario se dio de baja
 * 
 */
public class Usuario {	
	

	//ATRIBUTOS
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_usuario", nullable=false)
	private long idUsuario;
	
	
	@Column(name="dni_usuario", nullable=false)
	private String dniUsuario;
	
	@Column(name="nombre_usuario")
	private String nombreUsuario;
	
	@Column(name="apellidos_usuario")
	private String apellidosUsuario;
	
	@Column(name="tlf_usuario")
	private String tlfUsuario;
	
	@Column(name="email_usuario")
	private String emailUsuario;
	
	@Column(name="clave_usuario")
	private String claveUsuario;
	
	@Column(name="estaBloqueado_usuario")
	private boolean estaBloqueadoUsuario;
	
	@Column(name="fch_fin_bloqueo")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fchFinBloqueo;
	
	@Column(name="fch_alta_usuario")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fchAltaUsuario;
	
	@Column(name="fch_baja_usuario")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fchBajaUsuario;
	
    @ManyToOne
    @JoinColumn(name="id_acceso")
    Acceso acceso=new Acceso(1,"Usu","Acceso usuarios biblioteca");
   
    //GETT
    /**
     * Devuelve el id del usuario
     * @return  id del usuario
     */
    public long getIdUsuario() {
		return idUsuario;
	}

    /**
     * Devuelve el DNI del usuario
     * @return  DNI del usuario
     */
	public String getDniUsuario() {
		return dniUsuario;
	}

	/**
	 * inserta un DNI al usuario
	 * @param dniUsuario DNI al usuario
	 */
	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}

	/**
	 * Devuelve el Nombre del usuario
	 * @return Nombre del usuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * Inserta el Nombre al usuario
	 * @param nombreUsuario Nombre del usuario
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * Devuelve el Apellido del usuario
	 * @return Apellido del usuario
	 */
	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	/**
	 * inserta apellido al usuario
	 * @param apellidosUsuario apellido al usuario
	 */
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	/**
	 * Devuelve el telefono del usuario
	 * @return  telefono del usuario
	 */
	public String getTlfUsuario() {
		return tlfUsuario;
	}

	/**
	 * inserta un telefono al usuario
	 * @param tlfUsuario telefono al usuario
	 */
	public void setTlfUsuario(String tlfUsuario) {
		this.tlfUsuario = tlfUsuario;
	}

	/**
	 * Devuelve el Email del Usuario
	 * @return Email del Usuario
	 */
	public String getEmailUsuario() {
		return emailUsuario;
	}

	/**
	 * Inserta Email al usuario
	 * @param emailUsuario  Email al usuario
	 */
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	/**
	 * Devuelve la Clave del usuario
	 * @return Clave del usuario
	 */
	public String getClaveUsuario() {
		return claveUsuario;
	}

	/**
	 * inserta una contraseña al usuario
	 * @param claveUsuario  contraseña al usuario
	 */
	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	/**
	 * Devuelve si el usuario esta Bloqueado true, o si no esta bloqueado false
	 * @return estado de bloqueo
	 */
	public boolean isEstaBloqueadoUsuario() {
		return estaBloqueadoUsuario;
	}

	/**
	 *  inserta si el usuario esta bloqueado o no 
	 * @param estaBloqueadoUsuario si el usuario esta bloqueado o no 
	 */
	public void setEstaBloqueadoUsuario(boolean estaBloqueadoUsuario) {
		this.estaBloqueadoUsuario = estaBloqueadoUsuario;
	}

	/**
	 * Devuelve la fecha de Bloqueo del usuario
	 * @return fecha de Bloqueo del usuario
	 */
	public Calendar getFchFinBloqueo() {
		return fchFinBloqueo;
	}

	/**
	 * Devuelve la fecha de fin de Bloqueo del usuario
	 * @param fchFinBloqueo fecha de fin de Bloqueo del usuario
	 */
	public void setFchFinBloqueo(Calendar fchFinBloqueo) {
		this.fchFinBloqueo = fchFinBloqueo;
	}

	/**
	 * Devuelve la fecha de alta del usuario
	 * @return fecha de alta del usuario
	 */
	public Calendar getFchAltaUsuario() {
		return fchAltaUsuario;
	}

	/**
	 * Devuelve la fecha de baja del usuario
	 * @return fecha de baja del usuario
	 */

	public Calendar getFchBajaUsuario() {
		return fchBajaUsuario;
	}

	/**
	 * Inserta la fecha de baja del usuario
	 * @param fchBajaUsuario fecha de baja del usuario
	 */
	public void setFchBajaUsuario(Calendar fchBajaUsuario) {
		this.fchBajaUsuario = fchBajaUsuario;
	}

	/**
	 * Devuelve el ACCESO
	 * @return  ACCESO
	 */
	public Acceso getAcceso() {
		return acceso;
	}

	/**
	 * Inserta el Acceso del usuario
	 * @param acceso Acceso del usuario
	 */
	public void setAcceso(Acceso acceso) {
		this.acceso = acceso;
	}
	//CONSTRUCTORES

	/**
	 * Constructor Vacio por defecto
	 */
	public Usuario() {
		super();
	}

	/**
	 *  Constructor completo 
	 * @param dniUsuario dni del usuario
	 * @param nombreUsuario nombre del usuario
	 * @param apellidosUsuario apellido del usuario
	 * @param tlfUsuario telefono del usuario
	 * @param emailUsuario email del usuario
	 * @param claveUsuario clave del usuario
	 * @param estaBloqueadoUsuario si el usuario esta bloqueado
	 * @param fchFinBloqueo fecha de bloqueo
	 * @param fchAltaUsuario fecha de alta
	 * @param fchBajaUsuario fecha de baja
	 * @param acceso acceso
	 */
	public Usuario(String dniUsuario, String nombreUsuario, String apellidosUsuario, String tlfUsuario,
			String emailUsuario, String claveUsuario, boolean estaBloqueadoUsuario, Calendar fchFinBloqueo,
			Calendar fchAltaUsuario, Calendar fchBajaUsuario, Acceso acceso) {
		super();
		this.dniUsuario = dniUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellidosUsuario = apellidosUsuario;
		this.tlfUsuario = tlfUsuario;
		this.emailUsuario = emailUsuario;
		this.claveUsuario = claveUsuario;
		this.estaBloqueadoUsuario = estaBloqueadoUsuario;
		this.fchFinBloqueo = fchFinBloqueo;
		this.fchAltaUsuario = fchAltaUsuario;
		this.fchBajaUsuario = fchBajaUsuario;
		this.acceso = acceso;
	}
	
	/**
	 * Constructor sin fechas
	 * @param dniUsuario
	 * @param nombreUsuario
	 * @param apellidosUsuario
	 * @param tlfUsuario
	 * @param emailUsuario
	 * @param claveUsuario
	 */
	public Usuario(String dniUsuario, String nombreUsuario, String apellidosUsuario, String tlfUsuario,
			String emailUsuario, String claveUsuario) {
		super();
		this.dniUsuario = dniUsuario;
		this.nombreUsuario = nombreUsuario;
		this.apellidosUsuario = apellidosUsuario;
		this.tlfUsuario = tlfUsuario;
		this.emailUsuario = emailUsuario;
		this.claveUsuario = claveUsuario;
	}
	
}
