package com.example.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.entities.Administrador;
import com.example.entities.Comprador;

public interface CompradorService {
    
    public List<Comprador> findAll(Sort sort); 
    public Page<Comprador> findAll(Pageable pageable); 
    public Comprador findById(long id); 
    public Comprador save(Comprador comprador); 
    public void deleteById(long id); 
    public void delete(Comprador comprador); 
    
    public List<Comprador> findByAdministrador(Administrador administrador);
}
