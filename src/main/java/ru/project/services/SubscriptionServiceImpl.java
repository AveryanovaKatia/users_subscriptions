package ru.project.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.dto.SubscriptionRequestDto;
import ru.project.dto.SubscriptionResponseDto;
import ru.project.dto.UserResponseDto;
import ru.project.exception.NotFoundException;
import ru.project.mappers.SubscriptionMapper;
import ru.project.mappers.UserMapper;
import ru.project.models.Subscription;
import ru.project.repositories.SubscriptionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public SubscriptionResponseDto createSubscription(final SubscriptionRequestDto subscriptionRequestDto) {
        log.info("Запрос на добавление подписки.");
        final Subscription subscription =
                subscriptionRepository.save(SubscriptionMapper.toSubscription(subscriptionRequestDto));
        log.info("Подписка успешно добавлена под id {}", subscription.getId());
        return SubscriptionMapper.toSubscriptionResponseDto(subscription);
    }

    @Override
    public void deleteSubscription(final Long id) {
        log.info("Запрос на удаление подписки с id {}", id);
        subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подписки с id = {} нет." + id));
        subscriptionRepository.deleteById(id);
        log.info("Подписка с id {} успешно удалена ", id);
    }

    @Override
    public List<SubscriptionResponseDto> getTopSubscription(final Long count) {
        log.info("Запрос на получение ТОР-{} пополярных подписок", count);
        final List<Subscription> top = subscriptionRepository.findTopSubscriptionsByUserCount(count);
        log.info("Список ТОР-{} пополярных подписок получен", count);
        return top.stream().map(SubscriptionMapper::toSubscriptionResponseDto).toList();
    }

    @Override
    public List<UserResponseDto> getUsersBySubscriptionId(final Long id) {
        log.info("Запрос на получение всех пользователей, оформивших подписку с id = {}", id);
        final Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Подписки с id = {} нет." + id));
        log.info("Список всех пользователей, оформивших подписку с id = {} успешно получен", id);
        return subscription.getUsers().stream()
                .map(UserMapper::toUserResponseDto)
                .toList();
    }
}
