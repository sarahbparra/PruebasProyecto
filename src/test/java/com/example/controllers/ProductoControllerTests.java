package com.example.controllers;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.entities.Producto;
import com.example.entities.Proveedor;
import com.example.services.ProductoService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc

@AutoConfigureTestDatabase(replace = Replace.NONE)

public class ProductoControllerTests {
    
    @Autowired 
    private MockMvc mockMvc; 

    @MockBean
    private ProductoService productoService; 

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private FileUploadUtil fileUploadUtil;

    @MockBean
    private FileDownloadUtil fileDownloadUtil;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp () {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    @DisplayName("Test de intento de guardar un producto sin autorizacion")
    void testGuardarProducto() throws Exception {

       Proveedor proveedor = Proveedor.builder()
        .nombre("Proveedor")
        .apellidos("Test Test")
        .correo("proveedor@gg.com")
        .telefono("111 222 333")
        .build(); 

        Producto producto = Producto.builder()
        .nombre("Queso Test")
        .descripcion("test")
        .procedencia("prueba")
        .precio("00.00€")
        .peso(99.99)
        .volumen(99.99)
        .proveedor(proveedor)
        .build(); 

        String jsonStringProduct = objectMapper.writeValueAsString(producto);

        MockMultipartFile bytesArrayProduct = new MockMultipartFile("producto",
                null, "application/json", jsonStringProduct.getBytes());

        mockMvc.perform(multipart("/productos")
                .file("file", null)
                .file(bytesArrayProduct))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

//     @DisplayName("Test guardar producto con usuario mockeado")
//     @Test
//     @WithMockUser(username = "admin@gmail.com", 
//                   authorities = {"ADMIN"})
//     void testGuardarProductoConUserMocked() throws Exception {

//         Proveedor proveedor = Proveedor.builder()
//         .nombre("Proveedor")
//         .apellidos("Test Test")
//         .correo("proveedor@gg.com")
//         .telefono("111 222 333")
//         .build(); 

//         Producto producto = Producto.builder()
//         .nombre("Queso Test")
//         .descripcion("test")
//         .procedencia("prueba")
//         .precio("00.00€")
//         .peso(99.99)
//         .volumen(99.99)
//         .proveedor(proveedor)
//         .build();

//         String jsonStringProducto = 
//         objectMapper.writeValueAsString(producto); 

//         MockMultipartFile bytesArrayProduct = new MockMultipartFile("producto",
//         null, "application/json", jsonStringProducto.getBytes());

// mockMvc.perform(multipart("/productos")
//         .file("file", null)
//         .file(bytesArrayProduct))
//         .andExpect(status().isOk())
//         .andDo(print());


//     }
}
