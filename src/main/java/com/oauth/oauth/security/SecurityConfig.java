package com.oauth.oauth.security;//package com.kredmint.lib.core.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.authentication.AuthenticationManagerResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private OpaqueTokenIntrospector opaqueTokenIntrospector;

    @Primary
    @Bean
    public AuthenticationManagerResolver<HttpServletRequest> authenticationManagerResolver() {

        AuthenticationManager jwtAuthManager = new ProviderManager(Collections.singletonList(new JwtAuthenticationProvider(jwtDecoder)));
        AuthenticationManager opaqueAuthManager = new ProviderManager(Collections.singletonList(new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector)));

        return request -> {
            String authorization = request.getHeader("Authorization");
            if (authorization != null && authorization.startsWith("Bearer ")) {
                if (isJwt(authorization)) {
                    return jwtAuthManager;
                } else {
                    return opaqueAuthManager;
                }
            }
            return jwtAuthManager;
        };
    }

    public boolean isJwt(String token) {
        String bearer = token.split(" ")[1];
        if (token.contains("Bearer")) {
            List<String> tokens = Arrays.asList(bearer.split("\\."));
            return tokens.size() == 3;
        }
        return false;
    }
}
