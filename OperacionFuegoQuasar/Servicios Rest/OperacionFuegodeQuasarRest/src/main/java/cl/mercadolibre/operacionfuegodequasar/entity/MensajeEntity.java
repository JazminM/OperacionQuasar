package cl.mercadolibre.operacionfuegodequasar.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "mensaje")
public class MensajeEntity {
	
	@Id
	@GeneratedValue
	@Column(name = "id_mensaje")
	private Long idMensaje;
	@Column(name = "id_satelite")
	private Long idSatelite;
	@Column(name = "posicion")
	private Integer posicion;
	@Column(name = "mensaje_recibido")
	private String mensajeRecibido;
	
	@Override
	public String toString() {
		return "Mensaje [idMensaje=" + idMensaje + ", idSatelite=" + idSatelite + ", posicion=" + posicion
				+ ", mensajeRecibido=" + mensajeRecibido + "]";
	}

	
	public Long getIdMensaje() {
		return idMensaje;
	}


	public void setIdMensaje(Long idMensaje) {
		this.idMensaje = idMensaje;
	}


	public Long getIdSatelite() {
		return idSatelite;
	}


	public void setIdSatelite(Long idSatelite) {
		this.idSatelite = idSatelite;
	}


	public Integer getPosicion() {
		return posicion;
	}


	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}


	public String getMensajeRecibido() {
		return mensajeRecibido;
	}


	public void setMensajeRecibido(String mensajeRecibido) {
		this.mensajeRecibido = mensajeRecibido;
	}
	
	
}
