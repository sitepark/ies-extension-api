package com.sitepark.ies.extension.api.exeptions;

public class InitializationFailed extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InitializationFailed(String msg) {
		super(msg);
	}

	public InitializationFailed(String msg, Throwable t) {
		super(msg, t);
	}
}
