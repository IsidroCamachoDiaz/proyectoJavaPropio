package com.example.demo.Modelos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Implementacion de la Interface Usuario que extiende Jpa 
 * @author El Equipazo de los Lentos
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	/**
	 * Método que busca un usuario por Telefono en la base de datos y lo devuelve
	 * @param telefono
	 * @return Usuario o null
	 */
	Optional<Usuario> findByTlfUsuario(String telefono);
	
	/**
	 * Método que busca un usuario por Email en la base de datos y lo devuelve
	 * @param email
	 * @return Usuario o null
	 */
	Optional<Usuario> findByEmailUsuario(String email);
	
	/**
	 * Método que busca un usuario por Dni en la base de datos y lo devuelve
	 * @param dni
	 * @return Usuario o null
	 */
	Optional<Usuario> findByDniUsuario(String dni);
	
}
