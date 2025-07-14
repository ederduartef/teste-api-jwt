package com.jwtapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jwtapi.model.User;
import com.jwtapi.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        if ("admin".equals(user.getUsername()) && "123".equals(user.getPassword())) {
            String token = jwtService.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @GetMapping("/validar")
    public ResponseEntity<String> validarToken(@RequestParam String token) {
        boolean valid = jwtService.validateToken(token);
        if (valid) {
            String usuario = jwtService.getUsernameForToken(token);
            return ResponseEntity.ok("Token valido para usario" + usuario);
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }
}
