package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.ProveedorDao;
import com.example.entities.Proveedor;

import java.util.List;


@Service //para llamar a los beans 

public class ProveedorServiceImpl implements ProveedorService {


//Como los metodos van a llamar al DAO entonces tengo que insertarlo aqui con el @Autowired para que el Spring lo "inserta"
//el Autowired resuelve una dependencia, tambien se puede resolver via constructores 
   
    @Autowired 
    private ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> findAll() {
        return proveedorDao.findAll();
    }

    @Override
    public Proveedor findById(long id) {
        return proveedorDao.findById(id).get();
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorDao.save(proveedor);
    }

    @Override
    public void delete(Proveedor proveedor) {
         proveedorDao.delete(proveedor);
    }
  
   

}


    // @Autowired 
    // private ProveedorDao proveedorDao;

    // @Override
    // public List<Proveedor> findAll() {
    //     return proveedorDao.findAll();
    // }

    // @Override
    // public Proveedor findById(long id) {
    //     return proveedorDao.findById(id).get();
    // }

    // @Override
    // public Proveedor save(Proveedor proveedor) {
    //     return proveedorDao.save(proveedor);
    // }

    // @Override
    // public void delete(Proveedor proveedor) {
    //      proveedorDao.delete(proveedor);
    // }
