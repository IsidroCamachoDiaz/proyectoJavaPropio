package com.example.demo.Modelos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementacion de la Interface Acceso que extiende Jpa 
 * @author El grupazo de los Lentos
 */
@Repository
public interface TokenRepositorio extends JpaRepository<Token, Long>{
	/**
	 * Método que busca un token por su Token en la base de datos y lo devuelve
	 * @param token
	 * @return token o null
	 */
	Optional<Token> findByToken(String token);
}