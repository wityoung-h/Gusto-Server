package com.umc.gusto.domain.store.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long stateId;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String stateCode;

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private String stateName;
}
