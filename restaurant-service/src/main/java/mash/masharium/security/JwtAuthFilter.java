package mash.masharium.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.client.AuthServiceClient;
import mash.masharium.security.mapper.UserDetailsMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String HEADER_NAME = "Authorization";

    private final UserDetailsMapper userDetailsMapper;

    private final AuthServiceClient authServiceClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HEADER_NAME);

        if (StringUtils.isEmpty(header) || !StringUtils.startsWith(header, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            throw new RuntimeException("token is invalid");
        }

        String requestHeader = header.substring(BEARER_PREFIX.length());

        User user = userDetailsMapper.mapUserDetailsDtoToUser(authServiceClient.validateUsersToken(new Jwt(requestHeader)).getBody());


        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
