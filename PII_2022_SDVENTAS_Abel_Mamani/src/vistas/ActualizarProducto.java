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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.Producto;
import dominio.TipoDeProducto;
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

public class ActualizarProducto extends JDialog implements ActionListener, KeyListener{
	private JPanel panelHeader;
	private JPanel panelBody;
	private JPanel panelCodigo;
	private JPanel panelNombre;
	private JPanel panelMarca;
	private JPanel panelTipo;
	private JPanel panelDescripcion;
	private JPanel panelStock;
	private JPanel panelPrecio;
	private JPanel panelCenter;
	private JPanel panelBotones;
	
	private JLabel labelCodigo;
	private JLabel labelNombre;
	private JLabel labelMarca;
	private JLabel labelTipoProducto;
	private JLabel labelDescripcion;
	private JLabel labelStock;
	private JLabel labelPrecio;
	
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JComboBox <String> comboMarca;
	private JComboBox<String> comboTipoProducto;
	private JTextArea textAreaDescripcion;
	private JTextField textFieldStock;
	private JTextField textFieldPrecio;
	
	private JButton botonModificar;
	private JButton botonVolver;
//	private JTable tablita;
//	private JPanel panelTablita;
	private ArrayList<Producto> misProductos;
	private PanelProductos panelProducto; 
	
