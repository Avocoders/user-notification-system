package io.github.avocoders.userservicespring.mapper;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(CreateUserRequest request) {
        return new User(request.getName(), request.getEmail(), request.getAge());
    }
    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getCreatedAt());
    }
    public void updateEntity(User user, UpdateUserRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
    }
}
