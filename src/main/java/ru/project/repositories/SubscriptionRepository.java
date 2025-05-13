package ru.project.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s " +
            "LEFT JOIN s.users u " +
            "GROUP BY s.id " +
            "ORDER BY COUNT(u) DESC " +
            "LIMIT :count")
    List<Subscription> findTopSubscriptionsByUserCount(@Param("count") Long count);
}
