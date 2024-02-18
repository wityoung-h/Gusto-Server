package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupListRepository extends JpaRepository<GroupList,Long> {
    Optional<GroupList> findGroupListByGroupListId(Long groupListId);
    List<GroupList> findGroupListByGroupOrderByCreatedAtDesc(Group group);
    int countGroupListsByGroup(Group group);
}
