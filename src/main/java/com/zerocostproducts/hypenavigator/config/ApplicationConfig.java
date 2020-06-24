package com.zerocostproducts.hypenavigator.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Application config to read 'application.properties' attributes
 */
@Service
public class ApplicationConfig {
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfig.class);

  private Environment environment;

  @Autowired
  public ApplicationConfig(Environment environment) {
    this.environment = environment;
  }

  public String getProperty(ConfigKey key) {
    return environment.getProperty(key.getName());
  }
}