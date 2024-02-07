package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
    Optional<OpeningHours> findOpeningHoursByStoreStoreId(Long storeId);
}
