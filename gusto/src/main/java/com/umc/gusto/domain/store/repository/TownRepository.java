package com.umc.gusto.domain.store.repository;

import com.umc.gusto.domain.store.entity.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownRepository extends JpaRepository<Town, Long> {
    Town findByTownName(String townName);
}
