package vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dominio.Marca;
import dominio.TipoDeProducto;

public class PanelTipos extends JPanel{
	private JPanel panelHeader;
	private JPanel panelBody;
	private JScrollPane scroll;
	
	public PanelTipos() {
		agregarComponentes();
	}
	
	private void agregarComponentes() {
		Color colorFondo = Color.white;
		//PANEL BOTONES
		panelHeader = new JPanel();
		panelBody = new JPanel();
		
		
		panelBody.setBackground(colorFondo);
		panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		cargarTipos(panelBody);
		
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelHeader.add(Componente.getLabelConFormato4("Tipos de productos"));
		
		//scroll.setBackground(colorFondo);
		scroll = new JScrollPane(panelBody);	
		scroll.setBackground(colorFondo);
		scroll.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		
		this.setBackground(colorFondo);
		this.setLayout(new BorderLayout());
		this.add(panelHeader,BorderLayout.NORTH);
		this.add(scroll,BorderLayout.CENTER);
		
		this.updateUI();
	}
	
	public void cargarTipos(JPanel panel) {
		int filas = 0;
		//System.out.println("tamanio: " + TipoDeProducto.values().length + " filas: "+ TipoDeProducto.values().length / 4);
		filas = TipoDeProducto.values().length / 4;
		filas = filas + 1;
		panel.setLayout(new GridLayout(filas, 4, 10, 10));
		
		for(TipoDeProducto tipo : TipoDeProducto.values()) { 
			JPanel card = new JPanel();
			card.setBackground(new Color(244, 246, 247 ));
			card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
			card.add(Componente.getLabelConFormato3(tipo.name()));
			
			panel.add(card); 
		}
		
		
	}
}
