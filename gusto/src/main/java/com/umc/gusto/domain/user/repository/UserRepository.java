package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByNickname(String nickname);

    Long countUsersByNicknameAndMemberStatusIs(String nickname, User.MemberStatus status);
}
