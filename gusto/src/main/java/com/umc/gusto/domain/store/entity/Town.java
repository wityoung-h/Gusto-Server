package com.umc.gusto.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long townId;

    @Column(nullable = false, columnDefinition = "VARCHAR(8)")
    private String townCode;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String townName;
}
