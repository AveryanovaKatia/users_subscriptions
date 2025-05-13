package ru.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.project.group.CreateGroup;

@Data
public class UserRequestDto {

    @NotBlank(groups = {CreateGroup.class})
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank(groups = {CreateGroup.class})
    @Email(message = "Емейл должен содержать @ и наименование")
    @Size(min = 6, max = 255)
    private String email;
}
