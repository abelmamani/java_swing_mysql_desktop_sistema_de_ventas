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
import dominio.Precio;
import dominio.Producto;
import dominio.TipoDeProducto;
import exceptions.ExceptionDetalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;

class TestPrecio {

	@Test
	@Order(1)
	@DisplayName("01 Constructor")
	void test01(){
		
		//arrange
		Precio p = null;
		
		//actual
		try {
			p = new Precio(100); 
		}catch(ExceptionPrecio ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//axxert
		assertNotNull(p);
	}
	
	@Test
	@Order(2)
	@DisplayName("02 Getters")
	void test02() throws ExceptionPrecio{
		
		//arrange
		Precio p = null;
		
		try {
			p = new Precio(100); 
		}catch(ExceptionPrecio ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
	
		//assert
		assertEquals(String.valueOf(p.getValor()), "100.0");
		assertEquals(p.getFechaHastaConFormato(), "-");
		
		
	}
	
	@Test
	@Order(3)
	@DisplayName("03 Setters")
	void test03(){
		
		//arrange
		Precio p = null;
		GregorianCalendar fecha = new GregorianCalendar();
		Date f = new Date(2000, 9, 11);
		
		try {
			p = new Precio(100); 
		}catch(ExceptionPrecio ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
	
		//actual
		try {
			p.setValor(50); 
		}catch(ExceptionPrecio ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
	
		fecha.set(GregorianCalendar.YEAR, 2000);
		fecha.set(GregorianCalendar.MONTH, 9);
		fecha.set(GregorianCalendar.DATE, 11);
		p.setFechaHasta(fecha);
		
		//assert
		assertEquals(String.valueOf(p.getValor()), "50.0");
		assertEquals(p.getFechaHasta(), fecha);
//		System.out.println(p.getFechaHastaConFormato());
		assertEquals(p.getFechaHastaConFormato(), "11/10/2000");
	
	}

		
	@Test
	@Order(4)
	@DisplayName("04 toString()")
	void test04(){
		
		//arrange
		Precio p1 = null; 
		Precio p2 = null;
		Calendar fecha = Calendar.getInstance();
		fecha.set(2000, 9, 11);
		String toString1;
		String toString2;
		
		try {
			p1 = new Precio(100.0); 
			p2 = new Precio(150);
			p2.setValor(250);
			p2.setFechaHasta(Calendar.getInstance());
		}catch(ExceptionPrecio ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//Actual
		toString1 = p1.toString();
		toString2 = p2.toString();
		
		//assert
		//System.out.println(p1.toString());
		assertEquals(toString1, "El Precio es $100.0, ultima fecha de modificacion: -");
		//System.out.println(p2.toString());
		assertEquals(toString2, "El Precio es $250.0, ultima fecha de modificacion: 02/11/2022");
		
		
	}
	

}
