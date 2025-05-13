package ru.project.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private List<SubscriptionResponseDto> subscriptions;
}
