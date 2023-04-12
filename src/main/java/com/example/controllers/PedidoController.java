package com.example.controllers;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Pedido;
import com.example.services.PedidoService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")

public class PedidoController {
    
    @Autowired
    private PedidoService pedidoService; 

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll
    (@RequestParam(name = "page", required = false) Integer page, 
    @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Pedido>> responseEntity = null; 

        List<Pedido> pedidos = new ArrayList<>(); 

        //Se puede sort por varias propiedades, podría probar por apellidos?
        //Por probar

        Sort sortByCodigo = Sort.by("codigoPedido"); 

        if(page != null && size != null){

            try {
                
                Pageable pageable = PageRequest.of(page, size, sortByCodigo); 
                Page<Pedido> pedidosPaginados = pedidoService.findAll(pageable); 
                pedidos = pedidosPaginados.getContent(); 

                responseEntity = new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.OK); 

            } catch (Exception e) {
                //Si no hay páginas es que la petición era errónea. 

                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
            }
        } else {

            //Por otra parte, si solo se busca que esté ordenado 

            try {
                
                pedidos = pedidoService.findAll(sortByCodigo); 

                responseEntity = new ResponseEntity<List<Pedido>>(pedidos, HttpStatus.OK); 

            } catch (Exception e) {
                
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return responseEntity; 



    }

    @GetMapping("/{id}")

    public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Long id){

        ResponseEntity<Map<String, Object>> responseEntity = null; 
        Map<String, Object> responseAsMap = new HashMap<>(); 

        try {
            
            Pedido pedido = pedidoService.findById(id);  

            if(pedido != null){

                String succesMessage = "Pedido con id " + id + " ha sido encontrado :)"; 
                responseAsMap.put("mensaje", succesMessage); 
                responseAsMap.put("pedido", pedido); 

                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK); 

            } else {

                String errorMessage = "Pedido con id " + id + " no ha sido encontrado :("; 
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

    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> insert(
        @Valid
        @RequestBody Pedido pedido,
        BindingResult result){

            Map<String, Object> responseAsMap = new HashMap<>(); 
            ResponseEntity<Map<String, Object>> responseEntity = null; 

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

             Pedido pedidoDB = pedidoService.save(pedido); 

             String codigo = pedidoService.generarCodigo(); 

            pedido.setFechaPedido(LocalDate.now());

            pedido.setCodigoPedido(codigo);

             try {

                if(pedidoDB != null){


                    String mensaje = "Se ha dado de alta el pedido correctamente"; 
                    responseAsMap.put("mensaje", mensaje);
                    responseAsMap.put("pedido", pedidoDB); 
                    responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
    
                } else {
    
                    //No se ha dado de alta a nadie. 
                }
    
    
            } catch (DataAccessException e) {
                
                String errorGrave = "Ha tenido lugar un error grave " + ", y la causa más probable puede ser" 
                + e.getMostSpecificCause(); 
                responseAsMap.put("errorGrave", errorGrave); 
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR); 
            }
    
    
            return responseEntity; 
        }

        


}
