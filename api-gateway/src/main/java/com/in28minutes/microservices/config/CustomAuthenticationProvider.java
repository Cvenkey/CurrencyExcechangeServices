package com.in28minutes.microservices.config;

import com.in28minutes.microservices.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements ReactiveAuthenticationManager {

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    private final JwtService jwtTokenProvider;

    public CustomAuthenticationProvider(JwtService jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        return jwtTokenProvider.validateToken(token)
                .filter(valid -> valid)
                .map(valid -> new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                        token, this.getAuthorities(token)));
    }

    public List<GrantedAuthority> getAuthorities(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String role = claims.get("role", String.class);
        return List.of(new SimpleGrantedAuthority(role));
    }
}
