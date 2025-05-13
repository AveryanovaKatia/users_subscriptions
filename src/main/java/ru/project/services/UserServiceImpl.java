package ru.project.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.dto.UserRequestDto;
import ru.project.dto.UserResponseDto;
import ru.project.exception.ConflictException;
import ru.project.exception.NotFoundException;
import ru.project.mappers.UserMapper;
import ru.project.models.Subscription;
import ru.project.models.User;
import ru.project.repositories.SubscriptionRepository;
import ru.project.repositories.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        log.info("Запрос на добавление пользователя.");
        try {
            final User user = userRepository.save(UserMapper.toUser(userRequestDto));
            log.info("Пользователь успешно добавлен под id {}", user.getId());
            return UserMapper.toUserResponseDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("Адрес электронной почты уже занят почты");
        }
    }

    @Override
    public UserResponseDto getUser(Long id) {
        log.info("Запрос на получение пользователя с id {}", id);
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + id));
        log.info("Пользователь с id = {} успешно получен.{}", id);
        return UserMapper.toUserResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers(List<Long> userIds, Pageable pageable) {
        log.info("Запрос на получение списка пользователей");
        final List<User> users;
        if (Objects.isNull(userIds) || userIds.isEmpty()) {
            users = userRepository.findAll(pageable).getContent();
            log.info("Получен списк всех пользователей");
        } else {
            users = userRepository.findByIdIn(userIds, pageable);
            log.info("Получен списк пользователей по заданным id");
        }
        return users.stream().map(UserMapper::toUserResponseDto).toList();
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        log.info("Запрос на обновление данных пользователя с id {}", id);
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + id));
        if (Objects.nonNull(userRequestDto.getName())) {
            user.setName(userRequestDto.getName());
        }
        if (Objects.nonNull(userRequestDto.getEmail())) {
            user.setEmail(userRequestDto.getEmail());
        }
        final User newUser = userRepository.save(user);
        log.info("Пользователь с id {} успешно обновлен ", id);
        return UserMapper.toUserResponseDto(newUser);
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Запрос на удаление пользователя с id {}", id);
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + id));
        userRepository.deleteById(id);
        log.info("Пользователь с id {} успешно удален ", id);
    }

    @Override
    public UserResponseDto addSubscription(final Long id,
                                           final Long sub_id) {
        log.info("Запрос на добавление пользователю с id {} подписки с id {}", id, sub_id);
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + id));
        final Subscription subscription = subscriptionRepository.findById(sub_id)
                .orElseThrow(() -> new NotFoundException("Подписки с id = {} нет." + sub_id));
        if (user.getSubscriptions().contains(subscription)) {
            throw new ConflictException("У пользователя уже есть эта подписка");
        }
        user.getSubscriptions().add(subscription);
        userRepository.save(user);
        log.info("Подписка с id {} добавлена пользователю с id {}", sub_id, id);
        return UserMapper.toUserResponseDto(user);
    }

    @Override
    public void deleteSubscription(final Long id,
                                   final Long sub_id) {
        log.info("Запрос на удаление подписки {} у пользователя {}", sub_id, id);
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователя с id = {} нет." + id));
        final Subscription subscription = subscriptionRepository.findById(sub_id)
                .orElseThrow(() -> new NotFoundException("Подписки с id = {} нет." + sub_id));
        if (!user.getSubscriptions().contains(subscription)) {
            throw new NotFoundException("У пользователя нет такой подписки");
        }
        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
        log.info("Подписка {} удалена у пользователя {}", sub_id, id);
    }
}
