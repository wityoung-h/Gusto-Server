package com.umc.gusto.global;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.global.common.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@EnableScheduling
@Configuration
@Component
@RequiredArgsConstructor
public class DeleteSchedulingConfig {
// 삭제 시 상호 참조 주의!
    private final RouteRepository routeRepository;
//    private final RouteListRepository routeListRepository;

    // 일정 시간마다 실행
    @Transactional
    @Async
    @Scheduled(cron = "0 0 0 1 * ?")
    public void autoDelete() {
        // 기간이 지난 데이터 : 30일 => 수정 시점을 기준 + 현재 상태
        LocalDateTime threshold = LocalDateTime.now().minus(30, ChronoUnit.DAYS); // 현재 시간 기준 30일 이전

        // 각 리포지토리 DB별  삭제 대기를 찾아서 각자
        List<Route> routes = routeRepository.findRouteByStatusAndUpdatedAtBefore(BaseEntity.Status.INACTIVE,threshold);
//        for (Route route : routes) {
//            // 각 Route에 관련된 RouteList 항목 삭제
//            routeListRepository.deleteAll(routeListRepository.findByRouteOrderByOrdinalAsc(route));
//        }
        // 모든 Route 삭제
        routeRepository.deleteAll(routes);
        }

}
