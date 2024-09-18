package com.sh.financial.auth.security;

import com.sh.financial.auth.web.service.JwtService;
import com.sh.financial.utility.web.model.res.ApiResp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        //get jwt token from http request
        String token = getTokenFromRequest(request);
        System.out.println(SecurityContextHolder.getContext().getAuthentication());

        if(SecurityContextHolder.getContext().getAuthentication() != null){
            filterChain.doFilter(request, response);
            System.out.println("fassalkjaslk");
        }
        //validate Token

        if (token != null ){
            //get username from token
            String username = null;

            try {
                username = jwtService.decodeJwtToken(token).getSubject();
                System.out.println("Test: " + jwtService.decodeJwtToken(token).getSubject());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Username: " + username);
            //get user from database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //create authentication object
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            //set authentication object to security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //continue filter execution
        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length()); // cut the "Bearer " string
        }
        return null;

    }
}