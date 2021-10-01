package cl.mercadolibre.operacionfuegodequasar.controller;



import java.io.IOException;

import cl.mercadolibre.operacionfuegodequasar.config.PropertiesConfig;
import cl.mercadolibre.operacionfuegodequasar.model.Coordenada;
import cl.mercadolibre.operacionfuegodequasar.services.Services;
import cl.mercadolibre.operacionfuegodequasar.services.impl.ServicesImpl;
import cl.mercadolibre.operacionfuegodequasar.utils.Utils;

public class Controller {
	Services services = new ServicesImpl();
	Utils utils = new Utils();
	
	
	private static final String MENSAJE_INGRESE_DISTANCIA= "Ingrese la distancia al emisor del satelite ";
	private static final String DISTANCIA= "distancia: ";

	Coordenada coordenada = new Coordenada();
	
	public void getLocation() {
		double distancia1;
		double distancia2;
		double distancia3;
		String x;
		String y;
		
		System.out.println(MENSAJE_INGRESE_DISTANCIA+PropertiesConfig.SKYWALKER);
		System.out.println(DISTANCIA);
		distancia1 = utils.validaDouble();
		System.out.println(MENSAJE_INGRESE_DISTANCIA+PropertiesConfig.SATO);
		System.out.println(DISTANCIA);
		distancia2 = utils.validaDouble();
		System.out.println(MENSAJE_INGRESE_DISTANCIA+PropertiesConfig.KENOBI);
		System.out.println(DISTANCIA);
		distancia3 = utils.validaDouble();
		
		
		if(services.validaDistancia(distancia1, distancia2, distancia3)){
			coordenada = services.getLocation(distancia1, distancia2, distancia3);
			
			x = String.valueOf(String.format("%.3f",coordenada.getPosicionX())).replace(",", ".");
			y = String.valueOf(String.format("%.3f",coordenada.getPosicionY())).replace(",", ".");
			
			System.out.println("Las coordenadas de la ubicacion del emisor son: ["+ x + "," + y + "]");
			
			try {
				getMessage();
			} catch (IOException e) {
				System.out.println("******* Ocurrio un error *********");
				System.out.println("******* Programa Terminado *********");
				System.exit(5);
			}
		}else {
			System.out.println("******* Hay satelites fuera de rango *********");
			System.out.println("******* Programa Terminado *********");
			System.exit(5);
		}						
		
	}
	
	public void getMessage() throws IOException {		
		services.recepcionMensaje();
		
	}
}
