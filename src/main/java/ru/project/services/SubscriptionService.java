package ru.project.services;

import ru.project.dto.SubscriptionRequestDto;
import ru.project.dto.SubscriptionResponseDto;
import ru.project.dto.UserResponseDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponseDto createSubscription(final SubscriptionRequestDto subscriptionRequestDto);

    void deleteSubscription(final Long id);

    List<SubscriptionResponseDto> getTopSubscription(final Long count);

    List<UserResponseDto> getUsersBySubscriptionId(final Long id);
}
