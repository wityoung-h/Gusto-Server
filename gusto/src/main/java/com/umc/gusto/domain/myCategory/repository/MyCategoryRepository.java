package com.umc.gusto.domain.myCategory.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyCategoryRepository extends JpaRepository<MyCategory, Long> {

}
