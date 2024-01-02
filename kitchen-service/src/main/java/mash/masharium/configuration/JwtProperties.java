package mash.masharium.configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties("jwt")
@Configuration
@Getter
@Setter
public class JwtProperties {
    @NotBlank
    private String jwtSecret;
    @NotEmpty
    private Integer jwtExpirationDays;
}
