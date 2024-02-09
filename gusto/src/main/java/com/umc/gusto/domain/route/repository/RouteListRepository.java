package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.entity.RouteList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteListRepository extends JpaRepository<RouteList,Long> {
    int countRouteListByRoute(Route route);
}
