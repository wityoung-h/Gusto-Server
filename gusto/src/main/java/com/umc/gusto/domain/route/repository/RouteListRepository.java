package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.route.entity.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteListRepository extends JpaRepository<RouteList,Long> {
    List<RouteList> findAllByRoute(Long RouteId);
}
