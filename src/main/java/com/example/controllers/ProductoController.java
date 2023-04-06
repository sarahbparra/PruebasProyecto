package com.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

import com.example.entities.Producto;
import com.example.model.FileUploadResponse;
import com.example.services.ProductoService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor

public class ProductoController {
    
    @Autowired
    private ProductoService productoService; 

    @Autowired
private FileUploadUtil fileUploadUtil;

@Autowired
private FileDownloadUtil fileDownloadUtil;


    @GetMapping
    public ResponseEntity<List<Producto>> findAll(@RequestParam(name = "page", required = false) Integer page,
    @RequestParam(name = "size", required = false) Integer size) {

       ResponseEntity<List<Producto>> responseEntity = null; 

       List<Producto> productos = new ArrayList<>(); 

       Sort sortByNombre = Sort.by("nombre");  

       if(page != null && size != null){

           //Con paginación y ordenamiento siempre. 
           try {
               
               Pageable pageable = PageRequest.of(page, size, sortByNombre);
               Page<Producto> productosPaginados = productoService.findAll(pageable); 
               productos = productosPaginados.getContent(); 
               
               responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
               
           } catch (Exception e) {
               //Aquí no hay páginas, solo info

               responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
       }else{

           //Si no se desea paginación, pero con ordenamiento

           try {
               productos = productoService.findAll(sortByNombre); 

               responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);

           } catch (DataAccessException e) {
               
            System.out.println(e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

           }
       }

       return responseEntity; 
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Integer id){

      ResponseEntity<Map<String,Object>> responseEntity = null; 
      Map<String, Object> responseAsMap = new HashMap<>(); 
      

      try {
          
          Producto producto = productoService.findById(id); 

          if(producto != null){

              String successMessage = "Se ha encontrado el producto con id: " + id; 

          responseAsMap.put("mensaje", successMessage); 
          responseAsMap.put("producto", producto); 
          
          responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK); 
          }else{

              String errorMessage = "No se ha encontrado el producto con id" + id; 
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

 /**Método que guarda (persiste) un producto en la base de datos */
 @PostMapping
 //(consumes = "multipart/form-data")
 @Transactional
 public ResponseEntity<Map<String, Object>> insert(@Valid 
 @RequestPart(name = "producto") Producto producto,
  BindingResult result, @RequestPart(name = "file") MultipartFile file) throws IOException {
     Map<String, Object> responseAsMap = new HashMap<>();
     ResponseEntity<Map<String, Object>> responseEntity = null;

     /** Primero comprobar si hay errores en el producto recibido */

     if (result.hasErrors()) {
         List<String> errorMessages = new ArrayList<>();

         for (ObjectError error : result.getAllErrors()) {

             errorMessages.add(error.getDefaultMessage());

         }
         responseAsMap.put("errores", errorMessages);

         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

         return responseEntity;
     }
     /** Si no hay erroes, entonces persistimos el producto
     , comprobando previamente si nos han enviado una imágen
     o un archivo adjunto*/ 

     if(!file.isEmpty()) {
         String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
     
         producto.setImagenProducto(fileCode + "-" + file.getOriginalFilename());

         //Devolver información respecto al file rebicido

         FileUploadResponse fileUploadResponse = FileUploadResponse.builder()
         .fileName(fileCode + "-" + file.getOriginalFilename())
         .downloadURI("/productos/downloadFile/" + fileCode + "-" + file.getOriginalFilename())
         .size(file.getSize()).build();

         responseAsMap.put("info de la imagen: ", fileUploadResponse);

     }

     Producto productoDB = productoService.save(producto);

   

     try {

         if (productoDB != null) {
             String mensaje = "El producto se ha creado correctamente";
             responseAsMap.put("mensaje", mensaje);
             responseAsMap.put("producto", productoDB);
             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
         } else {
             // No se ha creado el producto
             responseAsMap.put("mensaje", "No se ha podido crear el producto");
             responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }

     } catch (DataAccessException e) {
         String errorGrave = "Ha tenido lugar un error grave" + ", y la causa más probable puede ser "
                 + e.getMostSpecificCause();
         responseAsMap.put("errorGrave", errorGrave);
         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
     }

     return responseEntity;

 }

/**Actualizo un producto en la base de datos */

@PutMapping("/{id}")
 @Transactional
 public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Producto producto, 
         BindingResult result, @PathVariable(name = "id") Integer id) {

     Map<String, Object> responseAsMap = new HashMap<>();
     ResponseEntity<Map<String, Object>> responseEntity = null;

     /** Primero comprobar si hay errores en el producto recibido */

     if (result.hasErrors()) {
         List<String> errorMessages = new ArrayList<>();

         for (ObjectError error : result.getAllErrors()) {

             errorMessages.add(error.getDefaultMessage());

         }
         responseAsMap.put("errores", errorMessages);

         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

         return responseEntity;
     }
     // Si no hay erroes, entonces persistimos el producto
     //Vinculando previamente el id que se recibe con el producto
     producto.setId(id);
     Producto productoDB = productoService.save(producto);

     try {

         if (productoDB != null) {
             String mensaje = "El producto se ha actualizado correctamente";
             responseAsMap.put("mensaje", mensaje);
             responseAsMap.put("producto", productoDB);
             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
         } else {
             // No se ha actualizado el producto
             responseAsMap.put("mensaje", "No se ha podido crear el producto");
             responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }

     } catch (DataAccessException e) {
         String errorGrave = "Ha tenido lugar un error grave" + ", y la causa más probable puede ser "
                 + e.getMostSpecificCause();
         responseAsMap.put("errorGrave", errorGrave);
         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
     }

     return responseEntity;

 }

//  /**Método de borrado por el que le pasas un id y se elimina */

 @DeleteMapping("/{id}")
  @Transactional

  public ResponseEntity<String> delete(@PathVariable(name = "id") Integer id) {


              
      ResponseEntity<String> responseEntity = null;

      

      try {
           //Recuperamos el producto:
           Producto producto = productoService.findById(id);
           if(producto != null) {
              productoService.delete(producto);
              responseEntity = new ResponseEntity<String>("Borrado exitosamente", HttpStatus.OK);
           } else {
              responseEntity = new ResponseEntity<String>("No existe el producto buscado", HttpStatus.NOT_FOUND);

           }

      } catch (DataAccessException e) {
          e.getMostSpecificCause();
          responseEntity = new ResponseEntity<String>("Error fatal", HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return responseEntity;

      }




}
