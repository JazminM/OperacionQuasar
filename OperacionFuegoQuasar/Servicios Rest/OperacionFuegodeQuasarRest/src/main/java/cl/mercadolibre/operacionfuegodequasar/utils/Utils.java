package cl.mercadolibre.operacionfuegodequasar.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {

	public int obtieneMenorArray(String[] mensajeSkyWalker, String[] mensajeSato, String[] mensajeKenobi) {
		int size=0;
		if(mensajeSkyWalker.length <= mensajeSato.length) {			
			if(mensajeSkyWalker.length <= mensajeKenobi.length) {
				size = mensajeSkyWalker.length;
			}else {
				size = mensajeKenobi.length;
			}
		}else{
			if(mensajeSato.length <= mensajeKenobi.length) {
				size = mensajeSato.length;
			}else {
				size = mensajeKenobi.length;
			}
		}
		
		return size;
	}
	
	public String[] obtieneNuevoArray(String[] satelite, int size) {
		String[] arrayFinal = new String[size];
		int valor = 0;
		valor = satelite.length- size; 
		for (int i = 0; i < size; i++) {	
			
			arrayFinal[i]= satelite[i+valor];		
			
		}		
		return arrayFinal;
	}
	
}
