package co.jackson.util.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "marvel.api")
public class MarvelApiProperties {
    private String baseUrl;
    private String charactersEndpoint;
    private String publicKey;
    private String privateKey;

}
