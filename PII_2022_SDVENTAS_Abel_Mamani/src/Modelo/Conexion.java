package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dominio.GestorDeVentas;
public class Conexion {
    private static Connection conn;
    private static Conexion instancia;
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String user = "root";
    private static String pass = "";
    private static String db = "ventas";
    private static String url  ="jdbc:mysql://localhost:3306/"+db+"?useUnicode=true&useJDBCCompliantTimeZoneShift=true&userLegacyDateTimeCode=false&serverTimezone=UTC";
    
    private Conexion() {
    	
    }
    
    public Connection conectar(){
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,user, pass);
            // Connect?
          //  if(conn != null)
               //System.out.println("Conexionn establecida exitosamente");
        }catch (ClassNotFoundException e) {
        	JOptionPane.showMessageDialog(null, "Clase no encontrada: " +e);
  
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null, "Error de conexion: " +e);
      
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error: " +e);
           
        }
        
        return conn;
    }
    
    public  Connection getConnection(){
		return conn;
    }
    public void cerrarConexion() throws SQLException  {
    	try {
    		conn.close();
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "Error: "+e);
    		conn.close();
    	}finally {
    		conn.close();
    	}
    }
    
    public static Conexion getInstancia() {
    	if(instancia == null) {
    		instancia = new Conexion();
    	}
    	return instancia;
    }
 
    
    public static void main(String []args) {
//    	Connect conn = new Connect();
//    	String query = "SELECT * FROM cliente";
//    	
//    	try{
//    
//    	    // create the preparedstatement and add the criteria
//    	    PreparedStatement ps = conn.getConnection().prepareStatement(query);
//    	    ResultSet rs = ps.executeQuery();
//    	    while ( rs.next() ){
//    	    
//    	      System.out.println(rs.getString("idCliente") + rs.getString("nombre")+rs.getString("apellido") + rs.getString("telefono"));
//    	    }
//    	      rs.close();
//    	      ps.close();
//    	    }catch (SQLException se){
//    	        System.out.println("error ==> " + se.getMessage());
//    	     }
    	
//    	Connect conn = new Connect();
//    	String query = "INSERT INTO cliente (nombre, apellido, dni, telefono) VALUES ('daniel', 'perez', '46455445', '5465465')";
//    	try{
//    
//    	    // create the preparedstatement and add the criteria
//    	    PreparedStatement ps = conn.getConnection().prepareStatement(query);
//    	    if(!ps.execute(query)) {
//    	    	System.out.println("SE HA GUARDADO EXITOSAMENTE");
//    	    }else {
//    	    	System.out.println("NO SE HA GUARDADO");
//    	    }
//    	    
//    	     // rs.close();
//    	      ps.close();
//    	    }catch (SQLException se){
//    	        System.out.println("error ==> " + se.getMessage());
//    	     }
//    	Connect conn = new Connect();
//    	String query = "UPDATE cliente SET nombre ='marcos', apellido = 'castillo' WHERE idCliente = 5";
//    	try{
//    
//    	    // create the preparedstatement and add the criteria
//    	    PreparedStatement ps = conn.getConnection().prepareStatement(query);
//    	    if(!ps.execute()) {
//    	    	System.out.println("SE HA actualizado EXITOSAMENTE");
//    	    }else {
//    	    	System.out.println("NO SE HA actualizado");
//    	    }
//    	    
//    	     // rs.close();
//    	      ps.close();
//    	    }catch (SQLException se){
//    	        System.out.println("error ==> " + se.getMessage());
//    	     }
    	
    	Conexion conn = new Conexion();
	
    	String query = "DELETE FROM cliente WHERE idCliente = 5";
    	try{
    
    	    // create the preparedstatement and add the criteria
    	    PreparedStatement ps = conn.getConnection().prepareStatement(query);
    	    if(!ps.execute()) {
    	    	System.out.println("SE HA Borrado EXITOSAMENTE");
    	    }else {
    	    	System.out.println("NO SE HA Borrado");
    	    }
    	    
    	     // rs.close();
    	      ps.close();
    	    }catch (SQLException se){
    	        System.out.println("error ==> " + se.getMessage());
    	     }
    
    } 
}
