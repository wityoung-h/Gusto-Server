package com.umc.gusto.global;

import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.global.common.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableScheduling
@Configuration
@Component
@RequiredArgsConstructor
public class DeleteSchedulingConfig {
// 삭제 시 상호 참조 주의!
    private final RouteRepository routeRepository;
    private final RouteListRepository routeListRepository;

    // 일정 시간마다 실행
    @Transactional
    @Async
    @Scheduled(cron = "0/60 * * * * ?")
    public void autoDelete() {
        // 각 리포지토리 DB별  삭제 대기를 찾아서 각자
        List<Route> routes = routeRepository.findRouteByStatus(BaseEntity.Status.INACTIVE);
        for (Route route : routes) {
            // 각 Route에 관련된 RouteList 항목 삭제
            routeListRepository.deleteAll(routeListRepository.findByRouteOrderByOrdinalAsc(route));
        }
        // 모든 Route 삭제
        routeRepository.deleteAll(routes);
        }


    // 이전 작업이 완료된 후 7일마다 실행 (밀리초 단위)
//    @Scheduled(fixedDelay = 1000)
//    public BaseEntity.Status  fixedRateScheduler() {
//        // 상태를 INACTIVE로 업데이트
//        return BaseEntity.Status.INACTIVE;
//
//    }

}
