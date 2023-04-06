package com.example.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.AdministradorDao;
import com.example.entities.Administrador;

/**
 * 
 * La clase AdministradorServiceImpl implementa la interfaz AdministradorService y proporciona la lógica de negocio para la entidad Administrador.
 * 
 * La anotación @Service indica que esta clase es un servicio y debe ser manejado por Spring para su gestión en tiempo de ejecución.
 * 
 * Los métodos de esta clase se encargan de realizar operaciones CRUD (Crear, Leer, Actualizar, Borrar) en la entidad Administrador, a través del uso de la interfaz
 * AdministradorDao.

 */
@Service
public class AdministradorServiceImpl implements AdministradorService {

    /** Creamos la variable de tipo Dao para poder inyectarle la capa DAO, puede resolverse con un @Autowire o mediante constructor: */
    
    @Autowired
    private AdministradorDao administradorDao;

    @Override
    public List<Administrador> findAll() {
        return administradorDao.findAll();
    }

    
    @Override
    public Administrador findById(long id) {
        return administradorDao.findById(id).get();
    }

    @Override
    public Administrador save(Administrador Administrador) {
       return administradorDao.save(Administrador);
    }
    
    
    @Override
    public void delete(Administrador Administrador) {
        administradorDao.delete(Administrador);
    }
    
        
    @Override
    public Optional<Administrador> findOptById(Long id) {
        return administradorDao.findById(id);
    }
    
    
}
