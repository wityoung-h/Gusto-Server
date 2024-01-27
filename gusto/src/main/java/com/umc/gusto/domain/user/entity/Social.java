package com.umc.gusto.domain.user.entity;

import com.umc.gusto.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Social extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Column(nullable = false)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public enum SocialType {
        KAKAO, NAVER, GOOGLE
    }
}
