package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Administrador;
import com.example.entities.Proveedor;
import com.example.services.AdministradorService;
import com.example.services.ProveedorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PruebasProyectoApplication implements CommandLineRunner {

	@Autowired
	private AdministradorService administradorService;

	@Autowired
	private ProveedorService proveedorService;

	public static void main(String[] args) {
		SpringApplication.run(PruebasProyectoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/**
		 * Método para agregar registros de muestra para administrador (crear y añadir
		 * administradores),
		 * Empleado (crear y añadir empleados) y proveedor (crear y añadir
		 * proveedors):
		 */

		administradorService.save(Administrador.builder()
				.nombre("Admin1")
				.apellidos("apellidos1")
				.correo("admin1@gmail.com")
				.telefono("telAdmin1")
				.build());

		administradorService.save(Administrador.builder()
				.nombre("Admin2")
				.apellidos("apellidos2")
				.correo("admin2@gmail.com")
				.telefono("telAdmin2")
				.build());

		// proveedorService.save(Proveedor.builder()
		// .id(1)
		// 		.nombre("prove1")
		// 		.administrador(administradorService.findById(1))
		// 		.build());
	}
}
