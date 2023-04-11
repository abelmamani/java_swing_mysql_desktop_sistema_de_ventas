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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Modelo.Producto_model;
import Modelo.Venta_model;
import dominio.Detalle;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionDetalle;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionPrecioCero;
import exceptions.ExceptionPrecioNegativo;
import exceptions.ExceptionProducto;
import exceptions.ExceptionProductoDescripcionNulo;
import exceptions.ExceptionProductoDescripcionVacio;
import exceptions.ExceptionProductoMarcaNulo;
import exceptions.ExceptionProductoNombreNulo;
import exceptions.ExceptionProductoNombreVacio;
import exceptions.ExceptionProductoStockNegativo;
import exceptions.ExceptionProductoTipoDeProductoNulo;
import exceptions.ExceptionVenta;
import exceptions.ExceptionVentaClienteNulo;
import exceptions.ExceptionVentaClienteNumero;
import exceptions.ExceptionVentaClienteVacio;
import exceptions.ExceptionVentaFechaNulo;
import exceptions.ExceptionVentaMedioDePagoNulo;
import exceptions.ExceptionVentaNumeroNegativo;

public class RegistrarVenta extends JDialog implements ActionListener, KeyListener{
	private JPanel panelHeader;
	private JPanel panelBody;
	//private JPanel panelCodigo;
	private JPanel panelCliente;
	private JPanel panelFecha;
	private JPanel panelMedioPago;
	private JPanel panelMontoTotal;
	private JPanel headerDetalle;
	private JPanel panelCodigoProducto;
	private JPanel panelCantidadProducto;
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelBotones;
	
	private JLabel labelCodigo;
	private JLabel labelFecha;
	private JLabel labelCliente;
	private JLabel labelMedioPago;
	private JLabel labelMontoTotal;
	private JLabel labelDetalle;
	private JLabel labelCantidad;
	
	//private JTextField textFieldCodigo;
	private JTextField textFieldFecha;
	private JTextField textFieldCliente;
	private JComboBox <String> comboMedioPago;
	private JTextField textFieldMontoTotal;
	private JTextField textFieldCantidadDetalle;
	private JTextField textFieldCodigoProducto;
	private JButton botonRegistrar;
	private JButton botonCancelar;
	private JButton botonVolver;
	private JButton botonAddDetalle;
	private JButton botonRemoveDetalle;
//	private JTable tablita;
//	private JPanel panelTablita;
	private ArrayList<Producto> misProductos;
	private PanelVentas panelVentas; 
	private Venta miVenta;
	private PanelDetalles bodyDetalle;
	
