package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    List<MyCategory> findByStatusAndUser(BaseEntity.Status status, User user);
    List<MyCategory> findByStatus(BaseEntity.Status status);
    Optional<MyCategory> findByMyCategoryId(Long myCategoryId);
    Optional<MyCategory> findByMyCategoryIdAndUser(Long myCategoryId, User user);
    @Query("SELECT m FROM MyCategory m WHERE m.myCategoryName = :myCategoryName AND m.user = :user")
    Optional<MyCategory> findByMyCategoryNameAndUser(String myCategoryName, User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = :status AND m.user = :user AND m.publishCategory = :publishStatus")
    List<MyCategory> findFilteredNicknames(BaseEntity.Status status, User user, PublishStatus publishStatus);


}
