package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s FROM Store s WHERE s.town.townCode = :townCode AND s.storeId IN :storeIds")
    List<Store> findByTownCodeAndStoreIds(String townCode, List<Long> storeIds);

    List<Store> findTop5ByStoreNameContains(String keyword);
}
