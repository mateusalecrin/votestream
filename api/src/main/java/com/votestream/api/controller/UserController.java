package com.votestream.api.controller;

import com.votestream.api.dto.UserRequest;
import com.votestream.api.dto.UserResponse;
import com.votestream.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
        var created = userService.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        var users = userService.findAll().stream().map(UserResponse::from).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(UserResponse.from(userService.findById(id)));
    }
}