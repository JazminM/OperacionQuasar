package cl.mercadolibre.operacionfuegodequasar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "satelite_parametros")
public class SateliteParametrosEntity {
	
	@Id
	@Column(name = "nombre_satelite")
	private String nombreSatelite;
	@Column(name = "coordenada_x")
	private double coordenadaX;
	@Column(name = "coordenada_y")
	private double coordenadaY;
	@Override
	public String toString() {
		return "SateliteParametrosEntity [nombreSatelite=" + nombreSatelite + ", coordenadaX=" + coordenadaX
				+ ", coordenadaY=" + coordenadaY + "]";
	}
	public String getNombreSatelite() {
		return nombreSatelite;
	}
	public void setNombreSatelite(String nombreSatelite) {
		this.nombreSatelite = nombreSatelite;
	}
	public double getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(double coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public double getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(double coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	
	
}
