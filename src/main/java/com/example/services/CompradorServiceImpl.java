package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.CompradorDao;
import com.example.entities.Comprador;

@Service

public class CompradorServiceImpl implements CompradorService {

   @Autowired
   private CompradorDao compradorDao; 
   
    @Override
    public List<Comprador> findAll(Sort sort) {
        
        return compradorDao.findAll(sort); 
    }

    @Override
    public Page<Comprador> findAll(Pageable pageable) {
        
        return compradorDao.findAll(pageable); 
    }

    @Override
    public Comprador findById(long id) {
        
        return compradorDao.findById(id); 
    }

    @Override
    public Comprador save(Comprador comprador) {
        
        return compradorDao.save(comprador); 
    }

    @Override
    public void deleteById(long id) {
        
        compradorDao.deleteById(id);
    }

    @Override
    public void delete(Comprador comprador) {
        
        compradorDao.delete(comprador);
    }
    
}
