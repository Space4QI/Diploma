package org.example.repositories;

import org.example.models.UserAchievementCrossRef;
import org.example.models.UserAchievementKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievementCrossRef, UserAchievementKey> {
    List<UserAchievementCrossRef> findByUserId(UUID userId);
}