package co.edu.parcial.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@EnableConfigurationProperties(DbSchemaProperties.class)
public class R2dbcInitConfig {
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory, DbSchemaProperties props, ResourceLoader loader) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(loader.getResource(props.schema())));
        return initializer;
    }
}

