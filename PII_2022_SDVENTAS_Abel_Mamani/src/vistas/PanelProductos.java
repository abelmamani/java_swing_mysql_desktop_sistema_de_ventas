package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto_model;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.Producto;
import dominio.TipoDeProducto;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import utiles.Util;

public class PanelProductos extends JPanel implements ActionListener {
	private JPanel panelHeader;
	private JPanel panelBody;
	private JPanel panelOpciones;
	private BuscarProductos panelBuscador;
	private JPanel panelBotones;
	private JPanel panelTabla;
	private JTable tabla;
	private JScrollPane jsPane;
	
	private JButton botonAgregar;
	private JButton botonModificar;
	private JButton botonEliminar;
	private JButton botonDetalle;
	private JButton botonBuscar;
	private ArrayList<Producto> misProductos;
	private RegistrarProducto registrarProducto;
	private DetalleProducto detalleProducto;
	private ActualizarProducto modificarProducto;
	private Principal ventanaPrincipal;
	
	public PanelProductos(Principal principal){
		ventanaPrincipal = principal;
		registrarProducto = null;
		panelBuscador = new BuscarProductos(this);
		detalleProducto = null;
		modificarProducto = null;
		agregarComponentes();
	}
	
	public void agregarComponentes(){
		Color colorNavbar = new Color(64, 64, 64);
		Color colorFondo = Color.WHITE;
		
		panelHeader = new JPanel();
		panelBody = new JPanel();
		panelOpciones = new JPanel();
		
		panelBotones = new JPanel();
		panelTabla = new JPanel();
		jsPane = new JScrollPane();
		
		botonAgregar = Componente.getButtonConIcono(new ImageIcon("src/imagenes/agregar.png"), colorFondo);
		botonModificar = Componente.getButtonConIcono(new ImageIcon("src/imagenes/editar.png"), colorFondo);
		botonEliminar = Componente.getButtonConIcono(new ImageIcon("src/imagenes/borrar.png"), colorFondo);
		botonDetalle = Componente.getButtonConIcono(new ImageIcon("src/imagenes/acercarse.png"), colorFondo);
		
		panelHeader.setBackground(colorFondo);
		//panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10, false));
		panelHeader.setLayout(new FlowLayout());
		panelHeader.add(Componente.getLabelConFormato4("Productos"));
		//panelBotones.setBackground(new Color(32, 136, 203));
		panelBotones.setBackground(colorFondo);
		panelBotones.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelBotones.setLayout(new GridLayout(1, 4, 2, 2));
		botonAgregar.addActionListener(this);
		panelBotones.add(botonAgregar);
		botonModificar.addActionListener(this);
		panelBotones.add(botonModificar);
		
		botonDetalle.addActionListener(this);
		panelBotones.add(botonDetalle);
		botonEliminar.addActionListener(this);
		panelBotones.add(botonEliminar);
		
		panelOpciones.setBackground(colorFondo);
		panelOpciones.setLayout(new GridLayout(2, 1));
		//panelOpciones.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelOpciones.add(panelBuscador);
		panelOpciones.add(panelBotones);
	
		
		panelTabla.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelTabla.setLayout(new GridLayout(1,1));
		iniciarTabla();
		
		
		panelBody.setLayout(new BorderLayout());
		panelBody.add(panelOpciones, BorderLayout.NORTH);
		panelBody.add(panelTabla, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelHeader, BorderLayout.NORTH);
		this.add(panelBody, BorderLayout.CENTER);
	}
	//public void iniciarTabla(String nombre, Marca marca, TipoDeProducto tipo) {
	public void iniciarTabla() {
				
		misProductos = obtenerProductos(panelBuscador.getParteNombre(), panelBuscador.getMarca(), panelBuscador.getTipoProducto());
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setRowCount(misProductos.size());
		modelo.setColumnCount(6);
		
		int i = 0;
		for (Producto p : misProductos) {
			modelo.setValueAt(Util.LPAD(p.getCodigoProducto()), i, 0);
			modelo.setValueAt(p.getNombre(), i, 1);
			modelo.setValueAt(p.getMarca(), i, 2);
			modelo.setValueAt(p.getTipoProducto(), i, 3);
			modelo.setValueAt(p.getStock(), i, 4);
			modelo.setValueAt("$ " + p.getPrecioActual(), i, 5);
			i = i + 1;
		}
		
		String fila[] = {"CODIGO", "NOMBRE", "MARCA", "TIPO", "STOCK", "PRECIO"};
		modelo.setColumnIdentifiers(fila);
		
		tabla = Componente.getTablaConFormato(modelo);
		panelTabla.removeAll();
		//jsPane.removeAll();
		//jsPane.add(tabla);
		panelTabla.add(new JScrollPane(tabla));
		jsPane.updateUI();
		panelTabla.updateUI();
	}
	
