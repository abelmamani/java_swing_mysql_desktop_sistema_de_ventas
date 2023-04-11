package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import dominio.Precio;
import dominio.Producto;
import dominio.TipoDeProducto;

public class DetalleProducto extends JDialog implements ActionListener{
	private JPanel panelHeader;
	private JPanel panelBody;
	private JPanel panelCodigo;
	private JPanel panelNombre;
	private JPanel panelMarca;
	private JPanel panelTipo;
	private JPanel panelDescripcion;
	private JPanel panelStock;
	private JPanel panelPrecio;
	private JPanel panelTabla;
	private JPanel panelWest;
	private JPanel panelCenter;
	private JPanel panelSouth;
	
	private JLabel labelCodigo;
	private JLabel labelNombre;
	private JLabel labelMarca;
	private JLabel labelTipoProducto;
	private JLabel labelDescripcion;
	private JLabel labelStock;
	private JLabel labelPrecio;
	
	private JTextField textFieldCodigo;
	private JTextField textFieldNombre;
	private JTextField textFieldMarca;
	private JTextField textFieldTipoProducto;
	private JTextArea textAreaDescripcion;
	private JTextField textFieldStock;
	private JTextField textFieldPrecio;
	private JTable tabla;

	private JButton botonVolver;

	private PanelProductos panelProducto; 
	
