package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.entities.Administrador;
import com.example.entities.Comprador;
import com.example.entities.Producto;
import com.example.entities.Proveedor;
import com.example.entities.Comprador.Genero;
import com.example.services.AdministradorService;
import com.example.services.CompradorService;
import com.example.services.ProveedorService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.services.ProductoService;

@SpringBootApplication
public class PruebasProyectoApplication implements CommandLineRunner {

	@Autowired
	private AdministradorService administradorService;

	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private CompradorService compradorService;

	@Autowired
	private ProductoService productoService;

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

		// Si los creo desde aquí, luego me da problemas. Es mejor crearlos desde
		// Postman o MySQL
		proveedorService.save(Proveedor.builder()
				.id(1)
				.nombre("prove1")
				.apellidos("apellidos1")
				.telefono("telComp1")
				.correo("1@mail.COM")
				.documentacionProveedor("")
				.administrador(administradorService.findById(1))
				.productos(productoService.findAll())
				.build());

		compradorService.save(Comprador
				.builder()
				.id(1)
				.nombre("Compra1")
				.apellidos("apellidos1")
				.correo("1@mail.COM")
				.telefono("telComp1")
				.fechaNacimiento(LocalDate.of(1995, 12, 12))
				.imagenComprador(null)
				.genero(Genero.HOMBRE)
				.administrador(administradorService.findById(1))
				.build());

		compradorService.save(Comprador.builder()
				.id(1)
				.nombre("Compra2")
				.apellidos("apellidos2")
				.correo("2@mail.com")
				.telefono("telComp2")
				.fechaNacimiento(LocalDate.of(1995, 12, 12))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1))
				.build());

		productoService.save(Producto.builder()
				.id(1)

				.nombre("Producto1")
				.descripcion("muy rico")
				.procedencia("")
				.precio("")
				.peso(1.0)
				.volumen(1.0)
				.proveedor(proveedorService.findById(1))
				.imagenProducto("")
				.build());
	}
}
