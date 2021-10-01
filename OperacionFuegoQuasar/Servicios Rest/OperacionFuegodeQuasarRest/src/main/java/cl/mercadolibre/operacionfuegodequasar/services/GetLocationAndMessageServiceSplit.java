package cl.mercadolibre.operacionfuegodequasar.services;

import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequestSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponse;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponseSplit;
import cl.mercadolibre.operacionfuegodequasar.excepcion.QuasarException;

public interface GetLocationAndMessageServiceSplit {
	public LocationAndMessageResponseSplit setSatelliteReception(LocationAndMessageRequestSplit locationAndMessageRequestSplit, String satelliteName) throws QuasarException;
	
	public void encuentraSatelite(String satellite_name) throws QuasarException;
	
	public LocationAndMessageResponse getLocationAndMessageSplit() throws QuasarException;
	
}