	public ActualizarProducto(Principal principal, PanelProductos panelProducto, boolean modal){
		super(principal, modal);
		//super("Actualizar Producto");
		this.setSize(820, 500);
		this.setLocationRelativeTo(null);
		this.panelProducto = panelProducto;
		this.setResizable(false);
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
		panelCenter = new JPanel();
		panelCodigo = new JPanel();
		panelNombre = new JPanel();
		panelMarca= new JPanel();
		panelTipo = new JPanel();
		panelStock = new JPanel();
		panelPrecio = new JPanel();
		panelBotones = new JPanel();
		panelDescripcion = new JPanel();
		
		labelCodigo = Componente.getLabelConFormato3("Codigo");
		labelNombre = Componente.getLabelConFormato3("Nombre");
		labelMarca = Componente.getLabelConFormato3("Marca");
		labelTipoProducto = Componente.getLabelConFormato3("Tipo de Producto");
		labelDescripcion = Componente.getLabelConFormato3("Descripcion");
		labelStock = Componente.getLabelConFormato3("Stock");
		labelPrecio = Componente.getLabelConFormato3("Precio");
		
		textFieldCodigo= Componente.getTextFieldConFormato();
		textFieldNombre = Componente.getTextFieldConFormato();
		comboMarca =  new JComboBox<String>();
		comboMarca.setBackground(colorFondo);
		comboTipoProducto = new JComboBox<String>();
		comboTipoProducto.setBackground(colorFondo);
		textAreaDescripcion = Componente.getTextAreaConFormato();
		textFieldStock = Componente.getTextFieldConFormato();
		textFieldStock.addKeyListener(this);
		textFieldPrecio = Componente.getTextFieldConFormato();
		textFieldPrecio.addKeyListener(this);
		botonModificar = Componente.getButtonConFormatoAzul("  ACTUALIZAR  ");
		botonVolver = Componente.getButtonConFormatoAzul("      VOLVER      ");
		cargarDatos();
		
		panelCodigo.setBackground(colorFondo);
		panelCodigo.setLayout(new GridLayout(2, 1));
		panelCodigo.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelCodigo.add(labelCodigo);
		panelCodigo.add(textFieldCodigo);
		
		panelNombre.setBackground(colorFondo);
		panelNombre.setLayout(new GridLayout(2, 1));
		panelNombre.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelNombre.add(labelNombre);
		textFieldCodigo.setText(""+GestorDeVentas.codigoDeProductos);
		textFieldCodigo.setEditable(false);
		panelNombre.add(textFieldNombre);
		
		panelMarca.setBackground(colorFondo);
		panelMarca.setLayout(new GridLayout(2,1));
		panelMarca.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		//panelMarca.setBorder (BorderFactory.createTitledBorder (getLabel("Marca").getText()));
		panelMarca.add(labelMarca);
		panelMarca.add(comboMarca);
		
		panelTipo.setBackground(colorFondo);
		panelTipo.setLayout(new GridLayout(2,1));
		panelTipo.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelTipo.add(labelTipoProducto);
		panelTipo.add(comboTipoProducto);
		
		panelDescripcion.setBackground(colorFondo);
		panelDescripcion.setLayout(new BorderLayout());
		panelDescripcion.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelDescripcion.add(labelDescripcion, BorderLayout.NORTH);
		panelDescripcion.add(new JScrollPane(textAreaDescripcion), BorderLayout.CENTER);
		
		panelStock.setBackground(colorFondo);
		panelStock.setLayout(new GridLayout(2,1));
		panelStock.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelStock.add(labelStock);
		panelStock.add(textFieldStock);
		
		panelPrecio.setBackground(colorFondo);
		panelPrecio.setLayout(new GridLayout(2,1));
		panelPrecio.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelPrecio.add(labelPrecio);
		panelPrecio.add(textFieldPrecio);
		
		panelCenter.setLayout(new GridLayout(3, 2));
		panelCenter.setBackground(colorFondo);
		panelCenter.add(panelCodigo);
		panelCenter.add(panelNombre);
		panelCenter.add(panelMarca);
		panelCenter.add(panelTipo);
		panelCenter.add(panelStock);
		panelCenter.add(panelPrecio);
		
		panelBotones.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelBotones.setLayout(new FlowLayout());
		panelBotones.setBackground(colorFondo);
		botonModificar.addActionListener(this);
		panelBotones.add(botonModificar);
		botonVolver.addActionListener(this);
		panelBotones.add(botonVolver);
		
		panelBody.setLayout(new GridLayout(1, 2));
		panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 30, false));
		panelBody.add(panelCenter);
		panelBody.add(panelDescripcion);
		
		
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10));
		panelHeader.add(Componente.getLabelConFormato4("Actualizar Producto"));
		 
		contenedorPrincipal.setBackground(Color.black);
		contenedorPrincipal.setLayout(new BorderLayout());
		contenedorPrincipal.add(panelHeader, BorderLayout.NORTH);
		contenedorPrincipal.add(panelBody, BorderLayout.CENTER);
		contenedorPrincipal.add(panelBotones, BorderLayout.SOUTH);
		
		
	}
	
	public void cargarDatos(){
		Producto_model pm = new Producto_model();
		if(panelProducto.getCodigoDeProducto() > 0 && pm.getProducto(panelProducto.getCodigoDeProducto()) != null) {
			//Producto producto = GestorDeVentas.getInstancia().getProducto(panelProducto.getCodigoDeProducto());
			Producto producto =pm.getProducto(panelProducto.getCodigoDeProducto());
			
			textFieldCodigo.setText(producto.getCodigoProducto()+"");
			textFieldNombre.setText(producto.getNombre());
			textFieldStock.setText(producto.getStock()+"");
			textFieldPrecio.setText(producto.getPrecioActual()+"");
			textAreaDescripcion.setText(producto.getDescripcion());
			cargarComboBoxMarca(producto.getMarca());
			cargarComboBoxTipoProducto(producto.getTipoProducto());
			
			panelBody.updateUI();
		}else {
			JOptionPane.showMessageDialog(this, "hubo un error al mostrar datos");
		}
	}
	
	private void cargarComboBoxMarca(Marca m){
		int index = 0;
		for(Marca marca : Marca.values()) { 
			comboMarca.addItem(marca.name()); 
			if(marca.name().equals(m.name())) {
				comboMarca.setSelectedIndex(index);
			}
			index++;
		}
	}
	
	private void cargarComboBoxTipoProducto(TipoDeProducto t){
		int index = 0;
		for(TipoDeProducto tipo: TipoDeProducto.values()) { 
		    comboTipoProducto.addItem(tipo.name());
		    if(tipo.name().equals(t.name())) {
				comboTipoProducto.setSelectedIndex(index);
			}
		    index++;
		}
	}
	
	private Marca getMarca(){
			return Marca.valueOf(comboMarca.getSelectedItem().toString());
	}
	
	private TipoDeProducto getTipoProducto(){
			return TipoDeProducto.valueOf(comboTipoProducto.getSelectedItem().toString());
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
	
	private boolean validarDouble(String text) {
		try {
			@SuppressWarnings("unused")
			double aux = Double.parseDouble(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		Producto_model pm = new Producto_model();
		if(source == botonModificar){
			//GestorDeVentas gv = GestorDeVentas.getInstancia();
			if(!validarEntero(textFieldStock.getText()))
						JOptionPane.showMessageDialog(this, "El stock ingresado no es valido!","Cuidado", JOptionPane.INFORMATION_MESSAGE);
			else {
				if(!validarDouble(textFieldPrecio.getText()))
					JOptionPane.showMessageDialog(this, "El Precio ingresado no es valido!","Cuidado", JOptionPane.INFORMATION_MESSAGE);
				else {
					
					
					try {
					
						if(pm.modificarProducto(Integer.parseInt(textFieldCodigo.getText()), textFieldNombre.getText(), getMarca().name(), getTipoProducto().name(), textAreaDescripcion.getText(), Integer.parseInt(textFieldStock.getText()), Double.parseDouble(textFieldPrecio.getText()))) {
								panelProducto.iniciarTabla();
								JOptionPane.showMessageDialog(this,"Se actualizo correctamente!!!");
								this.dispose();
							}else {
								JOptionPane.showMessageDialog(this, "El producto no se encuentra Cargado!","Cuidado",JOptionPane.WARNING_MESSAGE);
							}
						
						}catch (ExceptionProducto ex) {
							JOptionPane.showMessageDialog(this, ex.getMessage(),"Cuidado",JOptionPane.INFORMATION_MESSAGE);
						}catch (ExceptionPrecioNegativo ex2) {
							JOptionPane.showMessageDialog(this, ex2.getMessage(),"Cuidado",JOptionPane.INFORMATION_MESSAGE);
						}catch(Exception ex3) {
							ex3.printStackTrace();
						}
					
				}
			}
		}
	
		if(source == botonVolver)
			this.dispose();;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if(!Character.isDigit(c)) {
			e.consume();
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
	
//	public static void main(String[] args) {
//		PanelProductos p = new PanelProductos();
//		ActualizarProducto a = new ActualizarProducto(p);
//		a.setVisible(true);
//	}
	
	
	
}
