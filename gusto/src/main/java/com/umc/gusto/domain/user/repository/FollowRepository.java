package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

}
