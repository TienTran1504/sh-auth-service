package com.sh.financial.auth.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.sh.financial.auth.web.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);
        System.out.println("Before Set context: " + SecurityContextHolder.getContext().getAuthentication());
        if (token != null){
            System.out.println("Token found");
            String username = null;
            JWTClaimsSet payload = null;

            try {
                //String publicKey = getPublicKeyFromAuthServer("http://localhost:8080/authorize/token", token);
                String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvJ1A2jb4jIqHVeGXp" +
                        "+dxIJxgpkNvbUxFW7mfHPHaqqaz6gcx81CZJxDj7ewm+pzxB6bA7JhByg5AtKUhMHeWUNJBUJqAOlWBWyYVWTBcOYBmwjNfbo/jWHneZyjnDUKRPpewEItfQ8D1aeMw45P3uJGUFyLXBIx88ok7a8pX+0Jz2K/Q+PrFLvVMRmtoV40e28hqA7pUMlhS3t0aZ5MmHJyJkJEA4cil2H6lwFDKQYfQkHLWjYdUhWkv6/2wX8HsHxCTKqpSO3EPBL8kIoZ3TGSkwfYoHF/GfzloOII2z4mlC3i+R+YktR70TDWTWQWLWhlV23+D2o/XK39xxByTBQIDAQAB";

                payload = jwtService.decodeJwtToken(token, publicKey);
                username = payload.getSubject();
                System.out.println("username------" + username);
                System.out.println("payload------" + payload);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Set<GrantedAuthority> authorities = new HashSet<>();

            List<Map<String, String>> roles = (List<Map<String, String>>) payload.getClaim("roles");

            roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.get("authority")))
                    .forEach(authorities::add);

            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                    username, "N/A", authorities
            );
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("After Set context: " + SecurityContextHolder.getContext().getAuthentication());
        }
        filterChain.doFilter(request, response);
    }

    private String getPublicKeyFromAuthServer(String tokenUrl, String bearerToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("Authorization", "Bearer " + bearerToken);
        MultiValueMap<String, String> bodyParams = new LinkedMultiValueMap<>();
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(bodyParams, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                String privateKey = response.getBody();
                logger.info("Public Key retrieved: {}", privateKey);
                return privateKey;
            } else {
                logger.error("Failed to retrieve public key, status code: {}", response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving public key: {}", e.getMessage());
        }
        return null;
    }


    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length()); // cut the "Bearer " string
        }
        return null;
    }
}