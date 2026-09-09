package io.github.avocoders.userservicespring.dto;

import jakarta.validation.constraints.*;

public class UpdateUserRequest {

    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @NotNull
    @Min(0)
    @Max(150)
    private Integer age;

    public UpdateUserRequest() {}

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAge(Integer age) {
        this.age = age;
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

}
