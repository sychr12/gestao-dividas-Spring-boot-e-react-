package com.financas.backend.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financas.backend.user.dto.LoginRequest;
import com.financas.backend.user.dto.UserRequest;
import com.financas.backend.user.dto.UserResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        try {
            User user = new User();
            user.setUsername(request.username());
            user.setEmail(request.email());
            user.setPassword(request.password());

            User savedUser = service.salvar(user);

            UserResponse response = new UserResponse(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getEmail()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            // Log do erro
            throw e; // Será tratado pelo @ControllerAdvice
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            User user = service.login(request);

            UserResponse response = new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail()
            );
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Log do erro
            throw e; // Será tratado pelo @ControllerAdvice
        }
    }
}