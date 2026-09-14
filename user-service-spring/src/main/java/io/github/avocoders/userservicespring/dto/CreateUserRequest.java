package io.github.avocoders.userservicespring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Запрос на создание пользователя")
public class CreateUserRequest {
    @Schema(description = "Имя пользователя", example = "Nika")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Schema(description = "Почта пользователя", example = "Nika@ya.ru")
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
    @Schema(description = "Возраст пользователя", example = "20")
    @NotNull
    @Min(0)
    @Max(150)
    private Integer age;

    public CreateUserRequest() {}

}
