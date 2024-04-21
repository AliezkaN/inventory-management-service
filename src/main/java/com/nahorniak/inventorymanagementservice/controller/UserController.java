package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.response.UserDto;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.service.UserService;
import lombok.RequiredArgsConstructor;
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
        return userService.getAll()
                .stream()
                .map(selma::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return selma.toDto(userService.getById(id));
    }

}
