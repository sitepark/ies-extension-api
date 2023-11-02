package com.sitepark.ies.extension.api.exeptions;

public class InitializationFailedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InitializationFailedException(String msg) {
		super(msg);
	}

	public InitializationFailedException(String msg, Throwable t) {
		super(msg, t);
	}
}
