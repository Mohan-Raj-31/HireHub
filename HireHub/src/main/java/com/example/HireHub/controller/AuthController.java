package com.example.HireHub.controller;

import com.example.HireHub.DTO.LoginRequest;
import com.example.HireHub.model.Users;
import com.example.HireHub.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500","http://localhost:63342"})
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestBody Users users){
        return ResponseEntity.ok(authService.registerUser(users));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return ResponseEntity.ok(authService.login(loginRequest,request));
    }

}
