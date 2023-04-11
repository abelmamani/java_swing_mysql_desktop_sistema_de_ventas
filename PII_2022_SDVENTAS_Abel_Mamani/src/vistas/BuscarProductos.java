package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto_model;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.Producto;
import dominio.TipoDeProducto;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;

public class BuscarProductos extends JPanel implements ActionListener, DocumentListener{
	private JPanel panelNombre;
	private JPanel panelMarca;
	private JPanel panelTipo;
	private JPanel panelOrden;
	//private JButton botonBuscar;
	private JTextField txtNombre;
	private JComboBox<String> comboMarca;
	private JComboBox<String> comboTipoProducto;
	private JComboBox<String> comboOrden;
	PanelProductos panelProductos;
	
	public BuscarProductos(PanelProductos panel){
		agregarComponentes();
		this.panelProductos = panel;
	}
	
	public void agregarComponentes(){
		Color colorNavbar = new Color(64, 64, 64);
		Color colorFondo = Color.white;
		
		panelNombre = new JPanel();
		panelMarca = new JPanel();
		panelTipo = new JPanel();
		panelOrden = new JPanel();
		//botonBuscar = Componente.getButtonConFormato2("Buscar");
		txtNombre= Componente.getTextFieldConFormato();
		txtNombre.setToolTipText("ingrese nombre de producto");
		comboMarca = new JComboBox<String>();
		comboTipoProducto = new JComboBox<String>();
		comboOrden = new JComboBox<String>();
	
		this.setLayout(new GridLayout(1, 4));
		this.setBackground(colorFondo);
		this.cargarComboBoxMarca();
		this.cargarComboBoxTipoProducto();
		this.cargarComboBoxOrden();
		
		panelNombre.setBackground(colorFondo);
		panelNombre.setLayout(new GridLayout(2, 1));
		panelNombre.setBorder(BorderFactory.createLineBorder(colorFondo, 20, true));
		panelNombre.add(Componente.getLabelConFormato3("Nombre"));
		panelNombre.add(txtNombre);
		
		
		iniciarCombo(comboMarca, panelMarca, colorFondo, "Marca");
		iniciarCombo(comboTipoProducto, panelTipo, colorFondo, "Tipo de producto");
		iniciarCombo(comboOrden, panelOrden, colorFondo, "Ordenar por");
		//botonBuscar.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		txtNombre.getDocument().addDocumentListener(this);
		//botonBuscar.addActionListener(this);
		comboMarca.addActionListener (this);
		comboTipoProducto.addActionListener (this);
		comboOrden.addActionListener (this);
		
		this.add(panelNombre);
		this.add(panelMarca);
		this.add(panelTipo);
		this.add(panelOrden);
		//this.add(botonBuscar);


	}
	private void iniciarCombo(JComboBox combo, JPanel panel, Color color, String label) {
		combo.setBackground(color);
		panel.setBackground(color);
		panel.setLayout(new GridLayout(2, 1));
		panel.setBorder(BorderFactory.createLineBorder(color, 20, true));
		panel.add(Componente.getLabelConFormato3(label));
		panel.add(combo);
	}
	private void cargarComboBoxMarca(){
		comboMarca.addItem("Seleccionar");
		for(Marca marca : Marca.values()) { 
		    comboMarca.addItem(marca.name()); 
		}
	}
	
	private void cargarComboBoxTipoProducto(){
		comboTipoProducto.addItem("Seleccionar");
		for(TipoDeProducto tipo: TipoDeProducto.values()) { 
		    comboTipoProducto.addItem(tipo.name()); 
		}
	}
	
	private void cargarComboBoxOrden(){
		comboOrden.addItem("Seleccionar");
		comboOrden.addItem("MENOR PRECIO"); 
		comboOrden.addItem("MAYOR PRECIO"); 
		comboOrden.addItem("MENOR STOCK");
		comboOrden.addItem("MAYOR STOCK");
	}
	
	public String getOrden(){
		if(comboOrden.getSelectedIndex()==0)
			return null;
		else
			return comboOrden.getSelectedItem().toString();
	}
	
	public Marca getMarca(){
		if(comboMarca.getSelectedIndex()==0)
			return null;
		else
			return Marca.valueOf(comboMarca.getSelectedItem().toString());
	}
	
	
	
	public TipoDeProducto getTipoProducto(){
		if(comboTipoProducto.getSelectedIndex()==0)
			return null;
		else
			return TipoDeProducto.valueOf(comboTipoProducto.getSelectedItem().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Producto_model pm = new Producto_model();
		//if(e.getSource() == botonBuscar) {
			//if(GestorDeVentas.getInstancia().getProductos().size() == 0) {
			if(pm.getProductos().size() == 0) {
				JOptionPane.showMessageDialog(this, "no hay elementos");
			}else {
				//System.out.println("buscando");
				//panelProductos.iniciarTabla(txtNombre.getText().trim(), getMarca(), getTipoProducto());
				panelProductos.iniciarTabla();
			}
		//}
	}
	
	public String getParteNombre() {
		return txtNombre.getText();
	}
	
	public void blanquear() {
		txtNombre.setText("");
		comboMarca.setSelectedIndex(0);
		comboTipoProducto.setSelectedIndex(0);
		comboOrden.setSelectedIndex(0);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		  changedUpdate(e);
		
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		  changedUpdate(e);
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		panelProductos.iniciarTabla();
	}
	
}
