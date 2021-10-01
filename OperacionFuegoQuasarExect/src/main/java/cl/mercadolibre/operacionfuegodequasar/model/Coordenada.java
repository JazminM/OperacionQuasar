package cl.mercadolibre.operacionfuegodequasar.model;

import org.springframework.stereotype.Component;

@Component
public class Coordenada {
	
	private double posicionX;
	private double posicionY;
	
	public double getPosicionX() {
		return posicionX;
	}
	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}
	public double getPosicionY() {
		return posicionY;
	}
	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}
	
	@Override
	public String toString() {
		return "La distancia al emisor es  [" + posicionX + "," + posicionY + "]";
	}		

}
