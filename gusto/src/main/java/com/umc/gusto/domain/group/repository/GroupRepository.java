package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
