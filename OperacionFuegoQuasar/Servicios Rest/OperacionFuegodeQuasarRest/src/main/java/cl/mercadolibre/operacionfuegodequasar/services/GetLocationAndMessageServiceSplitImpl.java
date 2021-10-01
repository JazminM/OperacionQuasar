package cl.mercadolibre.operacionfuegodequasar.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequest;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageRequestSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponse;
import cl.mercadolibre.operacionfuegodequasar.dto.LocationAndMessageResponseSplit;
import cl.mercadolibre.operacionfuegodequasar.dto.Satelites;
import cl.mercadolibre.operacionfuegodequasar.entity.MensajeEntity;
import cl.mercadolibre.operacionfuegodequasar.entity.SateliteParametrosEntity;
import cl.mercadolibre.operacionfuegodequasar.entity.SatelitesEntity;
import cl.mercadolibre.operacionfuegodequasar.excepcion.QuasarException;
import cl.mercadolibre.operacionfuegodequasar.repository.MensajeRepository;
import cl.mercadolibre.operacionfuegodequasar.repository.SateliteParametrosRepository;
import cl.mercadolibre.operacionfuegodequasar.repository.SatelitesRepository;
import cl.mercadolibre.operacionfuegodequasar.utils.LocationAndMessageSplitConverter;

@Service
public class GetLocationAndMessageServiceSplitImpl implements GetLocationAndMessageServiceSplit {

	@Autowired
	SatelitesRepository satelitesRepository;
	@Autowired
	MensajeRepository mensajeRepository;
	@Autowired
	SatelitesEntity satelitesEntity, satelitesEntityAux;
	@Autowired
	List<SatelitesEntity> satelitesEntityListAux;
	@Autowired
	MensajeEntity mensajeEntity;
	@Autowired
	List<MensajeEntity> mensajeEntityList;
	@Autowired
	GetLocationAndMessageService getLocationAndMessageService;
	@Autowired
	LocationAndMessageSplitConverter locationAndMessageSplitConverter;
	@Autowired
	SateliteParametrosRepository sateliteParametrosRepository;
	@Autowired
	SateliteParametrosEntity sateliteParametrosEntity;
	
	private static final String MENSAJE_SATELITE_DESCONOCIDO= "Satelite desconocido";
	private static final String MENSAJE_ERROR= "Hubo un problema con la comunicación en la central de satelites";
	private static final String MENSAJE_NO_HAY_SUFICIENTE_INFO= "No hay suficiente información";


	@Override
	public LocationAndMessageResponseSplit setSatelliteReception(
			LocationAndMessageRequestSplit locationAndMessageRequestSplit, String satelliteName) throws QuasarException{
		satelitesEntityAux = new SatelitesEntity();
		LocationAndMessageResponseSplit locationAndMessageResponseSplit = new LocationAndMessageResponseSplit();
		try {		
				satelitesEntityAux = satelitesRepository.findByNombreSatelite(satelliteName.toLowerCase());
		
				// Pregunta si ya existe el mensaje
				if (satelitesEntityAux != null) {
		
					mensajeEntityList = mensajeRepository.findByIdSatelite(satelitesEntityAux.getIdSatelite());
		
					if (!mensajeEntityList.isEmpty()) {
						mensajeRepository.deleteAll(mensajeEntityList);
					}
					satelitesEntityAux.setDistancia(locationAndMessageRequestSplit.getDistance());
					satelitesRepository.saveAndFlush(satelitesEntityAux);
		
					insertaMensaje(locationAndMessageRequestSplit.getMessage(), satelitesEntityAux.getIdSatelite());
		
				} else {
					satelitesEntity = new SatelitesEntity();
					// Si no existe el satelite
					
						
						satelitesEntity.setDistancia(locationAndMessageRequestSplit.getDistance());
						satelitesEntity.setNombreSatelite(satelliteName.toLowerCase());
						satelitesRepository.saveAndFlush(satelitesEntity);
			
						insertaMensaje(locationAndMessageRequestSplit.getMessage(), satelitesEntity.getIdSatelite());
					
					
				}
				locationAndMessageResponseSplit = locationAndMessageSplitConverter
						.locationAndMessageSplitConverterToDto(satelliteName, locationAndMessageRequestSplit);
		}catch(QuasarException q) {
			throw new QuasarException(q.getMessage());
		}catch(Exception e) {
			throw new QuasarException(MENSAJE_ERROR);
		}
		return locationAndMessageResponseSplit;
	}

	private void insertaMensaje(String[] mensajes, Long idSatelite) throws QuasarException{
		try {
			for (int i = 0; i < mensajes.length; i++) {
				mensajeEntity = new MensajeEntity();
				mensajeEntity.setIdSatelite(idSatelite);
				mensajeEntity.setPosicion(i + 1);
				mensajeEntity.setMensajeRecibido(mensajes[i]);
				mensajeRepository.saveAndFlush(mensajeEntity);
			}
		} catch (Exception e) {
			throw new QuasarException(MENSAJE_ERROR);
		}
	}

	public void encuentraSatelite(String satellite_name) throws QuasarException{

		try {
			sateliteParametrosEntity = sateliteParametrosRepository.findByNombreSatelite(satellite_name);
			if (sateliteParametrosEntity == null) {			
				throw new QuasarException(MENSAJE_SATELITE_DESCONOCIDO);
			}
		} catch (Exception e) {
			throw new QuasarException(e.getMessage());
		}


	}

	public LocationAndMessageResponse getLocationAndMessageSplit() throws QuasarException{
		ArrayList<Satelites> satelitesList = new ArrayList<>();
		mensajeEntityList = new ArrayList<MensajeEntity>();
		LocationAndMessageRequest locationAndMessageRequest = new LocationAndMessageRequest();
		LocationAndMessageResponse locationAndMessageResponse = new LocationAndMessageResponse();
		
		try {
			satelitesEntityListAux = satelitesRepository.findAll();
	        
			//Para que pueda ingresar deben existir 3 satelites
			if (satelitesEntityListAux.size() == 3) {
				
					for (int i = 0; i < satelitesEntityListAux.size(); i++) {
							String[] msj;
							Satelites satelites = new Satelites();
							
								satelites.setName(satelitesEntityListAux.get(i).getNombreSatelite());
								satelites.setDistance(satelitesEntityListAux.get(i).getDistancia());
								
								mensajeEntityList= mensajeRepository.findMensajeByIdSateliteOrderByPosicionAsc(satelitesEntityListAux.get(i).getIdSatelite());
								msj = new String[mensajeEntityList.size()];
								
								for (int j = 0; j < mensajeEntityList.size(); j++) {					
									msj[j]=mensajeEntityList.get(j).getMensajeRecibido();						
								}							
								satelites.setMessage(msj);
							satelitesList.add(satelites);
						}
						locationAndMessageRequest.setSatelites(satelitesList);
						locationAndMessageResponse = getLocationAndMessageService
								.getLocationAndMessage(locationAndMessageRequest);					
				
			}else {
				throw new QuasarException(MENSAJE_NO_HAY_SUFICIENTE_INFO);
			}
		}catch(QuasarException q) {
				throw new QuasarException(q.getMessage());						
		}catch (Exception e) {
			throw new QuasarException(MENSAJE_ERROR);
		}
		return locationAndMessageResponse;
	}

}