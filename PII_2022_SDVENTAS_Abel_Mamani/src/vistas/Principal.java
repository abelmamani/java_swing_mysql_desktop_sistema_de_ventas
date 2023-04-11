package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import dominio.Producto;



public class Principal extends JFrame implements ActionListener {
	
	private JPanel panelNavbar;
	private JPanel panelImagen;
	private JPanel panelMenu;
	private JPanel panelContenido;

	private JButton botonVentas;
	private JButton botonProductos;
	private JButton botonMarcas;
	private JButton botonTipos;
	private JButton botonRecaudacion;
	private JButton botonSalir;
	
	private PanelProductos panelProductos;
	private PanelVentas panelVentas;
	private PanelRecaudacion panelRecaudacion;
	private PanelMarcas panelMarcas;
	private PanelTipos panelTipos;
	

	
	public Principal(){
		super("SISTEMA DE VENTAS");
		
		this.setSize(1280, 720);
		this.setLocation(0, 0);;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		agregarComponentes();
	}
	
	private void agregarComponentes(){
		Container contenedorPrincipal = getContentPane();
		
		panelProductos = new PanelProductos(this);
		panelVentas = new PanelVentas(this);
		panelRecaudacion = new PanelRecaudacion();
		panelMarcas = new PanelMarcas();
		panelTipos = new PanelTipos();
		
		panelNavbar = new JPanel();
		panelImagen = new JPanel();
		panelMenu = new JPanel();
		panelContenido = new JPanel();
		
		botonVentas = Componente.getButtonConFormato("VENTAS");
		botonProductos = Componente.getButtonConFormato("PRODUCTOS");
		botonMarcas = Componente.getButtonConFormato("MARCAS");
		botonTipos = Componente.getButtonConFormato("TIPO DE PRODUCTO");
		botonRecaudacion= Componente.getButtonConFormato("RECAUDACION");
		botonSalir= Componente.getButtonConFormato("SALIR");
		
		Color colorNavbar = new Color(64, 64, 64);
		Color colorNavbar2 = new Color(96, 96, 96);
	
		panelImagen.setBackground(colorNavbar2);
		panelImagen.setLayout(new BorderLayout());
		JLabel etiquetaIcono = new JLabel();
		etiquetaIcono.setHorizontalAlignment(JLabel.CENTER);
		etiquetaIcono.setIcon(new ImageIcon("src/imagenes/user100x100.png"));
		panelImagen.setBorder(BorderFactory.createLineBorder(colorNavbar2, 50, false));
		panelImagen.add(Componente.getLabelConFormato("Programacion II"), BorderLayout.NORTH);
		panelImagen.add(etiquetaIcono, BorderLayout.CENTER);
		panelImagen.add(Componente.getLabelConFormato("Abel Mamani"), BorderLayout.SOUTH);
		
		panelMenu.setBackground(colorNavbar2);
		panelMenu.setBorder(BorderFactory.createLineBorder(colorNavbar2, 2, false));
		panelMenu.setLayout(new GridLayout(7, 1, 10, 10));
		iniciarBoton(panelMenu, "src/imagenes/ventas.png", botonVentas);
		iniciarBoton(panelMenu, "src/imagenes/monitor.png", botonProductos);
		iniciarBoton(panelMenu, "src/imagenes/cliente.png", botonMarcas);
		iniciarBoton(panelMenu, "src/imagenes/informe.png", botonTipos);
		iniciarBoton(panelMenu, "src/imagenes/donacion.png", botonRecaudacion);
		iniciarBoton(panelMenu, "src/imagenes/salir.png", botonSalir);
	
		
		panelNavbar.setLayout(new BorderLayout());
		panelNavbar.add(panelImagen, BorderLayout.NORTH);
		panelNavbar.add(panelMenu, BorderLayout.CENTER);
		
		
		//PanelProductos panelProductos = new PanelProductos(this);
		panelContenido.setLayout(new GridLayout(1, 1));
		panelContenido.add(panelProductos);
		//contenedorPrincipal.setBackground(Color.black);
		contenedorPrincipal.setLayout(new BorderLayout());
		contenedorPrincipal.add(panelNavbar, BorderLayout.WEST);
		contenedorPrincipal.add(panelContenido, BorderLayout.CENTER);
		
		
	}
	
	private void iniciarBoton(JPanel panel, String imagen, JButton boton) {
		boton.setIcon(new ImageIcon(imagen));
		boton.addActionListener(this);
		panel.add(boton);
	}
	
	public static void main(String args[]){
		JFrame ventanaPrincipal = new Principal();
		ventanaPrincipal.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botonProductos) {
			panelRecaudacion.blanquear();
			panelVentas.cerrarVentanas();
			
			panelContenido.removeAll();
			panelProductos.limpiarBuscador();
			panelProductos.iniciarTabla();
			panelContenido.add(panelProductos);
			panelContenido.updateUI();
			
		}
		
		if(e.getSource() == botonVentas) {
			panelRecaudacion.blanquear();
			panelProductos.cerrarVentanas(true, true, true);
			
			panelContenido.removeAll();
			panelContenido.add(panelVentas);
			panelVentas.limpiarBuscador();
			panelVentas.iniciarTabla();
			panelContenido.updateUI();
			
		}
		
		if(e.getSource() == botonMarcas) {
			panelProductos.cerrarVentanas(true, true, true);
			panelVentas.cerrarVentanas();
			panelRecaudacion.blanquear();
			
			panelContenido.removeAll();
			panelContenido.add(panelMarcas);
			panelContenido.updateUI();
		}
		
		if(e.getSource() == botonTipos) {
			panelProductos.cerrarVentanas(true, true, true);
			panelVentas.cerrarVentanas();
			panelRecaudacion.blanquear();
			
			panelContenido.removeAll();
			panelContenido.add(panelTipos);
			panelContenido.updateUI();
		}
		
		if(e.getSource() == botonRecaudacion) {
			
		
			panelProductos.cerrarVentanas(true, true, true);
			panelVentas.cerrarVentanas();
	
			panelContenido.removeAll();
			panelRecaudacion.blanquear();
			panelContenido.add(panelRecaudacion);
			panelContenido.updateUI();
		
		}
		
		if(e.getSource() == botonSalir) {
			System.exit(0);
		}
		
	}
	
}
