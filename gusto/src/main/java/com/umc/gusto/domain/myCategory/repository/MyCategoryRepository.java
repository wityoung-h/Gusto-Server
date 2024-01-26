package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

    List<MyCategory> findByStatus(BaseEntity.Status status);

    Optional<MyCategory> findByMyCategoryName(String myCategoryName);
}
