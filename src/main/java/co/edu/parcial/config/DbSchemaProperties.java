package co.edu.parcial.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.db")
public record DbSchemaProperties(String schema) {
}

