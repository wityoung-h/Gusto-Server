package com.umc.gusto.domain.store.entity;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Store extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long storeId;

    @Column(columnDefinition = "VARCHAR(30)")
    private String storeName;

    private Float longtitude;

    private Float latitude;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stateId", nullable = false)
    private State state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cityId", nullable = false)
    private City city;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "townId", nullable = false)
    private Town town;

    @Column(nullable = false, columnDefinition = "VARCHAR(60)")
    private String address;

    @Column(columnDefinition = "VARCHAR(60)")
    private String oldAddress;

    @Column(columnDefinition = "VARCHAR(20)")
    private String contact;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private final StoreStatus storeStatus = StoreStatus.ACTIVE;

    public enum StoreStatus {
        ACTIVE, INACTIVE, CLOSED
    }
}

