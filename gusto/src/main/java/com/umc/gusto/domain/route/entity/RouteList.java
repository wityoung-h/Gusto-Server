package com.umc.gusto.domain.route.entity;

import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@EqualsAndHashCode
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class RouteList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long routeListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routeId", nullable = false)
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId", nullable = false)
    private Store store;

    @Column(nullable = false)
    private Integer ordinal;

}
