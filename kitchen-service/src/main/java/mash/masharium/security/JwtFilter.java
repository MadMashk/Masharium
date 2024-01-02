package mash.masharium.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.TokenValidationResponse;
import mash.masharium.exception.model.AuthorizeException;
import mash.masharium.integration.client.AuthServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Objects;

@Slf4j
//@Component
public class JwtFilter{

    private final AuthServiceClient authServiceClient;

    private final HandlerExceptionResolver resolver;

    public JwtFilter(AuthServiceClient authServiceClient,
                     @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.authServiceClient = authServiceClient;
        this.resolver = resolver;
    }
/*
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        log.info("фильтр");
        String authorization = httpServletRequest.getHeader("Authorization");
        String token;
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            try {
                TokenValidationResponse response = authServiceClient.validateUsersToken(new Jwt(token));
                if (Boolean.FALSE.equals(response.getSuccess())) {
                    throw new AuthorizeException("Ошибка авторизации в системе");
                }
            } catch
            (Exception e) {
                log.error(e.getMessage());
                resolver.resolveException(httpServletRequest, httpServletResponse, null, new AuthorizeException("Ошибка авторизации в системе"));
            }
        }
    }
*/
}
