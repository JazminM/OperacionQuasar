package cl.mercadolibre.operacionfuegodequasar.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class ResponseMessage {
	@JsonProperty("code")
	private String code;
	@JsonProperty("message")
	private String message;
	public ResponseMessage() {
		super();
	}
	@Override
	public String toString() {
		return "ResponseMessage [code=" + code + ", message=" + message + "]";
	}
	
}
