package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

import Modelo.Venta_model;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import utiles.Util;

public class BuscarVentas extends JPanel implements ActionListener, DocumentListener, KeyListener {
	private JPanel panelCliente;
	private JPanel panelFecha;
	private JPanel panelMedio;
	private JButton botonBuscar;
	private JTextField txtCliente;
	private JComboBox<String> comboMedioPago;
	private JPanel fechaVenta;
	private PanelVentas panelVentas;
	private JTextField txtDia;
	private JTextField txtMes;
	private JTextField txtAnio;
	private Calendar fecha = null;
	
	public BuscarVentas(PanelVentas panel){
		agregarComponentes();
		this.panelVentas = panel;
	}
	
	public void agregarComponentes(){
		Color colorNavbar = new Color(64, 64, 64);
		Color colorFondo = Color.white;
		
		panelCliente = new JPanel();
		panelMedio = new JPanel();
		panelFecha= new JPanel();
		fechaVenta = new JPanel();
		botonBuscar = Componente.getButtonConFormatoAzul("Buscar");
		txtCliente= Componente.getTextFieldConFormato();
		//txtCliente.getDocument().addDocumentListener(this);
		txtCliente.addKeyListener(this);
		txtCliente.setToolTipText("ingrese nombre de cliente");
		txtDia= Componente.getTextFieldConFormato();
		txtDia.addKeyListener(this);
		txtDia.setToolTipText("ingrese Dia (solo numeros)");
		txtMes= Componente.getTextFieldConFormato();
		txtMes.addKeyListener(this);
		txtMes.setToolTipText("ingrese Mes (solo numeros)");
		txtAnio= Componente.getTextFieldConFormato();
		txtAnio.setToolTipText("ingrese Año (solo numeros)");
		txtAnio.addKeyListener(this);
		
		
		comboMedioPago = new JComboBox<String>();
		comboMedioPago.setBackground(colorFondo);
		this.cargarComboBoxMedioPago();
		comboMedioPago.addActionListener(this);
		//error
		
		panelCliente.setBackground(colorFondo);
		panelCliente.setLayout(new GridLayout(2, 1));
		panelCliente.setBorder(BorderFactory.createLineBorder(colorFondo, 20, true));
		panelCliente.add(Componente.getLabelConFormato3("Cliente"));
		panelCliente.add(txtCliente);
		
		panelMedio.setBackground(colorFondo);
		panelMedio.setLayout(new GridLayout(2, 1));
		panelMedio.setBorder(BorderFactory.createLineBorder(colorFondo, 20, true));
		panelMedio.add(Componente.getLabelConFormato3("Medio de Pago"));
		panelMedio.add(comboMedioPago);
		
	 
		fechaVenta.setLayout(new GridLayout(1, 4, 2, 2));
		//fechaVenta.add(Componente.getLabelConFormato3("Dia"));
		fechaVenta.add(txtDia);
		//fechaVenta.add(Componente.getLabelConFormato3("Mes"));
		fechaVenta.add(txtMes);
		//fechaVenta.add(Componente.getLabelConFormato3("Año"));
		fechaVenta.add(txtAnio);
		fechaVenta.add(botonBuscar);
		
		panelFecha.setBackground(colorFondo);
		panelFecha.setLayout(new GridLayout(2, 1));
		panelFecha.setBorder(BorderFactory.createLineBorder(colorFondo, 20, true));
		panelFecha.add(Componente.getLabelConFormato3("Fecha de Venta"));
		panelFecha.add(fechaVenta);
		
		
		botonBuscar.addActionListener(this);
		
		this.setLayout(new GridLayout(1, 3));
		this.setBackground(colorFondo);
		//this.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 64), 10, false));
		this.add(panelCliente);
		this.add(panelMedio);
		this.add(panelFecha);
		//this.add(botonBuscar);


	}
	
	
	
