package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import dominio.Detalle;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Precio;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionDetalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import exceptions.ExceptionVenta;

public class Venta_model {
	private Conexion conexion = Conexion.getInstancia();
	public Venta_model() {
		
	}
	
	
	public double getRecaudacion(Calendar fechaDesde, Calendar fechaHasta){
		double salida = 0.0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query = "SELECT SUM(montoTotal) as 'recaudacion' FROM venta WHERE estado = 1 AND DATE(fechaVenta) BETWEEN '"+sdf.format(fechaDesde.getTime())+"' AND '"+sdf.format(fechaHasta.getTime())+"'";
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			if(consulta.next()) {
				salida = consulta.getDouble("recaudacion");
			}
			conexion.cerrarConexion();
			
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		}
		
	}
	
	public boolean registrarDetalle(int codVenta, Detalle d) {
		boolean salida = true;
		Producto_model pm = new Producto_model();
		Producto p = pm.getProducto(d.getCodigoProducto());
		if(p == null) {
			return false;
		}
		
		if(p.getStock() < d.getCantidad()) {
			return false;
		}
		String query = "INSERT INTO detalle (idVenta, idProducto, precio, cantidad) VALUES ('"+codVenta+"','"+d.getCodigoProducto()+"', '"+d.getPrecio()+"', '"+d.getCantidad()+"')"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement insertar = conectar.prepareStatement(query);
			if(insertar.execute(query)) {
				salida = false;
	    	}
			conexion.cerrarConexion();
			salida =  actualizarStock(d.getCodigoProducto(), p.getStock() - d.getCantidad());
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return false;
		}
	}
	

	public boolean registrarVenta(Venta venta){   
    	boolean salida = true;
		String cliente = venta.getCliente();
    	//String fecha = venta.getFechaVentaConFormato2();
    	MedioDePago medioPago = venta.getMedioDePago();
    	double montoTotal = venta.getMontoTotal();
	
		String query = "INSERT INTO venta (cliente, fechaVenta, medioPago, montoTotal) VALUES ('"+cliente+"', SYSDATE(), '"+medioPago.name()+"', '"+montoTotal+"')"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement insertar = conectar.prepareStatement(query);
			if(insertar.execute(query)) {
				salida = false;
				conexion.cerrarConexion();
	    	}else {
	    		conexion.cerrarConexion();
	    		Venta nuevo = this.getVenta();
	    		if(nuevo != null) {
	    			for(Detalle d: venta.getMisDetalles()) {
	    				if(!registrarDetalle(nuevo.getNumeroVenta(), d)) {
	    					return false;
	    				}
	    			}		
	    		}
	    	}
		
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return false;
		}
	}
	
	
	
	
	public ArrayList<Venta> buscarVentas(String query){
		ArrayList<Venta> salida = new ArrayList<Venta>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			while(consulta.next()) {
				int codigo = consulta.getInt("idVenta");
				String cliente = consulta.getString("cliente");
		    	String fecha = consulta.getString("fechaVenta");
		    	MedioDePago medioPago =MedioDePago.valueOf(consulta.getString("medioPago"));
		    	double montoTotal = consulta.getDouble("montoTotal");
				Calendar fechaVenta = Calendar.getInstance();
				fechaVenta.setTime(sdf.parse(fecha));
		    	
				Venta v = new Venta();
				v.setNumeroVenta(codigo);
		    	v.setCliente(cliente);
		    	v.setFechaVenta(fechaVenta);
		    	v.setMedioDePago(medioPago);
		    	v.setMontoTotal(montoTotal);
		    	v.setMisDetalles(this.getDetalles(codigo));
				salida.add(v);
			}
		
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return salida;
		}
		
	}
	
	public Venta getVenta(){   
		String query ="SELECT * FROM venta WHERE estado = '1' ORDER BY idVenta DESC LIMIT 1"; 
		Venta salida = null;
		ArrayList <Venta> ventas = buscarVentas(query);
		if(ventas.size() > 0) {
			salida = ventas.get(0);
		}
		return salida;
	}
	
	public Venta getVenta( int idVenta){   
		String query ="SELECT * FROM venta WHERE estado = 1 AND idVenta = '"+idVenta+"'";
		Venta salida = null;
		ArrayList <Venta> ventas = buscarVentas(query);
		if(ventas.size() > 0) {
			salida = ventas.get(0);
		}
		return salida;
	}
	
	public Venta getAllVenta( int idVenta){   
		String query ="SELECT * FROM venta WHERE idVenta = '"+idVenta+"'";
		Venta salida = null;
		ArrayList <Venta> ventas = buscarVentas(query);
		if(ventas.size() > 0) {
			salida = ventas.get(0);
		}
		return salida;
	}
	
	public ArrayList<Venta> getVentas(){   
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 ORDER BY idVenta DESC");
	}
	
	public ArrayList<Venta> getVentas(String parteCliente){   
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND cliente LIKE '%"+parteCliente+"%'");
	}
	
	public ArrayList<Venta> getVentas(MedioDePago medio){   
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND medioPago = '"+medio.name()+"'");
	}
	
	public ArrayList<Venta> getVentas(Calendar fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND DATE(fechaVenta) = '"+sdf.format(fecha.getTime())+"'");
	}
	
	public ArrayList<Venta> getVentas(String parteCliente, MedioDePago medio, Calendar fecha){  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND cliente LIKE '%"+parteCliente+"%' AND medioPago = '"+medio.name() +"' AND DATE(fechaVenta) = '"+sdf.format(fecha.getTime())+"'");
	}
	
	public ArrayList<Venta> getVentas(String parteCliente, MedioDePago medio){  
		
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND cliente LIKE '%"+parteCliente+"%' AND medioPago = '"+medio.name() +"'");
	}
	
	public ArrayList<Venta> getVentas(String parteCliente, Calendar fecha){  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND cliente LIKE '%"+parteCliente+"%' AND DATE(fechaVenta) = '"+sdf.format(fecha.getTime())+"'");
	}
	public ArrayList<Venta> getVentas(MedioDePago medio, Calendar fecha){  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return buscarVentas("SELECT * FROM venta WHERE estado = 1 AND medioPago = '"+medio.name() +"' AND DATE(fechaVenta) = '"+sdf.format(fecha.getTime())+"'");
	}
	
	public ArrayList<Detalle> getDetalles(int idVenta){   
		String query = "SELECT * FROM detalle WHERE idVenta = '"+idVenta+"'"; 
		ArrayList<Detalle> salida = new ArrayList<Detalle>();
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			while(consulta.next()) {
				double precio = consulta.getDouble("precio");
				int cantidad = consulta.getInt("cantidad");
				int codProducto = consulta.getInt("idProducto");
				try {
					Detalle d = new Detalle(codProducto, precio, cantidad);
					salida.add(d);
				}catch(ExceptionDetalle ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
					
				}
				
				
			}
			
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} 
	}
	
	
	
	public boolean eliminarVenta(int idVenta){ 
		Producto_model pm = new Producto_model();
		boolean salida = false;
		String query = "UPDATE venta SET estado = '0' WHERE idVenta = '"+idVenta+"'"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement actualizar = conectar.prepareStatement(query);
			if(!actualizar.execute(query)) {
				salida = true;
				for(Detalle d: getAllVenta(idVenta).getMisDetalles()) {
					Producto p = pm.getAllProducto(d.getCodigoProducto());
					
    				if(!actualizarStock(d.getCodigoProducto(), p.getStock() + d.getCantidad())) {
    					return false;
    				}
    			}	
			}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} 
	}
	
	public boolean actualizarStock(int idProducto, int cantidad){ 
		boolean salida = false;
		String query = "UPDATE producto SET stock = '"+cantidad+"' WHERE idProducto = '"+idProducto+"'"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement actualizar = conectar.prepareStatement(query);
			if(!actualizar.execute(query)) {
				salida = true;
			}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} 
	}

	public static void main(String args[]) {
		Venta_model pv = new Venta_model();
		//System.out.println(pm.getProducto().toString());
		//System.out.println(pm.getProducto(2).toString());
//		ArrayList<Detalle> d = pv.getDetalles(1);
//		System.out.println(d);
//		System.out.println(pv.getVenta());
//		System.out.println(pv.getVenta(2));
//		System.out.println(pv.getVentas());
	//
		//&&Detalle d = new Detalle(7, 10, 10);
		
		//System.out.println(pv.actualizarStock(7, 50));
		
		
		
	}
	
	
	
}
