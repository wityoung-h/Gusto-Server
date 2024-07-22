package com.umc.gusto.global;

import com.umc.gusto.domain.group.service.GroupService;
import com.umc.gusto.domain.route.entity.Route;
import com.umc.gusto.domain.route.repository.RouteListRepository;
import com.umc.gusto.domain.route.repository.RouteRepository;
import com.umc.gusto.domain.route.service.RouteService;
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
    private final RouteService routeService;
    private final GroupService groupService;

    // 일정 시간마다 실행
    @Transactional
    @Async
    @Scheduled(fixedRate = 10000)
    //@Scheduled(cron = "0 0 0 1 * ?") // 한 달 주기
    public void autoDelete() {
        routeService.hardDeleteAllSoftDeleted();
        groupService.hardDeleteAllSoftDeleted();

    }

}
