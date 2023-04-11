package utiles;

public class Util {
	public static String getStringConFormato(String entrada){
		String salida = entrada.trim().replace("\\s{2,}", " ").toLowerCase();
		return salida.substring(0, 1).toUpperCase() + salida.substring(1, salida.length());
	}
	
	public static boolean esFechaValida(int dia, int mes, int anio){
		if(dia<=0 || mes<=0 || mes>12 || anio<2000 || anio>2100)
			return false;
		int [] diasMeses = {31,28,31,30,31,30,31,31,30,31,30,31};
		if(diasMeses[mes-1]<dia){
			if(esAnioBisiesto(anio) && mes==2 && dia==29)
				return true;
			return false;
			}
		return true;
	}
	
	private static boolean esAnioBisiesto(int anio) {
		if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0)))
			return true;
		else
			return false;
	}
	
	public static String LPAD(int numero) {
		String cadena = String.valueOf(numero);
		if(cadena.length() < 9) {
			return "000000000".substring(cadena.length()) + cadena;
		}
		return cadena;
		
	}
}
