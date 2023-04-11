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

public class PanelMarcas extends JPanel{
	private JPanel panelHeader;
	private JPanel panelBody;
	private JScrollPane scroll;
	
	public PanelMarcas() {
		agregarComponentes();
	}
	
	private void agregarComponentes() {
		Color colorFondo = Color.white;
		//PANEL BOTONES
		panelHeader = new JPanel();
		panelBody = new JPanel();
		
		
		panelBody.setBackground(colorFondo);
		panelBody.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		cargarMarcas(panelBody);
		
		panelHeader.setBackground(colorFondo);
		panelHeader.setLayout(new FlowLayout());
		panelHeader.setBorder(BorderFactory.createLineBorder(colorFondo, 20, false));
		panelHeader.add(Componente.getLabelConFormato4("Marcas"));
		
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
	
	public void cargarMarcas(JPanel panel) {
		int filas = 0;
		filas = Marca.values().length / 4;
		filas  = filas +1;
		panel.setLayout(new GridLayout(filas, 4, 10, 10));
		for(Marca marca : Marca.values()) { 
			JPanel card = new JPanel();
			card.setBackground(new Color(244, 246, 247 ));
			card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
			card.add(Componente.getLabelConFormato3(marca.name()));
			
			panel.add(card); 
		}
		
		//panel.setLayout(new GridLayout());
	}
}
