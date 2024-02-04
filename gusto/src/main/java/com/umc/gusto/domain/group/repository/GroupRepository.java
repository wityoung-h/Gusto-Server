package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group,Long> {

    // PK로 그룹 찾기
    Optional<Group> findGroupByGroupId(Long groupId);
}
