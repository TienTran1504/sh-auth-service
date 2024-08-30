package com.sh.financial.auth.security;

import com.sh.financial.utility.web.model.res.ApiResp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    public AuthenticationFilter(CustomUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String[] credentials = extractCredentialsFromHeader(request);

            // Extract username and password from the Authorization header

            if (credentials != null) {
                    String email = credentials[0];
                    String password = credentials[1];

                    UsernamePasswordAuthenticationToken authentication = authenticate(email, password);
                    if (authentication != null) {
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            }
            filterChain.doFilter(request, response);

    }

    private String[] extractCredentialsFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
            // Extract base64 encoded credentials
            String base64Credentials = authorizationHeader.substring("Basic ".length());
            // Decode base64 to get "username:password"
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            // Split credentials into username and password
            return credentials.split(":", 2);
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken authenticate(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}