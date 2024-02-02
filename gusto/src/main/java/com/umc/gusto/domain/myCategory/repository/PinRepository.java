package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PinRepository extends JpaRepository<Pin, Long> {
    List<Pin> findByMyCategoryOrderByPinIdDesc(MyCategory myCategory);

    @Query("SELECT p FROM Pin p " +
            "JOIN p.store s " +
            "JOIN s.town t " +
            "WHERE p.myCategory = :myCategory AND t.townName = :townName " +
            "ORDER BY p.pinId DESC")
    List<Pin> findAllByUserAndMyCategoryOrderByPinIdDesc(MyCategory myCategory, String townName);
}
