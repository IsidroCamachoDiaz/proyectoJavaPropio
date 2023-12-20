package com.example.demo.Controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Servicios.UsuarioServicio;

import com.example.demo.Modelos.Usuario;

/**
 * Clase que controla las opciones de API y la comunicacion a la base de datos para los Usuarios
 * @author El Equipazo de los Lentos
 * 
 * @param usuarioServicio 
 */
@RestController
@RequestMapping("/usuarioApi")
public class UsuarioController {
	
	@Autowired
	private UsuarioServicio usuarioServicio;
	/**
	 * Devuelve todos los Usuario con la peticion GET
	 * @return Iterable<Acceso>
	 */
	@GetMapping("/usuarioSelect")
	public Iterable<Usuario> obtenerUsuario() {	
	return this.usuarioServicio.getUsuario();
	}
	/**
	  * Devuelve un Usuario en concreto dependiendo del id insertado con la peticion GET
	 * @param id Identificacion que usaremos para diferenciar a otros Usuario
	 * @return Optional<Usuario> Devuelve una Coleccion de Usuario o nulo 
	 */
	@GetMapping(path="/usuarioSelect/{id}")
	public Optional<Usuario> obtenerUsuarioID(@PathVariable("id") Long id) {	
	return this.usuarioServicio.getUsuarioId(id);
	}
	/**
	  * Devuelve un Usuario en concreto dependiendo del dni insertado con la peticion GET
	 * @param dni Identificacion que usaremos para diferenciar a otros Usuario
	 * @return Optional<Usuario> Devuelve una Coleccion de Usuario o nulo 
	 */
	@GetMapping(path="/usuarioSelect/usuarioDni/{dni}")
	public Optional<Usuario> obtenerUsuarioDni(@PathVariable("dni") String dni) {	
	return this.usuarioServicio.getUsuarioDni(dni);
	}
	/**
	  * Devuelve un Usuario en concreto dependiendo del email insertado con la peticion GET
	 * @param email Identificacion que usaremos para diferenciar a otros Usuario
	 * @return Optional<Usuario> Devuelve una Coleccion de Usuario o nulo 
	 */
	@GetMapping(path="/usuarioSelect/usuarioEmail/{email}")
	public Optional<Usuario> obtenerUsuarioEmail(@PathVariable("email") String email) {	
	return this.usuarioServicio.getUsuarioEmail(email);
	}
	/**
	  * Devuelve un Usuario en concreto dependiendo del telefono insertado con la peticion GET
	 * @param telefono Identificacion que usaremos para diferenciar a otros Usuario
	 * @return Optional<Usuario> Devuelve una Coleccion de Usuario o nulo 
	 */
	@GetMapping(path="/usuarioSelect/usuarioTlf/{tlf}")
	public Optional<Usuario> obtenerUsuarioTlf(@PathVariable("tlf") String telefono) {	
	return this.usuarioServicio.getUsuarioTlf(telefono);
	}
	
	/**
	 * Inserta un Usuario por el metodo POST
	 * @param Usuario Objetos usuario
	 */
	@PostMapping("/usuarioInsertar")
	public void insertarUsuario(@RequestBody Usuario usuario) {
		this.usuarioServicio.insertarUsuario(usuario);
	}
	/**
	 * Actualiza un Usuario en concreto por el id mediante el metodo PUST
	 * @param id Variable por el cual identificaremos el Usuario
	 * @param Usuario usuario
	 */
	@PutMapping(path="/usuarioActualizar/{id}")
	public void actualizarUsuario(@RequestBody Usuario usuario,@PathVariable("id") Long id) {
		this.usuarioServicio.updateUsuario(usuario, id);
	}
	/**
	  * Eliminar un Usuario en concreto mediante el metodo DELETE
	 * @param id Variable por el cual identificaremos el Usuario
	 * @return Boolean Devolvera "true" si se a Eliminado correctamente o "false" si a habido algun problema
	 */
	@DeleteMapping(path="/usuarioEliminar/{id}")
	public Boolean eliminarUsuario(@PathVariable("id") Long id) {
		return this.usuarioServicio.deleteUser(id);
	}
}
