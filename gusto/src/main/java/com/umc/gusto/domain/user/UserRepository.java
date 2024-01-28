package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
