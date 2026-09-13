package io.github.avocoders.userservicespring.controller;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Создать пользователя")
    @ApiResponse(responseCode = "201", description = "Пользователь создан")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
            @Valid @RequestBody
            CreateUserRequest request
    ) {
        return userService.create(request);
    }

    @Operation(summary = "Получить пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/{id}")
    public UserResponse getById(
            @PathVariable Long id) {
        return userService.getById(id);
    }

    @Operation(summary = "Получить список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список всех пользователей получен")
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @Operation(summary = "Обновить пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable
            Long id,
            @Valid @RequestBody
            UpdateUserRequest request
    ) {
        return userService.update(id, request);
    }

    @Operation(summary = "Удалить пользователя по id")
    @ApiResponse(responseCode = "204", description = "Пользователь удален")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable
            Long id
    ) {
        userService.delete(id);
    }

}
