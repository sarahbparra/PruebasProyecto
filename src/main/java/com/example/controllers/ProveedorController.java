package com.example.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Producto;
import com.example.entities.Proveedor;
import com.example.model.FileUploadResponse;
import com.example.services.ProductoService;
import com.example.services.ProveedorService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController //Para que todas las peticiones que devuelva sea JSON //En API Rest lo que se solicita o lo que se gestiona son recursos y en dependencia del verbo http que se use, será una petición u otra 

@RequestMapping("/proveedores")  // en el post man ponemos la url http://localhost:8080/proveedores
@RequiredArgsConstructor


    public class ProveedorController {
    

    @Autowired //insertamos dependencia con el @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService; 

    @Autowired 
    private FileUploadUtil fileUploadUtil;

    //Tambien podemos inyectar una dependencia usando un constructor, PARA ELLO INSERTAMOS EL @RequiredArgsConstructor
    private final FileDownloadUtil fileDownloadUtil;

    
    /**
     * Método para obtener todos los Proveedores como una lista.(Irene)
     * En este caso, al ser Proveedores, hemos decidido que una lista es suficiente, sin necesidad de paginado o tamaño
     */
   
    @GetMapping
    public ResponseEntity<List<Proveedor>> findAll() {
        ResponseEntity<List<Proveedor>> responseEntity = null;
        List<Proveedor> proveedores = proveedorService.findAll();

        if (proveedores.isEmpty()) {
            try {
                proveedores = proveedorService.findAll();
                responseEntity = new ResponseEntity<List<Proveedor>>(proveedores, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            responseEntity = ResponseEntity.ok(proveedores);
        }

        return responseEntity;
    }
     ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     
     /**
      * Metodo que me devuelve un proveedor dado su id
      */

      @GetMapping("/{id}") //para mostrar es get y para guardar es post 
      public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Integer id){

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>(); 



        try {
            
            Proveedor proveedor = proveedorService.findById(id);

            if(proveedor != null){
                String successMessage = "Se ha encontrado el proveedor con id:" + id; 
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("proveedor", proveedor);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
           
            } else {
            String errorMessage = "No se ha encontrado el proveedor con id:" + id; 
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

      //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
      /**
       * El metodo siguiente persiste o guarda un proveedor en la base de datos 
       */
        
       @PostMapping( consumes = "multipart/form-data")
       @Transactional 
       
       
       public ResponseEntity<Map<String,Object>> insert( //El metodo recibe un proveedor como parametro
        @Valid
        @RequestPart(name = "proveedor") Proveedor proveedor,
         BindingResult result,
         @RequestPart(name = "file") MultipartFile file) throws IOException{
       
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        
        if(result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>(); 

            for(ObjectError error : result.getAllErrors()) {  
                 errorMessages.add(error.getDefaultMessage()); //siendo el mensaje por defecto los mensajes que hemos creado en la clase Proveedor
        }
    
        responseAsMap.put("errores", errorMessages);
        responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
        return responseEntity;
    
    }

    
 //Si no hay errores persistimos el proveedor,PARA ELLO comprobamos previamente si nos han enviado una imagen o archivo 

            if(!file.isEmpty()){
                String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
                proveedor.setImagenProveedor(fileCode+"-"+file.getOriginalFilename()); 
                
                
 
                FileUploadResponse fileUploadResponse = FileUploadResponse
                             .builder()
                             .fileName(fileCode + "-" + file.getOriginalFilename())
                             .downloadURI("/proveedores/downloadFile/"
                                 +fileCode + "-" + file.getOriginalFilename())
                             .size(file.getSize())
                             .build();
 
                 responseAsMap.put("info de la imagen: ", fileUploadResponse);     
 
             }
             Proveedor proveedorDB = proveedorService.save(proveedor);

//Codigo que me ha añadido Sarah, y es para cuando haga un post en el postman se actualize el dato del proveedor con
//su producto en mi base de datos 

             List<Producto> productos = productoService.findAll(); 
             productos.stream().filter(p -> p.getProveedor() == null). 
             forEach(p -> p.setProveedor(proveedorDB));


            
             try{
             if(proveedorDB != null){
                 String mensaje ="el proveedor se ha creado correctamente";
                 responseAsMap.put("mensaje", mensaje);
                 responseAsMap.put("proveedor", proveedorDB);
                 responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
             
                } else{
                 //En caso que no se haya creado el PROVEEDOR
            //  responseAsMap.put("mensaje", "No se ha podido crear el proveedor");
            //  responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

         }

             
             }catch(DataAccessException e){
                 String errorGrave = "Ha tenido lugar un error grave" + ", y la causa mas probable puede ser"+
                                       e.getMostSpecificCause();
                 responseAsMap.put("error grave", errorGrave);
                 responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
             }
 
            
         return responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
     }

     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

       /**
       * El metodo siguiente actualiza el proveedor en la base de datos 
       */
        
       @PutMapping("/{id}")  //le pasamos el id del proveedor y para actualizar en el postMan usamos el @Put...
       @Transactional 
       
       
       public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Proveedor proveedor, 
                                                                BindingResult result,
                                                                @PathVariable(name = "id") Integer id) { 
 
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        
        if(result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>(); 

            for(ObjectError error : result.getAllErrors()) {  
                 errorMessages.add(error.getDefaultMessage()); 
        }
    
        responseAsMap.put("errores", errorMessages);
        responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
        return responseEntity;
    
    }


//SI NO HAY ERRORES, ENTONCES PERSISTIMOS EL proveedor Vinculando previamente el id que se recibe con el proveedor
    proveedor.setId(id);
    Proveedor proveedorDB = proveedorService.save(proveedor);
    

   try {
    if(proveedorDB != null){
        String mensaje = "El proveedor se ha actualizado correctamente";
        responseAsMap.put("mensaje", mensaje);
        responseAsMap.put("proveedor", proveedorDB);
        responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
    }

    else{
        
    // // No se ha actualizado el proveedor
    // responseAsMap.put("mensaje", "No se ha podido crear el proveedor");
    // responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
   
}

    
   } catch (DataAccessException e) {

    String errorGrave = "Ha tenido lugar un error grave, y la causa mas probable puede ser" + e.getMostSpecificCause();
    responseAsMap.put("errorGrave", errorGrave);
    responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
   }
        return responseEntity;
       
    
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
       * El metodo siguiente elimina un proveedor de la base de datos 
       */
        
       @DeleteMapping("/{id}")  //recibe el id del proveedor que queremos borrar
       @Transactional 
      

       public ResponseEntity <String> delete(@PathVariable(name = "id") Integer id) { 
                                                            
        ResponseEntity<String> responseEntity = null;

        try {
            //Primero buscamos el proveedor antes de eliminarlo
            Proveedor proveedor = proveedorService.findById(id);
            
            if(proveedor != null){ //si el proveedor  existe lo elimnamos entonces
                proveedorService.delete(proveedor);
                responseEntity = new ResponseEntity<String>("El proveedor se ha borrado correctamente", HttpStatus.OK);
                
            } else {

              responseEntity = new ResponseEntity<String>("No existe el proveedor", HttpStatus.NOT_FOUND);
         
            }

        } catch (DataAccessException e) {
           
    
            responseEntity = new ResponseEntity<String>("Error fatal", HttpStatus.INTERNAL_SERVER_ERROR);

            System.out.println(e.getMostSpecificCause());
         
            
        }
        
        return responseEntity;
        
        
       
    }
}