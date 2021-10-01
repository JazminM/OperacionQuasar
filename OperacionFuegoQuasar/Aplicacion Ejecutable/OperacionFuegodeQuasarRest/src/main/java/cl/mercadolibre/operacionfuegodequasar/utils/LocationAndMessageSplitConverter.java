package cl.mercadolibre.operacionfuegodequasar.utils;

import org.springframework.stereotype.Component;

import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequestSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponseSplit;

@Component
public class LocationAndMessageSplitConverter{

		
	public LocationAndMessageResponseSplit locationAndMessageSplitConverterToDto(String satellite_name,LocationAndMessageRequestSplit locationAndMessageRequestSplit) {
		LocationAndMessageResponseSplit locationAndMessageResponseSplit = new LocationAndMessageResponseSplit();
		locationAndMessageResponseSplit.setDistance(locationAndMessageRequestSplit.getDistance());
		locationAndMessageResponseSplit.setName(satellite_name);
		locationAndMessageResponseSplit.setMessage(locationAndMessageRequestSplit.getMessage());
		
		return locationAndMessageResponseSplit;
	}
	
}
