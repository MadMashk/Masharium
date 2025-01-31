package mash.masharium.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "client-settings")
public class ClientProperties {
    private String authServiceUrl;
    private String orderServiceUrl;
}
