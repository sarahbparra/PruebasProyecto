package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entities.Proveedor;
import com.example.services.ProveedorService;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> findAll(
        @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Proveedor>> responseEntity = null;
        List<Proveedor> provedores = new ArrayList<>();

        Sort sortByNombre = Sort.by("nombre");

        // comprobamos si tenemos paginas y provedores:
        if (page != null && size != null) {
            // con paginación y ordenación:
            try {
                Pageable pageable = PageRequest.of(page, size, sortByNombre);

                Page<Proveedor> proveedoresPaginados = proveedorService.findAll(pageable);
                provedores = proveedoresPaginados.getContent();
                responseEntity = new ResponseEntity<List<Proveedor>>(provedores, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // sin paginación, pero con ordenamiento:
            try {
                provedores = proveedorService.findAll(sortByNombre);
                responseEntity = new ResponseEntity<List<Proveedor>>(provedores, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return responseEntity;

    }

    

    @GetMapping("/pageable")
    public ResponseEntity<Page<Proveedor>> findAll(Pageable pageable) {
        Page<Proveedor> proveedores = proveedorService.findAll(pageable);
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> findById(@PathVariable long id) {
        Proveedor proveedor = proveedorService.findById(id);
        return ResponseEntity.ok(proveedor);
    }

    @PostMapping
    public ResponseEntity<Proveedor> save(@RequestBody Proveedor proveedor) {
        Proveedor proveedorGuardado = proveedorService.save(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> update(@PathVariable long id, @RequestBody Proveedor proveedor) {
        proveedor.setId(id);
        Proveedor proveedorActualizado = proveedorService.save(proveedor);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        Proveedor proveedor = proveedorService.findById(id);
        proveedorService.delete(proveedor);
        return ResponseEntity.noContent().build();
    }
}
