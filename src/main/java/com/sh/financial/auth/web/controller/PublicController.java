package com.sh.financial.auth.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class
PublicController {
    @RequestMapping("/message")
    public ResponseEntity<String> getMessage() {

        return ResponseEntity.ok("Hello from public API controller");
    }

    @RequestMapping("/message2")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getMessage2() {

        return ResponseEntity.ok("Hello ADMIN");
    }

    @RequestMapping("/message3")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getMessage3() {

        return ResponseEntity.ok("Hello USER");
    }
}
