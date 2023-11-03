package com.sitepark.ies.extension.api.exeptions;

public class InitializationFailedException extends RuntimeException {

	private static final long serialVersionUID = -424807632562905165L;

	public InitializationFailedException(String msg) {
		super(msg);
	}

	public InitializationFailedException(String msg, Throwable t) {
		super(msg, t);
	}
}
