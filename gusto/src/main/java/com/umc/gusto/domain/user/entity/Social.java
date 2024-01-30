package com.umc.gusto.domain.user.entity;

import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Social extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SocialType socialType;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private SocialStatus socialStatus = SocialStatus.WAITING_SIGN_UP;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Column(columnDefinition = "BINARY(16)")
    private UUID temporalToken;

    public enum SocialType {
        KAKAO, NAVER, GOOGLE
    }

    public enum SocialStatus {
        CONNECTED, WAITING_SIGN_UP, DISCONNECTED
    }

    public void updateUser(User user) {
        this.user = user;
    }

    public void updateSocialStatus(SocialStatus socialStatus) {
        this.socialStatus = socialStatus;
    }
}
