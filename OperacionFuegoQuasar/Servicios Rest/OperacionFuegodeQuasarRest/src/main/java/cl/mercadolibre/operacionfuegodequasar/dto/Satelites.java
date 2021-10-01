package cl.mercadolibre.operacionfuegodequasar.dto;

import java.util.Arrays;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Satelites {
	
	private static final String OBLIGATORIO= "es obligatorio";
	private static final String PARAMETRO= "Párametro";
	
	@NotNull(message= PARAMETRO+" 'name' "+OBLIGATORIO)
	@NotBlank(message= PARAMETRO+" 'name' "+OBLIGATORIO)
	@JsonProperty("name")
	private String name;
	@NotNull(message= PARAMETRO+" 'distance' "+OBLIGATORIO)
	@DecimalMin(value="0.1", message="Distancia no válida, debe ser mayor a 0")
	@JsonProperty("distance")
	private Double distance;
	@NotNull(message= PARAMETRO+" 'message' "+OBLIGATORIO)
	@NotEmpty(message= PARAMETRO+" message "+OBLIGATORIO)
	@JsonProperty("message")
	private String[] message;
	

	@Override
	public String toString() {
		return "Satelites [name=" + name + ", distance=" + distance+ ", message=" + Arrays.toString(message) + "]";
	}

}