	public ArrayList<Producto> obtenerProductos(String nombre, Marca marca, TipoDeProducto tipo)  {
		ArrayList<Producto> salida = new ArrayList<Producto>();
		Producto_model pm = new Producto_model();
		if((nombre != null && nombre.length() != 0) && marca != null && tipo != null)
			//misProductos = GestorDeVentas.getInstancia().getProductos(nombre, marca, tipo);
			salida = pm.getProductos(nombre, marca, tipo);
		else {
			if(nombre != null && nombre.length() != 0) {
				if(marca != null) {
					//misProductos = GestorDeVentas.getInstancia().getProductos(nombre, marca);
					salida = pm.getProductos(nombre, marca);
				}else {
					if(tipo != null) {
						//misProductos = GestorDeVentas.getInstancia().getProductos(nombre, tipo);
						salida = pm.getProductos(nombre, tipo);
					}else {
						//misProductos = GestorDeVentas.getInstancia().getProductos(nombre);
						salida = pm.getProductos(nombre);
						
					}
				}
			}else {
				if(marca != null) {
					if(tipo != null) {
						//misProductos = GestorDeVentas.getInstancia().getProductos(marca, tipo);
						salida = pm.getProductos(marca, tipo);
					}else {
						//misProductos = GestorDeVentas.getInstancia().getProductos(marca);
						salida = pm.getProductos(marca);
					}
				}else {
					if(tipo != null) {
						//misProductos = GestorDeVentas.getInstancia().getProductos(tipo);
						salida = pm.getProductos(tipo);
					}else {
						//misProductos = GestorDeVentas.getInstancia().getProductos();
						salida = pm.getProductos();
					}
				}
			}
		}
		
		return pm.ordenarProductos(salida, panelBuscador.getOrden());
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botonAgregar) {
			this.cerrarVentanas(false, true, true);
			if(registrarProducto == null) 
				this.registrarProducto = new RegistrarProducto(ventanaPrincipal, this, true);
			registrarProducto.setVisible(true);
		}
		
		if(e.getSource() == botonModificar) {
			this.cerrarVentanas(true, false, true);
			if(puedoTrabajarConLaTabla()) {
				if(modificarProducto == null) 
					this.modificarProducto = new ActualizarProducto(ventanaPrincipal, this, true);
				modificarProducto.cargarDatos();
				modificarProducto.setVisible(true);
			}
		}
		
		if(e.getSource() == botonEliminar) {
			Producto_model pm = new Producto_model();
			this.cerrarVentanas(true, true, true);
			if(puedoTrabajarConLaTabla()) {
				//if(GestorDeVentas.getInstancia().deleteProducto((int)tabla.getValueAt(tabla.getSelectedRow(), 0))) {
				ImageIcon icon = new ImageIcon("src/imagenes/pregunta.png");
				int result = JOptionPane.showConfirmDialog(this, 
			                "Esta seguro de que desea eliminar el producto "+tabla.getValueAt(tabla.getSelectedRow(), 0)+"?" , "Eliminar", 
			                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if(result == 0) {
					if(pm.eliminarProducto(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0) + ""))) {
						iniciarTabla();
						ImageIcon icon2 = new ImageIcon("src/imagenes/comprobado.png");
						JOptionPane.showMessageDialog(this, "Se elimino correctamente!","Eliminar", JOptionPane.INFORMATION_MESSAGE, icon2);
					}else {
						JOptionPane.showMessageDialog(this, "No se pudo eliminar" );
					}
				}
				
			}
		}
		
		if(e.getSource() == botonDetalle) {
			this.cerrarVentanas(true, true, false);
			if(puedoTrabajarConLaTabla()) {
				if(detalleProducto == null) 
					this.detalleProducto = new DetalleProducto(ventanaPrincipal, this, true);
				detalleProducto.cargarDatos();
				detalleProducto.setVisible(true);
			}
			
		}
		
	}
	
	public int getCodigoDeProducto() {
		if(tabla == null) {
			return -1;
		}else{
			if(tabla.getSelectedRow() < 0 )
				return -1;
			return Integer.parseInt(String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0)));
		//	return Integer.parseInt(t);
		}
	}
	
	public boolean puedoTrabajarConLaTabla() {
		Producto_model pm = new Producto_model();
		//if(GestorDeVentas.getInstancia().getProductos().size() > 0) {
		if(pm.getProductos().size() > 0) {
			if(getCodigoDeProducto() < 0) {
				JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila!" );
			}else {
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(this, "No hay registros!" );
		}
		return false;
	}
	
	public void cerrarVentanas(boolean cerrarRegistrar, boolean cerrarModificar, boolean cerrarDetalle) {
		if(registrarProducto != null && cerrarRegistrar) {
			registrarProducto.blanquear();
			registrarProducto.dispose();
		}
		
		if(modificarProducto != null && cerrarModificar) {
			modificarProducto.dispose();
		}
		
		if(detalleProducto != null && cerrarDetalle) {
			detalleProducto.dispose();
		}
	}
	
	public void limpiarBuscador() {
		panelBuscador.blanquear();
	}
}
