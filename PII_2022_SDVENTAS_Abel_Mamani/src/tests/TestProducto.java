package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dominio.Marca;
import dominio.Precio;
import dominio.Producto;
import dominio.TipoDeProducto;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import exceptions.ExceptionStock;
import utiles.Util;


class TestProducto {

	@Test
	@Order(1)
	@DisplayName("01 Constructores")
	void test01(){
		
		//arrange 
		Producto p1 = null;
		Producto p2 = null;
		Producto p3 = null;
		
		//actual
		try {
			p1 = new Producto(1, "ram 4 GB", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 12, 12.0);
			p2 = new Producto(1, "ram 4 GB", Marca.ADATA, TipoDeProducto.RAM); 
			p3 = new Producto(1, "ram 4 GB", TipoDeProducto.RAM); 
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertNotNull(p1);
		assertNotNull(p2);	
		assertNotNull(p3);
	}
	
	@Test
	@Order(2)
	@DisplayName("02 Getters")
	void test02(){
		
		//arrange
		int codigoProducto = 1;
		String nombre = "ram 4 GB";
		Marca marca = Marca.ADATA;
		TipoDeProducto tipoDeProducto = TipoDeProducto.RAM;
		String descripcion = "descripcion de la ram";
		int stock = 10;
		float precio = 4000;
		Producto p1 = null;
		
		try{
			p1 = new Producto(codigoProducto, nombre, marca, tipoDeProducto, descripcion, stock, precio);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertEquals(1, p1.getCodigoProducto());
		assertEquals(Util.getStringConFormato(nombre), p1.getNombre());
		assertEquals(marca, p1.getMarca());
		assertEquals(tipoDeProducto, p1.getTipoProducto());
		assertEquals(descripcion, p1.getDescripcion());
		assertEquals(stock, p1.getStock());
		assertEquals(String.valueOf(precio), String.valueOf(p1.getPrecioActual()));
		
	}
	
	@Test
	@Order(3)
	@DisplayName("03 Setters")
	void test03(){
		
		//Arrange
		Producto p1 = null;
		
		try{
			p1 = new Producto(1, "ram 4 GB", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 2, 45);
			
			//actual
			p1.setNombre("ryzen 3200g");
			p1.setMarca(Marca.KINGSTON);
			p1.setTipoProducto(TipoDeProducto.CPU);
			p1.setDescripcion("nueva descripcion");
			p1.setStock(15);
			p1.setPrecio(4520);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertEquals(1, p1.getCodigoProducto());
		assertEquals("Ryzen 3200g", p1.getNombre());
		assertEquals(Marca.KINGSTON, p1.getMarca());
		assertEquals(TipoDeProducto.CPU, p1.getTipoProducto());
		assertEquals("nueva descripcion", p1.getDescripcion());
		assertEquals(15, p1.getStock());
		assertEquals(String.valueOf(4520.0), String.valueOf(p1.getPrecioActual()));
		
		
		
	}

		
	@Test
	@Order(4)
	@DisplayName("04 getPrecioActual y setPrecio")
	void test04(){
		
		//arange
		Producto p1 = null;
		double precioActual1 = 0.0;
		double precioActual2 = 0.0;
		double precioActual3 = 0.0;
		
		try {
			p1 = new Producto(2, "Ryzen 3200g", Marca.AMD, TipoDeProducto.CPU, "nuevaDescripcion", 2, 15000);
		
			//actual
			precioActual1 = p1.getPrecioActual();
			p1.setPrecio(100.0);
			precioActual2 = p1.getPrecioActual();
			p1.setPrecio(150.0);
			precioActual3 = p1.getPrecioActual();
			
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertEquals(String.valueOf(15000.0), String.valueOf(precioActual1));
		assertEquals(String.valueOf(100.0), String.valueOf(precioActual2));
		assertEquals(String.valueOf(150.0), String.valueOf(precioActual3));
		assertEquals("02/11/2022", p1.getMisPrecios().get(0).getFechaHastaConFormato());
		assertEquals("02/11/2022", p1.getMisPrecios().get(1).getFechaHastaConFormato());
		assertEquals("-", p1.getMisPrecios().get(2).getFechaHastaConFormato());
		
	}
	
	@Test
	@Order(5)
	@DisplayName("05 getPrecio(fechaExacta)")
	void test08(){
		
		//arrange
		Calendar fecha1 = Calendar.getInstance();
		
		ArrayList<Precio> salida1;
		ArrayList<Precio> salida2;
		Producto p1 = null;
		try {
			p1 = new Producto(1, "asrock A320m", TipoDeProducto.MOTHERBOARD);
			p1.setPrecio(6000.0);
			p1.setPrecio(6500.0);
			p1.setPrecio(7000.0);
		
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//actual
		salida1 = p1.getPrecioPorFecha(fecha1);
		fecha1.set(Calendar.MONTH, 9);
		salida2 = p1.getPrecioPorFecha(fecha1);
		
		//assert
		assertEquals(salida1.size(), 2);
		assertEquals(salida2.size(), 0);
	}

}