	public RegistrarVenta(Principal principal, PanelVentas panelVentas, boolean modal){
		
		//super("Registrar Producto");
		super(principal, modal);
		this.setSize(820, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.panelVentas = panelVentas;
		//this.miVenta = new Venta();
//		this.tablita = t;
//		this.panelTablita = pt;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		agregarComponentes();
		
	}
	
	private void agregarComponentes(){
		Container contenedorPrincipal = getContentPane();
		Color colorFondo = Color.white;
		
		panelHeader = new JPanel();
		panelBody = new JPanel();
		panelIzquierdo = new JPanel();
		//panelCodigo = new JPanel();
		panelCliente = new JPanel();
		panelFecha= new JPanel();
		panelMedioPago = new JPanel();
		panelMontoTotal = new JPanel();
		headerDetalle = new JPanel();
		panelCodigoProducto = new JPanel();
		panelCantidadProducto = new JPanel();
		panelDerecho = new JPanel();
		panelBotones = new JPanel();
		bodyDetalle = new PanelDetalles();
		
		//labelCodigo = Componente.getLabelConFormato3("Codigo venta");

		labelFecha = Componente.getLabelConFormato3("Fecha");
		labelCliente = Componente.getLabelConFormato3("Cliente");
		labelMedioPago = Componente.getLabelConFormato3("Medio de pago");
		labelMontoTotal = Componente.getLabelConFormato3("Monto total");
		labelDetalle = Componente.getLabelConFormato3("Detalle de venta");
		labelCantidad = Componente.getLabelConFormato3("Cantidad");
		labelCodigo = Componente.getLabelConFormato3("Codigo Producto");
		
		//textFieldCodigo= Componente.getTextFieldConFormato();
		textFieldFecha = Componente.getTextFieldConFormato();
		textFieldCliente = Componente.getTextFieldConFormato();
		textFieldCliente.addKeyListener(this);
		textFieldCliente.setToolTipText("ingrese nombre del cliente (sin numeros)");
		textFieldMontoTotal = Componente.getTextFieldConFormato();
		comboMedioPago =  new JComboBox<String>();
		comboMedioPago.setBackground(colorFondo);
		cargarComboBoxMedioPago();
		textFieldCantidadDetalle = Componente.getTextFieldConFormato();
		textFieldCantidadDetalle.addKeyListener(this);
		textFieldCantidadDetalle.setToolTipText("ingrese cantidad de producto");
		textFieldCodigoProducto= Componente.getTextFieldConFormato();
		textFieldCodigoProducto.addKeyListener(this);
		textFieldCodigoProducto.setToolTipText("ingrese un codigo de producto");
		botonRegistrar = Componente.getButtonConFormatoAzul(" REGISTRAR ");
		botonCancelar = Componente.getButtonConFormatoAzul(" CANCELAR ");
		botonVolver = Componente.getButtonConFormatoAzul("  VOLVER  ");
		
		botonAddDetalle =  Componente.getButtonConIcono(new ImageIcon("src/imagenes/nuevo.png"), colorFondo);
		
		//botonRemoveDetalle = Componente.getButtonConFormato4("ELIMINAR");
		botonRemoveDetalle = Componente.getButtonConIcono(new ImageIcon("src/imagenes/eliminar.png"), colorFondo);
		
		
		
		
		//textFieldCodigo.setText(""+GestorDeVentas.codigoDeVentas);
		//textFieldCodigo.setEditable(false);
		
		iniciarGrupo(panelFecha, textFieldFecha, labelFecha, colorFondo);
		Calendar f = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			
		textFieldFecha.setText(""+ sdf.format(f.getTime()));
		textFieldFecha.setEditable(false);
		panelFecha.add(textFieldFecha);
		
		iniciarGrupo(panelCliente, textFieldCliente, labelCliente, colorFondo);
		panelMedioPago.setBackground(colorFondo);
		panelMedioPago.setLayout(new GridLayout(2,1));
		panelMedioPago.setBorder(BorderFactory.createLineBorder(colorFondo, 2, true));
		panelMedioPago.add(labelMedioPago);
		panelMedioPago.add(comboMedioPago);
		iniciarGrupo(panelMontoTotal, textFieldMontoTotal, labelMontoTotal, colorFondo);
		iniciarGrupo(panelCodigoProducto, textFieldCodigoProducto, labelCodigo, colorFondo);
		iniciarGrupo(panelCantidadProducto, textFieldCantidadDetalle, labelCantidad, colorFondo);
		
		
		headerDetalle.setBackground(colorFondo);
		headerDetalle.setLayout(new GridLayout(1, 4, 0, 10));
		//headerDetalle.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		botonAddDetalle.addActionListener(this);
		headerDetalle.add(botonAddDetalle);
		botonRemoveDetalle.addActionListener(this);
		//botonRemoveDetalle.setBorder(BorderFactory.createLineBorder(colorFondo, 2, false));
		headerDetalle.add(botonRemoveDetalle);
		//botonAddDetalle.setBorder(BorderFactory.createLineBorder(colorFondo, 2, false));
		panelCodigoProducto.setBackground(colorFondo);
		headerDetalle.add(panelCodigoProducto);
		panelCantidadProducto.setBackground(colorFondo);
		headerDetalle.add(panelCantidadProducto);
		//headerDetalle.add(panelMontoTotal);
	
		panelDerecho.setBackground(colorFondo);
		panelDerecho.setLayout(new BorderLayout());
		panelDerecho.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelDerecho.add(headerDetalle, BorderLayout.NORTH);
		panelDerecho.add(bodyDetalle, BorderLayout.CENTER);
		
		panelIzquierdo.setBackground(colorFondo);
		panelIzquierdo.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelIzquierdo.setLayout(new GridLayout(4, 1));
		//panelIzquierdo.add(panelCodigo);
		panelIzquierdo.add(panelFecha);
		panelIzquierdo.add(panelCliente);
		panelIzquierdo.add(panelMedioPago);
		textFieldMontoTotal.setText("0.0");
		textFieldMontoTotal.setEditable(false);
		panelIzquierdo.add(panelMontoTotal);

		panelBotones.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(colorFondo);
		botonRegistrar.addActionListener(this);
		panelBotones.add(botonRegistrar);
		botonCancelar.addActionListener(this);
		panelBotones.add(botonCancelar);
		botonVolver.addActionListener(this);
		panelBotones.add(botonVolver);
		
		panelBody.setLayout(new BorderLayout());
		panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelBody.add(panelIzquierdo,  BorderLayout.EAST);
		panelBody.add(panelDerecho, BorderLayout.CENTER);
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10));
		panelHeader.add(Componente.getLabelConFormato4("Registrar venta"));
		 
