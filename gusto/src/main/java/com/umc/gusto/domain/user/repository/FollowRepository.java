package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.Follow;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollower_UserIdAndFollowing_UserId(UUID follower, UUID following);

    @Query("SELECT f FROM Follow f WHERE f.follower = :follower AND f.followId > :followId ORDER BY f.createdAt")
    List<Follow> findFollowList(@Param("follower") User follower, @Param("followId") Long followId, Pageable pageable);

}