	public DetalleProducto(Principal principal, PanelProductos panelProducto, boolean modal){
		
		//super("Detalle de producto");
		super(principal, modal);
		this.setSize(820, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.panelProducto = panelProducto;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		agregarComponentes();
		
	}
	
	private void agregarComponentes(){
		Container contenedorPrincipal = getContentPane();
		Color colorFondo = Color.white;
		
		panelHeader = new JPanel();
		panelBody = new JPanel();
		panelCenter = new JPanel();
		panelWest = new JPanel();
		panelSouth = new JPanel();
		panelCodigo = new JPanel();
		panelNombre = new JPanel();
		panelMarca= new JPanel();
		panelTipo = new JPanel();
		panelStock = new JPanel();
		panelPrecio = new JPanel();
		panelDescripcion = new JPanel();
		panelTabla = new JPanel();
		tabla = new JTable();
	
		
		labelCodigo = Componente.getLabelConFormato3("Codigo");
		labelNombre = Componente.getLabelConFormato3("Nombre");
		labelMarca = Componente.getLabelConFormato3("Marca");
		labelTipoProducto = Componente.getLabelConFormato3("Tipo de Producto");
		labelDescripcion = Componente.getLabelConFormato3("Descripcion");
		labelStock = Componente.getLabelConFormato3("Stock");
		labelPrecio = Componente.getLabelConFormato3("Precio $");
		
		textFieldCodigo= Componente.getTextFieldConFormato();
		textFieldNombre = Componente.getTextFieldConFormato();
		textFieldMarca = Componente.getTextFieldConFormato();
		textFieldTipoProducto = Componente.getTextFieldConFormato();
		textAreaDescripcion = Componente.getTextAreaConFormato();
		textFieldStock = Componente.getTextFieldConFormato();
		textFieldPrecio = Componente.getTextFieldConFormato();
		botonVolver = Componente.getButtonConFormatoAzul("     VOLVER     ");
		
		
		
		
		
		panelTabla.setBorder(BorderFactory.createLineBorder(colorFondo, 10, false));
		panelTabla.setLayout(new GridLayout(1,1));
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
		panelMarca.add(textFieldMarca);
		
		
		panelTipo.setBackground(colorFondo);
		panelTipo.setLayout(new GridLayout(2,1));
		panelTipo.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelTipo.add(labelTipoProducto);
		panelTipo.add(textFieldTipoProducto);
		
		panelDescripcion.setBackground(colorFondo);
		panelDescripcion.setLayout(new BorderLayout());
		panelDescripcion.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelDescripcion.add(labelDescripcion, BorderLayout.NORTH);
		panelDescripcion.add(new JScrollPane(textAreaDescripcion), BorderLayout.CENTER);
		panelDescripcion.add(Componente.getLabelConFormato3("Precios"), BorderLayout.SOUTH);
		
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
		
		panelCenter.setLayout(new GridLayout(2, 1));
		panelCenter.add(panelDescripcion);
		panelCenter.add(panelTabla);
		
		panelWest.setBackground(colorFondo);
		panelWest.setLayout(new GridLayout(3, 2));
		panelWest.add(panelCodigo);
		panelWest.add(panelNombre);
		panelWest.add(panelMarca);
		panelWest.add(panelTipo);
		panelWest.add(panelStock);
		panelWest.add(panelPrecio);
		
		panelSouth.setBorder(BorderFactory.createLineBorder(colorFondo, 10, true));
		panelSouth.setLayout(new FlowLayout());
		panelSouth.setBackground(colorFondo);
		botonVolver.addActionListener(this);
		panelSouth.add(botonVolver);
		
		panelBody.setLayout(new GridLayout(1,2));
		panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 30, false));
		panelBody.add(panelWest);
		panelBody.add(panelCenter);
		
		
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10));
		panelHeader.add(Componente.getLabelConFormato4("Detalle de Producto"));
		 
		contenedorPrincipal.setBackground(Color.black);
		contenedorPrincipal.setLayout(new BorderLayout());
		contenedorPrincipal.add(panelHeader, BorderLayout.NORTH);
		contenedorPrincipal.add(panelBody, BorderLayout.CENTER);
		contenedorPrincipal.add(panelSouth, BorderLayout.SOUTH);
		
		
	}
	
	public void iniciarTabla(ArrayList<Precio> precios, double precioActual) {
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setRowCount(precios.size());
		modelo.setColumnCount(3);
		int i = 0;
		for (Precio p : precios) {
			modelo.setValueAt(i+1,i, 0);
			modelo.setValueAt("$ " + p.getValor(), i, 1);
			if(p.getFechaHastaConFormato2() == "-") {
				modelo.setValueAt("Actual", i, 2);
			}else {
				modelo.setValueAt(p.getFechaHastaConFormato2(), i, 2);
			}
			
		
			i = i + 1;
		}
		
		String fila[] = {"N", "valor", "Fecha"};
		modelo.setColumnIdentifiers(fila);
		tabla = Componente.getTablaConFormato(modelo);
		panelTabla.removeAll();
		panelTabla.add(new JScrollPane(tabla));
		panelTabla.updateUI();
	}
	public void cargarDatos(){
		//if(panelProducto.getCodigoDeProducto() > 0 && GestorDeVentas.getInstancia().getProducto(panelProducto.getCodigoDeProducto()) != null) {
		Producto_model pm = new Producto_model();
		if(panelProducto.getCodigoDeProducto() > 0 && pm.getProducto(panelProducto.getCodigoDeProducto()) != null) {
			//Producto producto = GestorDeVentas.getInstancia().getProducto(panelProducto.getCodigoDeProducto());
			Producto producto = pm.getProducto(panelProducto.getCodigoDeProducto());
			textFieldCodigo.setText(producto.getCodigoProducto()+"");
			textFieldCodigo.setEditable(false);
			textFieldNombre.setText(producto.getNombre());
			textFieldNombre.setEditable(false);
			textFieldStock.setText(producto.getStock()+"");
			textFieldStock.setEditable(false);
			textFieldPrecio.setText(producto.getPrecioActual()+"");
			textFieldPrecio.setEditable(false);
			textAreaDescripcion.setText(producto.getDescripcion());
			textAreaDescripcion.setEditable(false);
			textFieldMarca.setText(producto.getMarca()+"");
			textFieldMarca.setEditable(false);
			textFieldTipoProducto.setText(producto.getTipoProducto()+"");
			textFieldTipoProducto.setEditable(false);
			iniciarTabla( producto.getMisPrecios(), producto.getPrecioActual());
			panelBody.updateUI();
		}else {
			JOptionPane.showMessageDialog(this, "hubo un error al mostrar datos");
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botonVolver)
			this.dispose();
	}
//	public static void main(String[] args) {
//		PanelProductos panelProducto = new PanelProductos();
//		DetalleProducto d = new DetalleProducto(panelProducto);
//		d.setVisible(true);
//	}
}
