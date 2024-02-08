package com.umc.gusto.domain.review.entity;

import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review extends BaseEntity {

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

    @Builder.Default
    private LocalDate visitedAt = LocalDate.now();

    private String img1;

    private String img2;

    private String img3;

    private String img4;

    @Column(length = 200)
    private String comment;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "publishReview", nullable = false, length = 10)
    private PublishStatus publishReview = PublishStatus.PUBLIC;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    @Builder.Default
    private Integer liked = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storeId")
    private Store store;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Tagging> taggingSet = new HashSet<>();          // 중복 허용x

    public List<String> getImageList(){ //TODO: monogoDB를 활용하여 해당 코드를 리팩토링하는게 좋을 듯
        List<String> imageList = new ArrayList<>();
        if(this.img1 != null) imageList.add(this.img1);
        if(this.img2 != null) imageList.add(this.img2);
        if(this.img3 != null) imageList.add(this.img3);
        if(this.img4 != null) imageList.add(this.img4);

        return imageList;
    }
    public void connectHashTag(Tagging tagging) {
        this.taggingSet.add(tagging);
    }

    public void updateVisitedAt(LocalDate visitedAt) {
        this.visitedAt = visitedAt;
    }

    public void updateMenu(String menuName) {
        this.menuName = menuName;
    }

    public void updateTaste(Integer taste) {
        this.taste = taste;
    }

    public void updateSpiciness(Integer spiciness) {
        this.spiciness = spiciness;
    }

    public void updateMood(Integer mood){
        this.mood = mood;
    }

    public void updateToilet(Integer toilet){
        this.toilet = toilet;
    }

    public void updateParking(Integer parking){
        this.parking = parking;
    }

    public void updateComment(String comment){
        this.comment = comment;
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    //TODO: image를 리뷰엔티티가 아닌 file엔티티(photo)로 구분해서 만드는게 나을듯
    public void updateImg1(String img1){
        this.img1 = img1;
    }

    public void updateImg2(String img2){
        this.img2 = img2;
    }

    public void updateImg3(String img3){
        this.img3 = img3;
    }

    public void updateImg4(String img4){
        this.img4 = img4;
    }
}
