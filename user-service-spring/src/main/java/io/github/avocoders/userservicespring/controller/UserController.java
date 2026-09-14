package io.github.avocoders.userservicespring.controller;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public EntityModel<UserResponse> create(
            @Valid @RequestBody
            CreateUserRequest request
    ) {
        UserResponse userResponse = userService.create(request);
        Link self = linkTo(methodOn(UserController.class).getById(userResponse.id())).withSelfRel();
        Link users = linkTo(methodOn(UserController.class).getAll()).withRel("users");
        EntityModel<UserResponse> model = EntityModel.of(userResponse);
        model.add(self);
        model.add(users);
        return model;
    }

    @Operation(summary = "Получить пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользователь найден")
    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    @GetMapping("/{id}")
    public EntityModel<UserResponse> getById(
            @PathVariable Long id) {
        UserResponse userResponse = userService.getById(id);
        Link self = linkTo(methodOn(UserController.class).getById(userResponse.id())).withSelfRel();
        Link users = linkTo(methodOn(UserController.class).getAll()).withRel("users");
        EntityModel<UserResponse> model = EntityModel.of(userResponse);
        model.add(self);
        model.add(users);
        return model;
    }

    @Operation(summary = "Получить список всех пользователей")
    @ApiResponse(responseCode = "200", description = "Список всех пользователей получен")
    @GetMapping
    public CollectionModel<UserResponse> getAll() {
        List<UserResponse> userResponseList = userService.getAll();
        Link self = linkTo(methodOn(UserController.class).getAll()).withSelfRel();
        CollectionModel<UserResponse> model = CollectionModel.of(userResponseList);
        model.add(self);
        return model;
    }

    @Operation(summary = "Обновить пользователя по id")
    @ApiResponse(responseCode = "200", description = "Пользователь обновлен")
    @PutMapping("/{id}")
    public EntityModel<UserResponse> update(
            @PathVariable
            Long id,
            @Valid @RequestBody
            UpdateUserRequest request
    ) {
        UserResponse userResponse = userService.update(id, request);
        Link self = linkTo(methodOn(UserController.class).getById(userResponse.id())).withSelfRel();
        Link users = linkTo(methodOn(UserController.class).getAll()).withRel("users");
        EntityModel<UserResponse> model = EntityModel.of(userResponse);
        model.add(self);
        model.add(users);
        return model;
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
