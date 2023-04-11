package dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import utiles.Util;

public class GestorDeVentas {
	private static GestorDeVentas gestorDeVentas;
	private ArrayList<Producto> misProductos;
	private ArrayList<Venta> misVentas;
	public static int codigoDeProductos = 1;
	public static int codigoDeVentas = 1;
	
	
	private GestorDeVentas() {
		misProductos = new ArrayList<Producto>();
		misVentas = new ArrayList<Venta>();
	}
	
	public static GestorDeVentas getInstancia() {
		if(gestorDeVentas == null)
			gestorDeVentas = new GestorDeVentas();
		return gestorDeVentas;
	}
	
	public void Limpiar() {
		misProductos.clear();
		misVentas.clear();
	}
	
	public Producto getProducto (int codigoDeProducto){
		if(existeProductoCargado(codigoDeProducto))
		return misProductos.stream()
						   .filter(p->p.getCodigoProducto()==codigoDeProducto)
						   .findFirst()
						   .get();
		else
			return null;
	}
	
	public boolean addProducto(Producto nuevoProducto){
		if(!existeProductoCargado(nuevoProducto.getCodigoProducto())){
			codigoDeProductos= codigoDeProductos+1;
			return misProductos.add(nuevoProducto);
		}
		return false;
	}
	
	private boolean existeProductoCargado(int codigoDeProducto){
		return misProductos.stream()
						   .filter(p->p.getCodigoProducto()==codigoDeProducto)
						   .count() > 0;
	}
	
	public boolean deleteProducto(int codigoDeProducto){
		if(existeProductoCargado(codigoDeProducto)){
			Producto producto = this.getProducto(codigoDeProducto);
			return misProductos.remove(producto);
		}
		return false;
	}
	
	public boolean updateProducto(int codigoDeProducto, String nombre, Marca marca,TipoDeProducto tipo,String descripcion, int stock, double precio) throws ExceptionProducto, ExceptionPrecio{
		if(existeProductoCargado(codigoDeProducto)){
			Producto producto = this.getProducto(codigoDeProducto);
			producto.setNombre(nombre);
			producto.setMarca(marca);
			producto.setTipoProducto(tipo);
			producto.setDescripcion(descripcion);
			producto.setStock(stock);
			if(producto.getPrecioActual() != precio)
				producto.setPrecio(precio);
			return true;
		}else {
			return false;
		}
	}
	
	public int getCantidadDeProductos(){
		return misProductos.size();
	}
	
	public ArrayList<Producto> getProductos(){
		return misProductos;
	}
	
	
	
	public ArrayList<Producto> getProductos(String parteNombre){
		ArrayList<Producto> salida = misProductos
				.stream()
				.filter(p->p.getNombre().contains(Util.getStringConFormato(parteNombre)))
				.sorted((p1,p2)->p1.getNombre().compareTo(p2.getNombre()))
				.collect(Collectors.toCollection(ArrayList<Producto>::new));
		return salida;
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, Marca marca){
		ArrayList<Producto> salida = misProductos
				.stream()
				.filter(p->p.getNombre().contains(Util.getStringConFormato(parteNombre)) && p.getMarca().equals(marca))
				.sorted((o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual()))
				.collect(Collectors.toCollection(ArrayList<Producto>::new));
		return salida;
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, TipoDeProducto tipo){
		ArrayList<Producto> salida = misProductos
				.stream()
				.filter(p->p.getNombre().contains(Util.getStringConFormato(parteNombre)) && p.getTipoProducto().equals(tipo))
				.sorted((o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual()))
				.collect(Collectors.toCollection(ArrayList<Producto>::new));
		return salida;
	}
	
	public ArrayList<Producto> getProductos(TipoDeProducto tipoDeProducto){
		Predicate <Producto> p = pr->pr.getTipoProducto().equals(tipoDeProducto);
		Comparator <Producto> c = (o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual());
		return this.getProductos(p, c);
	}
	
	public ArrayList<Producto> getProductos(Marca marca){
		Predicate <Producto> p = pr->pr.getMarca().equals(marca);
		Comparator <Producto> c = (o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual());
		return this.getProductos(p, c);
	}
	