		contenedorPrincipal.setBackground(colorFondo);
		contenedorPrincipal.setLayout(new BorderLayout());
		contenedorPrincipal.add(panelHeader, BorderLayout.NORTH);
		contenedorPrincipal.add(panelBody, BorderLayout.CENTER);
		contenedorPrincipal.add(panelBotones, BorderLayout.SOUTH);
		
		
	}
	
	private void iniciarGrupo(JPanel panel, JTextField txt, JLabel label, Color color) {
		panel.setBackground(Color.white);
		panel.setLayout(new GridLayout(2,1));
		panel.setBorder(BorderFactory.createLineBorder(color, 2, true));
		panel.add(label);
		panel.add(txt);
	}
	
	private void cargarComboBoxMedioPago(){
		comboMedioPago.addItem("Seleccionar");
		for(MedioDePago medio: MedioDePago.values()) { 
		    comboMedioPago.addItem(medio.name()); 
		}
	}
	
	private MedioDePago getMedioPago(){
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
			if(aux > 0)
				return true;
			else
				return false;
		}catch(Exception e){
			return false;
		}
	}
	
	private boolean validarDouble(String text) {
		try {
			@SuppressWarnings("unused")
			double aux = Double.parseDouble(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void blanquear(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		//textFieldCodigo.setText(""+GestorDeVentas.codigoDeVentas);
		Calendar f = Calendar.getInstance();
		textFieldFecha.setText(""+ sdf.format(f.getTime()));
		textFieldCliente.setText("");
		textFieldMontoTotal.setText("");
		textFieldCodigoProducto.setText("");
		textFieldCantidadDetalle.setText("");
		comboMedioPago.setSelectedIndex(0);
		textFieldMontoTotal.setText("0.0");
		miVenta = new Venta();
		bodyDetalle.iniciarTablaDetalle(miVenta);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source == botonRegistrar){
			if(validarString(textFieldCliente.getText())) {
				if(miVenta.getCantidadDeDetalles() > 0) {
					GestorDeVentas gv = GestorDeVentas.getInstancia();
					Venta_model vm = new Venta_model();
					try {
						Venta v = new Venta(gv.codigoDeVentas, textFieldCliente.getText(), Calendar.getInstance(), getMedioPago());
						v.setMisDetalles(miVenta.getMisDetalles());
						v.setMontoTotal(miVenta.getMontoTotal());
						//boolean exito = gv.addVenta(v);
						boolean exito = vm.registrarVenta(v);
						if(exito) {
						//blanquear();
						bodyDetalle.iniciarTablaDetalle(null);
						panelVentas.iniciarTabla();
//						
						this.dispose();
						//JOptionPane.showMessageDialog(this, "se registro la venta");
						
						}else {
							JOptionPane.showMessageDialog(this, "la venta ya esta cargado","Cuidado",JOptionPane.INFORMATION_MESSAGE);
						}
					}catch(ExceptionVenta ex) {
						JOptionPane.showMessageDialog(this, ex.getMessage(), "Cuidado",JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}else {
					JOptionPane.showMessageDialog(this, "No puede registrar una venta con monto total igual a $0","Cuidado",JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this, "El nombre del cliente no pude contener dijitos!","Cuidado",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		
		if(source == botonAddDetalle) {
			String codigoProducto = textFieldCodigoProducto.getText();
			String cantidadProducto = textFieldCantidadDetalle.getText();
			if(!validarEntero(codigoProducto)) {
				JOptionPane.showMessageDialog(this, "El codigo de producto debe ser un numero entero", "Cuidado",JOptionPane.INFORMATION_MESSAGE);
			}else {
				if(!validarEntero(cantidadProducto)) {
					JOptionPane.showMessageDialog(this, "Ingrese una cantidad valida!", "Cuidado",JOptionPane.INFORMATION_MESSAGE);
				}else {
					Producto_model pm = new  Producto_model();
					//Producto p =  GestorDeVentas.getInstancia().getProducto(Integer.parseInt(codigoProducto));
					Producto p = pm.getProducto(Integer.parseInt(codigoProducto));
					if(p == null) {
						JOptionPane.showMessageDialog(this, "El producto no se encuentra registrado!","Cuidado",JOptionPane.INFORMATION_MESSAGE);
					}else {
						if(p.getStock() >= Integer.parseInt(cantidadProducto)) {
							double total = Double.parseDouble(textFieldMontoTotal.getText());
							int cantidad = Integer.parseInt(cantidadProducto);
							double precio = p.getPrecioActual();
							Detalle detalle = null;
							try{
								detalle = new Detalle(Integer.parseInt(codigoProducto), p.getPrecioActual(), Integer.parseInt(cantidadProducto));
							}catch(ExceptionDetalle ex) {
								JOptionPane.showMessageDialog(this, ex.getMessage(),"Cuidado",JOptionPane.WARNING_MESSAGE);
								
							}
							boolean exito = miVenta.addDetalle(detalle);
							if(exito) {
								miVenta.addDetalle(detalle);
								//total = total + (cantidad*precio);
								total = miVenta.calcularMontoTotal();
								textFieldMontoTotal.setText(total+"");
								miVenta.setMontoTotal(total);
								
								bodyDetalle.iniciarTablaDetalle(miVenta);
								//panelVentas.iniciarTabla(null, null, null);
								//p.setStock(p.getStock() - Integer.parseInt(cantidadProducto));
								
								
							}else {
								JOptionPane.showMessageDialog(this, "Producto ya cargado!!!","Cuidado",JOptionPane.WARNING_MESSAGE);
								
							}
						}else {
							JOptionPane.showMessageDialog(this, "No hay suficiente stock","Stock",JOptionPane.INFORMATION_MESSAGE);
							
						}
						
					}
				}
			}
			
			
		}
		
		if(source == botonRemoveDetalle) {
		
			if(bodyDetalle.puedoTrabajarConLaTabla()) {
				double total = Double.parseDouble(textFieldMontoTotal.getText());
				int cantidad = bodyDetalle.getCantidadDeProducto();
				double precio = bodyDetalle.getPrecioDeProducto();
				boolean exito = miVenta.removeDetalle(bodyDetalle.getCodigoDeProducto());
				if(exito) {
					total = miVenta.calcularMontoTotal();
					miVenta.setMontoTotal(total);
					textFieldMontoTotal.setText(total+"");
					bodyDetalle.iniciarTablaDetalle(miVenta);
				
				}else {
					JOptionPane.showMessageDialog(this, "Hubo un error, No se pudo eliminar" );
				}
				
			}
		}
		//if(source == volver)
		//	dispose();
		
		
		if(source == botonCancelar)
			blanquear();
		if(source == botonVolver) {
			blanquear();
			this.dispose();;
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(e.getSource() == textFieldCliente) {
			if(!Character.isAlphabetic(c) && c != ' ') {
				e.consume();
			}
		}
		
		if(e.getSource() == textFieldCodigoProducto || e.getSource() == textFieldCantidadDetalle) {
			if(!Character.isDigit(c)) {
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
