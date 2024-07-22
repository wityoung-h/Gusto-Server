package com.umc.gusto.domain.group.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.group.entity.GroupList;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupListRepository extends JpaRepository<GroupList,Long> {
    Optional<GroupList> findGroupListByGroupListId(Long groupListId);
    @Query("SELECT gl from GroupList gl where gl.group =:group ORDER BY gl.groupListId desc")
    Page<GroupList> findFirstGroupListOrderByDesc(@Param("group") Group group , Pageable pageable);

    @Query("SELECT gl from GroupList gl where gl.group =:group AND gl.groupListId <:groupListId ORDER BY gl.groupListId desc")
    Page<GroupList> findGroupListByGroupOrderByCreatedAtDesc(@Param("group") Group group, @Param("groupListId") Long groupListId , Pageable pageable);
    int countGroupListsByGroup(Group group);
    Boolean existsGroupListByGroupAndStore(Group group, Store store);

    @Modifying //DELETE 쿼리임을 명시
    @Query("DELETE FROM GroupList gl WHERE gl.group.groupId = :groupId")
    void deleteByGroupId(@Param("groupId") Long groupId);
}
