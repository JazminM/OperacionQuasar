package cl.mercadolibre.operacionfuegodequasar;

import java.io.IOException;
import java.util.Scanner;

import cl.mercadolibre.operacionfuegodequasar.config.PropertiesConfig;
import cl.mercadolibre.operacionfuegodequasar.controller.Controller;
public class OperacionFuegoQuasarApp {		
	
	public static void main(String [] args) throws IOException {	
		boolean volver;
		do {		
		
		Controller controller = new Controller();		
		
		System.out.println("****************************************************************");
		System.out.println("**************Bienvenido al servicio de Auxilio*****************");
		System.out.println("La ubicación de los Satélites actualmente en servicio");		
		System.out.println("1) Skywalker es [100,-100]");
		System.out.println("2) Sato es [500,100]");
		System.out.println("3) Kenobi es [-500,-200]");
		System.out.println("Para obtener las coordenadas del emisor, digite las distancias de cada satelite");
		 
		controller.getLocation();
			
		
		System.out.println("¿Quieres volver a intentarlo? Presiona Y, de lo contrario presiona otra tecla");
		volver = continuar();
		
		}while(volver);
	}
	
	private static boolean continuar() {
		String intento;
		boolean volver;
		Scanner scan = new Scanner(System.in);			
		intento = scan.next().toUpperCase();
		if(intento.equalsIgnoreCase(PropertiesConfig.YES)) {
			volver = false;
			System.out.println("Cerrando conexiones");
			System.out.println("Servicio de Auxilio Cerrado!!!!");
			System.exit(5);
			scan.close();
		}else {
			volver = true;
		}
		return volver;
	}
}
