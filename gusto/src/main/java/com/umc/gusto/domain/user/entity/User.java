package com.umc.gusto.domain.user.entity;

import com.umc.gusto.global.common.BaseTime;
import com.umc.gusto.global.common.PublishStatus;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User extends BaseTime {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID userid;

    @Column(nullable = false, length = 15)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    @Column(nullable = false)
    private String profileImage;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "publishReview", nullable = false, length = 10)
    private PublishStatus publishReview = PublishStatus.PUBLIC;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "publishPin", nullable = false, length = 10)
    private PublishStatus publishPin = PublishStatus.PUBLIC;

    @Column(nullable = false)
    private Long follower;

    @Column(nullable = false)
    private Integer pinCnt;

    @Column(nullable = false)
    private Integer reviewCnt;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MemberStatus memberStatus = MemberStatus.ACTIVE;

    public enum Gender {
        FEMALE, MALE, NONE
    }

    public enum Age {
        TEEN, TWENTIES, THIRTIES, FOURTIES, FIFTIES, OLDER, NONE
    }

    public enum MemberStatus {
        ACTIVE, INACTIVE, PAUSE
    }

}
