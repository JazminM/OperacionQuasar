package cl.mercadolibre.operacionfuegodequasar.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utils {

	public double validaDouble() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String valor = "";
		boolean exit = true;
		double numero =0;
		do {
			try {
				valor = br.readLine();
				numero = Double.valueOf(valor);
				if(numero > 0) {
					exit = false;
				}else {
					System.out.println("La distancia no es válido, debe ser mayor a 0, intentelo nuevamente");
				}				
			} catch (IOException e) {
				System.out.println("Ocurrio un error");
			}catch (NumberFormatException excepcion) {
				System.out.println("El valor ingresado no es válido, intentelo nuevamente");				
			}		
				
		}while(exit);
		return numero;
	}

	
	public int obtieneMenorArray(String[] mensajeSkyWalker, String[] mensajeSato, String[] mensajeKenobi) {
		int size=0;
		if(mensajeSkyWalker.length <= mensajeSato.length) {			
			if(mensajeSkyWalker.length <= mensajeKenobi.length) {
				size = mensajeSkyWalker.length;
			}else {
				size = mensajeKenobi.length;
			}
		}else{
			if(mensajeSato.length <= mensajeKenobi.length) {
				size = mensajeSato.length;
			}else {
				size = mensajeKenobi.length;
			}
		}
		
		return size;
	}
	
	public String[] obtieneNuevoArray(String[] satelite, int size) {
		String[] arrayFinal = new String[size];
		int valor = 0;
		valor = satelite.length- size; 
		for (int i = 0; i < size; i++) {	
			
			arrayFinal[i]= satelite[i+valor];		
			
		}		
		return arrayFinal;
	}
	
	public boolean unirMensaje(String satelite1, String satelite2, boolean var) {
		StringBuilder sb = new StringBuilder();
		
		if (!satelite1.isEmpty() && !satelite2.isEmpty()) {
			if (satelite1.equals(satelite2) && var) {
				sb.append(satelite1+ " ");
				var = false;
			}
		}
		
		return var;
	}
	
	public String[] transformaMensaje(ArrayList<String> mensajeList) {
		
		String[] msj = new String[mensajeList.size()];
		for(int i=0; i<mensajeList.size(); i++) {
			msj[i] = mensajeList.get(i);
		}
		return msj;
	}

	
}
