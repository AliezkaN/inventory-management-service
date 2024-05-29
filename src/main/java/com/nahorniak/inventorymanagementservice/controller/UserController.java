package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.request.UserRequest;
import com.nahorniak.inventorymanagementservice.dto.response.UserDto;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SelmaMapper selma;

    @GetMapping
    List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping("/whoami")
    public UserDto getConnectedUser(Authentication connectedUser) {
        return selma.toDto((UserEntity) connectedUser.getPrincipal());
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserRequest request, Authentication connectedUser){
        return userService.updateUser(request, connectedUser);
    }
}
