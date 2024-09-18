package com.sh.financial.auth.web.controller;

import com.sh.financial.auth.web.model.req.RegisterReq;
import com.sh.financial.auth.web.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/private")
public class PrivateController {
//    private AuthService authService;
//    public PrivateController(AuthService authService) {
//        this.authService = authService;
//    }
    @RequestMapping("/message")
    public String getMessage() {
        return "Hello from private API controller";
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@Validated @RequestBody RegisterReq registerReq) {
//        String response = authService.register(registerReq);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
}
