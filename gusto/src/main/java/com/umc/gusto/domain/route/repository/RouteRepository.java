package com.umc.gusto.domain.route.repository;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route,Long> {

    // 동일한 이름의 루트가 있는지 확인 -> 존재하면 TRUE를 반환하고, 그렇지 않으면 FALSE
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Route r WHERE r.routeName = :routeName AND r.status = :status AND r.user = :user")
    Boolean existsByRouteName(@Param("routeName") String routeName, @Param("status") BaseEntity.Status status, @Param("user") User user);

//    Boolean existsByRouteNameAndStatus(String routeName, BaseEntity.Status status);

    // rootId PK값으로 루트 찾기
    Optional<Route> findRouteByRouteIdAndStatus(Long routeId,BaseEntity.Status status);

    // 그룹X, ACTIVE
    @Query("SELECT r FROM Route r WHERE r.routeId = ?1 AND r.status = ?2 AND r.group.groupId IS NULL")
    Optional<Route> findRouteByRouteIdAndStatusAndGroup(Long routeId, BaseEntity.Status status);

    // 유저의 루트 목록 조회 , 그룹X
    List<Route> findRouteByUserAndStatus(User user, BaseEntity.Status status);

    // 그룹의 루트 목록 조회
    List<Route> findRoutesByGroupAndStatus(Group group, BaseEntity.Status status);

    // 그룹의 루트 개수 조회
    int countRoutesByGroupAndStatus(Group group, BaseEntity.Status status);
}
