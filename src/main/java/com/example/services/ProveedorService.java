package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Proveedor;



public interface ProveedorService  {
    
    //public List<Proveedor> findAll(Sort sortByNombre);
    //public Page<Proveedor> findAll(Pageable pageable);
   
    public List<Proveedor> findAll();
    public Proveedor findById (long id); 

    public Proveedor save (Proveedor proveedor);

    public void delete(Proveedor proveedor);
    
}

