package com.umc.gusto.domain.user.repository;

import com.umc.gusto.domain.user.entity.User;
import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);
}
