package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Precio;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import exceptions.ExceptionVenta;

public class Producto_model {
	private Conexion conexion = Conexion.getInstancia();
	
	public Producto_model() {
	
	}
	public boolean registrarPrecio(int codProducto, double valor) {
		boolean salida = true;
		String query = "INSERT INTO precio (valor, idProducto) VALUES ('"+valor+"','"+codProducto+"')"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement insertar = conectar.prepareStatement(query);
			if(insertar.execute(query)) {
				salida = false;
	    	}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return false;
		}
	}
	

	public boolean registrarProducto(Producto producto){   
    	boolean salida = true;
    	String nombre = producto.getNombre();
    	String marca = producto.getMarca().name();
    	String tipo = producto.getTipoProducto().name();
    	String descripcion = producto.getDescripcion();
    	int stock = producto.getStock();
    	double precio = producto.getPrecioActual();
		String query = "INSERT INTO producto (nombre, marca, tipoProducto, descripcion, stock) VALUES ('"+nombre+"', '"+marca+"', '"+tipo+"', '"+descripcion+"', '"+stock+"')"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement insertar = conectar.prepareStatement(query);
			if(insertar.execute(query)) {
				salida = false;
				conexion.cerrarConexion();
	    	}else {
	    		conexion.cerrarConexion();
	    		Producto nuevo = this.getProducto();
	    		if(nuevo != null) {
	    			if(registrarPrecio(nuevo.getCodigoProducto(),producto.getPrecioActual()) == false) {
	    				salida = false;
	    			}
	    	    			
	    		}
	    	}
		
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return false;
		}
	}
	
	public boolean modificarProducto(int idProducto, String nombre, String marca, String tipo, String desc, int stock, double precio	) throws ExceptionProducto, ExceptionPrecio{   
    	boolean salida = false;
    	GestorDeVentas gv = GestorDeVentas.getInstancia();
    	gv.Limpiar();
    	Producto p = new Producto(idProducto, nombre,Marca.valueOf(marca),TipoDeProducto.valueOf(tipo), desc, stock, precio);
    	gv.addProducto(p);
    	gv.Limpiar();
    	String query = "UPDATE producto SET nombre = '"+nombre+"', marca = '"+marca+"', tipoProducto = '"+tipo+"', descripcion = '"+desc+"', stock = '"+stock+"' WHERE idProducto = '"+idProducto+"'"; 
		
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement actualizar = conectar.prepareStatement(query);
			if(actualizar.execute(query)) {
				salida = false;
				System.out.println("no se actualizo");
	    	}else {
	    		conexion.cerrarConexion();
	    		Producto nuevo = this.getProducto();
	    		if(nuevo != null) {
	    			int codPrecio = getCodPrecioActual(nuevo.getCodigoProducto());
	    			double valorPrecio = getPrecioActual(nuevo.getCodigoProducto());
	    			if(codPrecio > 0) {
	    				if(valorPrecio != precio) {
	    					if(registrarPrecio(nuevo.getCodigoProducto(), precio)) {
	    						if(updatePrecioActual(codPrecio)) {
	    							salida = true;
	    						}
	    					}
	    					
	    				}else {
	    					salida = true;
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
	
	public Producto getProducto(int idProducto){   
		String query = "SELECT * FROM producto WHERE estado = 1 AND idProducto = '"+idProducto+"'"; 
		Producto salida = null;
		ArrayList <Producto> productos = buscarProductos(query);
		if(productos.size() > 0) {
			salida = productos.get(0);
		}
		return salida;
	}
	
	public Producto getAllProducto(int idProducto){   
		String query = "SELECT * FROM producto WHERE idProducto = '"+idProducto+"'"; 
		Producto salida = null;
		ArrayList <Producto> productos = buscarProductos(query);
		if(productos.size() > 0) {
			salida = productos.get(0);
		}
		return salida;
	}
	
	public Producto getProducto(){   
		String query ="SELECT * FROM producto WHERE estado = '1' ORDER BY idProducto DESC LIMIT 1"; 
		Producto salida = null;
		ArrayList <Producto> productos = buscarProductos(query);
		if(productos.size() > 0) {
			salida = productos.get(0);
		}
		return salida;
	}
	
	public ArrayList<Producto> buscarProductos(String query){
		ArrayList<Producto> salida = new ArrayList<Producto>();
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			while(consulta.next()) {
				int codigo = consulta.getInt("idProducto");
				String nombre = consulta.getString("nombre");
		    	Marca marca = Marca.valueOf(consulta.getString("marca"));
		    	TipoDeProducto tipo =TipoDeProducto.valueOf(consulta.getString("tipoProducto"));
		    	String descripcion = consulta.getString("descripcion");
		    	int stock = consulta.getInt("stock");
				Producto p = new Producto(codigo, nombre, marca, tipo, descripcion, stock);
				p.setMisPrecios(this.getPrecios(codigo));
				salida.add(p);
			}
		
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		}
		
	}
	public ArrayList<Producto> getProductos(){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 ORDER BY idProducto DESC");
	}
	
	public ArrayList<Producto> getProductos(String parteNombre){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND nombre LIKE '%"+parteNombre+"%'");
	}
	
	public ArrayList<Producto> getProductos(Marca marca){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND marca = '"+marca.name()+"'");
	}
	
	public ArrayList<Producto> getProductos(TipoDeProducto tipo){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND tipoProducto = '"+tipo.name()+"'");
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, Marca marca, TipoDeProducto tipo){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND nombre LIKE '%"+parteNombre+"%' AND marca = '"+marca.name() +"' AND tipoProducto = '"+tipo.name()+"'");
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, Marca marca){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND nombre LIKE '%"+parteNombre+"%' AND marca = '"+marca.name() +"'");
	}
	
	public ArrayList<Producto> getProductos(String parteNombre, TipoDeProducto tipo){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND nombre LIKE '%"+parteNombre+"%' AND tipoProducto = '"+tipo.name()+"'");
	}
	
	public ArrayList<Producto> getProductos(Marca marca, TipoDeProducto tipo){   
		return buscarProductos("SELECT * FROM producto WHERE estado = 1 AND marca = '"+marca.name() +"' AND tipoProducto = '"+tipo.name()+"'");
	}
	
	public ArrayList<Producto> ordenarProductos(ArrayList<Producto> productos, String orden){
		ArrayList<Producto> salida = productos;
		Comparator <Producto> menorPrecio = (o1, o2)-> (int) (o1.getPrecioActual() - o2.getPrecioActual());
		Comparator <Producto> mayorPrecio = (o1, o2)-> (int) (o2.getPrecioActual() - o1.getPrecioActual());
		Comparator <Producto> menorStock = (o1, o2)-> o1.getStock() - o2.getStock() ;
		Comparator <Producto> mayorStock = (o1, o2)-> o2.getStock() - o1.getStock();
		
		if(orden != null) {
			if(orden.equals("MENOR PRECIO")) {
				salida = GestorDeVentas.getInstancia().ordenarProductos(productos, menorPrecio);
			}else if(orden.equals("MAYOR PRECIO")) {
				salida = GestorDeVentas.getInstancia().ordenarProductos(productos, mayorPrecio);
			}else if(orden.equals("MENOR STOCK")) {
				salida = GestorDeVentas.getInstancia().ordenarProductos(productos, menorStock);
			}else if(orden.equals("MAYOR STOCK")) {
				salida = GestorDeVentas.getInstancia().ordenarProductos(productos, mayorStock);
				
			}
		}
		
		return salida;
	}

	public ArrayList<Precio> getPrecios(int idProducto){   
		String query = "SELECT * FROM precio WHERE idProducto = '"+idProducto+"'"; 
		ArrayList<Precio> salida = new ArrayList<Precio>();
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			while(consulta.next()) {
				double valor = consulta.getDouble("valor");
				String fecha = consulta.getString("fechaHasta");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar fechaHasta = Calendar.getInstance();
			
				if(fecha != null)
					fechaHasta.setTime(sdf.parse(fecha));
				else
					fechaHasta = null;
				Precio p = new Precio(valor, fechaHasta);
				salida.add(p);
				
			}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			
			System.out.println("eerorr  en la conversoio de fecha");
			e.printStackTrace();
			return salida;
		}
	}
	
	public int getCodPrecioActual(int idProducto){ 
		int salida = -1;
		String query = "SELECT idPrecio FROM precio WHERE idProducto = '"+idProducto+"' AND fechaHasta is null"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			if(consulta.next()) {
				salida = consulta.getInt("idPrecio");
			}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} 
	}
	
	public double getPrecioActual(int idProducto){ 
		double salida = 0;
		String query = "SELECT valor FROM precio WHERE idProducto = '"+idProducto+"' AND fechaHasta is null"; 
		try {
			Connection conectar = conexion.conectar();
			PreparedStatement buscar = conectar.prepareStatement(query);
			ResultSet consulta = buscar.executeQuery();
			
			if(consulta.next()) {
				salida = consulta.getDouble("valor");
			}
			conexion.cerrarConexion();
			return salida;
		}catch(SQLException e) {
			System.out.println("Error: " +e);
			return salida;
		} 
	}
	
	public boolean updatePrecioActual(int idPrecio){ 
		boolean salida = false;
		String query = "UPDATE precio SET fechaHasta = SYSDATE() WHERE idPrecio = '"+idPrecio+"'"; 
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
	
	public boolean eliminarProducto(int idProducto){ 
		boolean salida = false;
		String query = "UPDATE producto SET estado = '0' WHERE idProducto = '"+idProducto+"'"; 
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
		Producto_model pm = new Producto_model();
		//System.out.println(pm.getProducto().toString());
		//System.out.println(pm.getProducto(2).toString());
		ArrayList<Precio> p = pm.getPrecios(4);
		System.out.println(p.get(0).getValor());
		System.out.println(pm.getProducto().toString());
	}
	
	
	
}
