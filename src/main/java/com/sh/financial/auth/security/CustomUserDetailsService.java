package com.sh.financial.auth.security;


import com.sh.financial.auth.entity.Client;
import com.sh.financial.auth.exception.AuthAPIException;
import com.sh.financial.auth.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {

        Client client = clientRepository.findByClientId(clientId);
        if(client == null){
            throw new AuthAPIException(HttpStatus.NOT_FOUND, "client Not Found");
        }

        Set<GrantedAuthority> authorities = client.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                client.getClientId(), client.getClientSecret(), authorities
        );
    }

}
