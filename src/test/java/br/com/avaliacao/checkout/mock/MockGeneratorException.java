package br.com.avaliacao.checkout.mock;

public class MockGeneratorException extends RuntimeException {

	private static final long serialVersionUID = 154920967870312594L;

	public MockGeneratorException() {
	}

	public MockGeneratorException(final String message) {
		super(message);
	}

	public MockGeneratorException(final Throwable throwable) {
		super(throwable);
	}

	public MockGeneratorException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

}
