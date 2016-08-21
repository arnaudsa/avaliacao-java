package br.com.avaliacao.checkout.exception;

import org.apache.log4j.Logger;

import br.com.avaliacao.checkout.to.MessageErrorTO;

public class CartInvalidException extends BusinessException {

	private static final Logger LOGGER = Logger.getLogger(CartInvalidException.class);
	
	private static final long serialVersionUID = 1L;

	public CartInvalidException(final String message, final Throwable cause) {
		super(message, cause);
		LOGGER.error(message, cause);
	}

	public CartInvalidException(final String message) {
		super(message);
		LOGGER.error(message);
	}

	public CartInvalidException(final Throwable cause) {
		super(cause);
		LOGGER.error(cause);
	}

	public CartInvalidException(final MessageErrorTO messageError) {
		super(messageError);
		LOGGER.error(messageError.getMensagens());
	}
}
