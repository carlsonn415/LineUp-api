package com.example.lineup_api.controllers;

import com.example.lineup_api.dtos.*;
import com.example.lineup_api.mappers.UserMapper;
import com.example.lineup_api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private final Set<String> SortByOptions = Set.of(
            "id",
            "username",
            "email"
    );

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestHeader(name = "api_key") String apiKey,
            @RequestParam(required = false, defaultValue = "", name = "sort") String sortBy
    ) {
        if (!Objects.equals(apiKey, "1234")) {
            return ResponseEntity.badRequest().build();
        }

        if (!SortByOptions.contains(sortBy)) {
            sortBy = "id";
        }

        var result = userRepository.findAll(Sort.by(sortBy))
                .stream()
                .map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(result);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    // Get user by username
    @GetMapping


    // Post user
    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody RegisterUserRequest request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var user = userMapper.toEntity(request);

        // ensure user has inputted an email, password, and username
        if (user.getEmail() == null || user.getPassword() == null ||
                user.getEmail().isEmpty() || user.getPassword().isEmpty() ||
                user.getUsername() == null || user.getUsername().isEmpty()
        ) {
            return ResponseEntity.badRequest().build();
        }

        // ensure email or username is not already in use
        if (userRepository.existsByEmail(user.getEmail()) || userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        // Hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // save user
        userRepository.save(user);

        // return uri to new user
        var userDto = userMapper.toDto(user);
        var uri = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    // Put new Username
    @PutMapping("/{id}/change-username")
    public ResponseEntity<UserDto> changeUsername(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangeUsernameRequest request
    ) {
        // ensure the username field is not null or empty
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // ensure username is not already in use
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().build();
        }

        // ensure user exists
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.changeUsername(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/{id}/change-email")
    public ResponseEntity<UserDto> changeEmail(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangeEmailRequest request
    ) {
        // ensure the email field is not null or empty
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // ensure email is not already in use
        if (userRepository.existsByUsername(request.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        // ensure user exists
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userMapper.changeEmail(request, user);
        userRepository.save(user);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable(name = "id") Long id,
            @RequestBody ChangePasswordRequest request
    ) {
        // ensure the new password is not null or empty
        if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // ensure user exists
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        // ensure old password matches one found in the database
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userMapper.changePassword(request, user);
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }
}
