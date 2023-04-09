package com.example.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Comprador;
import com.example.model.FileUploadResponse;
import com.example.services.CompradorService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/compradores")
@RequiredArgsConstructor

public class CompradorController {
    
    @Autowired
    private CompradorService compradorService; 

    @Autowired
    private FileUploadUtil fileUploadUtil; 

    @Autowired
    private FileDownloadUtil fileDownloadUtil; 

    //Método para recuperar un listado de compradores 

    @GetMapping 
     public ResponseEntity<List<Comprador>> findAll
     (@RequestParam(name = "page", required = false) Integer page, 
     @RequestParam(name = "size", required = false) Integer size){

        ResponseEntity<List<Comprador>> responseEntity = null; 

        List<Comprador> compradores = new ArrayList<>(); 

        Sort sortById = Sort.by("id"); 

        if(page != null && size != null){

            try {

                Pageable pageable = PageRequest.of(page, size, sortById); 
                Page<Comprador> compradoresPaginados = compradorService.findAll(pageable); 
                compradores = compradoresPaginados.getContent(); 

                responseEntity = new ResponseEntity<List<Comprador>>(compradores, HttpStatus.OK); 
            

            } catch (Exception e) {
                
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
            }
            
        } else {

            try {
                
                compradores = compradorService.findAll(sortById); 

                responseEntity = new ResponseEntity<List<Comprador>>(compradores, HttpStatus.OK); 
            } catch (Exception e) {
                
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT); 
            }
        }


        return responseEntity; 

     }

     
     //Método para recuperar el comprador por id 

     @GetMapping("/{id}")

     public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Long id){

        ResponseEntity<Map<String, Object>> responseEntity = null; 
        Map<String, Object> responseAsMap = new HashMap<>(); 

        try {
            
            Comprador comprador = compradorService.findById(id); 

            if(comprador != null){

                String succesMessage = "Comprador con id " + id + " ha sido encontrado"; 
                responseAsMap.put("mensaje", succesMessage); 
                responseAsMap.put("comprador", comprador); 

                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK); 

            } else {

                String errorMessage = "No se ha encontrado el comprador con id " + id; 
                responseAsMap.put("error", errorMessage); 
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND); 

            }

        } catch (Exception e) {
            String errorGrave = "Error grave"; 
            responseAsMap.put("error", errorGrave); 
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR); 
        }

        return responseEntity; 
    }
    //Método para dar de alta a un comprador 
    
    @PostMapping(consumes = "multipart/form-data")
    @Transactional

    public ResponseEntity<Map<String, Object>> insert(
        @Valid
        @RequestPart(name = "comprador") Comprador comprador, 
        BindingResult result, 
        @RequestPart(name = "file") MultipartFile file) throws IOException {

            Map<String, Object> responseAsMap = new HashMap<>(); 
            ResponseEntity<Map<String, Object>> responseEntity = null;   

            LocalDate fechaNacimiento = comprador.getFechaNacimiento(); 
            LocalDate fechaActual = LocalDate.now();
            int edad = Period.between(fechaNacimiento, fechaActual).getYears();

            if(result.hasErrors()){
                //Ahora se guardan aquí los erroes
                List<String> errorMessages = new ArrayList<>(); 
    
                for (ObjectError error : result.getAllErrors()){
    
                    errorMessages.add(error.getDefaultMessage()); 
    
                }
    
                responseAsMap.put("errores", errorMessages); 
    
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST); 
    
                return responseEntity; 
             }
             if(!file.isEmpty()) {
    
                String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file); 
                comprador.setImagenComprador(fileCode + "-" + file.getOriginalFilename());
    
                //Devolver información respecto al file recibido. 
    
                FileUploadResponse fileUploadResponse = FileUploadResponse
                .builder()
                .fileName(fileCode + "-" + file.getOriginalFilename())
                .downloadURI("/compradores/downloadFile/" + fileCode + "-" + file.getOriginalFilename())
                .size(file.getSize())
                .build(); 
    
                responseAsMap.put("info del archivo", fileUploadResponse); 
    
             }

             Comprador compradorDB = compradorService.save(comprador);

             try {

                if(compradorDB != null){

                    if(edad < 18){

                    String errorFecha = "No se puede dar de alta sin ser mayor de edad  ._."; 
                    responseAsMap.put("error", errorFecha); 
                    responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
                    return responseEntity;

                    }
                

                String mensaje = "Se ha dado de alta al comprador correctamente"; 
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("comprador", compradorDB); 
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

            } else {


            }
                
             } catch (DataAccessException e) {
                
                String errorGrave = "Ha tenido lugar un error grave, y la causa más probable puede ser: " 
            + e.getMostSpecificCause(); 
            responseAsMap.put("errorGrave", errorGrave); 
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR); 
             }

            return responseEntity; 
        }

//Método para eliminar compradores 

@DeleteMapping("/{id}")
    @Transactional

    public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id){

        ResponseEntity<String> responseEntity = null; 

        try {
            
            Comprador comprador = compradorService.findById(id); 

            if(comprador != null){

                compradorService.delete(comprador);
                responseEntity = new ResponseEntity<String>("El comprador "+ id + "  se ha borrado correctamente", HttpStatus.OK); 
            } else {

                responseEntity = new ResponseEntity<String>(" El comprador con id " + id
                + " no existe.", HttpStatus.NOT_FOUND); 
            }
        } catch (DataAccessException e) {
            
            e.getMostSpecificCause(); 
            responseEntity = new ResponseEntity<String>("Error fatal", HttpStatus.INTERNAL_SERVER_ERROR); 
        }


        return responseEntity; 
    }


    //Método para actualizar un comprador. 

    @PutMapping("/{id}")
    @Transactional
   public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Comprador comprador, 
         BindingResult result, 
         @PathVariable(name = "id") Integer id){

     Map<String, Object> responseAsMap = new HashMap<>(); 
     ResponseEntity<Map<String, Object>> responseEntity = null; 

     /**
      * Primero. Comprobar si hay errores en el comprador recibido. 
      */

      if(result.hasErrors()){
         //Ahora se guardan aquí los erroes
         List<String> errorMessages = new ArrayList<>(); 

         for (ObjectError error : result.getAllErrors()){

             errorMessages.add(error.getDefaultMessage()); 

         }

         responseAsMap.put("errores", errorMessages); 

         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST); 

         return responseEntity; 
      }

      //Si no hay errores, entonces persistimos el producto 
      //Vinculando previamente el id que se recibe con el producto

      comprador.setId(id);

      Comprador compradorDB = compradorService.save(comprador); 

      try {

         if(compradorDB != null){

             String mensaje = "El comprador se ha creado correctamente."; 
             responseAsMap.put("mensaje", mensaje); 
             responseAsMap.put("comprador", compradorDB); 
             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK); 
 
          } else {
 
             //No se ha actualizado el comprador. 
 
          }
         
      } catch (DataAccessException e) {

         String errorGrave = "Ha tenido lugar un error grave " + ", y la causa más probable puede ser" 
         + e.getMostSpecificCause(); 
         responseAsMap.put("errorGrave", errorGrave); 
         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR); 
      }

      

     return responseEntity; 

   }

        /**
     *  Implementa filedownnload end point API 
     **/    
    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "fileCode") String fileCode) {

        Resource resource = null;

        try {
            resource = fileDownloadUtil.getFileAsResource(fileCode); 
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found ", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType))
        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
        .body(resource);

    }
        

}
