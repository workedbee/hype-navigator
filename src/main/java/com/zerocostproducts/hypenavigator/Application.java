package com.zerocostproducts.hypenavigator;

import com.google.api.services.youtube.model.Caption;
import com.zerocostproducts.hypenavigator.youtube.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Main Spring application
 */
//@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication(scanBasePackages = {"com.zerocostproducts.hypenavigator"})
public class Application {
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    SpringApplication.run(com.zerocostproducts.hypenavigator.Application.class, args);
  }

  @Bean
  CommandLineRunner init(Client client) {
    return (args) -> {
      try {
        List<String> ids = client.listMostRatedVideoIds("RU", 40);
        if (ids.isEmpty()) {
          return;
        }
        List<Caption> captions = client.listCaptions(ids.get(0));
        if (captions.isEmpty()) {
          return;
        }
        String content = client.loadCaption(captions.get(0).getId());
        int k = 0;
      } catch (Exception ex) {
        LOG.error("Problem with ids", ex);
      }
    };
  }
}