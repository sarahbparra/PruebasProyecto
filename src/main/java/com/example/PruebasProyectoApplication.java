package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.services.ProductoService;

@SpringBootApplication
public class PruebasProyectoApplication implements CommandLineRunner{

@Autowired
private ProductoService productoService;

	public static void main(String[] args) {
		SpringApplication.run(PruebasProyectoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
/**Agrego registros para admin: */

	}

}
