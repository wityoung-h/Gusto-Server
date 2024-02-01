package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByTownName(String townName);
}