	private void cargarComboBoxMedioPago(){
		comboMedioPago.addItem("Seleccionar");
		for(MedioDePago medio: MedioDePago.values()) { 
		    comboMedioPago.addItem(medio.name()); 
		}
	}
	
	public MedioDePago getMedioPago(){
		if(comboMedioPago.getSelectedIndex()==0)
			return null;
		else
			return MedioDePago.valueOf(comboMedioPago.getSelectedItem().toString());
	}
	
	private boolean validarString(String text) {
		boolean salida = true;
		for(int i = 0; i < text.length(); i++) {
			if(Character.isDigit(text.charAt(i))) {
				salida = false;
			}
		}
		return salida;
	}
	
	private boolean validarEntero(String text) {
		try {
			@SuppressWarnings("unused")
			int aux = Integer.parseInt(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//if(e.getSource() == botonBuscar) {
			
			Venta_model vm = new Venta_model();
			//if(GestorDeVentas.getInstancia().getVentas().size() == 0) {
			if(vm.getVentas().size() == 0) {
				//JOptionPane.showMessageDialog(this, "no hay ningun registros de Ventas");
			}else {
				if(validarString(txtCliente.getText())) {
					if(txtDia.getText().trim().length() == 0 && txtMes.getText().trim().length() == 0 && txtAnio.getText().trim().length() == 0 ) {
						fecha = null;
						//panelVentas.iniciarTabla(txtCliente.getText().trim(), fecha, getMedioPago());
						panelVentas.iniciarTabla();
						
					}else {
						if(validarEntero(txtDia.getText()) && validarEntero(txtMes.getText()) && validarEntero(txtAnio.getText())) {
							int dia = Integer.parseInt(txtDia.getText());
							int mes = Integer.parseInt(txtMes.getText());
							int anio = Integer.parseInt(txtAnio.getText());
							if(Util.esFechaValida(dia, mes, anio)) {
								fecha = Calendar.getInstance();
								fecha.set(Calendar.DAY_OF_MONTH, dia);
								fecha.set(Calendar.MONTH, mes-1);
								fecha.set(Calendar.YEAR, anio);
								fecha.set(Calendar.HOUR, 0);
								fecha.set(Calendar.MINUTE, 0);
								fecha.set(Calendar.SECOND, 0);
								panelVentas.iniciarTabla();
								
							}else {
								JOptionPane.showMessageDialog(this, "Ingrese una fecha valida!","Error", JOptionPane.ERROR_MESSAGE);
								fecha = null;
							}
						}else {
							JOptionPane.showMessageDialog(this, "fecha no valida (mes, dia, año)!","Error", JOptionPane.ERROR_MESSAGE);
							fecha = null;
						}	
					}
					
				}else {
					JOptionPane.showMessageDialog(this, "El nombre del cliente no pude tener numeros!","Error", JOptionPane.ERROR_MESSAGE);
				}
		
			//}
		}
	}
	
	public String getParteCliente() {
		return this.txtCliente.getText().trim();
	}
	public Calendar getFechaVenta() {
		return this.fecha;
	}
	
	public void blanquear() {
		txtAnio.setText("");
		txtDia.setText("");
		txtMes.setText("");
		txtCliente.setText("");
		comboMedioPago.setSelectedIndex(0);
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		System.out.println(txtCliente.getText());
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		System.out.println(txtCliente.getText());
		  changedUpdate(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		//txtCliente.setText("");
		panelVentas.iniciarTabla();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(e.getSource() == txtCliente) {
			if(!Character.isAlphabetic(c)) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtDia) {
			if(!Character.isDigit(c) || txtDia.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtMes) {
			if(!Character.isDigit(c) || txtMes.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtAnio) {
			if(!Character.isDigit(c) || txtAnio.getText().length() >= 4) {
				e.consume();
			}
		}
		
		panelVentas.iniciarTabla();
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//panelVentas.iniciarTabla();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		panelVentas.iniciarTabla();
	}
	
}
