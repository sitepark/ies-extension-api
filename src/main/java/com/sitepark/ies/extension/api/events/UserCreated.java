package com.sitepark.ies.extension.api.events;

public class UserCreated {

  private final long id;

  protected UserCreated(Builder builder) {
    this.id = builder.id;
  }

  public long getId() {
    return this.id;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {

    private long id;

    private Builder() {}

    public Builder id(long id) {
      if (id <= 0) {
        throw new IllegalArgumentException("Id should be greater then 0");
      }
      this.id = id;
      return this;
    }

    public UserCreated build() {
      return new UserCreated(this);
    }
  }
}
