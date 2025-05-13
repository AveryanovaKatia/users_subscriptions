package ru.project.mappers;

import ru.project.dto.UserRequestDto;
import ru.project.dto.UserResponseDto;
import ru.project.models.User;

public class UserMapper {

    public static User toUser(final UserRequestDto userRequestDto) {

        final User user = new User();

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());

        return user;
    }

    public static UserResponseDto toUserResponseDto(final User user) {

        final UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setSubscriptions(
                user.getSubscriptions().stream()
                .map(SubscriptionMapper::toSubscriptionResponseDto)
                .toList());

        return userResponseDto;
    }
}
