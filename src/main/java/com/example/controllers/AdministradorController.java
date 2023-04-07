package com.example.controllers;

import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaType;

import com.example.entities.Administrador;
import com.example.services.AdministradorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//En lugar de controller usamos un restcontroller
//En una API Rest se gestiona un recurso y en dependencias de http sera la peticion u otra
@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    /**
     * Método para obtener todos los administradores como una lista. En este caso,
     * al ser podos
     * administradores, hemos decidido que una lista es suficiente, sin necesidad de
     * paginado o tamaño
     */
    // FUNCIONA
    @GetMapping
    public ResponseEntity<List<Administrador>> findAll() {
        ResponseEntity<List<Administrador>> responseEntity = null;
        List<Administrador> administradores = administradorService.findAll();

        if (administradores.isEmpty()) {
            try {
                administradores = administradorService.findAll();
                responseEntity = new ResponseEntity<List<Administrador>>(administradores, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            responseEntity = ResponseEntity.ok(administradores);
        }

        return responseEntity;
    }

    // Obtener un administrador por su id:
    // FUNCIONA
    // falta mensaje de que no se ha podido!!!!
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Long id) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);

        try {
            Administrador administrador = administradorService.findById(id); //sin el opcional no reconoce los mensajes cuando no esta

            if (administrador != null) {
                String successMessage = "Se ha encontrado el administrador con id: " + id;
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("administrador", administrador);
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
            } else {
                String errorMessage = "No se ha podido encontrar el administrador con id: " + id;
                responseAsMap.put("errores", errorMessage);
                System.out.println("Error: " + errorMessage);

                responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            String errorGrave = "Error grave";
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            responseAsMap.put("error", errorGrave);
            System.out.println("Error grave: " + e.getMessage()); // añadido como comprobante
        }

        return responseEntity;
    }

    // Crear un nuevo administrador.
    // OJO: si en el cuerpo mantienes un id que ya existe, modificará los datos de
    // ese administrador
    // FUNCIONA
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Map<String, Object>> createAdministrador(
            @Valid @RequestBody Administrador administrador,
            BindingResult result) {
        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        // Si hay que comprobar si hay errores en el administrador recibido
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            // Se recorren todos los errores y se agregan a la lista
            for (ObjectError error : result.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            } // ALTERNATIVA: convertir for en stream con expresión lambda
            responseAsMap.put("errores", errorMessages);
            // Se devuelve la respuesta con los errores y un código de estado BAD REQUEST
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }
        // Si no hay errores, se guarda el administrador en la base de datos
        Administrador adminDataBase = administradorService.save(administrador);

        try {
            if (adminDataBase != null) {
                String mensaje = "El administrador se ha creado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("administrador", adminDataBase);
                // Se devuelve la respuesta con el administrador creado y un código de estado
                // CREATED
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.CREATED);
            }

        } catch (DataAccessException e) {
            // Si ocurre una excepción al guardar el administrador, se devuelve una
            // respuesta con un mensaje de error
            // y un código de estado INTERNAL SERVER ERROR
            String errorGrave = "Se ha producido un error grave, y la causa más probable puede ser: "
                    + e.getMostSpecificCause();
            responseAsMap.put("errorGrave", errorGrave);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // Actualizar un administrador existente. OJO: si pones un id que no exite, lo
    // creará con el siguiente id autogenerado
    // FUNCIONA
    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> updateAdministrador(
            @Valid @RequestBody Administrador administrador,
            BindingResult result,
            @PathVariable(name = "id") Integer id) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Si hay que comprobar si hay errores en el administrador recibido
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            // Se recorren todos los errores y se agregan a la lista
            for (ObjectError error : result.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            } // ALTERNATIVA: convertir for en stream con expresión lambda

            responseAsMap.put("errores", errorMessages);
            // Se devuelve la respuesta con los errores y un código de estado BAD REQUEST
            return new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
        }

        // Si no hay errores, entonces persistimos el producto. Para ello se vicula,
        // previamente, el id que se recibe con el producto
        administrador.setId(id);
        Administrador adminDataBase = administradorService.save(administrador);

        try {

            // Se crea la respuesta con un mensaje de éxito y el administrador creado
            if (adminDataBase != null) {
                String mensaje = "El producto se ha actualizado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("producto", adminDataBase);
                // Se devuelve la respuesta con el administrador creado y un código de estado
                // CREATED
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                        HttpStatus.OK);
            }

        } catch (DataAccessException e) {
            // Si ocurre una excepción al guardar el administrador, se devuelve una
            // respuesta con un mensaje de error
            // y un código de estado INTERNAL SERVER ERROR
            String errorGrave = "Se ha producido un error grave, y la causa más probable puede ser: "
                    + e.getMostSpecificCause();

            responseAsMap.put("errorGrave", errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    // Eliminar un administrador por su id
    // FUNCIONA
    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {

        ResponseEntity<String> responseEntity = null;

        try {

            // Primero lo recuperamos

            Administrador administrador = administradorService.findOptById(id).orElse(null);
       

            // Si existe, lo borramos
            if (administrador != null) {
                administradorService.delete(administrador);

                responseEntity = new ResponseEntity<String>("El administrador " + id + "  se ha borrado correctamente",
                        HttpStatus.OK);
            } else {
                // De lo contrario, informamos de que no existe
                responseEntity = new ResponseEntity<String>("Este administrador con el id " + id + " no existe", HttpStatus.NOT_FOUND);

            }

        } catch (DataAccessException e) {
            e.getMostSpecificCause();
            responseEntity = new ResponseEntity<String>("Error fatal", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }
}