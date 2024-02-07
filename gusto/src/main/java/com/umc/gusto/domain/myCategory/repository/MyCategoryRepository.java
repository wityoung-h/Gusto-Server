package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.nickname = :nickname AND m.publishCategory = 'PUBLIC'")
    List<MyCategory> findByUserNickname(String nickname);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.nickname = :nickname AND m.myCategoryId = :myCategoryId AND m.publishCategory = 'PUBLIC'")
    Optional<MyCategory> findByMyCategoryIdAndUserNickname(String nickname, Long myCategoryId);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.publishCategory = 'PUBLIC' AND m.user = :user")
    List<MyCategory> findByStatusAndPublishCategoryAndUser(User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.myCategoryName = :myCategoryName AND m.user = :user")
    Optional<MyCategory> findByMyCategoryNameAndUser(String myCategoryName, User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.myCategoryId = :myCategoryId AND m.user = :user")
    Optional<MyCategory> findByUserAndMyCategoryId(User user, Long myCategoryId);
}
