package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.request.AuthenticationRequest;
import com.nahorniak.inventorymanagementservice.dto.request.RegistrationRequest;
import com.nahorniak.inventorymanagementservice.dto.response.AuthenticationResponse;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.persistance.RoleEntity;
import com.nahorniak.inventorymanagementservice.persistance.ShopEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.repository.RoleRepository;
import com.nahorniak.inventorymanagementservice.repository.ShopRepository;
import com.nahorniak.inventorymanagementservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShopRepository shopRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(RegistrationRequest request) {
        String roleName = request.getRole().toUpperCase();
        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role with such name (" + roleName + ") not found!"));
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createDate(LocalDateTime.now())
                .enabled(true)
                .build();
        userRepository.save(userEntity);
        ShopEntity shop = ShopEntity.builder()
                .name(request.getShopName())
                .address(request.getShopAddress())
                .contactNumber(request.getShopContactNumber())
                .description(request.getShopDescription())
                .manager(userEntity)
                .build();
        shopRepository.save(shop);
    }

    public AuthenticationResponse login(AuthenticationRequest request){
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((UserEntity) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (UserEntity) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
