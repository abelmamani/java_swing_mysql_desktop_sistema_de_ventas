package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dominio.Detalle;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionDetalleCantidadNegativo;
import exceptions.ExceptionDetalleCodigoProductoNegativo;
import exceptions.ExceptionDetallePrecioCero;
import exceptions.ExceptionDetallePrecioNegativo;
import exceptions.ExceptionPrecioCero;
import exceptions.ExceptionPrecioNegativo;
import exceptions.ExceptionProducto;
import exceptions.ExceptionProductoCodigoProductoNegativo;
import exceptions.ExceptionProductoDescripcionNulo;
import exceptions.ExceptionProductoDescripcionVacio;
import exceptions.ExceptionProductoMarcaNulo;
import exceptions.ExceptionProductoNombreNulo;
import exceptions.ExceptionProductoNombreVacio;
import exceptions.ExceptionProductoStockNegativo;
import exceptions.ExceptionProductoTipoDeProductoNulo;
import exceptions.ExceptionVentaClienteNulo;
import exceptions.ExceptionVentaClienteNumero;
import exceptions.ExceptionVentaClienteVacio;
import exceptions.ExceptionVentaFechaNulo;
import exceptions.ExceptionVentaMedioDePagoNulo;
import exceptions.ExceptionVentaNumeroNegativo;


class TestExcepciones {

	@Test
	@Order(1)
	@DisplayName("ExceptionProductoCodigoProductoNulo hereda de ExceptionProducto")
	void test01() {
		try {
			Producto p = new Producto(-1, "ram", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoCodigoProductoNegativo ex){
			assertEquals("El codigo no puder ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(2)
	@DisplayName("ExceptionProductoNombreNulo hereda de ExceptionProducto")
	void test02() {
		try {
			Producto p = new Producto(1, null, Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoNombreNulo ex){
			assertEquals("Ingrese un nombre", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(3)
	@DisplayName("ExceptionProductoNombreVacio hereda de ExceptionProducto")
	void test03() {
		try {
			Producto p = new Producto(1, "", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoNombreVacio ex){
			assertEquals("El nombre no puede estar vacio", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(4)
	@DisplayName("ExceptionProductoMarcaNulo hereda de ExceptionProducto")
	void test04() {
		try {
			Producto p = new Producto(1, "ram", null, TipoDeProducto.RAM, "descripcion de la ram", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoMarcaNulo ex){
			assertEquals("Seleccione una marca", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(5)
	@DisplayName("ExceptionProductoTipoDeProductoNulo hereda de ExceptionProducto")
	void test05() {
		try {
			Producto p = new Producto(1, "ram",Marca.ADATA, null, "descripcion de la ram", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoTipoDeProductoNulo ex){
			assertEquals("Seleccione un tipo de Producto", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	
	@Test
	@Order(6)
	@DisplayName("ExceptionProductoDescripcionNulo hereda de ExceptionProducto")
	void test06() {
		try {
			Producto p = new Producto(1, "ram", Marca.ADATA, TipoDeProducto.RAM, null, 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoDescripcionNulo ex){
			assertEquals("Ingrese una descripcion", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(7)
	@DisplayName("ExceptionProductoDescripcionVacio hereda de ExceptionProducto")
	void test07() {
		try {
			Producto p = new Producto(1, "ram", Marca.ADATA, TipoDeProducto.RAM, "", 12, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoDescripcionVacio ex){
			assertEquals("La descripcion no puede estar vacio", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(8)
	@DisplayName("ExceptionProductoStockNegativo hereda de ExceptionProducto")
	void test08() {
		try {
			Producto p = new Producto(1, "ram", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", -10, 12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionProductoStockNegativo ex){
			assertEquals("El stock no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(9)
	@DisplayName("ExceptionPrecioCero hereda de ExceptionPrecio")
	void test09() {
		try {
			Producto p = new Producto(1, "ram", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 10, 0.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionPrecioCero ex){
			assertEquals("EL precio no puede valer Cero", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(10)
	@DisplayName("ExceptionPrecioNegativo hereda de ExceptionPrecio")
	void test10() {
		try {
			Producto p = new Producto(1, "ram", Marca.ADATA, TipoDeProducto.RAM, "descripcion de la ram", 10, -12.0);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionPrecioNegativo ex){
			assertEquals("EL precio no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	
	@Test
	@Order(11)
	@DisplayName("ExceptionDetalleCodigoProductoNulo hereda de ExceptionPrecio")
	void test11() {
		try {
			Detalle d = new Detalle(-1, 12.0, 10);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionDetalleCodigoProductoNegativo ex){
			assertEquals("El codigo no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(12)
	@DisplayName("ExceptionDetallePrecioCero hereda de ExceptionPrecio")
	void test12() {
		try {
			Detalle d = new Detalle(1, 0.0, 10);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionDetallePrecioCero ex){
			assertEquals("EL precio no puede valer Cero", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	
	@Test
	@Order(13)
	@DisplayName("ExceptionDetallePrecioNegativo hereda de ExceptionPrecio")
	void test13() {
		try {
			Detalle d = new Detalle(1, -12.0, 10);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionDetallePrecioNegativo ex){
			assertEquals("EL precio no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	
	@Test
	@Order(14)
	@DisplayName("ExceptionDetalleCantidadNegativo hereda de ExceptionPrecio")
	void test14() {
		try {
			Detalle d = new Detalle(1, 12.0, -10);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionDetalleCantidadNegativo ex){
			assertEquals("La cantidad no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(15)
	@DisplayName("ExceptionVentaNumeroNegativo hereda de ExceptionVenta")
	void test15() {
		try {
			Venta v = new Venta(-1, "abel", Calendar.getInstance(), MedioDePago.MERCADOPAGO);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaNumeroNegativo ex){
			assertEquals("El numero de la venta no puede ser negativo", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(16)
	@DisplayName("ExceptionVentaClienteNulo hereda de ExceptionVenta")
	void test16() {
		try {
			Venta v = new Venta(1, null, Calendar.getInstance(), MedioDePago.MERCADOPAGO);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaClienteNulo ex){
			assertEquals("Ingrese un cliente", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(17)
	@DisplayName("ExceptionVentaClienteVacio hereda de ExceptionVenta")
	void test17() {
		try {
			Venta v = new Venta(1, "", Calendar.getInstance(), MedioDePago.MERCADOPAGO);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaClienteVacio ex){
			assertEquals("El cliente no puede estar vacio", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(18)
	@DisplayName("ExceptionVentaFechaNulo hereda de ExceptionVenta")
	void test18() {
		try {
			Venta v = new Venta(1, "abel", null, MedioDePago.MERCADOPAGO);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaFechaNulo ex){
			assertEquals("Ingrese una fecha valida", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}
	
	@Test
	@Order(19)
	@DisplayName("ExceptionVentaMedioDePagoNulo hereda de ExceptionVenta")
	void test19() {
		try {
			Venta v = new Venta(1, "abel", Calendar.getInstance(), null);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaMedioDePagoNulo ex){
			assertEquals("Seleccione un Medio de Pago", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}

	@Test
	@Order(20)
	@DisplayName("ExceptionVentaClienteNumero hereda de ExceptionVenta")
	void test20() {
		try {
			Venta v = new Venta(1, "45abe", Calendar.getInstance(), MedioDePago.PAYPAL);
			fail("esta linea no deberia correrse");
		} 
		catch (ExceptionVentaClienteNumero ex){
			assertEquals("El cliente no puede tener numeros", ex.getMessage());
		}
		catch (Exception ex) {
			fail("esta linea no deberia correrse");
		}
	}

}
