package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dominio.Detalle;
import dominio.GestorDeVentas;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionDetalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import exceptions.ExceptionVenta;

class TestVenta {

	@Test
	@Order(1)
	@DisplayName("01 Constructores")
	void test01(){
		
		//arrange
		Calendar fecha1 = GregorianCalendar.getInstance();
		Venta v1 = null;
		Venta v2 = null;
		
		//actual
		try {
			v1 = new Venta(1,"juan alvarez", fecha1, MedioDePago.DEBITO);
			v2 = new Venta(1, fecha1, MedioDePago.DEBITO);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}
		
		//assert
		assertNotNull(v2);
		assertNotNull(v1);
		
		
	
	}
	
	@Test
	@Order(2)
	@DisplayName("02 Getters")
	void test02(){
		
		//arrange
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int codigoVenta = 1;
		int codigoVentaActual;
		String cliente = "juan alvarez";
		String clienteActual;
		MedioDePago medioDePago = MedioDePago.DEBITO;
		MedioDePago medioDePagoActual;
		Calendar fecha1 = GregorianCalendar.getInstance();
		String fechaActual;
		String montoTotal;
		Venta v1 = null;
		
		try {
			v1 = new Venta(codigoVenta, cliente, fecha1, medioDePago);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}
		
		//actual
		codigoVentaActual = v1.getNumeroVenta(); 
		clienteActual = v1.getCliente();
		medioDePagoActual = v1.getMedioDePago();
		fechaActual = v1.getFechaVentaConFormato();
		montoTotal = String.valueOf(v1.getMontoTotal());
		
		//assert
		assertEquals(codigoVenta, codigoVentaActual);
		assertEquals(cliente, clienteActual);
		assertEquals(medioDePago, medioDePagoActual);
		assertEquals(fecha1, v1.getFechaVenta());
		assertEquals(sdf.format(fecha1.getTime()), fechaActual);
		assertEquals("0.0", montoTotal);
		assertEquals(0 , v1.getCantidadDeDetalles());
		
	}
	
	@Test
	@Order(3)
	@DisplayName("03 Setters") 
	void test03(){
		
		//arrange
		Calendar fecha1 = GregorianCalendar.getInstance();
		Calendar fecha2 = GregorianCalendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int codigoVenta = 1; 
		String cliente = "juan alvarez";
		MedioDePago medioDePago = MedioDePago.DEBITO;
		Venta v1 = null;
		
		try {
			v1 = new Venta(codigoVenta, cliente, fecha1, medioDePago);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}	
		
		//actual
		v1.setNumeroVenta(3);
		v1.setCliente("will martino");
		v1.setMedioDePago(MedioDePago.MERCADOPAGO);
		v1.setFechaVenta(fecha2);
		v1.setMontoTotal(100);
				
		//assert
		assertEquals(3, v1.getNumeroVenta());
		assertEquals("will martino", v1.getCliente());
		assertEquals(MedioDePago.MERCADOPAGO,v1.getMedioDePago());
		assertEquals(fecha2, v1.getFechaVenta());
		assertEquals(sdf.format(fecha2.getTime()), v1.getFechaVentaConFormato());
		assertEquals("100.0", String.valueOf(v1.getMontoTotal()));
		
	}

		
	@Test
	@Order(4)
	@DisplayName("04 addDetalle()")
	void test04(){
		
		//arrange
		Calendar fecha1 = GregorianCalendar.getInstance();
		int codigoVenta = 1; 
		String cliente = "juan alvarez";
		MedioDePago medioDePago = MedioDePago.DEBITO;
		Detalle d1 = null;
		Detalle d2 = null;
		Detalle d3 = null;
		Detalle d4 = null;
		Venta v1 = null;
		ArrayList<Detalle> detalles = new 	ArrayList<Detalle>();
		
		try {
			d1 = new Detalle(1, 12000.0, 2);
			d2 = new Detalle(2, 5000, 1);
			d3 = new Detalle(1, 9100, 3);
			d4 = new Detalle(3, 6000, 1);
			v1 = new Venta(codigoVenta, cliente, fecha1, medioDePago);
			
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}
		
		
		detalles.add(d1);
		detalles.add(d2);
		detalles.add(d4);
		
		//assert
		assertEquals(0, v1.getCantidadDeDetalles());
		
		assertTrue(v1.addDetalle(d1));
		assertEquals(1, v1.getCantidadDeDetalles());
		
		assertTrue(v1.addDetalle(d2));
		assertEquals(2, v1.getCantidadDeDetalles());
		
		assertFalse(v1.addDetalle(d3));
		assertEquals(2, v1.getCantidadDeDetalles());
		
		assertTrue(v1.addDetalle(d4));
		assertEquals(3, v1.getCantidadDeDetalles());
		
		assertEquals(detalles, v1.getMisDetalles());
			
		
		
		
		
		
		
		
		
	}
	
	
	@Test
	@Order(5)
	@DisplayName("05 removeDetalle()")
	void test05(){
		
		//arrange
		Calendar fecha1 = GregorianCalendar.getInstance();
		int codigoVenta = 1; 
		String cliente = "juan alvarez";
		MedioDePago medioDePago = MedioDePago.DEBITO;
		Detalle d1 = null;
		Detalle d2 = null;
		Detalle d3 = null;
		Venta v1 = null;
		
		try {
			d1 = new Detalle(1, 12000.0, 2);
			d2 = new Detalle(2, 5000, 1);
			d3 = new Detalle(3, 9100, 3);
			v1 = new Venta(codigoVenta, cliente, fecha1, medioDePago);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}
		
		//actual
		v1.addDetalle(d1);
		v1.addDetalle(d2);
		v1.addDetalle(d3);
	
		
		//assert
		assertEquals(3, v1.getCantidadDeDetalles());
		
		assertTrue(v1.removeDetalle(1));
		assertEquals(2, v1.getCantidadDeDetalles());
		
		assertTrue(v1.removeDetalle(3));
		assertEquals(1, v1.getCantidadDeDetalles());
		
		assertFalse(v1.removeDetalle(3));
		assertEquals(1, v1.getCantidadDeDetalles());
		
		assertTrue(v1.removeDetalle(2));
		assertEquals(0, v1.getCantidadDeDetalles());
		
	
	}
	
	@Test
	@Order(6)
	@DisplayName("06 calcularMontoTotal() y setMontoTotal()")
	void test06(){
		//arrange
		Calendar fecha1 = GregorianCalendar.getInstance();
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		int codigoVenta = 1; 
		String cliente = "juan alvarez";
		MedioDePago medioDePago = MedioDePago.DEBITO;
		Detalle d1 = null;
		Detalle d2 = null;
		Detalle d3 = null;
		Venta v1 = null;
		double montoTotal = 0.0;
		
		try {
			d1 = new Detalle(1, 12000.0, 2);
			d2 = new Detalle(2, 5000, 1);
			d3 = new Detalle(3, 9100, 3);
			v1 = new Venta(codigoVenta, cliente, fecha1, medioDePago);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");			
		}
		
		v1.addDetalle(d1);
		v1.addDetalle(d2);
		v1.addDetalle(d3);
		
		//actual
		montoTotal = v1.calcularMontoTotal();
		v1.setMontoTotal(montoTotal);
		
		//assert
		assertEquals(3, v1.getCantidadDeDetalles());
		assertEquals(String.valueOf(montoTotal), String.valueOf(montoTotal));
		assertEquals(String.valueOf(montoTotal), String.valueOf(v1.getMontoTotal()));
	
	}
	

}
