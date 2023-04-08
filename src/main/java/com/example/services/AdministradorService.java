package com.example.services;

import java.util.List;
import java.util.Optional;

import com.example.entities.Administrador;

/**
 * Interfaz que define los servicios para la entidad Administrador.
 */
public interface AdministradorService {

    /**     * Este método obtiene todos los administradores:    */
    public List<Administrador> findAll();

    /**
     * Método que busca un administrador por su identificador:*/
    public Administrador findById(long id);

    /**
     * Guarda un administrador en la base de datos:*/
    public Administrador save(Administrador administrador);

    /**
     * Elimina un administrador de la base de datos:*/
    public void delete(Administrador administrador);


    //Opcional para que funcione delete cuando un id no existe:
    public Optional<Administrador> findOptById(Long id) ;
    
    
    /**
     * No es necesario un metodo update, porque el save inserta o actualiza, en
     * dependencia de que el id exista o no, es decir, si no existe lo
     * crea, y si existe actualiza la informacion.
     */

}