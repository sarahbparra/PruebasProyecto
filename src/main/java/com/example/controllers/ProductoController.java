// package com.example.controllers;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.dao.DataAccessException;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.entities.Producto;
// import com.example.services.ProductoService;

// import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/productos")
// @RequiredArgsConstructor

// public class ProductoController {
    
//     @Autowired
//     private ProductoService productoService; 

//     @GetMapping
//     public ResponseEntity<List<Producto>> findAll(@RequestParam(name = "page", required = false) Integer page,
//     @RequestParam(name = "size", required = false) Integer size) {

//        ResponseEntity<List<Producto>> responseEntity = null; 

//        List<Producto> productos = new ArrayList<>(); 

//        Sort sortByNombre = Sort.by("descripcion");  

//        if(page != null && size != null){

//            //Con paginación y ordenamiento siempre. 
//            try {
               
//                Pageable pageable = PageRequest.of(page, size, sortByNombre);
//                Page<Producto> productosPaginados = productoService.findAll(pageable); 
//                productos = productosPaginados.getContent(); 
               
//                responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
               
//            } catch (Exception e) {
//                //Aquí no hay páginas, solo info

//                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//        }else{

//            //Si no se desea paginación, pero con ordenamiento

//            try {
//                productos = productoService.findAll(sortByNombre); 

//                responseEntity = new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);

//            } catch (DataAccessException e) {
               
//             System.out.println(e.getMostSpecificCause());
//             responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

//            }
//        }

//        return responseEntity; 
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Integer id){

//       ResponseEntity<Map<String,Object>> responseEntity = null; 
//       Map<String, Object> responseAsMap = new HashMap<>(); 
      

//       try {
          
//           Producto producto = productoService.findById(id); 

//           if(producto != null){

//               String successMessage = "Se ha encontrado el producto con id: " + id; 

//           responseAsMap.put("mensaje", successMessage); 
//           responseAsMap.put("producto", producto); 
          
//           responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK); 
//           }else{

//               String errorMessage = "No se ha encontrado el producto con id" + id; 
//               responseAsMap.put("error", errorMessage); 
//               responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);  
//           }


//       } catch (Exception e) {
         
//           String errorGrave = "Error grave"; 
//           responseAsMap.put("error", errorGrave); 
//           responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
//       }

//       return responseEntity; 

//     }
// }
