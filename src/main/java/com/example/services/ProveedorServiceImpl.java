package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.ProveedorDao;
import com.example.entities.Administrador;
import com.example.entities.Proveedor;

@Service

public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorDao proveedorDao;

    @Override
    public List<Proveedor> findAll(Sort sort) {

        return proveedorDao.findAll(sort);
    }

    @Override
    public Page<Proveedor> findAll(Pageable pageable) {

        return proveedorDao.findAll(pageable);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {

        return proveedorDao.save(proveedor);
    }

    @Override
    public void delete(Proveedor proveedor) {

        proveedorDao.delete(proveedor);
    }

    @Override
    public Proveedor findById(long id) {

        return proveedorDao.findById(id);
    }

    @Override
    public List<Proveedor> findByAdministrador(Administrador administrador) {
        return proveedorDao.findByAdministrador(administrador);
    }
}
