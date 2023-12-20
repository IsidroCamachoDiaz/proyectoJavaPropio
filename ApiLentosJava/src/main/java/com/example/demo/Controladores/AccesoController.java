package com.example.demo.Controladores;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Modelos.Acceso;
import com.example.demo.Servicios.AccesoServicio;

/**
 * Clase que controla las opciones de API y la comunicacion a la base de datos para los Accesos
 * 
 * @author El Equipazo de los Lentos
 * 
 * @param AccesoServicio Almacenara el acceso Al Servidor
 */
@RestController
@RequestMapping("/accesoApi")
public class AccesoController {

	@Autowired
	private AccesoServicio accesoServicio;
	/**
	 * Devuelve todos los Accesos con la peticion GET
	 * @return Iterable<Acceso> Objeto que almacena todos los acessos 
	 */
	@GetMapping("/accesoSelect")
	public Iterable<Acceso> obtenerAcceso() {	
	return this.accesoServicio.getAcceso();
	}
	
	/**
	  * Devuelve un Acceso en concreto dependiendo del id insertado con la peticion GET
	 * @param id Identificacion que usaremos para diferenciar a otros Accesos
	 * @return Optional<Acceso> Devuelve una Coleccion de Acceso o nulo 
	 */
	@GetMapping(path="/accesoSelect/{id}")
	public Optional<Acceso> obtenerAccesoID(@PathVariable("id") Long id) {	
	return this.accesoServicio.getAccesoId(id);
	}
	
	/**
	 * Inserta un Acceso por el metodo POST
	 * @param Acceso Objetos Acceso
	 */
	@PostMapping("/accesoInsertar")
	public void insertarAcceso(@RequestBody Acceso acceso) {
		this.accesoServicio.insertarAcceso(acceso);
	}
	
	/**
	 * Actualiza un Acceso en concreto por el id mediante el metodo PUST
	 * @param id Variable por el cual identificaremos el Acceso
	 * @param Acceso acceso
	 */
	@PutMapping(path="/accesoActualizar/{id}")
	public void actualizarAcceso(@RequestBody Acceso acceso,@PathVariable("id") Long id) {
		this.accesoServicio.updateAcceso(acceso, id);
	}
	
	/**
	  * Eliminar un Acceso en concreto mediante el metodo DELETE
	 * @param id Variable por el cual identificaremos el Acceso
	 * @return Boolean Devolvera "true" si se a Eliminado correctamente o "false" si a habido algun problema
	 */
	@DeleteMapping(path="/accesoEliminar/{id}")
	public Boolean eliminarAcceso(@PathVariable("id") Long id) {
		return this.accesoServicio.deleteAcceso(id);
	}
}
