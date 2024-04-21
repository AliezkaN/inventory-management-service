package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.domain.User;
import com.nahorniak.inventorymanagementservice.dto.request.SignUpRequest;
import com.nahorniak.inventorymanagementservice.dto.response.AuthenticationResponse;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.RoleEntity;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.repository.RoleRepository;
import com.nahorniak.inventorymanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SelmaMapper selma;
//    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(SignUpRequest request){
        String roleName = request.getRole().toUpperCase();
        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role with such name (" + roleName + ") not found!"));
        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        UserEntity savedUser = userRepository.save(userEntity);
        User userDomain = selma.toDomain(savedUser);
        String jwtToken = jwtService.generateToken(userDomain);
        String refreshToken = jwtService.generateRefreshToken(userDomain);
//        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractEmail(refreshToken);
//        if (userEmail != null) {
//            User user = this.repository.findByEmail(userEmail)
//                    .map(selma::toDomain)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                String accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                AuthenticationResponse authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }
}
