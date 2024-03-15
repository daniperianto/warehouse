package com.ruangmain.warehouse.service;

import com.ruangmain.warehouse.dto.auth.LoginRequest;
import com.ruangmain.warehouse.dto.auth.RegisterRequest;
import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.libs.auth.JwtService;
import com.ruangmain.warehouse.mapper.Mapper;
import com.ruangmain.warehouse.mapper.impl.UserMapper;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper mapper;

    public ResponseUser register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        return mapper.mapTo(repository.save(user));
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public String login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = repository.findByEmail(loginRequest.getEmail()).orElseThrow();
        return jwtService.generateToken(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    public List<ResponseUser> findAll() {
        return mapper.mapTo(repository.findAll());
    }
}
