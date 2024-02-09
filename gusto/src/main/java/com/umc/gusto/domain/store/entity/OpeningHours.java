package com.umc.gusto.domain.store.entity;

import com.umc.gusto.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class OpeningHours extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long opHoursId;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_day", nullable = false, length = 15)
    private BusinessDay businessDay;

    private LocalTime openedAt;

    private LocalTime closedAt;

    private LocalTime breakStart;

    private LocalTime breakEnd;

    public enum BusinessDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
