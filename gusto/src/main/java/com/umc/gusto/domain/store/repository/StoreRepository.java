package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.Category;
import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT c FROM Category c WHERE c.store.storeId = :storeId")
    Optional<Category> findCategoryByStoreId(Long storeId);
}
