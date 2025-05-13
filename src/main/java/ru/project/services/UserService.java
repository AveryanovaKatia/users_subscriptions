package ru.project.services;

import org.springframework.data.domain.Pageable;
import ru.project.dto.UserRequestDto;
import ru.project.dto.UserResponseDto;

import java.util.List;

public interface UserService {


    UserResponseDto createUser(final UserRequestDto userDto);

    UserResponseDto getUser(final Long id);

    List<UserResponseDto> getAllUsers(final List<Long> userIds,
                                     final Pageable pageable);

    UserResponseDto updateUser(final Long id,
                               UserRequestDto userDto);

    void deleteUser(final Long id);

    UserResponseDto addSubscription(final Long id,
                                    final Long sub_id);

    void deleteSubscription(final Long id,
                            final Long sub_id);
}
