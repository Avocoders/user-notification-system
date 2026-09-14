package io.github.avocoders.userservicespring.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Запрос на обновление пользователя")
public class UpdateUserRequest {
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

    public UpdateUserRequest() {}

}
