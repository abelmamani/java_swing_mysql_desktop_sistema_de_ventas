package dominio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.stream.Collectors;

import exceptions.ExceptionDetalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionVenta;
import exceptions.ExceptionVentaClienteNulo;
import exceptions.ExceptionVentaClienteNumero;
import exceptions.ExceptionVentaClienteVacio;
import exceptions.ExceptionVentaFechaNulo;
import exceptions.ExceptionVentaMedioDePagoNulo;
import exceptions.ExceptionVentaNumeroNegativo;


public class Venta {
	private int numeroVenta;
	private String cliente;
	private Calendar fechaVenta;
	private MedioDePago medioDePago;
	private double montoTotal;
	private ArrayList<Detalle> misDetalles;
	
	//constructores
	public Venta() {
		this.montoTotal = 0.0;
		this.misDetalles = new ArrayList<Detalle>();
	}
	public Venta(int numeroVenta, String cliente, Calendar fechaVenta, MedioDePago medioDePago) throws ExceptionVenta {
		if(validarNumeroDeVenta(numeroVenta))
			this.numeroVenta = numeroVenta;
		if(validarCliente(cliente))
			this.cliente = cliente;
		if(validarFechaDeVenta(fechaVenta))
			this.fechaVenta = fechaVenta;
		if(validarMedioDePago(medioDePago))
			this.medioDePago = medioDePago;
			
		this.montoTotal = 0.0;
		this.misDetalles = new ArrayList<Detalle>();
	}
	
	public Venta(int numeroVenta, Calendar fechaVenta, MedioDePago medioDePago) throws ExceptionVenta {
		if(validarNumeroDeVenta(numeroVenta))
			this.numeroVenta = numeroVenta;
		if(validarFechaDeVenta(fechaVenta))
			this.fechaVenta = fechaVenta;
		if(validarMedioDePago(medioDePago))
			this.medioDePago = medioDePago;
		this.montoTotal = 0.0;
		this.misDetalles = new ArrayList<Detalle>();
	}
	
	private boolean validarNumeroDeVenta(int numeroVenta) throws ExceptionVenta{
		if(numeroVenta < 0) {
			throw new ExceptionVentaNumeroNegativo("El numero de la venta no puede ser negativo");
		}
		return true;
	}
	
	private boolean validarCliente(String cliente) throws ExceptionVenta {
		if(cliente ==null)
			throw new ExceptionVentaClienteNulo("Ingrese un cliente");
		if(cliente.trim().length() == 0)
			throw new ExceptionVentaClienteVacio("El cliente no puede estar vacio");
		
		boolean isNumeric = false;
	    for (int i = 0; i < cliente.length(); i++) {
	    	if (Character.isDigit(cliente.charAt(i))) {
	    		isNumeric = true;
	    	}
	    }
	    if(isNumeric)
	    	throw new ExceptionVentaClienteNumero("El cliente no puede tener numeros");
		return true;
	}
	
	private boolean validarFechaDeVenta(Calendar fechaDeVenta) throws ExceptionVenta {
		if(fechaDeVenta ==null)
			throw new ExceptionVentaFechaNulo("Ingrese una fecha valida");
		return true;
	}
	
	private boolean validarMedioDePago(MedioDePago medioDePago) throws ExceptionVenta {
		if(medioDePago==null)
			throw new ExceptionVentaMedioDePagoNulo("Seleccione un Medio de Pago");
		return true;
	}
	//metodos de detalle
	
	public boolean addDetalle(Detalle nuevoDetalle) {
		if(!existeDetalleCargado(nuevoDetalle.getCodigoProducto())) 
			return misDetalles.add(nuevoDetalle);
		return false;
	}
	
	public boolean removeDetalle(int codProducto) {
		if(existeDetalleCargado(codProducto)){
			Detalle d = misDetalles.stream().filter(detalle->detalle.getCodigoProducto() == codProducto).findFirst().get();
			return misDetalles.remove(d);
		}
		return false;
	}
	
	private boolean existeDetalleCargado(int codigoProducto){
		return misDetalles
				.stream()
				.filter(detalle->detalle.getCodigoProducto() == codigoProducto).count() > 0 ;
	}

//	public boolean updateDetalle(int codigoProducto, int cantidad){
//		if(existeDetalleCargado(codigoProducto)){
//			Detalle miDetalle = misDetalles
//					.stream()
//					.filter(d -> d.getCodigoProducto() == codigoProducto)
//					.findFirst()
//					.get();
//			miDetalle.setCantidad(cantidad);
//			return true;
//		}
//		
//		return false;
//	}
	
	
	//getters and setters

	public int getNumeroVenta() {
		return numeroVenta;
	}

	public void setNumeroVenta(int numeroVenta) {
		this.numeroVenta = numeroVenta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Calendar getFechaVenta() {
		return fechaVenta;
	}
	public String getFechaVentaConFormato() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(fechaVenta.getTime());
	}
	public String getFechaVentaConFormato2() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(fechaVenta.getTime());
	}

	public void setFechaVenta(Calendar fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public MedioDePago getMedioDePago() {
		return medioDePago;
	}

	public void setMedioDePago(MedioDePago medioDePago) {
		this.medioDePago = medioDePago;
	}

	public double getMontoTotal() {
		return montoTotal;
	}
	
	public double calcularMontoTotal() {
		return this.getMisDetalles()
				   .stream()
				   .mapToDouble(d-> d.getPrecio() * d.getCantidad())
				   .sum();
//				   .map(v->v.toString())
//				   .collect(Collectors.toCollection(ArrayList<String>::new));
				
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

	public ArrayList<Detalle> getMisDetalles() {
		return misDetalles;
	}
	
	public void setMisDetalles(ArrayList<Detalle> detalles) {
		this.misDetalles = detalles;
	}
	
	public int getCantidadDeDetalles() {
		return misDetalles.size();
	}

	//hashcode and equals
	@Override
	public int hashCode() {
		return Objects.hash(numeroVenta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venta other = (Venta) obj;
		return numeroVenta == other.numeroVenta;
	}

	
	//toSting()
	@Override
	public String toString() {
		return "Venta [numeroVenta=" + numeroVenta + ", cliente=" + cliente + ", fechaVenta=" + fechaVenta
				+ ", medioDePago=" + medioDePago + ", montoTotal=" + montoTotal + ", misDetalles=" + misDetalles + "]";
	}
	
	

	
	
	
	
	
	
}
