package com.sh.financial.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        System.out.println(request.getHeader("Authorization"));



        // Extract username and password from the Authorization header
        String[] credentials = extractCredentialsFromHeader(request);

        if(credentials == null){
            filterChain.doFilter(request, response);
        }

        if (credentials != null) {
            String email = credentials[0];
            String password = credentials[1];
            try {
                UsernamePasswordAuthenticationToken authentication = authenticate(email, password);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (BadCredentialsException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
            filterChain.doFilter(request, response);
            //set authentication object to security context
        }

        //continue filter execution
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length()); // cut the "Bearer " string
        }
        return null;

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

    private UsernamePasswordAuthenticationToken authenticate(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println(userDetails.getUsername());
        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}