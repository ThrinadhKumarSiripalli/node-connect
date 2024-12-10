package com.facets.cloud.node.connect;

import com.facets.cloud.node.connect.config.DatabaseConfig;
import com.facets.cloud.node.connect.config.SecurityConfig;
import com.facets.cloud.node.connect.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DatabaseConfig.class, WebConfig.class, SecurityConfig.class})
@EntityScan("com.facets.cloud.node.connect.model")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
