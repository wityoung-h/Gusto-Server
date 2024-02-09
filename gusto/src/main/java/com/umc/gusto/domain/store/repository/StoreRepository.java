package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.Category;
import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT oh FROM OpeningHours oh WHERE oh.store.storeId = :storeId")
    Optional<OpeningHours> findOpeningHoursByStoreId(Long storeId);
    @Query("SELECT s.category FROM Store s WHERE s.storeId = :storeId")
    Optional<Category> findCategoryByStoreId(Long storeId);
    @Query("SELECT s FROM Store s WHERE s.town.townName = :townName AND s.storeId IN :storeIds")
    List<Store> findByTownNameAndStoreIds(String townName, List<Long> storeIds);
}