package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import dominio.Detalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import exceptions.ExceptionVenta;
import utiles.Util;



class TestGestorDeVentas {

	@Test
	@Order(1)
	@DisplayName("01 Gestor de Ventas Singleton")
	void test01() {
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		GestorDeVentas gv2 = GestorDeVentas.getInstancia();
		
		assertNotNull(gv);
		assertNotNull(gv2);
		assertTrue(gv==gv2);
		assertTrue(gv.equals(gv2));
	}
	
	@Test
	@Order(2)
	@DisplayName("02 AddProducto 1")
	void test02(){
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 15, 3500);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//Assert
		assertTrue(gv.addProducto(p1));
		assertEquals(1, gv.getCantidadDeProductos());
	}
	
	@Test
	@Order(3)
	@DisplayName("03 AddProducto 2")
	void test03() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(1, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 5, 6000);
			p4 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertEquals(0, gv.getCantidadDeProductos());
		
		assertTrue(gv.addProducto(p1));
		assertEquals(1, gv.getCantidadDeProductos());
		
		assertTrue(gv.addProducto(p2));
		assertEquals(2, gv.getCantidadDeProductos());
		
		assertFalse(gv.addProducto(p3));
		assertEquals(2, gv.getCantidadDeProductos());
		
		assertTrue(gv.addProducto(p4));
		assertEquals(3, gv.getCantidadDeProductos());
		
	}
	
	@Test
	@Order(4)
	@DisplayName("04 UpdateProducto")
	void test04() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		boolean esperado = false;
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		
		//Actual 
		try{
			esperado =  gv.updateProducto(2, "fuente redragon", Marca.REDRAGON, TipoDeProducto.FUENTE, "nueva descripcion", 10, 12000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		//assert
		assertEquals(3, gv.getCantidadDeProductos());
		assertTrue(esperado);
		assertEquals("Fuente redragon", p2.getNombre());
		assertEquals(Marca.REDRAGON, p2.getMarca());
		assertEquals(TipoDeProducto.FUENTE, p2.getTipoProducto());
		assertEquals("nueva descripcion", p2.getDescripcion());
		assertEquals(10, p2.getStock());
		assertEquals("12000.0", p2.getPrecioActual()+"");
	}
	
	@Test
	@Order(5)
	@DisplayName("05 DeleteProducto(codigo) simple")
	void test05() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		
		//Assert
		assertEquals(2, gv.getCantidadDeProductos());
		assertTrue(gv.deleteProducto(1));
		assertEquals(1, gv.getCantidadDeProductos());
		assertTrue(gv.deleteProducto(2));
		assertEquals(0, gv.getCantidadDeProductos());
	}
	
	@Test
	@Order(6)
	@DisplayName("06 DeleteProducto(codigo) medio")
	void test06() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 5, 6000);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		
		
		ArrayList<Producto> productos = new ArrayList<Producto>();
		productos.add(p2);
		productos.add(p4);
		
		//Assert
		assertEquals(4, gv.getCantidadDeProductos());
		
		assertTrue(gv.deleteProducto(1));
		assertEquals(3, gv.getCantidadDeProductos());
		
		assertFalse(gv.deleteProducto(11));
		assertEquals(3, gv.getCantidadDeProductos());
		
		assertTrue(gv.deleteProducto(3));
		assertEquals(2, gv.getCantidadDeProductos());
		
		assertFalse(gv.deleteProducto(3));
		assertEquals(2, gv.getCantidadDeProductos());
		
		assertEquals(productos, gv.getProductos());
	}
	
	//Busca por parteNombre y Ordena por nombre alfabeticamente.
	@Test
	@Order(7)
	@DisplayName("07 Busqueda y Ordenacion 1, getProductos(parteNombre)")
	void test07() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 5, 6000);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p3);
		esperado.add(p2);
	
		//Assert
		assertEquals(esperado , gv.getProductos("Fuent"));
		assertEquals(esperado.size(),2);
	}
	
	
	//1) busque con pareteNombre de un pruducto que contenga y los ordene por precio
	@Test
	@Order(8)
	@DisplayName("08 Busqueda y Ordenacion 2, getProductos(parteNombre)")
	void test08(){
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		String parteNombre = "fuente";
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		Comparator<Producto> OrdenarPorPrecio = (Producto o1, Producto o2)-> (int)(o1.getPrecioActual() - o2.getPrecioActual());
		//Predicate<Producto> BuscarPorParteNombre = (Producto o1) -> o1.getNombre().contains(parteNombre);
		Predicate <Producto>BuscarPorParteNombre = p-> p.getNombre().contains(Util.getStringConFormato(parteNombre));
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		esperado.add(p2);
		esperado.add(p6);
		
		//Assert
		assertEquals(esperado , gv.getProductos(BuscarPorParteNombre, OrdenarPorPrecio));
	}
	
	//2) busqueda por marca de producto  y los ordena por precio
	@Test
	@Order(9)
	@DisplayName("09 Busqueda y Ordenacion , getProductos(Marca)")
	void test09(){
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		
		//Assert
		assertEquals(esperado, gv.getProductos(Marca.GAMEMAX));
	}
	
	//3) busqueda por tipoPrducto de producto  y los ordena por precio
	@Test
	@Order(10)
	@DisplayName("10 Busqueda y Ordenacion , getProductos(TipoDeProducto)")
	void test10() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		esperado.add(p2);
		esperado.add(p6);
		
		//Assert
		assertEquals(esperado, gv.getProductos(TipoDeProducto.FUENTE));
	}
	
	
	//4) busqueda por parteNombre, marca y los ordena por precio
	@Test
	@Order(11)
	@DisplayName("11 Busqueda y Ordenacion , getProductos(parteNombre, marca)")
	void test11() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		
		//Assert
		assertEquals(esperado, gv.getProductos("fuente", Marca.GAMEMAX));
	}
	
	//5) busqueda por parteNombre, tipo de pruducto y los ordena por precio
	@Test
	@Order(12)
	@DisplayName("12 Busqueda y Ordenacion , getProductos(parteNombre, TipoDeProducto)")
	void test12() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p2);
		esperado.add(p6);
		
		//Assert
		assertEquals(esperado, gv.getProductos("fuente 600w", TipoDeProducto.FUENTE));
	}
	
	//6) busqueda por marca , tipo de pruducto y los ordena por precio
	@Test
	@Order(13)
	@DisplayName("13 Busqueda y Ordenacion , getProductos(Marca, TipoDeProducto)")
	void test13() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		
		//Assert
		assertEquals(esperado, gv.getProductos(Marca.GAMEMAX, TipoDeProducto.FUENTE));
	}
	
	//7) busqueda por parteNombre, marca , tipo de pruducto y los ordena por precio
	@Test
	@Order(14)
	@DisplayName("14 Busqueda y Ordenacion , getProductos(parteNombre, Marca, TipoDeProducto)")
	void test14() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Producto p4  = null;
		Producto p5  = null;
		Producto p6  = null;
		
		try{
			p1 = new Producto(1, "disco 180 GB ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon" , 15, 3500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 16, 6000);
			p3 = new Producto(3, "fuente 500w",Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcon", 3, 5400);
			p4 = new Producto(4, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 10, 65000);
			p5 = new Producto(5, "fuente 600w 80 plus bronce", Marca.GAMEMAX, TipoDeProducto.FUENTE, "descripcion", 5, 4700);
			p6 = new Producto(6, "fuente 600w 80 plus bronce", Marca.GYGABYTE, TipoDeProducto.FUENTE, "descripcion", 4, 7000);
		}catch(ExceptionProducto ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		gv.addProducto(p1);
		gv.addProducto(p2);
		gv.addProducto(p3);
		gv.addProducto(p4);
		gv.addProducto(p5);
		gv.addProducto(p6);
		
		ArrayList<Producto> esperado = new ArrayList<Producto>();
		esperado.add(p5);
		esperado.add(p3);
		
		//Assert
		assertEquals(esperado, gv.getProductos("fuente", Marca.GAMEMAX, TipoDeProducto.FUENTE));
	}
	
	@Test
	@Order(15)
	@DisplayName("15 addVenta(Venta)")
	void test15() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Venta v = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		boolean actual = false;
	
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
		
			v = new Venta(1, Calendar.getInstance(), MedioDePago.CHEQUE);
			v.addDetalle(new Detalle(1, 45000.0, 1));
		
			//Actual
			actual = gv.addVenta(v);
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
		
		//assert
		assertTrue(actual);
		assertEquals(1, gv.getCantidadDeVentas());
		assertEquals(5, p1.getStock());
		
		
	}
	

	//Anuelar una venta
	@Test
	@Order(16)
	@DisplayName("16 deleteVenta(codigoVenta) ")
	void test16() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
				
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
			
			v1 = new Venta(1, Calendar.getInstance(), MedioDePago.CHEQUE);
			v2 = new Venta(2, Calendar.getInstance(), MedioDePago.CHEQUE);
			v3 = new Venta(3, Calendar.getInstance(), MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
			
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
			
			//Assert
			assertEquals(3, gv.getCantidadDeVentas());
			assertTrue(gv.deleteVenta(1));
			assertEquals(6, p1.getStock());
			assertEquals(2, gv.getCantidadDeVentas());
			
			assertFalse(gv.deleteVenta(14));
			assertEquals(2, gv.getCantidadDeVentas());
			
			assertTrue(gv.deleteVenta(2));
			assertEquals(5, p2.getStock());
			assertEquals(1, gv.getCantidadDeVentas());
			
			assertTrue(gv.deleteVenta(3));
			assertEquals(15, p3.getStock());
			assertEquals(0, gv.getCantidadDeVentas());
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
			
	}
	
	//8) busqueda por parteClliente
	@Test
	@Order(17)
	@DisplayName("17 Busqueda y Ordenacion , getVentas(parteCliente)")
	void test17() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
				
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
					
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", Calendar.getInstance(), MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", Calendar.getInstance(), MedioDePago.CHEQUE);
			v3 = new Venta(3, "daniel", Calendar.getInstance(), MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
				
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
			
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v2);
		
		//Assert
		assertEquals(esperado, gv.getVentas("j"));
	}
	
	//9) busqueda por medioDePago y ordena por fecha de venta
	@Test
	@Order(18)
	@DisplayName("18 Busqueda y Ordenacion , getVentas(MedioDePago)")
	void test18() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
							
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
			
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
						
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
					
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v3);
					
		//Assert
		assertEquals(esperado, gv.getVentas(MedioDePago.CHEQUE));
	}
	
	//10) busqueda por fechaExacta
	@Test
	@Order(19)
	@DisplayName("19 Busqueda y Ordenacion , getVentas(Calendar)")
	void test19() {
		
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fecha.set(2022, 5, 1);
		
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
							
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
			
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
						
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
					
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v2);
					
		//Assert
		assertEquals(esperado, gv.getVentas(fecha));
	}
	
	//11) busqueda por parteCliente, fechaExacta y ordena por fecha de venta
	@Test
	@Order(20)
	@DisplayName("20 Busqueda y Ordenacion , getVentas(parteCliente, Calendar)")
	void test20() {
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fecha.set(2022, 5, 1);
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
								
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
							
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
						
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v2);
						
		//Assert
		assertEquals(esperado, gv.getVentas("a", fecha));
	}
	
	//12) busqueda por parteCliente, MedioPago y ordena por fecha de venta
	@Test
	@Order(21)
	@DisplayName("21 Busqueda y Ordenacion , getVentas(parteCliente, MedioPago)")
	void test21() {
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fecha.set(2022, 5, 1);
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
								
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
							
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
						
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v3);
						
		//Assert
		assertEquals(esperado, gv.getVentas("an", MedioDePago.CHEQUE));
	}

	//13) busqueda por fechaExacta, MedioPago y ordena por fecha de venta
	@Test
	@Order(22)
	@DisplayName("22 Busqueda y Ordenacion , getVentas(Calendar, MedioPago)")
	void test22() {
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Venta v4 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fecha.set(2022, 5, 1);
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
								
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v4 = new Venta(4, "marcos", f1, MedioDePago.CHEQUE);
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
							
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
			gv.addVenta(v4);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
						
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v4);
						
		//Assert
		assertEquals(esperado, gv.getVentas(MedioDePago.CHEQUE, fecha));
	}

	//14) busqueda por parteCliente, fechaExacta, MedioPago y ordena por fecha de venta
	@Test
	@Order(23)
	@DisplayName("23 Busqueda y Ordenacion , getVentas(parteCliente, Calendar, MedioPago)")
	void test23() {
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
		Venta v4 = null;
		Venta v5 = null;
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fecha = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fecha.set(2022, 5, 1);
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
								
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
			v4 = new Venta(4, "marcos", f1, MedioDePago.CHEQUE);
			v5 = new Venta(5, "juan", f1, MedioDePago.CHEQUE);
			
			v1.addDetalle(new Detalle(1, 45000.0, 1));
			v2.addDetalle(new Detalle(2, 6000, 1));
			v3.addDetalle(new Detalle(3, 60000.0, 1));
							
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
			gv.addVenta(v4);
			gv.addVenta(v5);
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
						
		ArrayList<Venta> esperado = new ArrayList<Venta>();
		esperado.add(v1);
		esperado.add(v5);
						
		//Assert
		assertEquals(esperado, gv.getVentas("juan",  fecha, MedioDePago.CHEQUE));
	}
	
	
	@Test
	@Order(24)
	@DisplayName("24 getRecaudacion(fechaDesde, fechaHasta)")
	void test24() {
		//Arrange
		GestorDeVentas gv = GestorDeVentas.getInstancia();
		gv.Limpiar();
		
		Venta v1 = null;
		Venta v2 = null;
		Venta v3 = null;
	
		Producto p1  = null;
		Producto p2  = null;
		Producto p3  = null;
		Calendar f1 = Calendar.getInstance();
		Calendar f2 = Calendar.getInstance();
		Calendar f3 = Calendar.getInstance();
		Calendar fechaHasta = Calendar.getInstance();
		Calendar fechaDesde1 = Calendar.getInstance();
		Calendar fechaDesde2 = Calendar.getInstance();
		f1.set(2022, 5, 1);
		f2.set(2022, 5, 1);
		f3.set(2022, 5, 5);
		fechaHasta.set(2022, 10, 1);
		fechaDesde1.set(2022, 5, 1);
		fechaDesde2.set(2022, 5, 5);
			
		try{
			p1 = new Producto(1, "disco ssd", Marca.ADATA, TipoDeProducto.ALMACENAMIENTO, "descripcon", 6, 4500);
			p2 = new Producto(2, "fuente 600w 80 plus bronce", Marca.DEPCOOL, TipoDeProducto.FUENTE, "descripcion", 5, 6000);
			p3 = new Producto(3, "6060 super", Marca.NVIDIA, TipoDeProducto.GPU, "descripcon", 15, 60000);
								
			gv.addProducto(p1);
			gv.addProducto(p2);
			gv.addProducto(p3);
				
			v1 = new Venta(1, "juan", f1, MedioDePago.CHEQUE);
			v2 = new Venta(2, "javier", f2, MedioDePago.PAYPAL);
			v3 = new Venta(3, "daniel", f3, MedioDePago.CHEQUE);
		
			
			v1.addDetalle(new Detalle(1, 4500.0, 1));
			v1.setMontoTotal(4500);
			
			v2.addDetalle(new Detalle(2, 6000, 1));
			v2.setMontoTotal(6000);
			
			v3.addDetalle(new Detalle(3, 60000.0, 1));
			v3.setMontoTotal(60000);
			
			gv.addVenta(v1);
			gv.addVenta(v2);
			gv.addVenta(v3);
		
		}catch(ExceptionVenta ex) {
			fail("esta linea no deberia correrse");
		}catch(Exception ex) {
			fail("esta linea no deberia correrse");
		}
					
		
		//Assert
		assertEquals("10500.0",String.valueOf(gv.getRecaudacion(fechaDesde1, fechaDesde1)));
		assertEquals("60000.0",String.valueOf(gv.getRecaudacion(fechaDesde2, fechaHasta)));
		assertEquals("70500.0",String.valueOf(gv.getRecaudacion(fechaDesde1, fechaHasta)));
		assertEquals("0.0",String.valueOf(gv.getRecaudacion(fechaHasta, fechaHasta)));
	}

}
