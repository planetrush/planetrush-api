package com.planetrush.planetrush.infra.flask.exception;

public class ImageSimilarityCheckErrorException extends RuntimeException {

	public ImageSimilarityCheckErrorException() {
	}

	public ImageSimilarityCheckErrorException(String message) {
		super(message);
	}

	public ImageSimilarityCheckErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageSimilarityCheckErrorException(Throwable cause) {
		super(cause);
	}

	public ImageSimilarityCheckErrorException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
