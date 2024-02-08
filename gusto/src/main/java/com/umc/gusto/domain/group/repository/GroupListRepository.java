package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupListRepository extends JpaRepository<GroupList, Long> {
    int countGroupListsByGroup(Group group);
}
