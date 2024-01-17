package com.umc.gusto.domain.route.entity;

import com.umc.gusto.domain.group.entity.Group;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Route extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long routeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group group;

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private String routeName;

}
