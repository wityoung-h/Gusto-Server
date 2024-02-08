package com.umc.gusto.domain.group.entity;

import com.umc.gusto.domain.group.model.response.GroupMemberResponseDto;
import com.umc.gusto.domain.user.entity.User;
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
public class GroupMember extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupMemberId", nullable = false, updatable = false)
    private Long groupMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
