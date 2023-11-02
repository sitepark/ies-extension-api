package com.sitepark.ies.extension.api.exeptions;

import com.sitepark.ies.extension.api.Extension;

public class InvalidExtensionClassException extends ExtensionException {

	private final Class<? extends Extension> extensionClass;

	private static final long serialVersionUID = 1L;

	public InvalidExtensionClassException(Class<? extends Extension> extensionClass, String message) {
		super(message);
		this.extensionClass = extensionClass;
	}

	public InvalidExtensionClassException(Class<? extends Extension> extensionClass, String message, Throwable t) {
		super(message, t);
		this.extensionClass = extensionClass;
	}

	public Class<? extends Extension> getExtensionClass() {
		return this.extensionClass;
	}

}
