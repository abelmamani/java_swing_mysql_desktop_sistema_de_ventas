package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import Modelo.Venta_model;
import dominio.Detalle;
import dominio.GestorDeVentas;
import dominio.Marca;
import dominio.MedioDePago;
import dominio.Producto;
import dominio.TipoDeProducto;
import dominio.Venta;
import exceptions.ExceptionPrecio;
import exceptions.ExceptionProducto;
import utiles.Util;

public class PanelVentas extends JPanel implements ActionListener {
	private JPanel panelHeader;
	private JPanel panelBody;
	private JPanel panelOpciones;
	private BuscarVentas panelBuscador;
	private JPanel panelBotones;
	private JPanel panelTabla;
	private JTable tabla;
	private JScrollPane jsPane;
	
	private JButton botonAgregar;
	//private JButton botonModificar;
	private JButton botonEliminar;
	//private JButton botonDetalle;
	private JButton botonBuscar;
	private ArrayList<Venta> misVentas;
	private RegistrarVenta registrarVenta;
	private PanelDetalles panelDetalles;
	private Principal ventanaPrincipal;
	public PanelVentas(Principal principal){
		ventanaPrincipal = principal;
		registrarVenta = null;
		panelBuscador = new BuscarVentas(this);
	//	detalleVenta = null;
		//modificarVenta = null;
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
		//panelTablaDetalle = new JPanel();
		jsPane = new JScrollPane();
		
		botonAgregar = Componente.getButtonConIcono( new ImageIcon("src/imagenes/nuevaVenta.png"), colorFondo);
		botonEliminar = Componente.getButtonConIcono( new ImageIcon("src/imagenes/anularVenta.png"), colorFondo);
		
		panelHeader.setBackground(colorFondo);
		//panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 10, false));
		panelHeader.setLayout(new FlowLayout());
		panelHeader.add(Componente.getLabelConFormato4("Ventas"));
		//panelBotones.setBackground(new Color(32, 136, 203));
		panelBotones.setBackground(colorFondo);
		panelBotones.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelBotones.setLayout(new GridLayout(1, 4, 2, 2));
		botonAgregar.addActionListener(this);
		panelBotones.add(botonAgregar);
	
		botonEliminar.addActionListener(this);
		panelBotones.add(botonEliminar);
	
		
		panelOpciones.setBackground(colorFondo);
		panelOpciones.setLayout(new GridLayout(2, 1));
		//panelOpciones.setBorder(BorderFactory.createLineBorder(colorFondo, 10, false));
		panelOpciones.add(panelBuscador);
		panelOpciones.add(panelBotones);
		
		
		panelTabla.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelTabla.setLayout(new GridLayout(2,1, 2, 2));
		panelDetalles = new PanelDetalles();
		iniciarTabla();
		//iniciarTablaDetalle(null);
		
		panelBody.setLayout(new BorderLayout());
		panelBody.add(panelOpciones, BorderLayout.NORTH);
		panelBody.add(panelTabla, BorderLayout.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(panelHeader, BorderLayout.NORTH);
		this.add(panelBody, BorderLayout.CENTER);
	}
	
	public void iniciarTabla() {
		//panelBuscador.updateUI();
		obtenerProductos(panelBuscador.getParteCliente(), panelBuscador.getFechaVenta(), panelBuscador.getMedioPago());
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setRowCount(misVentas.size());
		modelo.setColumnCount(5);
		int i = 0;
		for (Venta v : misVentas) {
			modelo.setValueAt(Util.LPAD(v.getNumeroVenta()), i, 0);
			modelo.setValueAt(v.getCliente(), i, 1);
			modelo.setValueAt(v.getFechaVentaConFormato2(), i, 2);
			modelo.setValueAt(v.getMedioDePago(), i, 3);
			modelo.setValueAt("$ " + v.getMontoTotal(), i, 4);
			i = i + 1;
		}
		
		String fila[] = {"N° VENTA", "CLIENTE", "FECHA DE VENTA", "MEDIO DE PAGO", "MONTO TOTAL"};
		modelo.setColumnIdentifiers(fila);
		
		panelTabla.removeAll();
		
		tabla = Componente.getTablaConFormato(modelo);
	
		//jsPane.removeAll();
		//jsPane.add(tabla);
		
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    @Override
		    public void valueChanged(ListSelectionEvent event) {
		        if (tabla.getSelectedRow() > -1) {
		           //JOptionPane.showMessageDialog(tabla, tabla.getValueAt(tabla.getSelectedRow(), 0));
		        	//Venta v = GestorDeVentas.getInstancia().getVenta((int)tabla.getValueAt(tabla.getSelectedRow(), 0));
		        	Venta_model vm = new Venta_model();
		        	Venta v = vm.getVenta(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0)+""));
		        	panelDetalles.iniciarTablaDetalle(v);
		        }
		    }
		});
		
		panelTabla.add(new JScrollPane(tabla));
		panelDetalles.iniciarTablaDetalle(null);
		panelTabla.add(panelDetalles);
		jsPane.updateUI();
		panelTabla.updateUI();
	}
	

