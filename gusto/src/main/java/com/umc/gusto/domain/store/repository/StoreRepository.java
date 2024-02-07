package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<OpeningHours> findByStoreId(Long storeId);
}