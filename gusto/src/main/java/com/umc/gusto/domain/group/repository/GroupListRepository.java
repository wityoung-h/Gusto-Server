package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupListRepository extends JpaRepository<GroupList,Long> {
    Optional<GroupList> findGroupListByGroupListId(Long groupListId);
}
