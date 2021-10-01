package cl.mercadolibre.operacionfuegodequasar.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationAndMessageRequest {
	
	private static final String OBLIGATORIO= "es obligatorio";
	private static final String PARAMETRO= "Párametro";
	
	@Autowired
	@NotNull(message= PARAMETRO+" 'satelites' "+OBLIGATORIO)
	@NotEmpty(message= PARAMETRO+" 'satelites "+OBLIGATORIO)
	@Size(min=3, max=3, message = "El número de Satélites debe ser 3")
	@JsonProperty("satelites")
	public @Valid List<Satelites> satelites;
	
	@Override
	public String toString() {
		return "LocationAndMessageRequest [satelites=" + satelites + "]";
	}
	
}
