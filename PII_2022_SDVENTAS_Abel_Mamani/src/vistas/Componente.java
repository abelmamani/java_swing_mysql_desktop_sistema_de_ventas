package vistas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

public class Componente {
	
	public static JButton getButtonConIcono(ImageIcon icon, Color color) {
		
		JButton salida = new JButton("");
		salida.setBackground(color);
		salida.setIcon(icon);
		salida.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 0,false));
		salida.setHorizontalAlignment(JButton.CENTER);
		return salida;
	}
	
	public static JButton getButtonConFormato(String nombre) {
		JButton salida = new JButton(nombre);
		salida.setFont(new Font("Segos UI", Font.BOLD, 14));
		salida.setBackground(new Color(96, 96, 96));
		//salida.setBorder(BorderFactory.createLineBorder(new Color(36, 113, 163), 4, true));
		salida.setForeground(Color.white);
		salida.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 2, true));
		salida.setHorizontalAlignment(JButton.LEFT);
		
		return salida;
	}
	
	public static JButton getButtonConFormato2(String nombre) {
		JButton salida = getButtonConFormato(nombre);
		salida.setHorizontalAlignment(JButton.CENTER);
		return salida;
	}
	
	public static JButton getButtonConFormato3(String nombre) {
		JButton salida = getButtonConFormato(nombre);
		salida.setHorizontalAlignment(JButton.CENTER);
		salida.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 64), 2, true));
		salida.setBackground(new Color(64, 64, 64));
		return salida;
	}
	
	public static JButton getButtonConFormato4(String nombre) {
		JButton salida = getButtonConFormato3(nombre);
		salida.setBorder(BorderFactory.createLineBorder(new Color(64, 64, 64), 10, false));
		return salida;
	}
	
	public static JButton getButtonConFormatoVerde(String nombre) {
		JButton salida = getButtonConFormato4(nombre);
		salida.setBackground(new Color(25, 135, 84));
		salida.setBorder(BorderFactory.createLineBorder(new Color(25, 135, 84), 10, false));
		
		return salida;
	}
	
	public static JButton getButtonConFormatoAzul(String nombre) {
		JButton salida = getButtonConFormato4(nombre);
		salida.setBackground(new Color(13, 110, 253));
		salida.setBorder(BorderFactory.createLineBorder(new Color(13, 110, 253), 10, false));
		
		return salida;
	}
	
	public static JButton getButtonConFormatoRojo(String nombre) {
		JButton salida = getButtonConFormato4(nombre);
		salida.setBackground(new Color(220, 53, 69));
		salida.setBorder(BorderFactory.createLineBorder(new Color(220, 53, 69), 10, false));
		return salida;
	}
	
	public static JTextField getTextFieldConFormato() {
		JTextField salida = new JTextField("");
		salida.setFont(new Font("Segos UI", Font.BOLD, 14));
		salida.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
		salida.setBackground(Color.white);
		//salida.setBackground(Color.white);
		return salida;
	}
	
	public static JTextArea getTextAreaConFormato() {
		JTextArea salida = new JTextArea(0,0);
		salida.setFont(new Font("Segos UI", Font.BOLD, 14));
		salida.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));
		//salida.setLineWrap(true);
		//salida.setWrapStyleWord(true);
		//salida.setBackground(Color.white);
		return salida;
	}
	
	public static JLabel getLabelConFormato(String nombre) {
		JLabel salida = new JLabel(nombre);
		salida.setFont(new Font("Segos UI", Font.CENTER_BASELINE, 14));
		//salida.setOpaque(false);
		//salida.setBorder(BorderFactory.createLineBorder(Color.blue, 10, true));
		salida.setHorizontalAlignment(JLabel.CENTER);
		salida.setForeground(Color.white);
		
		return salida;
	}
	
	
	
	public static JLabel getLabelConFormato2(String nombre) {
		JLabel salida = getLabelConFormato(nombre);
		salida.setHorizontalAlignment(JLabel.LEFT);
		return salida;
	}
	public static JLabel getLabelConFormato3(String nombre) {
		JLabel salida = getLabelConFormato(nombre);
		salida.setForeground(Color.DARK_GRAY);
		return salida;
	}
	
	public static JLabel getLabelConFormato4(String nombre) {
		JLabel salida = getLabelConFormato(nombre);
		salida.setForeground(Color.black);
		salida.setFont(new Font("Segos UI", Font.ROMAN_BASELINE, 24));
		return salida;
	}
	
	public static JTable getTablaConFormato(TableModel modelo) {
		JTable salida = new JTable();
		JTableHeader tableHeader = salida.getTableHeader();
		tableHeader.setFont(new Font("Segos UI", Font.BOLD, 14));
		tableHeader.setOpaque(false);
		tableHeader.setBackground(new Color(64, 64, 64));
		tableHeader.setForeground(Color.WHITE);
		salida.setTableHeader(tableHeader);
		//tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		salida.setModel(modelo);
		salida.setBackground(new Color(244, 246, 247 ));
		salida.setFont(new Font("Segos UI", Font.BOLD, 13));
		//tabla.setGridColor(Color.BLUE);
		salida.setShowHorizontalLines(false);
		salida.setShowVerticalLines(false);
		salida.setSelectionBackground(Color.DARK_GRAY);
		salida.setSelectionForeground(Color.white);
	
		return salida;
	}
}
