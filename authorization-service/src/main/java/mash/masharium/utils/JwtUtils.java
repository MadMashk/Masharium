package mash.masharium.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import mash.masharium.configuration.JwtProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private static final Logger logger = LogManager.getLogger(JwtUtils.class);
    private final JwtProperties jwtProperties;

    public String createToken(UUID deviceId) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getJwtSecret());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, jwtProperties.getJwtExpirationDays());
        Date date = calendar.getTime();
        return JWT.create()
                .withIssuedAt(date)
                .withClaim("deviceId", deviceId.toString())
                .sign(algorithm);
    }

    public void validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getJwtSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            throw new JWTVerificationException(exception.getMessage(), exception);
        }
    }

    public UUID getIdFromJWTToken(String jwt) {
        return UUID.fromString(JWT.decode(jwt).getClaim("deviceId").asString());
    }

}
