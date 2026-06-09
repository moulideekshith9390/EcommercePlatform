package com.ecommerce.controller;

import com.ecommerce.model.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
        @Autowired private UserRepository userRepository;
            @Autowired private PasswordEncoder passwordEncoder;
                @Autowired private JwtUtil jwtUtil;
                    @Autowired private UserDetailsService userDetailsService;

                        @PostMapping("/register")
                            public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
                                    if (userRepository.existsByEmail(req.getEmail())) {
                                                return ResponseEntity.status(HttpStatus.CONFLICT)
                                                                    .body(Map.of("error", "Email already registered"));
                                                                            }
                                                                                    User user = User.builder()
                                                                                                    .name(req.getName())
                                                                                                                    .email(req.getEmail())
                                                                                                                                    .password(passwordEncoder.encode(req.getPassword()))
                                                                                                                                                    .role(Role.CUSTOMER)
                                                                                                                                                                    .build();
                                                                                                                                                                            userRepository.save(user);
                                                                                                                                                                                    return ResponseEntity.status(HttpStatus.CREATED)
                                                                                                                                                                                                    .body(Map.of("message", "User registered successfully"));
                                                                                                                                                                                                        }
                                                                                                                                                                                                        
                                                                                                                                                                                                            @PostMapping("/login")
                                                                                                                                                                                                                public ResponseEntity<?> login(@RequestBody LoginRequest req) {
                                                                                                                                                                                                                        try {
                                                                                                                                                                                                                                    authenticationManager.authenticate(
                                                                                                                                                                                                                                                        new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
                                                                                                                                                                                                                                                                } catch (Exception e) {
                                                                                                                                                                                                                                                                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                                                                                                                                                                                                                                                                                .body(Map.of("error", "Invalid email or password"));
                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                UserDetails userDetails = userDetailsService.loadUserByUsername(req.getEmail());
                                                                                                                                                                                                                                                                                                                        String token = jwtUtil.generateToken(userDetails);
                                                                                                                                                                                                                                                                                                                                return ResponseEntity.ok(Map.of("token", token, "email", req.getEmail()));
                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                    
                                                                                                                                                                                                                                                                                                                                        @Data
                                                                                                                                                                                                                                                                                                                                            static class RegisterRequest {
                                                                                                                                                                                                                                                                                                                                                    private String name;
                                                                                                                                                                                                                                                                                                                                                            private String email;
                                                                                                                                                                                                                                                                                                                                                                    private String password;
                                                                                                                                                                                                                                                                                                                                                                        }
                                                                                                                                                                                                                                                                                                                                                                        
                                                                                                                                                                                                                                                                                                                                                                            @Data
                                                                                                                                                                                                                                                                                                                                                                                static class LoginRequest {
                                                                                                                                                                                                                                                                                                                                                                                        private String email;
                                                                                                                                                                                                                                                                                                                                                                                                private String password;
                                                                                                                                                                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                                                                                                                                                                    }
