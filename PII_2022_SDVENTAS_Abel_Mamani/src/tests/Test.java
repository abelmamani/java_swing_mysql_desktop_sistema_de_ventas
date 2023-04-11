package tests;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import utiles.Util;

public class Test {

	public static void main(String[] args) {
//	     	Date d = new Date(2000, 9, 11);
//	     	Date d2 = new Date(2000, 9, 10);
//	     	Date d3 = new Date(2000, 9, 12);
//	     	System.out.println(d);
//	     	System.out.println(d.getDate()+" "+ d.getMonth()+ " "+ d.getYear());
//	     	System.out.println("after d2: " + d.after(d2));
//	     	System.out.println("after d3: "+d.after(d3));
//	     	System.out.println("bef d2: " + d.before(d2));
//	     	System.out.println("before d3: "+d.before(d3));

		String nombre = "hola";
		System.out.println(Util.getStringConFormato(nombre));
		
		
		//test precio - error en las fechas
		//test detalle - es necesario un equals(codigoProducto y codigoVenta) si detalle es un arreglo de venta?
		//test producto - (traer precio por fecha) en ese caso solo traer el primero? o un listado?
		//test Venta -  es necesario setters para venta??
		//test updateDetalle - solo remplaza la cantidad especificada o recibe la cantidad y la opcon para sumar o restar?
		//test gestroVenta -  como hago el addVenta(parametros)??? creo antes un aux para añadir y quitar detalles??
		
	}

}
