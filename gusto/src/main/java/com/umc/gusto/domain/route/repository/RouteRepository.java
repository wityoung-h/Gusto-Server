package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route,Long> {
    Boolean findRouteByRouteName(String routeName);
}
