package com.example.demo.Modelos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Implementacion de la Interface Acceso que extiende Jpa 
 * @author El Equipazo de los Lentos
 */
@Repository
public interface AccesoRepositorio extends JpaRepository<Acceso, Long>{

}
