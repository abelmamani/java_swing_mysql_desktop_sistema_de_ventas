package dominio;

import java.util.Objects;

import exceptions.ExceptionDetalle;
import exceptions.ExceptionDetalleCantidadNegativo;
import exceptions.ExceptionDetalleCodigoProductoNegativo;
import exceptions.ExceptionDetallePrecioCero;
import exceptions.ExceptionDetallePrecioNegativo;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionPrecioCero;
import exceptions.ExceptionPrecioNegativo;
import exceptions.ExceptionProducto;
import exceptions.ExceptionProductoCodigoProductoNegativo;
import exceptions.ExceptionProductoStockNegativo;

public class Detalle {
	private int codigoProducto;
	private double precio;
	private int cantidad;

	public Detalle(int codigoProducto, double precio, int cantidad) throws ExceptionDetalle {
        validarDetalle(codigoProducto, precio, cantidad);
    	}

    	private void validarDetalle(int codigoProducto, double precio, int cantidad) throws ExceptionDetalle {
        validarCodigoProducto(codigoProducto);
        validarPrecio(precio);
        validarCantidad(cantidad);

        asignarDetalle(codigoProducto, precio, cantidad);
    	}

	private void asignarDetalle(int codigoProducto, double precio, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.cantidad = cantidad;
    	}

	
	}
	private boolean validarCodigoProducto(int idProducto) throws ExceptionDetalle{
		if(idProducto < 0)
			throw new ExceptionDetalleCodigoProductoNegativo("El codigo no puede ser negativo");
		return true;
	}
	
	private boolean validarPrecio(double valor) throws ExceptionDetalle{
		if(valor == 0)
			throw new ExceptionDetallePrecioCero("EL precio no puede valer Cero");
		if(valor < 0)
			throw new ExceptionDetallePrecioNegativo("EL precio no puede ser negativo");
		return true;
	}
	
	private boolean validarCantidad(int cantidad) throws ExceptionDetalle{
		if(cantidad < 0)
			throw new ExceptionDetalleCantidadNegativo("La cantidad no puede ser negativo");
		return true;
	}
	
	public int getCodigoProducto() {
		return codigoProducto;
	}

	public double getPrecio() {
		return precio;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codigoProducto, precio);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Detalle other = (Detalle) obj;
		return codigoProducto == other.codigoProducto
				&& Double.doubleToLongBits(precio) == Double.doubleToLongBits(other.precio);
	}
	@Override
	public String toString() {
		return "Codigo: " + codigoProducto + ", Precio: " + precio + ", Cantidad: " + cantidad;
	}
	
	
	
	
	
	
}
