package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFollower_UserIdAndFollowing_UserId(UUID follower, UUID following);

}
