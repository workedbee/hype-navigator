package com.zerocostproducts.hypenavigator.config;

/**
 * Keys for application configuration file
 */
public enum ConfigKey {
  YOUTUBE_API_KEY("youtube.api.key"),
  APPLICATION_NAME("application.name");

  private String name;

  ConfigKey(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