	public ArrayList<Producto> getProductos( Marca marca, TipoDeProducto tipoDeProducto){
		Predicate <Producto> p1 = p -> p.getMarca().equals(marca);
		Predicate <Producto> p2 = p->p.getTipoProducto().equals(tipoDeProducto);
		Comparator <Producto> c1 = (o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual());
		Predicate <Producto> p3 = p1.and(p2);
		
		return this.getProductos(p3, c1);
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, Marca marca, TipoDeProducto tipoDeProducto){
		Predicate <Producto> p1 = p-> p.getNombre().contains(Util.getStringConFormato(parteNombre));
		Predicate <Producto> p2 = p -> p.getMarca().equals(marca);
		Predicate <Producto> p3 = p->p.getTipoProducto().equals(tipoDeProducto);
		Comparator <Producto> c1 = (o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual());
		Predicate <Producto> p4 = p1.and(p2).and(p3);
		
		return this.getProductos(p4, c1);
	}
	
	public ArrayList<Producto> getProductos(Predicate<Producto> p, Comparator<Producto> c){
		ArrayList<Producto> salida = misProductos
				.stream()
				.filter(p)
				.sorted(c)
				.collect(Collectors.toCollection(ArrayList<Producto>::new));
		return salida;
	}
	
	public ArrayList<Producto> ordenarProductos(ArrayList<Producto> productos, Comparator<Producto> c){
		ArrayList<Producto> salida = productos.stream().sorted(c).collect(Collectors.toCollection(ArrayList<Producto>::new));
		return salida;
	}
	
	//VENTAS
	
	
	public boolean addVenta(Venta nuevaVenta) throws ExceptionProducto{
		if(!existeVentaCargado(nuevaVenta.getNumeroVenta())){
			codigoDeVentas++;
			for(Detalle d: nuevaVenta.getMisDetalles()) {
				Producto p = this.getProducto(d.getCodigoProducto());
				p.setStock(p.getStock() - d.getCantidad());
			}
			return misVentas.add(nuevaVenta);
		}
		return false;
	}
	
	public boolean deleteVenta(int codigoVenta) throws ExceptionProducto{
		if(existeVentaCargado(codigoVenta)){
			Venta venta= this.getVenta(codigoVenta);
			for(Detalle d: venta.getMisDetalles()) {
				Producto p = this.getProducto(d.getCodigoProducto());
				p.setStock(p.getStock() + d.getCantidad());
			}
			return misVentas.remove(venta);
		}
		return false;
	}
	
	private boolean existeVentaCargado(int numeroDeVenta){
		return misVentas.stream().filter(p->p.getNumeroVenta() == numeroDeVenta).count() > 0;
	}
	
	public double getRecaudacion(Calendar fechaExacta){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		double recaudacion = 0.0;
		recaudacion = getVentas(fechaExacta).stream()
											.mapToDouble(v->v.getMontoTotal())
											.sum();
		//.filter(v->v.getFechaVentaConFormato().equals(sdf.format(fechaExacta.getTime())))
		return recaudacion;
	}
	
	public double getRecaudacion(Calendar fechaDesde, Calendar fechaHasta){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		double recaudacion = 0.0;
		recaudacion = getVentas(fechaDesde, fechaHasta).stream()
													   .mapToDouble(v->v.getMontoTotal())
													   .sum();
		//.filter(v->v.getFechaVentaConFormato().equals(sdf.format(fechaExacta.getTime())))
		return recaudacion;
	}
	
	public int getCodigoDeVentas() {
		return this.codigoDeVentas;
	}
	
	public int getCodigoDeProductos() {
		return this.codigoDeProductos;
	}
	
	public int getCantidadDeVentas(){
		return misVentas.size();
	}
	
	public ArrayList<Venta> getVentas(){
		return misVentas;
	}
	
	public Venta getVenta (int codigoVenta){
		if(existeVentaCargado(codigoVenta))
		return misVentas.stream()
						   .filter(v->v.getNumeroVenta()==codigoVenta)
						   .findFirst()
						   .get();
		else
			return null;
	}
	
