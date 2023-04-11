package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dominio.Detalle;
import exceptions.ExceptionDetalle;


class TestDetalle {

	@Test
	@Order(1)
	@DisplayName("01 Constructor")
	void test01(){
		
		//arrange
		Detalle d = null;
		
		//actual
		try {
			d = new Detalle(1, 50.0, 2);
		}catch(ExceptionDetalle ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertNotNull(d);
	}
	
	@Test
	@Order(2)
	@DisplayName("02 Getters")
	void test02(){
		
		//arrange
		int codigoEsperado = 1;
		String precioEsperado = "50.0";
		int cantidadEsperado = 2;
		int codigoActual;
		String precioActual;
		int cantidadActual;
		
		Detalle d = null;
		
		try {
			d = new Detalle(1, 50.0, 2);
		}catch(ExceptionDetalle ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//actual
		codigoActual = 	 d.getCodigoProducto();
		precioActual = String.valueOf(d.getPrecio());
		cantidadActual = d.getCantidad();
		
		//assert
		assertEquals(codigoEsperado, codigoActual);
		assertEquals(precioEsperado, precioActual);
		assertEquals(cantidadEsperado, cantidadActual);
	}
	
	@Test
	@Order(3)
	@DisplayName("03 Set cantidad")
	void test03(){
		
		//arrange
		int cantidadEsperado = 4;
		Detalle d = null;
		
		try {
			d = new Detalle(1, 50.0, 2);
		}catch(ExceptionDetalle ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//actual
		d.setCantidad(4);
		
		//assert
		assertEquals(cantidadEsperado, d.getCantidad());
	}

		
	@Test
	@Order(4)
	@DisplayName("04 toString()")
	void test04(){
		Detalle d1 = null;
		Detalle d2 = null;
		Detalle d3 = null;
		String toString1;
		String toString2;
		String toString3;
		
		try {
			d1 = new Detalle(1, 50.0, 2);
			d2 = new Detalle(2, 58.4, 1);
		    d3 = new Detalle(3, 100.0, 3);
		}catch(ExceptionDetalle ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//actual
		toString1 = d1.toString();
		toString2 = d2.toString();
		toString3 = d3.toString();
		
		//assert
		//System.out.println(d1.toString());
		assertEquals("Codigo: 1, Precio: 50.0, Cantidad: 2", toString1);
		assertEquals("Codigo: 2, Precio: 58.4, Cantidad: 1", toString2);
		assertEquals("Codigo: 3, Precio: 100.0, Cantidad: 3", toString3);
		
		
	}
	

}
