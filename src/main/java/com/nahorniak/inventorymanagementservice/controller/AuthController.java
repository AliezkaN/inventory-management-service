package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.request.SignUpRequest;
import com.nahorniak.inventorymanagementservice.dto.response.AuthenticationResponse;
import com.nahorniak.inventorymanagementservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
