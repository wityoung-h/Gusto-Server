package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.publishCategory = 'PUBLIC' AND m.user.nickname = :nickname AND m.myCategoryId = :myCategoryId")
    Optional<MyCategory> findByMyCategoryPublicIdAndUserNickname(String nickname, Long myCategoryId);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.nickname = :nickname AND m.myCategoryId = :myCategoryId")
    Optional<MyCategory> findByMyCategoryIdAndUserNickname(String nickname, Long myCategoryId);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.publishCategory = 'PUBLIC' AND m.user = :user")
    List<MyCategory> findByUserNicknameAndPublishCategoryPublic(User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user = :user")
    List<MyCategory> findByUserNicknameAndPublishCategory(User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.myCategoryName = :myCategoryName AND m.user = :user")
    Optional<MyCategory> findByMyCategoryNameAndUser(String myCategoryName, User user);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.myCategoryId = :myCategoryId AND m.user = :user")
    Optional<MyCategory> findByUserAndMyCategoryId(User user, Long myCategoryId);
}
