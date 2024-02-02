package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.user.nickname = :nickname AND m.publishCategory = 'PUBLIC'")
    List<MyCategory> findByUserNickname(String nickname);
    @Query("SELECT m FROM MyCategory m WHERE m.status = 'ACTIVE' AND m.publishCategory = 'PUBLIC'")
    List<MyCategory> findByStatusAndPublishCategory();
    Optional<MyCategory> findByUserNicknameAndMyCategoryId(String nickname, Long myCategoryId);

    @Query("SELECT DISTINCT p FROM Pin p " +
            "JOIN FETCH p.store s " +
            "JOIN FETCH s.town t " +
            "WHERE p.myCategory.myCategoryId = :myCategoryId " +
            "AND t.townName = :townName")
    List<Pin> findPinsByMyCategoryIdAndTownName(Long myCategoryId, String townName);

    Optional<MyCategory> findByMyCategoryNameAndUser(String myCategoryName, User user);
}