//	
	public void obtenerProductos(String cliente, Calendar fecha, MedioDePago medioPago)  {
		misVentas = new ArrayList<Venta>();
		//misVentas = GestorDeVentas.getInstancia().getVentas();
		Venta_model vm = new Venta_model();
		if((cliente != null && cliente.trim().length() != 0) && fecha != null && medioPago != null)
			//misVentas = GestorDeVentas.getInstancia().getVentas(cliente, fecha, medioPago);
			misVentas = vm.getVentas(cliente, medioPago, fecha); 
		else {
			if(cliente != null && cliente.trim().length() != 0) {
				if(fecha != null) {
					//misVentas = GestorDeVentas.getInstancia().getVentas(cliente, fecha);
					misVentas  = vm.getVentas(cliente, fecha);
				}else {
					if(medioPago != null) {
						//misVentas = GestorDeVentas.getInstancia().getVentas(cliente, medioPago);
						misVentas = vm.getVentas(cliente, medioPago);
					}else {
						//misVentas = GestorDeVentas.getInstancia().getVentas(cliente);
						misVentas = vm.getVentas(cliente);
					}
				}
			}else {
				if(fecha != null) {
					if(medioPago != null) {
						//misVentas = GestorDeVentas.getInstancia().getVentas(medioPago, fecha);
						misVentas = vm.getVentas(medioPago, fecha);
					}else {
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						System.out.println(sdf.format(fecha.getTime()));
						//misVentas = GestorDeVentas.getInstancia().getVentas(fecha);
						misVentas = vm.getVentas(fecha);
					}
				}else {
					if(medioPago != null) {
						//misVentas = GestorDeVentas.getInstancia().getVentas(medioPago);
						misVentas = vm.getVentas(medioPago);
					}else {
						//misVentas = GestorDeVentas.getInstancia().getVentas();
						misVentas = vm.getVentas();
					}
				}
			}
		}
		
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botonAgregar) {
			if(registrarVenta == null) 
				this.registrarVenta = new RegistrarVenta(ventanaPrincipal, this, true);
			registrarVenta.blanquear();
			registrarVenta.setVisible(true);
			
			//registrarVenta.transferFocus();
			//JOptionPane.showMessageDialog(this, "esto muestra despues de volver");
		}
		
		if(e.getSource() == botonEliminar) {
			if(puedoTrabajarConLaTabla()) {
				//try {
					//if(GestorDeVentas.getInstancia().deleteVenta((int)tabla.getValueAt(tabla.getSelectedRow(), 0))) {
				ImageIcon icon = new ImageIcon("src/imagenes/pregunta.png");
				int result = JOptionPane.showConfirmDialog(this, 
			                "Esta seguro de que desea anular la venta "+tabla.getValueAt(tabla.getSelectedRow(), 0)+"?" , "Anular", 
			                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, icon);
				if(result == 0) {
					Venta_model vm = new Venta_model();
					if(vm.eliminarVenta(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0) + ""))) {
						iniciarTabla();
						panelDetalles.iniciarTablaDetalle(null);
						ImageIcon icon2 = new ImageIcon("src/imagenes/comprobado.png");
						JOptionPane.showMessageDialog(this, "La venta se Anulo correctamente!","Anular", JOptionPane.INFORMATION_MESSAGE, icon2);
					
							
					}else {
						JOptionPane.showMessageDialog(this, "No se pudo eliminar" );
					}
				}
				//} catch (HeadlessException ) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				//}
			}
		}
		
	}
	
	public int getCodigoDeVenta() {
		if(tabla == null) {
			return -1;
		}else{
			if(tabla.getSelectedRow() < 0 )
				return -1;
			return Integer.parseInt(String.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0)));
			//return (int)tabla.getValueAt(tabla.getSelectedRow(), 0);
		}
	}
	
	public boolean puedoTrabajarConLaTabla() {
		//if(GestorDeVentas.getInstancia().getVentas().size() > 0) {
		Venta_model vm = new Venta_model();
		if(vm.getVentas().size() > 0) {	
			if(getCodigoDeVenta() < 0) {
				JOptionPane.showMessageDialog(this, "Por favor, seleccione una fila!" );
			}else {
				return true;
			}
		}else {
			JOptionPane.showMessageDialog(this, "No hay registros!" );
		}
		return false;
	}
	
	public void cerrarVentanas() {
		if(registrarVenta != null) {
			registrarVenta.dispose();
		}
	}
	
	public void limpiarBuscador() {
		panelBuscador.blanquear();
	}
}