	public ArrayList<Venta> getVentas(Calendar fechaExacta){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return misVentas.stream()
						.filter(v->v.getFechaVentaConFormato().equals(sdf.format(fechaExacta.getTime())))
						.collect(Collectors.toCollection(ArrayList<Venta>::new));
	}
	
	public ArrayList<Venta> getVentas(Calendar fechaDesde, Calendar fechaHasta){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return misVentas.stream()
						.filter(v-> v.getFechaVentaConFormato().equals(sdf.format(fechaDesde.getTime())) || 
									v.getFechaVentaConFormato().equals(sdf.format(fechaHasta.getTime())) ||
									v.getFechaVenta().getTime().after(fechaDesde.getTime()) && 
									v.getFechaVenta().getTime().before(fechaHasta.getTime()))
						.collect(Collectors.toCollection(ArrayList<Venta>::new));
						
	}
	
	public ArrayList<Venta> getVentas(String cliente){
		return misVentas.stream()
						.filter(v->v.getCliente().contains(cliente))
						.collect(Collectors.toCollection(ArrayList<Venta>::new));
	}
	
	public ArrayList<Venta> getVentas(MedioDePago medioPago){
		return misVentas.stream()
						.filter(v->v.getMedioDePago().equals(medioPago))
						.sorted((o1, o2)-> (int) (o1.getFechaVenta().compareTo(o2.getFechaVenta())))
						.collect(Collectors.toCollection(ArrayList<Venta>::new));
	}
	
	
	
	
	public ArrayList<Venta> getVentas(String cliente, Calendar fechaHasta, MedioDePago medioPago){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Predicate <Venta> v1 = v->v.getCliente().contains(cliente);
		Predicate <Venta> v2 = v->v.getFechaVentaConFormato().equals(sdf.format(fechaHasta.getTime()));
		Predicate <Venta> v3 = v->v.getMedioDePago().equals(medioPago);
		Comparator <Venta> c2 = (o1, o2)-> (int) (o1.getFechaVenta().compareTo(o2.getFechaVenta()));
		Predicate <Venta> v4 = v1.and(v2).and(v3);
		return this.getVentas(v4, c2);
	}
	
	public ArrayList<Venta> getVentas(String cliente, MedioDePago medioPago){
		Predicate <Venta> v1 = v->v.getCliente().contains(cliente);
		Predicate <Venta> v2 = v->v.getMedioDePago().equals(medioPago);
		Comparator <Venta> c2 = (o1, o2)-> (int) (o1.getFechaVenta().compareTo(o2.getFechaVenta()));
		Predicate <Venta> v3 = v1.and(v2);
		return this.getVentas(v3, c2);
	}
	
	public ArrayList<Venta> getVentas(String cliente, Calendar fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Predicate <Venta> v1 = v->v.getCliente().contains(cliente);
		Predicate <Venta> v2 = v->v.getFechaVentaConFormato().equals(sdf.format(fecha.getTime()));
		Comparator <Venta> c2 = (o1, o2)-> (int) (o1.getFechaVenta().compareTo(o2.getFechaVenta()));
		Predicate <Venta> v3 = v1.and(v2);
		return this.getVentas(v3, c2);
	}
	
	public ArrayList<Venta> getVentas(MedioDePago medioPago,  Calendar fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Predicate <Venta> v1 = v->v.getMedioDePago().equals(medioPago);
		Predicate <Venta> v2 = v->v.getFechaVentaConFormato().equals(sdf.format(fecha.getTime()));
		Comparator <Venta> c2 = (o1, o2)-> (int) (o1.getFechaVenta().compareTo(o2.getFechaVenta()));
		Predicate <Venta> v3 = v1.and(v2);
		return this.getVentas(v3, c2);
	}
	
	public ArrayList<Venta> getVentas(Predicate<Venta> p, Comparator<Venta> c){
		ArrayList<Venta> salida = misVentas
				.stream()
				.filter(p)
				.sorted(c)
				.collect(Collectors.toCollection(ArrayList<Venta>::new));
		return salida;
	}
	
	

	
	
}
