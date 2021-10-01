package cl.mercadolibre.operacionfuegodequasar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Position {
	@JsonProperty("x")
	@JsonInclude(Include.NON_NULL)
	private Double x;
	@JsonProperty("y")
	@JsonInclude(Include.NON_NULL)
	private Double y;

}
