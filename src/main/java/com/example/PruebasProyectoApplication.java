package com.example;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.dao.AdministradorDao;
import com.example.dao.CompradorDao;
import com.example.dao.ProductoDao;
import com.example.dao.ProveedorDao;
import com.example.entities.Administrador;
import com.example.entities.Comprador;
import com.example.entities.Comprador.Genero;
import com.example.entities.Producto;
import com.example.entities.Proveedor;

@SpringBootApplication
public class PruebasProyectoApplication implements CommandLineRunner {

	@Autowired
	private AdministradorDao administradorService;

	@Autowired
	private ProveedorDao proveedorService;

	@Autowired
	private CompradorDao compradorService;

	@Autowired
	private ProductoDao productoService;


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
				.nombre("Irene")
				.apellidos("González Moreno")
				.correo("irene@gmail.com")
				.telefono("123 456 789")
				.build());

		administradorService.save(Administrador.builder()
				.nombre("Zineb")
				.apellidos("Afkir Benjelloun-Andaloussi")
				.correo("zineb@gmail.com")
				.telefono("123 456 789")
				.build());

		// Admin sin proveedores ni compradores:

		administradorService.save(Administrador.builder()
				.nombre("Sarah")
				.apellidos("Benabidi Parra")
				.correo("sarah@gmail.com")
				.telefono("123 456 789")
				.build());

			administradorService.save(Administrador.builder()
				.nombre("Celia")
				.apellidos("Cava Ruíz")
				.correo("celia@gmail.com")
				.telefono("123 456 789")
				.build());				

		// 3 PROVEEDORES
		proveedorService.save(Proveedor.builder()
				.id(1)
				.nombre("Judith")
				.apellidos("Alende Martínez")
				.telefono("123 456 789")
				.correo("judith@gmail.com")
				.documentacionProveedor("")
				.administrador(administradorService.findById(1L).get())
				.productos(productoService.findAll())
				.build());

		proveedorService.save(Proveedor.builder()
				.id(2)
				.nombre("Salma")
				.apellidos("Asbayti Sakai")
				.telefono("123 456 789")
				.correo("salma@gmail.COM")
				.documentacionProveedor("")
				.administrador(administradorService.findById(2L).get())
				.productos(productoService.findAll())
				.build());

			proveedorService.save(Proveedor.builder()
				.id(3)
				.nombre("María José")
				.apellidos("Andreu Álvarez")
				.telefono("123 456 789")
				.correo("mj@gmail.COM")
				.documentacionProveedor("")
				.administrador(administradorService.findById(2L).get())
				.productos(productoService.findAll())
				.build());

			proveedorService.save(Proveedor.builder()
				.id(4)
				.nombre("Elisabet")
				.apellidos("Agulló García")
				.telefono("123 456 789")
				.correo("elisabet@gmail.COM")
				.documentacionProveedor("")
				.administrador(administradorService.findById(3L).get())
				.productos(productoService.findAll())
				.build());

			proveedorService.save(Proveedor.builder()
				.id(5)
				.nombre("Llanos")
				.apellidos("Ruíz Moreno")
				.telefono("123 456 789")
				.correo("llanos@gmail.COM")
				.documentacionProveedor("")
				.administrador(administradorService.findById(4L).get())
				.productos(productoService.findAll())
				.build());


		// Admin sin proveedores ni compradores:

		// 3 COMPRADORES
		compradorService.save(Comprador
				.builder()
				.id(1)
				.nombre("Cristina")
				.apellidos("Galindo Velasco")
				.correo("cristina@gmail.com")
				.telefono("123 456 789")
				.fechaNacimiento(LocalDate.of(1999, 12, 12))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1L).get())
				.build());

		compradorService.save(Comprador.builder()
				.id(2)
				.nombre("Dayana")
				.apellidos("Del Villar Gutierrez")
				.correo("dayana@gmail.com")
				.telefono("telComp2")
				.fechaNacimiento(LocalDate.of(2002, 12, 12))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1L).get())
				.build());

		compradorService.save(Comprador.builder()
				.id(3)
				.nombre("Aurore Rose Julie")
				.apellidos("Boudinot")
				.correo("aurore@gmail.com")
				.telefono("123 456 789")
				.fechaNacimiento(LocalDate.of(2000, 04, 17))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1L).get())
				.build());

		compradorService.save(Comprador.builder()
				.id(4)
				.nombre("Mara")
				.apellidos("Romero Diaz")
				.correo("mara@gmail.com")
				.telefono("123 456 789")
				.fechaNacimiento(LocalDate.of(1999, 12, 12))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1L).get())
				.build());

			compradorService.save(Comprador.builder()
				.id(5)
				.nombre("Belén")
				.apellidos("Chocano Collado")
				.correo("belen@gmail.com")
				.telefono("123 456 789")
				.fechaNacimiento(LocalDate.of(1999, 12, 12))
				.imagenComprador(null)
				.genero(Genero.MUJER)
				.administrador(administradorService.findById(1L).get())
				.build());


		// 3 PRODUCTOS
		productoService.save(Producto.builder()
				.id(1)
				.nombre("Queso Caserío")
				.descripcion("Queso de leche de cabra, de pasta prensada y corteza natural.")
				.procedencia("Aledo")
				.precio("11.50 €")
				.peso(300.0)
				.volumen(0.0)
				.proveedor(proveedorService.findById(1))
				.imagenProducto("")
				.build());

		productoService.save(Producto.builder()
				.id(2)
				.nombre("Vino de la Ermita")
				.descripcion("Vino de color rojo intenso con aromas a frutas maduras, especias y notas balsámicas.")
				.procedencia("Jumilla")
				.precio("15.50 €")
				.peso(0.0)
				.volumen(0.75)
				.proveedor(proveedorService.findById(2))
				.imagenProducto("")
				.build());

		productoService.save(Producto.builder()
				.id(3)
				.nombre("Queso fresco de Cabra")
				.descripcion("Queso elaborado con leche cruda de cabra y tiene un sabor intenso y ligeramente picante.")
				.procedencia("Totana")
				.precio("13.70 €")
				.peso(300.0)
				.volumen(0.0)
				.proveedor(proveedorService.findById(4))
				.imagenProducto("")
				.build());

			productoService.save(Producto.builder()
				.id(4)
				.nombre("Vino Monastrell")
				.descripcion("Elaborado con la uva homónima, que tiene un característico sabor a frutas negras maduras.")
				.procedencia("Yecla")
				.precio("18.50 €")
				.peso(0.0)
				.volumen(0.75)
				.proveedor(proveedorService.findById(2))
				.imagenProducto("")
				.build());

		
	}
}
