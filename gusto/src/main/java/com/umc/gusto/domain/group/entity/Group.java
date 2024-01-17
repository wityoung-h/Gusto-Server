package com.umc.gusto.domain.group.entity;

import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
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

    @Column(nullable = false)
    private String groupName;

    private String groupScript;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(columnDefinition = "VARCHAR(50)")
    private String notice;

    @OneToMany(mappedBy = "group")
    private List<GroupMember> groupMembers;

    @OneToMany(mappedBy = "group")
    private List<GroupList> groupLists;

}
