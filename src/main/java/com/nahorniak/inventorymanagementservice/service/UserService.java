package com.nahorniak.inventorymanagementservice.service;

import com.nahorniak.inventorymanagementservice.domain.User;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import com.nahorniak.inventorymanagementservice.mappers.SelmaMapper;
import com.nahorniak.inventorymanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final SelmaMapper selma;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(selma::toDomain)
                .orElseThrow(() -> new UsernameNotFoundException("User with username ("+ username +") not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll()
                .stream()
                .map(selma::toDomain)
                .toList();
    }

    public User getById(Long id) throws ResourceNotFoundException{
        return userRepository.findById(id)
                .map(selma::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("User with id (" + id + ") not found!"));
    }
}
