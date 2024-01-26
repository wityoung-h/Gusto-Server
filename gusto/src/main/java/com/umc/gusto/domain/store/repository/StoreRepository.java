package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByMyCategory(MyCategory myCategory);
}
