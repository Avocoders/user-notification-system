package io.github.avocoders.userservicespring.controller;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
            @Valid @RequestBody
            CreateUserRequest request
    ) {
        return userService.create(request);
    }

    @GetMapping("/{id}")
    public UserResponse getById(
            @PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable
            Long id,
            @Valid @RequestBody
            UpdateUserRequest request
    ) {
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            Long id
    ) {
        userService.delete(id);
    }

}
