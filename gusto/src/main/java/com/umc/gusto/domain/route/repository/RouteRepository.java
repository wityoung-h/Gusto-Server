package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route,Long> {

    // 동일한 이름의 루트가 있는지 확인
    Boolean existsByRouteNameAndStatus(String routeName, BaseEntity.Status status);

    // 유저의 루트 목록 조회
    List<Route> findRouteByUserAndStatus(User user, BaseEntity.Status status);
}
