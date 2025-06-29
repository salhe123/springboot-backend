package com.example.controller;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequest;
import com.example.dto.RegisterRequest;
import com.example.service.AuthService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

   @PostMapping("/register")
public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
    authService.register(request);
    // Use a Map or a DTO for custom JSON
    Map<String, String> body = new HashMap<>();
    body.put("message", "User registered successfully");
    return ResponseEntity.ok(body);
}

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}