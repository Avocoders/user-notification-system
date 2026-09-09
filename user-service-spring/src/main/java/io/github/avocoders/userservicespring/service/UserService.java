package io.github.avocoders.userservicespring.service;

import io.github.avocoders.userservicespring.dto.CreateUserRequest;
import io.github.avocoders.userservicespring.dto.UpdateUserRequest;
import io.github.avocoders.userservicespring.dto.UserResponse;
import io.github.avocoders.userservicespring.entity.User;
import io.github.avocoders.userservicespring.exception.UserNotFoundException;
import io.github.avocoders.userservicespring.mapper.UserMapper;
import io.github.avocoders.userservicespring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse create(CreateUserRequest request) {
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse getById(Long id) {
        User foundUser = findUserById(id);
        return userMapper.toResponse(foundUser);
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toResponse).toList();
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        User foundUser = findUserById(id);
        userMapper.updateEntity(foundUser, request);
        return userMapper.toResponse(foundUser);
    }

    public void delete(Long id) {
        User foundUser = findUserById(id);
        userRepository.delete(foundUser);
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
