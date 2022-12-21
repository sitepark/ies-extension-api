package com.sitepark.ies.extension.api.events;

public class ContentEntryPurged {

	private final long id;

	protected ContentEntryPurged(Builder builder) {
		this.id = builder.id;
	}

	public long getId() {
		return this.id;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {

		private long id;

		private Builder() { }

		public Builder id(long id) {
			assert id > 0;
			this.id = id;
			return this;
		}

		public ContentEntryPurged build() {
			return new ContentEntryPurged(this);
		}
	}
}
