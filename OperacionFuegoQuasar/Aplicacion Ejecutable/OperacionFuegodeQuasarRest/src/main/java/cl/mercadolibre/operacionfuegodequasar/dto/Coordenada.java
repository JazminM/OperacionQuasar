package cl.mercadolibre.operacionfuegodequasar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordenada {
	
	private Double posicionX;
	private Double posicionY;
	
	@Override
	public String toString() {
		return "La distancia al emisor es  [" + posicionX + "," + posicionY + "]";
	}

}