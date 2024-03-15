package com.ruangmain.warehouse.controller;

import com.ruangmain.warehouse.dto.auth.LoginRequest;
import com.ruangmain.warehouse.dto.auth.RegisterRequest;
import com.ruangmain.warehouse.dto.auth.ResponseUser;
import com.ruangmain.warehouse.model.User;
import com.ruangmain.warehouse.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        // check if user already exists or not
        Optional<User> user = service.findByEmail(request.getEmail());
        if (user.isPresent()) return ResponseEntity.badRequest().body("email already exist");

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ResponseUser>> getAllUser() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getUser(@PathVariable @Parameter(example = "2") Long id) {
        Optional<User> user = service.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.badRequest().body("user doesn't exist!");
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable @Parameter(example = "2") Long id) {
        // can only delete role user
        Optional<User> user = service.findById(id);
        if (user.isPresent() && user.get().getRole().contains("ADMIN")) {
            return ResponseEntity.badRequest().body("cannot delete admin");
        }

        service.delete(id);

        return ResponseEntity.status(204).build();
    }
}
