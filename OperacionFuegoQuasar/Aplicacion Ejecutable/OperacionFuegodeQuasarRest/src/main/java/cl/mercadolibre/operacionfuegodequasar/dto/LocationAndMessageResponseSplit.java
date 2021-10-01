package cl.mercadolibre.operacionfuegodequasar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationAndMessageResponseSplit {

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("name")
	public String name;
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("distance")
	public Double distance;
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("message")
	public String[] message;
	
}
