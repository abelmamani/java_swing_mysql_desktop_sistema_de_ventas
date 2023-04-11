package dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.stream.Collectors;

import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;

import exceptions.ExceptionProductoDescripcionNulo;
import exceptions.ExceptionProductoDescripcionVacio;
import exceptions.ExceptionProductoCodigoProductoNegativo;
import exceptions.ExceptionProductoMarcaNulo;

import exceptions.ExceptionProductoNombreNulo;
import exceptions.ExceptionProductoNombreVacio;
import exceptions.ExceptionProductoStockNegativo;
import exceptions.ExceptionProductoTipoDeProductoNulo;
import exceptions.ExceptionStock;

import utiles.Util;

public class Producto {
	private int codigoProducto;
	private String nombre;
	private Marca marca;
	private TipoDeProducto tipoProducto;
	private String descripcion;
	private ArrayList<Precio> misPrecios;
	private int stock;
	public Producto(int codigoProducto, String nombre, Marca marca, TipoDeProducto tipoProducto, String descripcion, int stock) {
		this.codigoProducto = codigoProducto;
		this.nombre = nombre;
		this.marca = marca;
		this.tipoProducto = tipoProducto;
		this.descripcion = descripcion;
		this.stock = stock;
		this.misPrecios = new ArrayList<Precio>();
	}
	public Producto(int codigoProducto, String nombre, Marca marca, TipoDeProducto tipoProducto, String descripcion, int stock, double precio) throws ExceptionProducto, ExceptionPrecio{
		
		if(validarCodigoProducto(codigoProducto))
			this.codigoProducto = codigoProducto;
		if(validarNombre(nombre))
			this.nombre = Util.getStringConFormato(nombre);
		if(validarMarca(marca))
			this.marca = marca;
		if(validarTipoDeProducto(tipoProducto))
			this.tipoProducto = tipoProducto;
		if(validarDescripcion(descripcion))
			this.descripcion = descripcion;
		if(validarStock(stock))
			this.stock = stock;
		this.misPrecios = new ArrayList<Precio>();
		this.setPrecio(precio);

	}
	
	public Producto(int codigoProducto, String nombre, Marca marca, TipoDeProducto tipoProducto) throws ExceptionProducto{
		if(validarCodigoProducto(codigoProducto))
			this.codigoProducto = codigoProducto;
		if(validarNombre(nombre))
			this.nombre = Util.getStringConFormato(nombre);
		if(validarMarca(marca))
			this.marca = marca;
		if(validarTipoDeProducto(tipoProducto))
			this.tipoProducto = tipoProducto;
		
		this.misPrecios = new ArrayList<Precio>();
		
	}
	
	public Producto(int codigoProducto, String nombre, TipoDeProducto tipoProducto) throws ExceptionProducto{
		if(validarCodigoProducto(codigoProducto))
			this.codigoProducto = codigoProducto;
		if(validarNombre(nombre))
			this.nombre = Util.getStringConFormato(nombre);
		if(validarTipoDeProducto(tipoProducto))
			this.tipoProducto = tipoProducto;
		
		this.misPrecios = new ArrayList<Precio>();
		
	}
	
	//metodos validar que arrojan excepciones
	
	private boolean validarCodigoProducto(int idProducto) throws ExceptionProducto{
		if(idProducto < 0)
			throw new ExceptionProductoCodigoProductoNegativo("El codigo no puder ser negativo");
		return true;
	}
	
	private boolean validarNombre(String nombre) throws ExceptionProducto{
		if(nombre == null)
			throw new ExceptionProductoNombreNulo("Ingrese un nombre");
		if(nombre.trim().length() == 0)
			throw new ExceptionProductoNombreVacio("El nombre no puede estar vacio");
		return true;
	}
	
	private boolean validarMarca(Marca marca) throws ExceptionProducto{
		if(marca == null)
			throw new ExceptionProductoMarcaNulo("Seleccione una marca");
		return true;
	}
	
	private boolean validarTipoDeProducto(TipoDeProducto tipoDeProducto) throws ExceptionProducto{
		if(tipoDeProducto == null)
			throw new ExceptionProductoTipoDeProductoNulo("Seleccione un tipo de Producto");
		return true;
	}
	
