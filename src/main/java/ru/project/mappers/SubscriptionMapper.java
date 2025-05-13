package ru.project.mappers;

import ru.project.dto.SubscriptionRequestDto;
import ru.project.dto.SubscriptionResponseDto;
import ru.project.models.Subscription;

public class SubscriptionMapper {

    public static Subscription toSubscription(final SubscriptionRequestDto subscriptionRequestDto) {

        final Subscription subscription = new Subscription();

        subscription.setServiceName(subscriptionRequestDto.getServiceName());

        return subscription;
    }

    public static SubscriptionResponseDto toSubscriptionResponseDto(final Subscription subscription) {

        final SubscriptionResponseDto subscriptionResponseDto = new SubscriptionResponseDto();

        subscriptionResponseDto.setId(subscription.getId());
        subscriptionResponseDto.setServiceName(subscription.getServiceName());

        return subscriptionResponseDto;
    }
}
