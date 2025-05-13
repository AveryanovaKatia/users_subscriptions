package ru.project.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.project.dto.UserRequestDto;
import ru.project.dto.UserResponseDto;
import ru.project.group.CreateGroup;
import ru.project.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseDto> createUser(
                            @RequestBody @Validated(CreateGroup.class) @Valid UserRequestDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    // этот эндпоинт возвращает пользователя со списком подписок,
    // поэтому отдельный эндпоинт для получения только подписок пользователя не нужен
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@RequestParam(required = false) final List<Long> ids,
                                                            @RequestParam(defaultValue = "0") @PositiveOrZero final int from,
                                                            @RequestParam(defaultValue = "10") @Positive final int size) {
        final int page = from / size;
        return ResponseEntity.ok(userService.getAllUsers(ids, PageRequest.of(page, size)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable @Positive Long id,
                                                      @RequestBody @Valid UserRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Пользователь удален: " + id);
    }

    @PutMapping("/{id}/subscriptions/{sub_id}")
    public ResponseEntity<UserResponseDto> addSubscription(@PathVariable @Positive Long id,
                                                           @PathVariable @Positive Long sub_id) {
        return ResponseEntity.ok(userService.addSubscription(id, sub_id));
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteSubscription(@PathVariable Long id,
                                                     @PathVariable @Positive Long sub_id) {
        userService.deleteSubscription(id, sub_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("У пользователя удалена подписка: " + sub_id);
    }

}
