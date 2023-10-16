package com.sitepark.ies.extension.api.events;

public class UserPurged {

	private final long id;

	protected UserPurged(Builder builder) {
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
			if (id <= 0) {
				throw new IllegalArgumentException("Id should be greater then 0");
			}
			this.id = id;
			return this;
		}

		public UserPurged build() {
			return new UserPurged(this);
		}
	}
}
