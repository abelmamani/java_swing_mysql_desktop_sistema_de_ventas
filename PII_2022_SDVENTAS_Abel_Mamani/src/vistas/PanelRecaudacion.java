package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Modelo.Venta_model;
import dominio.GestorDeVentas;
import utiles.Util;

public class PanelRecaudacion extends JPanel implements ActionListener, KeyListener{

	private JPanel panelHeader;
	private JPanel panelBody;
	private JPanel panelFecha;
	private JPanel panelFechaDesde;
	private JPanel panelDescripcion;
	private JPanel panelFechaHasta;
	private JPanel panelBotones;
	private JButton obtener;
	private JButton cancelar;
	//private JButton volver;

	private JTextField txtDiaDesde;
	private JTextField txtMesDesde;
	private JTextField txtAnioDesde;
	private JTextField txtDiaHasta;
	private JTextField txtMesHasta;
	private JTextField txtAnioHasta;
	private JTextArea descripcion;
	private SimpleDateFormat sdf;
	public PanelRecaudacion() {
		//super("OBTENER RECAUDACION");
		sdf = new SimpleDateFormat("dd/MM/yyyy");
		//this.setSize(820, 500);
		//setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//setLocation(300,300);
		agregarComponentes();
	}

	private void agregarComponentes() {
		Color colorFondo = Color.white;
		//PANEL BOTONES
		panelHeader = new JPanel();
		panelBody = new JPanel();
		panelFecha = new JPanel();
		panelFechaDesde = new JPanel();
		panelFechaHasta = new JPanel();
		panelDescripcion = new JPanel();
		panelBotones = new JPanel();
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(colorFondo);
		panelBotones.setBorder(BorderFactory.createLineBorder(colorFondo, 10));

		obtener = iniciarBoton("OBTENER");
		cancelar = iniciarBoton("CANCELAR");
		//volver = iniciarBoton("   VOLVER   ");
		descripcion = Componente.getTextAreaConFormato();
		descripcion.setEditable(false);
		txtDiaDesde = iniciarTextField("dia del Mes");
		txtMesDesde = iniciarTextField("Mes");
		txtAnioDesde = iniciarTextField("Anio");
		txtDiaHasta = iniciarTextField("dia del Mes");
		txtMesHasta = iniciarTextField("Mes");
		txtAnioHasta = iniciarTextField("Anio");

		panelFechaDesde.setLayout(new GridLayout(1, 4, 2, 2));
		panelFechaDesde.setBackground(colorFondo);
		panelFechaDesde.setBorder(BorderFactory.createLineBorder(colorFondo, 20));
		panelFechaDesde.add(Componente.getLabelConFormato3("Dia"));
		panelFechaDesde.add(txtDiaDesde);
		panelFechaDesde.add(Componente.getLabelConFormato3("Mes"));
		panelFechaDesde.add(txtMesDesde);
		panelFechaDesde.add(Componente.getLabelConFormato3("Año"));
		panelFechaDesde.add(txtAnioDesde);
		
		panelFechaHasta.setLayout(new GridLayout(1, 4, 2, 2));
		panelFechaHasta.setBackground(colorFondo);
		panelFechaHasta.setBorder(BorderFactory.createLineBorder(colorFondo, 20));
		panelFechaHasta.add(Componente.getLabelConFormato3("Dia"));
		panelFechaHasta.add(txtDiaHasta);
		panelFechaHasta.add(Componente.getLabelConFormato3("Mes"));
		panelFechaHasta.add(txtMesHasta);
		panelFechaHasta.add(Componente.getLabelConFormato3("Año"));
		panelFechaHasta.add(txtAnioHasta);
		
		panelDescripcion.setLayout(new BorderLayout());
		panelDescripcion.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelDescripcion.setBackground(colorFondo);
		panelDescripcion.add(Componente.getLabelConFormato3("Descripcion"), BorderLayout.NORTH);
		panelDescripcion.add(descripcion, BorderLayout.CENTER);
		
	
		//panelFecha.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelFecha.setLayout(new GridLayout(2, 2, 2, 2));
		panelFecha.setBackground(colorFondo);
		panelFecha.add(Componente.getLabelConFormato3("Desde  "));
		panelFecha.add(Componente.getLabelConFormato3("Hasta "));
		panelFecha.add(panelFechaDesde);
		panelFecha.add(panelFechaHasta);
		
		
		panelBody.setLayout(new BorderLayout());
		panelBody.setBackground(colorFondo);
		//panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelBody.add(panelFecha, BorderLayout.NORTH);
		panelBody.add(panelDescripcion, BorderLayout.CENTER);

	
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10, false));
		panelHeader.add(Componente.getLabelConFormato4("Recaudacion de Ventas"));
		this.blanquear();
		//Container cp = getContentPane();
		//cp.add(panelHeader,BorderLayout.NORTH);
		//cp.add(panelBody,BorderLayout.CENTER);
		//cp.add(panelBotones,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(panelHeader,BorderLayout.NORTH);
		this.add(panelBody,BorderLayout.CENTER);
		this.add(panelBotones,BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		JButton source = (JButton) e.getSource();
		
		if(source == obtener){
			if(validarEntero(txtDiaDesde.getText()) && validarEntero(txtDiaHasta.getText()) &&  validarEntero(txtMesDesde.getText()) && validarEntero(txtMesHasta.getText()) &&  validarEntero(txtAnioDesde.getText()) && validarEntero(txtAnioHasta.getText()) ) {
				int diaDesde = Integer.parseInt(txtDiaDesde.getText());
				int mesDesde = Integer.parseInt(txtMesDesde.getText());
				int anioDesde = Integer.parseInt(txtAnioDesde.getText());
				int diaHasta= Integer.parseInt(txtDiaHasta.getText());
				int mesHasta = Integer.parseInt(txtMesHasta.getText());
				int anioHasta = Integer.parseInt(txtAnioHasta.getText());
				if(Util.esFechaValida(diaDesde,mesDesde, anioDesde) && Util.esFechaValida(diaHasta,mesHasta, anioHasta)) {
					Calendar fechaDesde = Calendar.getInstance();
					Calendar fechaHasta = Calendar.getInstance();
					fechaDesde.set(Calendar.DAY_OF_MONTH, diaDesde);
					fechaDesde.set(Calendar.MONTH, mesDesde-1);
					fechaDesde.set(Calendar.YEAR, anioDesde);
					
					fechaHasta.set(Calendar.DAY_OF_MONTH, diaHasta);
					fechaHasta.set(Calendar.MONTH, mesHasta-1);
					fechaHasta.set(Calendar.YEAR, anioHasta);
					Venta_model vm = new Venta_model();
					//double recaudacion = GestorDeVentas.getInstancia().getRecaudacion(fechaDesde, fechaHasta);
					double recaudacion = vm.getRecaudacion(fechaDesde, fechaHasta);
					
					if(recaudacion == 0.0) {
						descripcion.setText("NO EXITE RECAUDACION EN ESTA FECHA");
						//JOptionPane.showMessageDialog(this,"NO EXITE RECAUDACION EN ESTA FECHA");
					}else {
						descripcion.setText("LA RECAUDACION ENTRE "+ sdf.format(fechaDesde.getTime())+" Y "+sdf.format(fechaHasta.getTime())+" ES: "+ recaudacion);
						//JOptionPane.showMessageDialog(this, "LA RECAUDACION ENTRE "+ sdf.format(fechaDesde.getTime())+" Y "+sdf.format(fechaHasta.getTime())+" ES: "+ recaudacion);
					}
				}else {
					JOptionPane.showMessageDialog(this, "LA FECHA INGRESADA NO ES VALIDA","Cuidado!",JOptionPane.INFORMATION_MESSAGE);
				}
					
			}else {
				JOptionPane.showMessageDialog(this, "LA FECHA INGRESADA NO ES VALIDA","Cuidado!",JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
		//if(source == volver)
			//dispose();
		if(source == cancelar)
			blanquear();
	}
	
	private JButton iniciarBoton(String texto){
		JButton boton = Componente.getButtonConFormatoAzul(texto);
		boton.addActionListener(this);
		panelBotones.add(boton);
		return boton;
	}
	
	private JTextField iniciarTextField(String msg) {
		JTextField salida = Componente.getTextFieldConFormato();
		salida.setColumns(3);
		salida.setToolTipText(msg);
		salida.addKeyListener(this);
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
	
	public void blanquear() {
		Calendar fecha = Calendar.getInstance();
		txtDiaDesde.setText(""+ fecha.get(Calendar.DAY_OF_MONTH));
		txtMesDesde.setText("" + (fecha.get(Calendar.MONTH)+1));
		txtAnioDesde.setText(""+fecha.get(Calendar.YEAR));
		
		txtDiaHasta.setText(""+ fecha.get(Calendar.DAY_OF_MONTH));
		txtMesHasta.setText("" + (fecha.get(Calendar.MONTH) + 1));
		txtAnioHasta.setText(""+fecha.get(Calendar.YEAR));
		
		descripcion.setText("");
		this.updateUI();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		
		if(e.getSource() == txtDiaDesde) {
			if(!Character.isDigit(c) || txtDiaDesde.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtDiaHasta) {
			if(!Character.isDigit(c) || txtDiaHasta.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtMesDesde) {
			if(!Character.isDigit(c) || txtMesDesde.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtMesHasta) {
			if(!Character.isDigit(c) || txtMesHasta.getText().length() >= 2) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtAnioDesde) {
			if(!Character.isDigit(c) || txtAnioDesde.getText().length() >= 4) {
				e.consume();
			}
		}
		
		if(e.getSource() == txtAnioHasta) {
			if(!Character.isDigit(c) || txtAnioHasta.getText().length() >= 4) {
				e.consume();
			}
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
