package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findGroupByGroupId(Long groupId);
    Optional<Group> findGroupByGroupIdAndStatus(Long groupId, BaseEntity.Status status);
    List<Group> findGroupsByGroupIdInAndStatus(List<Long> groupIds, BaseEntity.Status status);
    @Query("SELECT ic.group FROM InvitationCode ic WHERE ic.code = :code AND ic.group.status = :status")
    Optional<Group> findGroupByCodeAndStatus(@Param("code") String code, @Param("status") BaseEntity.Status status);
}
