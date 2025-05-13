package ru.project.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.project.dto.SubscriptionRequestDto;
import ru.project.dto.SubscriptionResponseDto;
import ru.project.dto.UserResponseDto;
import ru.project.services.SubscriptionService;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {

private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SubscriptionResponseDto> createSubscription(
                                                 @RequestBody @Valid SubscriptionRequestDto subscriptionRequestDto) {
        return ResponseEntity.ok(subscriptionService.createSubscription(subscriptionRequestDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Подписка удалена: " + id);
    }

    @GetMapping("/top")
    public ResponseEntity<List<SubscriptionResponseDto>> getTopSubscription(
                                                 @RequestParam(defaultValue = "3") Long count) {
        return ResponseEntity.ok(subscriptionService.getTopSubscription(count));
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<UserResponseDto>> getUsersBySubscriptionId(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getUsersBySubscriptionId(id));
    }
}