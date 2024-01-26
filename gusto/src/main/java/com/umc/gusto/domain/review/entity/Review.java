package com.umc.gusto.domain.review.entity;

import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseTime;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(length = 105)
    private String menuName;

    private Integer taste;

    private Integer spiciness;

    private Integer mood;

    private Integer toilet;

    private Integer parking;

    private LocalDate visitedAt;

    private String img1;

    private String img2;

    private String img3;

    private String img4;

    @Column(length = 200)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "publishReview", nullable = false, length = 10)
    private PublishStatus publishReview = PublishStatus.PUBLIC;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer liked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Tagging> taggingSet = new HashSet<>();          // 중복 허용x
}
