package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.nickname = :nickname AND m.publishCategory = 'PUBLIC' AND m.user = :user")
    List<MyCategory> findByUserNickname(String nickname);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.publishCategory = 'PUBLIC' AND m.user = :user")
    List<MyCategory> findByStatusAndPublishCategoryAndUser(User user);
    @Query("SELECT p FROM Pin p " +
            "JOIN FETCH p.store s " +
            "JOIN FETCH s.town t " +
            "WHERE p.myCategory.myCategoryId = :myCategoryId " +
            "AND t.townName = :townName")
    List<Pin> findPinsByMyCategoryIdAndTownName(Long myCategoryId, String townName);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.myCategoryName = :myCategoryName AND m.user = :user")
    List<MyCategory> findByMyCategoryNameAndUser(String myCategoryName, User user);
    Optional<MyCategory> findByUserAndMyCategoryId(User user, Long myCategoryId);
}
