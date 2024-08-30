package com.sh.financial.auth.web.controller;

import org.springframework.http.ResponseEntity;
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
}
