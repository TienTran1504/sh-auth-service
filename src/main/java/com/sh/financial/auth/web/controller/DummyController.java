package com.sh.financial.auth.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sh.financial.auth.web.model.req.DummyReq;
import com.sh.financial.utility.web.model.res.ApiResp;

import jakarta.validation.Valid;

@RestController("/")
public class DummyController {
  
  @GetMapping
  public ResponseEntity<?> get() {
    return ResponseEntity.ok().body(ApiResp.builder()
        .success(true)
        .build());
  }
  
  @PostMapping
  public ResponseEntity<?> post(@Valid @RequestBody DummyReq req) {
    return ResponseEntity.ok().body(ApiResp.builder()
        .success(true)
        .data(req)
        .build());
  }
}