package cl.mercadolibre.operacionfuegodequasar.excepcion;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class QuasarException extends Exception{

	private static final long serialVersionUID = -5300469257491710241L;

	public QuasarException(String message) {
		super(message);
	}

	public QuasarException(String message, Throwable cause) {
		super(message, cause);
	}

}
