package com.example.services;

import java.util.List;

import com.example.entities.Proveedor;



public interface ProveedorService  {

   
    public List<Proveedor> findAll();
    
    public Proveedor findById (long id); 

    public Proveedor save (Proveedor proveedor);

    public void delete(Proveedor proveedor);













        
    //public List<Proveedor> findAll(Sort sortByNombre);
    //public Page<Proveedor> findAll(Pageable pageable);
    
}

