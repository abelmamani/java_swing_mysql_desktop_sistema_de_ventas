	package dominio;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import exceptions.ExceptionPrecio;
import exceptions.ExceptionPrecioCero;
import exceptions.ExceptionPrecioNegativo;

public class Precio {
	private double valor;
	private Calendar fechaHasta;
	
	public Precio(double valor, Calendar fecha){
		this.valor = valor;
		this.fechaHasta = fecha;
	}
	
	public Precio(double valor) throws ExceptionPrecio{
		if(validarPrecio(valor))
			this.valor = valor;
		this.fechaHasta = null;
	}


	private boolean validarPrecio(double valor) throws ExceptionPrecio {
		if(valor == 0)
			throw new ExceptionPrecioCero("EL precio no puede valer Cero");
		if(valor < 0)
			throw new ExceptionPrecioNegativo("EL precio no puede ser negativo");
		return true;
	}


	public double getValor() {
		return valor;
	}

	public void setValor(double valor) throws ExceptionPrecio{
		if(validarPrecio(valor))
			this.valor = valor;
	}

	public void setFechaHasta(Calendar fechaActual){
		this.fechaHasta = GregorianCalendar.getInstance();
		this.fechaHasta.setTime(fechaActual.getTime());
	}
	
	public Calendar getFechaHasta() {
		return fechaHasta;
	}
	
	public String getFechaHastaConFormato(){
		if(this.fechaHasta == null)
			return "-";
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		 return sdf.format(this.fechaHasta.getTime());
	}
	
	public String getFechaHastaConFormato2(){
		if(this.fechaHasta == null)
			return "-";
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		 return sdf.format(this.fechaHasta.getTime());
	}

	@Override
	public String toString() {
		return "El Precio es $" + valor + ", ultima fecha de modificacion: " + getFechaHastaConFormato();
	
	}
	
}
