package cl.mercadolibre.operacionfuegodequasar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "satelites")
public class SatelitesEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "id_satelite")
	private Long idSatelite;
	@Column(name = "nombre_satelite")
	private String nombreSatelite;
	@Column(name = "distancia")
	private double distancia;
	
	@Override
	public String toString() {
		return "Satelites [idSatelite=" + idSatelite + ", nombreSatelite=" + nombreSatelite + ", distancia=" + distancia
				+ "]";
	}

	public Long getIdSatelite() {
		return idSatelite;
	}

	public void setIdSatelite(Long idSatelite) {
		this.idSatelite = idSatelite;
	}

	public String getNombreSatelite() {
		return nombreSatelite;
	}

	public void setNombreSatelite(String nombreSatelite) {
		this.nombreSatelite = nombreSatelite;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	
	
}
