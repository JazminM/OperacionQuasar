package cl.mercadolibre.operacionfuegodequasar.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cl.mercadolibre.operacionfuegodequasar.config.PropertiesConfig;
import cl.mercadolibre.operacionfuegodequasar.model.Coordenada;
import cl.mercadolibre.operacionfuegodequasar.model.UneMensaje;
import cl.mercadolibre.operacionfuegodequasar.services.Services;
import cl.mercadolibre.operacionfuegodequasar.utils.Utils;



public class ServicesImpl implements Services {

	Utils utils = new Utils();
	private static final String PROGRAMA_TERMINADO = "*************************** Programa Terminado **********************************";
	private static final String ESPACIOS = "-----------------------------------------------------------------------------------------";
	private static final String MENSAJE_NO_DETERMINADO = "********************* El mensaje no puede ser determinado ***********************";

	public Coordenada getLocation(double distancia1, double distancia2, double distancia3) {

		double valor1 = 0;
		double valor2 = 0;
		double var1 = 0;
		double var2 = 0;
		double angulo = 0;
		double posicionx = 0;
		double posiciony = 0;

		// Vamos a mover las coordenadas, para dejar un punto en el eje (0.0)

		Coordenada satelite1 = new Coordenada();
		Coordenada satelite2 = new Coordenada();
		Coordenada satelite3 = new Coordenada();
		Coordenada emisor = new Coordenada();

		satelite1.setPosicionX(PropertiesConfig.SKYWALKER_X + PropertiesConfig.MOVER_X);
		satelite1.setPosicionY(PropertiesConfig.SKYWALKER_Y + PropertiesConfig.MOVER_Y);

		satelite2.setPosicionX(PropertiesConfig.SATO_X + PropertiesConfig.MOVER_X);
		satelite2.setPosicionY(PropertiesConfig.SATO_Y + PropertiesConfig.MOVER_Y);

		satelite3.setPosicionX(PropertiesConfig.KENOBI_X + PropertiesConfig.MOVER_X);
		satelite3.setPosicionY(PropertiesConfig.KENOBI_Y + PropertiesConfig.MOVER_Y);

		// posteriormente, se rotaran los ejes de Sato y Kenobi, para dejar en el eje x
		// el punto.
		try {
			angulo = (Math.toRadians(PropertiesConfig.ANGULO));
	
			valor1 = (satelite2.getPosicionX() * Math.cos(angulo)) - (satelite2.getPosicionY() * Math.sin(angulo));
			valor2 = (satelite2.getPosicionX() * Math.sin(angulo)) + (satelite2.getPosicionY() * Math.cos(angulo));
	
			satelite2.setPosicionX(valor1);
			satelite2.setPosicionY(valor2);
	
			valor1 = (satelite3.getPosicionX() * Math.cos(angulo)) - (satelite3.getPosicionY() * Math.sin(angulo));
			valor2 = (satelite3.getPosicionX() * Math.sin(angulo)) + (satelite3.getPosicionY() * Math.cos(angulo));
	
			satelite3.setPosicionX(valor1);
			satelite3.setPosicionY(valor2);
	
			// Ahora se calcula el punto (X,Y)
	
			// Primero X
			valor1 = (Math.pow(distancia1, 2) - Math.pow(distancia2, 2) + Math.pow(satelite2.getPosicionX(), 2));
			valor2 = (2 * satelite2.getPosicionX());
			posicionx = (valor1 / valor2);
	
			// Ahora Y
	
			valor1 = (Math.pow(distancia1, 2) - Math.pow(distancia3, 2) + Math.pow(satelite3.getPosicionX(), 2)
					+ Math.pow(satelite3.getPosicionY(), 2));
			valor2 = ((2 * satelite3.getPosicionY()) - ((satelite3.getPosicionX() / satelite3.getPosicionY()) * posicionx));
			posiciony = (valor1 / valor2);
	
			// Ahpora se debe rotar al valor original
	
			angulo = (Math.toRadians(PropertiesConfig.ANGULO) * (-1));
	
			var1 = (posicionx * Math.cos(angulo)) - (posiciony * Math.sin(angulo));
			var2 = (posicionx * Math.sin(angulo)) + (posiciony * Math.cos(angulo));
	
			emisor.setPosicionX(var1 - PropertiesConfig.MOVER_X);
			emisor.setPosicionY(var2 - PropertiesConfig.MOVER_Y);
		}catch(Exception e) {
			System.out.println("*********************Ocurrio un error al realizar el calculo***********************");
			System.out.println(PROGRAMA_TERMINADO);
			System.exit(5);
		}
		return emisor;
	}

	public boolean validaDistancia(double distancia1, double distancia2, double distancia3) {

		double distancia13 = (distancia1 + distancia3);
		double distancia12 = (distancia1 + distancia2);
		double distancia23 = (distancia2 + distancia3);

		if (distancia13 > 600 && distancia12 > 400 && distancia23 > 1000) {
			return true;
		}

		return false;
	}

