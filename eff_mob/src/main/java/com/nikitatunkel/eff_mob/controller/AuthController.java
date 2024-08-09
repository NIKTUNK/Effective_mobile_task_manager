package com.nikitatunkel.eff_mob.controller;

import com.nikitatunkel.eff_mob.model.User;
import com.nikitatunkel.eff_mob.service.UserService;
import com.nikitatunkel.eff_mob.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, request.get("password")));
            String token = jwtTokenProvider.createToken(username, userService.findByUsername(username).getRoles());
            return ResponseEntity.ok(Map.of("username", username, "token", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Invalid username/password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
