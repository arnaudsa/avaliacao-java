/**
 * 
 */
package br.com.avaliacao.checkout.exception;

import br.com.avaliacao.checkout.to.MessageErrorTO;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private MessageErrorTO messageError;

	
	public BusinessException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public BusinessException(final String message) {
		super(message);
	}

	public BusinessException(final Throwable cause) {
		super(cause);
	}

	public BusinessException(final MessageErrorTO messageError) {
		this.messageError = messageError;
	}

	public MessageErrorTO getMessageError() {
		return messageError;
	}
}
