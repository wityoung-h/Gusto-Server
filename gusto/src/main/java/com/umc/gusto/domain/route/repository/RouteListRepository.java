package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteListRepository extends JpaRepository<RouteList,Long> {
    List<RouteList> findAllByRoute(Long RouteId);

    int countRouteListByRoute(Long routeId);
}
