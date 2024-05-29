package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.dto.request.UserRequest;
import com.nahorniak.inventorymanagementservice.dto.response.UserDto;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.persistance.UserEntity;
import com.nahorniak.inventorymanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final SelmaMapper selma;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username ("+ username +") not found"));
    }

    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(selma::toDto)
                .toList();
    }

    public UserDto getById(Long id) throws ResourceNotFoundException{
        return userRepository.findById(id)
                .map(selma::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id (" + id + ") not found!"));
    }

    public UserDto updateUser(UserRequest request, Authentication connectedUser) {
        Long userId = ((UserEntity) connectedUser.getPrincipal()).getId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id (" + userId + ") not found!"));
        Optional.ofNullable(request.getFirstName()).ifPresent(user::setFirstName);
        Optional.ofNullable(request.getLastName()).ifPresent(user::setLastName);
        Optional.ofNullable(request.getEmail()).ifPresent(user::setEmail);
        return selma.toDto(userRepository.save(user));
    }
}
