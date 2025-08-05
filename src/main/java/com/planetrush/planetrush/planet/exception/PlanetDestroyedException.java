package com.planetrush.planetrush.planet.exception;

public class PlanetDestroyedException extends RuntimeException {

	public PlanetDestroyedException() {
	}

	public PlanetDestroyedException(Throwable cause) {
		super(cause);
	}

	public PlanetDestroyedException(String message) {
		super(message);
	}

	public PlanetDestroyedException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlanetDestroyedException(String message, Throwable cause, boolean enableSuppression,
		boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
