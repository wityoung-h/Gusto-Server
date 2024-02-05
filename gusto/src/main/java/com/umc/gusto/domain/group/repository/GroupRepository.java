package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findGroupByGroupId(Long groupId);
}