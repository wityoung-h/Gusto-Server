package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Long countUsersByNicknameAndMemberStatusIs(String nickname, User.MemberStatus status);
}
