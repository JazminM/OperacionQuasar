package cl.mercadolibre.operacionfuegodequasar.dto;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationAndMessageResponse {
	@Autowired
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("position")
	public Position position;
	@JsonProperty("message")
	@JsonInclude(Include.NON_NULL)
	public String message;
	
}
