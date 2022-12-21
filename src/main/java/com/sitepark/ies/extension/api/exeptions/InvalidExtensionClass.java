package com.sitepark.ies.extension.api.exeptions;

import com.sitepark.ies.extension.api.Extension;

public class InvalidExtensionClass extends ExtensionException {

	private Class<? extends Extension> extensionClass;

	private static final long serialVersionUID = 1L;

	public InvalidExtensionClass(Class<? extends Extension> extensionClass, String message) {
		super(message);
	}

	public InvalidExtensionClass(Class<? extends Extension> extensionClass, String message, Throwable t) {
		super(message, t);
	}

	public Class<? extends Extension> getExtensionClass() {
		return this.extensionClass;
	}

}
