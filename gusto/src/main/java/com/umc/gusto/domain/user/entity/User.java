package com.umc.gusto.domain.user.entity;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.user.enums.*;
import com.umc.gusto.global.common.BaseTime;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseTime {

    @Id
    private UUID userId = UUID.randomUUID();

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

    @Enumerated(EnumType.STRING)
    @Column(name = "publishReview", nullable = false, columnDefinition = "VARCHAR DEFAULT 'PUBLIC'")
    private PublishStatus publishReview;

    @Enumerated(EnumType.STRING)
    @Column(name = "publishPin", nullable = false, columnDefinition = "VARCHAR DEFAULT 'PUBLIC'")
    private PublishStatus publishPin;

    @Column(nullable = false)
    private Long follower;

    @Column(nullable = false)
    private Integer pinCnt;

    @Column(nullable = false)
    private Integer reviewCnt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR DEFAULT 'ACTIVE")
    private MemberStatus memberStatus;

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
