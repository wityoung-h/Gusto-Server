package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT oh FROM OpeningHours oh WHERE oh.store.storeId = :storeId")
    Optional<OpeningHours> findOpeningHoursByStoreId(Long storeId);
}