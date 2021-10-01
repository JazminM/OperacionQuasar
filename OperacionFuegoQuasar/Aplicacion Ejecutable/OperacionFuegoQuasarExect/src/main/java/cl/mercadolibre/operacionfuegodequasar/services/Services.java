package cl.mercadolibre.operacionfuegodequasar.services;

import java.io.IOException;

import cl.mercadolibre.operacionfuegodequasar.exception.QuasarException;
import cl.mercadolibre.operacionfuegodequasar.model.Coordenada;
import cl.mercadolibre.operacionfuegodequasar.model.UneMensaje;

public interface Services{
	
	Coordenada getLocation(double distancia1 ,double distancia2, double distancia3);
	
	boolean validaDistancia(double distancia1 ,double distancia2, double distancia3);
	
	void recepcionMensaje() throws IOException;
	
	void calculoMensaje(String[] mensajeSkyWalker, String[]mensajeSato,
			String[] mensajeKenobi) throws Exception;
	
	String[] contenido(String nombre) throws IOException;
	
	UneMensaje comparaDosSatelites(String[] satelite1, String[] satelite2, int i, boolean var, StringBuilder sb) throws QuasarException;
}
