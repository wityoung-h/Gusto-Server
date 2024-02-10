package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
    List<OpeningHours> findByStoreStoreId(Long storeId);
}