	public void recepcionMensaje() throws IOException {

		String[] mensajeSkyWalker;
		String[] mensajeSato;
		String[] mensajeKenobi;

		try {
			System.out.println(ESPACIOS);
			System.out.println(ESPACIOS);
			System.out.println(ESPACIOS);
			System.out.println(ESPACIOS);
			System.out.println(ESPACIOS);
			System.out.println("RECIBIENDO MENSAJES DE SATELITES...");
			System.out.println("Para decodificar el mensaje final ingrese lo recibido por cada satelite. Para ello presione 'ENTER' por cada palabra del mensaje y finalice ingresando el caracter '/'");
			mensajeSkyWalker = contenido(PropertiesConfig.SKYWALKER);
			mensajeSato = contenido(PropertiesConfig.SATO);
			mensajeKenobi = contenido(PropertiesConfig.KENOBI);

			calculoMensaje(mensajeSkyWalker, mensajeSato, mensajeKenobi);
			

		} catch (Exception e) {
			System.out.println(MENSAJE_NO_DETERMINADO);
			System.out.println(PROGRAMA_TERMINADO);
			System.exit(5);
		}
	}

	public void calculoMensaje(String[] mensajeSkyWalker, String[]mensajeSato,
			String[] mensajeKenobi){
		String mensaje = "";
		
		String[] sateliteSky;
		String[] sateliteSato;
		String[] sateliteKeno;
			int sizeMenor = utils.obtieneMenorArray(mensajeSkyWalker, mensajeSato, mensajeKenobi);
			boolean var;	
			if(mensajeSkyWalker.length == 0 || mensajeSato.length == 0 || mensajeKenobi.length == 0) {
				System.out.println(MENSAJE_NO_DETERMINADO);
				System.out.println(PROGRAMA_TERMINADO);
				System.exit(5);
			}
			sateliteSky = utils.obtieneNuevoArray(mensajeSkyWalker, sizeMenor);
			sateliteSato = utils.obtieneNuevoArray(mensajeSato, sizeMenor);
			sateliteKeno = utils.obtieneNuevoArray(mensajeKenobi, sizeMenor);
			StringBuilder sb = new StringBuilder();
			UneMensaje uneMensaje = new UneMensaje();
			for (int i = 0; i < sizeMenor; i++) {
				var = true;
				uneMensaje = comparaDosSatelites(sateliteSky, sateliteSato, i, var, sb);
				uneMensaje = comparaDosSatelites(sateliteSato, sateliteKeno, i, uneMensaje.isValor(), uneMensaje.getMensaje());
				uneMensaje = comparaDosSatelites(sateliteSky, sateliteKeno, i, uneMensaje.isValor(), uneMensaje.getMensaje());
			
				
				if (!sateliteSky[i].isEmpty() && uneMensaje.isValor()) {
						uneMensaje.setMensaje(sb.append(sateliteSky[i]+ " "));
						uneMensaje.setValor(false);				
				} 
				
				if (!sateliteSato[i].isEmpty() && uneMensaje.isValor()) {
						uneMensaje.setMensaje(sb.append(sateliteSato[i]+ " "));
						uneMensaje.setValor(false);					
				} 
				
				if (!sateliteKeno[i].isEmpty() && uneMensaje.isValor()) {
						uneMensaje.setMensaje(sb.append(sateliteKeno[i]+ " "));
						uneMensaje.setValor(false);					
				} 
			}
			mensaje = sb.toString().trim();
			
			System.out.println("Mensaje recibido es: [" + mensaje + "]");
	}

	public String[] contenido(String nombre){
		boolean exit = true;
		ArrayList<String> mensajeList = new ArrayList<String>();		
		System.out.println("Mensaje recibido en el satelite " + nombre + ": ");
		do {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String valor = "";
			try {
				valor = br.readLine();
			} catch (IOException e) {
				System.out.println("******************* No es posible leer la linea *******************************");
				System.out.println(PROGRAMA_TERMINADO);
				System.exit(5);
			}

			if (valor.equals("/")) {
				exit = false;
			} else {
				mensajeList.add(valor);
			}

		} while (exit);
		return utils.transformaMensaje(mensajeList);
	}
	
	public UneMensaje comparaDosSatelites(String[] satelite1, String[] satelite2, int i, boolean var, StringBuilder sb){
		UneMensaje uneMensaje = new UneMensaje();
		if (!satelite1[i].isEmpty() && !satelite2[i].isEmpty()) {
			if (satelite1[i].equals(satelite2[i])) {
				if(var) {
					sb.append(satelite1[i]+ " ");
					var = false;
				}				
			} else {
				System.out.println(MENSAJE_NO_DETERMINADO);
				System.out.println(PROGRAMA_TERMINADO);
				System.exit(5);
			}
		}
		uneMensaje.setValor(var);
		uneMensaje.setMensaje(sb);
		return uneMensaje;
	}

}
