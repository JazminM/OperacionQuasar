package cl.mercadolibre.operacionfuegodequasar.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequest;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequestSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponse;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponseSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.ResponseMessage;
import cl.mercadolibre.operacionfuegodequasar.excepcion.QuasarException;
import cl.mercadolibre.operacionfuegodequasar.services.GetLocationAndMessageService;
import cl.mercadolibre.operacionfuegodequasar.services.GetLocationAndMessageServiceSplit;

@RestController
@RequestMapping(value = "/OperacionFuegoDeQuasar", produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
public class AppController {
	@Autowired
	GetLocationAndMessageService getLocationAndMessageService;
	@Autowired
	ResponseMessage responseMessage;
	@Autowired
	GetLocationAndMessageServiceSplit getLocationAndMessageServiceSplit;

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/topsecret")
	public ResponseEntity getLocationAndMessage(@Valid @RequestBody LocationAndMessageRequest locationAndMessageRequest, Errors errors) throws QuasarException {
		LocationAndMessageResponse locationAndMessageResponse;
		if (errors.hasErrors()) {
			responseMessage.setCode("404");
			responseMessage.setMessage(errors.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
		} else {			
			try {
				locationAndMessageResponse = getLocationAndMessageService.getLocationAndMessage(locationAndMessageRequest);
				return ResponseEntity.status( HttpStatus.OK)
						.body(locationAndMessageResponse);
			}catch(QuasarException q) {			
				responseMessage.setCode("404");
				responseMessage.setMessage(q.getMessage());
				return ResponseEntity.status( HttpStatus.NOT_FOUND)
						.body(responseMessage);
			}							
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/topsecret_split/{satellite_name}")
	public ResponseEntity setLocationAndMessageSplit(@Valid @RequestBody LocationAndMessageRequestSplit locationAndMessageRequestSplit, Errors errors, @PathVariable String satellite_name) throws QuasarException{
		LocationAndMessageResponseSplit locationAndMessageResponseSplit;
		if (errors.hasErrors()) {
			responseMessage.setCode("404");
			responseMessage.setMessage(errors.getFieldError().getDefaultMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
		} else {
			try {
				getLocationAndMessageServiceSplit.encuentraSatelite(satellite_name);
				locationAndMessageResponseSplit = getLocationAndMessageServiceSplit.setSatelliteReception(locationAndMessageRequestSplit, satellite_name);
				return ResponseEntity.status( HttpStatus.OK)
						.body(locationAndMessageResponseSplit);
			}catch(QuasarException q) {
				responseMessage.setCode("404");
				responseMessage.setMessage(q.getMessage());
				return ResponseEntity.status( HttpStatus.NOT_FOUND)
						.body(responseMessage);
			}				
			
		}
	}
	@SuppressWarnings("rawtypes")
	@GetMapping(path = "/topsecret_split")
	public ResponseEntity getLocationAndMessageSplit() throws QuasarException{	
		LocationAndMessageResponse locationAndMessageResponse = new LocationAndMessageResponse();
		try {
			locationAndMessageResponse = getLocationAndMessageServiceSplit.getLocationAndMessageSplit();
			return ResponseEntity.status(HttpStatus.OK).body(locationAndMessageResponse);		
		}catch(QuasarException q) {
			responseMessage.setCode("404");
			responseMessage.setMessage(q.getMessage());
			return ResponseEntity.status( HttpStatus.NOT_FOUND)
					.body(responseMessage);
		}		
	}

}
