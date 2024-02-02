package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SocialRepository extends JpaRepository<Social, Long> {
    Optional<Social> findBySocialTypeAndProviderId(Social.SocialType socialType, String providerId);

    Optional<Social> findByTemporalToken(UUID tempToken);
}
