package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUserIdAndMemberStatusIs(UUID uuid, User.MemberStatus status);

    Optional<User> findByNicknameAndMemberStatusIs(String nickname, User.MemberStatus status);

    Long countUsersByNicknameAndMemberStatusIs(String nickname, User.MemberStatus status);
}
