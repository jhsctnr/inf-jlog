package com.jlog.api;

import com.jlog.api.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class JlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(JlogApplication.class, args);
    }

}
