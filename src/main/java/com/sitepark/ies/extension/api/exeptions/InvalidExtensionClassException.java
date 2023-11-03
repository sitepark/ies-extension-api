package com.sitepark.ies.extension.api.exeptions;

import com.sitepark.ies.extension.api.Extension;

public class InvalidExtensionClassException extends ExtensionException {

	private static final long serialVersionUID = 6670400793703514354L;

	private final Class<? extends Extension> extensionClass;

	public InvalidExtensionClassException(
			Class<? extends Extension> extensionClass,
			String message) {
		super(message);
		this.extensionClass = extensionClass;
	}

	public InvalidExtensionClassException(
			Class<? extends Extension> extensionClass,
			String message,
			Throwable t) {
		super(message, t);
		this.extensionClass = extensionClass;
	}

	public Class<? extends Extension> getExtensionClass() {
		return this.extensionClass;
	}

}
