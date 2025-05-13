package ru.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionRequestDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String serviceName;
}
