package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto_model;
import dominio.Detalle;
import dominio.GestorDeVentas;
import dominio.Producto;
import dominio.Venta;
import utiles.Util;


public class PanelDetalles extends JPanel {
	private JTable tabla;
	private ArrayList<Detalle> misDetalles;
	
	public PanelDetalles(){
		agregarComponentes();
	}
	
	public void agregarComponentes(){
		this.setLayout(new GridLayout(1,1));
		iniciarTablaDetalle(null);
	}
	
	public void iniciarTablaDetalle(Venta v) {
		Producto_model pm = new Producto_model();
		
		if(v == null) {
			misDetalles = new ArrayList<Detalle>();
		}else {
			misDetalles = v.getMisDetalles();
		}
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setRowCount(misDetalles.size());
		modelo.setColumnCount(5);
		int i = 0;
		
		for (Detalle d : misDetalles) {
			//Producto p = GestorDeVentas.getInstancia().getProducto(d.getCodigoProducto());
			Producto p = pm.getAllProducto(d.getCodigoProducto());
			modelo.setValueAt(Util.LPAD(p.getCodigoProducto()), i, 0);
			modelo.setValueAt(p.getNombre() , i, 1);
			modelo.setValueAt(d.getCantidad(), i, 2);
			modelo.setValueAt(d.getPrecio(), i, 3);
			modelo.setValueAt(d.getPrecio() * d.getCantidad(), i, 4);
			i = i + 1;
		}
	
		String fila[] = {"CODIGO", "PRODUCTO", "CANTIDAD", "PRECIO", "SUBTOTAL"};
		modelo.setColumnIdentifiers(fila);
		this.removeAll();
		tabla = Componente.getTablaConFormato(modelo);
		//jsPane.removeAll();
		//jsPane.add(tabla);
		this.add(new JScrollPane(tabla));
		//jsPane.updateUI();
		this.updateUI();
	}
	
	public int getCodigoDeProducto() {
		if(tabla == null) {
			return -1;
		}else{
			if(tabla.getSelectedRow() < 0 )
				return -1;
			return Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0) +"");
		}
	}
	
	public int getCantidadDeProducto() {
		return Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 2) + "");
			
	}
	
	public double getPrecioDeProducto() {
		return  Double.parseDouble(tabla.getValueAt(tabla.getSelectedRow(), 3) + "");
	}
	
	public boolean puedoTrabajarConLaTabla() {
		if(misDetalles.size() > 0) {
			if(getCodigoDeProducto() < 0) {
				JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila!!!" );
			}else {
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(this, "No hay registros!!!" );
		}
		return false;
	}
	
	
}
