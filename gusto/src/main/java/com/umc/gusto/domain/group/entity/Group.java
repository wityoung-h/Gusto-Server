package com.umc.gusto.domain.group.entity;

import com.umc.gusto.domain.group.model.response.GroupResponseDto;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
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
@Table(name = "GroupTable")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId", nullable = false, updatable = false)
    private Long groupId;

    @Column(nullable = false, columnDefinition = "VARCHAR(10)")
    private String groupName;

    @Column(columnDefinition = "VARCHAR(20)")
    private String groupScript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(columnDefinition = "VARCHAR(50)")
    private String notice;
}