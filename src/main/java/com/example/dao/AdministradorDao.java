package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entities.Administrador;

public interface AdministradorDao extends JpaRepository<Administrador, Long>{
    
}
