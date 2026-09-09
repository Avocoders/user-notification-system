package io.github.avocoders.userservicespring.dto;

import java.time.LocalDateTime;

public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final Integer age;
    private final LocalDateTime createdAt;

    public UserResponse( Long id, String name, String email, Integer age, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public Integer getAge() {
        return this.age;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
}
