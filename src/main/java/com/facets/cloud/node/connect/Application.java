package com.facets.cloud.node.connect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import({ResourceConfig.class, DatabaseConfig.class, WebConfig.class, ServiceConfig.class})
@EntityScan("com.facets.cloud.node.connect.model")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}