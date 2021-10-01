package cl.mercadolibre.operacionfuegodequasar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mercadolibre.operacionfuegodequasar.config.Properties;
import cl.mercadolibre.operacionfuegodequasar.dto.Coordenada;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequest;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponse;
import cl.mercadolibre.operacionfuegodequasar.dto.Position;
import cl.mercadolibre.operacionfuegodequasar.entity.SatelitesEntity;
import cl.mercadolibre.operacionfuegodequasar.excepcion.QuasarException;
import cl.mercadolibre.operacionfuegodequasar.repository.SatelitesRepository;
import cl.mercadolibre.operacionfuegodequasar.utils.UneMensaje;
import cl.mercadolibre.operacionfuegodequasar.utils.Utils;

@Service
public class GetLocationAndMessageServiceImpl implements GetLocationAndMessageService{
	
	@Autowired
	SatelitesRepository sateliteRepository;
	@Autowired
	SatelitesEntity satelites;
	@Autowired
	Utils utils;
	
	private static final String MENSAJE_SIN_DETERMINAR= "No se puede determinar el mensaje";
	private static final String MENSAJE_ERROR= "Hubo un problema con la comunicación en la central de satelites";
	private static final String MENSAJE_SATELITE_FUERA_RANGO= "Satelites fuera de rango";
	
	@Override
	public LocationAndMessageResponse getLocationAndMessage(LocationAndMessageRequest locationAndMessageRequest) throws QuasarException{
		
		Coordenada coordenada = new Coordenada();
		Position position = new Position();
		LocationAndMessageResponse locationAndMessageResponse = new LocationAndMessageResponse();
		String[] mensajeSkyWalker=null;
		String[] mensajeKenobi=null;
		String[] mensajeSato=null;
		String mensaje = "";
		double distancia1 = 0;
		double distancia2 = 0;
		double distancia3 = 0;
		try {

			for (int i = 0; i < locationAndMessageRequest.getSatelites().size(); i++) {
				switch (locationAndMessageRequest.getSatelites().get(i).getName()) {			
				case Properties.SKYWALKER:	
					mensajeSkyWalker = locationAndMessageRequest.getSatelites().get(i).getMessage();
					distancia1 = locationAndMessageRequest.getSatelites().get(i).getDistance();
					
					break;
				case Properties.SATO:
					mensajeSato = locationAndMessageRequest.getSatelites().get(i).getMessage();
					distancia2 = locationAndMessageRequest.getSatelites().get(i).getDistance();

					break;
				case Properties.KENOBI:
					mensajeKenobi = locationAndMessageRequest.getSatelites().get(i).getMessage();
					distancia3 = locationAndMessageRequest.getSatelites().get(i).getDistance();

					break;
				default:					
					break;
				}
				
			}		

			mensaje = calculoMensaje(mensajeSkyWalker, mensajeSato, mensajeKenobi);
			locationAndMessageResponse.setMessage(mensaje);
			if (validaDistancia(distancia1,distancia2,distancia3)) {
				coordenada = getLocation(distancia1, distancia2, distancia3);
				if(coordenada !=null) {
					position.setX(coordenada.getPosicionX());
					position.setY(coordenada.getPosicionY());
					locationAndMessageResponse.setPosition(position);
				}else {
					throw new QuasarException(MENSAJE_SATELITE_FUERA_RANGO);
				}				

			}else {
				throw new QuasarException(MENSAJE_SATELITE_FUERA_RANGO);
			}
			
		} catch(QuasarException q) {
			  throw new QuasarException(q.getMessage());
		}catch(Exception e) {
			  throw new QuasarException(MENSAJE_ERROR);
		}

		return locationAndMessageResponse;

	}

	public Coordenada getLocation(double distancia1, double distancia2, double distancia3) throws QuasarException{

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

		satelite1.setPosicionX(Properties.SKYWALKER_X + Properties.MOVER_X);
		satelite1.setPosicionY(Properties.SKYWALKER_Y + Properties.MOVER_Y);

		satelite2.setPosicionX(Properties.SATO_X + Properties.MOVER_X);
		satelite2.setPosicionY(Properties.SATO_Y + Properties.MOVER_Y);

		satelite3.setPosicionX(Properties.KENOBI_X + Properties.MOVER_X);
		satelite3.setPosicionY(Properties.KENOBI_Y + Properties.MOVER_Y);

		// posteriormente, se rotaran los ejes de Sato y Kenobi, para dejar en el eje x
		// el punto.
		try {
			angulo = (Math.toRadians(Properties.ANGULO));
	
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
	
			// Ahora se debe rotar al valor original
	
			angulo = (Math.toRadians(Properties.ANGULO) * (-1));
	
			var1 = (posicionx * Math.cos(angulo)) - (posiciony * Math.sin(angulo));
			var2 = (posicionx * Math.sin(angulo)) + (posiciony * Math.cos(angulo));
			
			//Y finalmente se restan las posiciones para volver a la posicion original
			double aux1 = (var1 - Properties.MOVER_X);
			double aux2 = (var2 - Properties.MOVER_Y);
			emisor.setPosicionX(cortaValor(aux1));
			emisor.setPosicionY(cortaValor(aux2));
		}catch(Exception e) {
			throw new QuasarException(MENSAJE_ERROR);
		}
		return emisor;
	}
	
	public double cortaValor(double valor) {
		double scale = Math.pow(10, 3);
        double doubleM1 = Math.round(valor*scale)/scale;		
		return Double.valueOf(doubleM1);
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


	public String calculoMensaje(String[] mensajeSkyWalker, String[]mensajeSato,
			String[] mensajeKenobi) throws QuasarException{
		String mensaje = "";

		String[] sateliteSky;
		String[] sateliteSato;
		String[] sateliteKeno;
		int sizeMenor = utils.obtieneMenorArray(mensajeSkyWalker, mensajeSato, mensajeKenobi);
		boolean var;	

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
		mensaje = uneMensaje.getMensaje().toString().trim();

		return mensaje;
	}
	
	private UneMensaje comparaDosSatelites(String[] satelite1, String[] satelite2, int i, boolean var, StringBuilder sb) throws QuasarException {
		UneMensaje uneMensaje = new UneMensaje();
		if (!satelite1[i].isEmpty() && !satelite2[i].isEmpty()) {
			if (satelite1[i].equals(satelite2[i])) {
				if(var) {
					sb.append(satelite1[i]+ " ");
					var = false;
				}				
			} else {
				throw new QuasarException(MENSAJE_SIN_DETERMINAR);
			}
		}
		uneMensaje.setValor(var);
		uneMensaje.setMensaje(sb);
		return uneMensaje;
	}
}
