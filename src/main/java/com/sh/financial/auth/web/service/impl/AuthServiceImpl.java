package com.sh.financial.auth.web.service.impl;

import com.sh.financial.auth.entity.Role;
import com.sh.financial.auth.entity.User;
import com.sh.financial.auth.exception.ApplicationErrorCode;
import com.sh.financial.auth.exception.AuthAPIException;
import com.sh.financial.auth.repository.RoleRepository;
import com.sh.financial.auth.repository.UserRepository;
import com.sh.financial.auth.web.model.req.RegisterReq;
import com.sh.financial.auth.web.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Override
    public String register(RegisterReq registerReq) {
        if(userRepository.existsByEmail(registerReq.getEmail())) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        User user = new User();
        user.setName(registerReq.getName());
        user.setEmail(registerReq.getEmail());
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");

        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return "User Registered Successfully!";
    }
}
