package com.sh.financial.auth.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @RequestMapping("/message")
    public String getMessage() {
        return "Hello from public API controller";
    }
}
