package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupMember;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    List<GroupMember> findGroupMembersByGroup(Group group);
  
    @Query("SELECT gm.groupMemberId FROM GroupMember gm WHERE gm.group = :group AND gm.user = :user")
    Long findGroupMemberIdByGroupAndUser(Group group, User user);
  
    Boolean existsGroupMemberByGroupAndUser(Group group, User user);

    Optional<GroupMember> findGroupMemberByGroupAndGroupMemberId(Group group, Long memberId);
    Optional<GroupMember> findGroupMemberByGroupAndUser(Group group, User user);
  
    @Query("SELECT gm.group.groupId FROM GroupMember gm WHERE gm.user = :user")
    List<Long> findGroupIdsByUser(User user);
    int countGroupMembersByGroup(Group group);

}