	private boolean validarDescripcion(String descripcion) throws ExceptionProducto{
		if(descripcion == null)
			throw new ExceptionProductoDescripcionNulo("Ingrese una descripcion");
		if(descripcion.trim().length() == 0)
			throw new ExceptionProductoDescripcionVacio("La descripcion no puede estar vacio");
		return true;
	}
	
	private boolean validarStock(int stock) throws ExceptionProducto{
		if(stock < 0)
			throw new ExceptionProductoStockNegativo("El stock no puede ser negativo");
		return true;
	}

	//metodos de precio
	public void setPrecio(double valor) throws ExceptionPrecio{
		//crear nuevo precio
		Precio nuevoPrecio= new Precio(valor);
		//validar si el arraylist esta vacio y agregar primer precio
		//si no esta vacio, traer el precioMasReciente y actualizarlo, add nuevo Precio
		
		if(this.misPrecios.size() == 0)
			this.misPrecios.add(nuevoPrecio);
		else{
			Precio precioMasReciente = this.misPrecios
											.stream()
											.filter(precio->precio.getFechaHasta() == null)
											.findFirst()
											.get();
			Calendar fechaActual = GregorianCalendar.getInstance();
		    precioMasReciente.setFechaHasta(fechaActual);
		    this.misPrecios.add(nuevoPrecio);
		}
	}
	
	public double getPrecioActual(){
		if(misPrecios.size() == 0)
			return 0.0;
		//utilizar stream para buscar el precio mas reciente(es el precio con fecha nulo)
		Precio precioMasReciente = this.misPrecios
				.stream()
				.filter(p -> p.getFechaHasta() == null)
				.findFirst()
				.get();
		return precioMasReciente.getValor();
	}
	
	public ArrayList<Precio> getPrecioPorFecha(Calendar fechaExacta){
		ArrayList<Precio> salida = new ArrayList<Precio>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean existeFecha = misPrecios.stream().
				filter(p->p.getFechaHastaConFormato().equals(sdf.format(fechaExacta.getTime()))).
				findFirst().isPresent();
		if(existeFecha){
			salida =  misPrecios.stream()
					.filter(pr1->pr1.getFechaHastaConFormato().equals(sdf.format(fechaExacta.getTime())))
					.collect(Collectors.toCollection(ArrayList<Precio>::new));
		}
			
		return salida;
	}
	
	//metodos de stock
	public void setStock(int valor) throws ExceptionProducto{
		if(validarStock(valor))
			this.stock = valor;
	}
	
	
	public void descontarStock(int valor){
		this.stock = this.stock - valor;
	}
	
	
	public int getStock(){
		return this.stock;
	}
	
	//GETTERS AND SETTERS
	public int getCodigoProducto() {
		return this.codigoProducto;
	}
	public void setIdProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) throws ExceptionProducto {
		if(validarNombre(nombre))
			this.nombre = Util.getStringConFormato(nombre);
	}
	public Marca getMarca() {
		
		return marca;
	}
	public void setMarca(Marca marca) throws ExceptionProducto {
		if(validarMarca(marca))
			this.marca = marca;
	}
	public TipoDeProducto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(TipoDeProducto tipoProducto) throws ExceptionProducto {
		if(validarTipoDeProducto(tipoProducto))
			this.tipoProducto = tipoProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) throws ExceptionProducto {
		if(validarDescripcion(descripcion))
			this.descripcion = descripcion;
	}
	public ArrayList<Precio> getMisPrecios() {
		return misPrecios;
	}
	
	public void setMisPrecios(ArrayList<Precio> precios) {
		this.misPrecios = precios;
	}

	

	
	//hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(codigoProducto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		return codigoProducto == other.codigoProducto;
	}
	
	
	//to string
	
	@Override
	public String toString() {
		return "Producto [codigoProducto=" + codigoProducto + ", nombre=" + nombre + ", marca=" + marca
				+ ", tipoProducto=" + tipoProducto + ", descripcion=" + descripcion + ", misPrecios=" + misPrecios
				+ ", miStock=" + stock + "]";
	}
	
	
	

	
	
	
	
	
	
	
}
