package com.in28minutes.microservices.config;

import com.in28minutes.microservices.services.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements ServerAuthenticationConverter {

    private final JwtService jwtTokenProvider;

    public JwtAuthenticationFilter(JwtService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(ex -> Mono.justOrEmpty(ex.getRequest().getHeaders().getFirst("Authorization")))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))  // Extract token after "Bearer "
                .flatMap(token -> {
                    if (jwtTokenProvider.validateToken(token).block().equals(Boolean.TRUE)) {
                        return Mono.just(new UsernamePasswordAuthenticationToken(
                                jwtTokenProvider.getUsername(token),
                                token,
                                jwtTokenProvider.getAuthorities(token)));
                    } else {
                        return Mono.empty();
                    }
                });
    }
}
