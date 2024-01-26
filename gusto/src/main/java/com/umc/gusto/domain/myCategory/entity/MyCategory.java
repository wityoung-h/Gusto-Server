package com.umc.gusto.domain.myCategory.entity;

import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.BaseTime;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MyCategory extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myCategoryId;

    @Column(nullable = false, length = 10)
    private String myCategoryName;

    @Column(nullable = false)
    private Integer myCategoryIcon;

    @Column(length = 20)
    private String myCategoryScript;

    @Builder.Default
    @Enumerated(EnumType.STRING)
<<<<<<< HEAD
    @Column(name = "publishCategory",nullable = false, length = 10)
    private PublishStatus publishCategory = PublishStatus.PUBLIC;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BaseEntity.Status status = BaseEntity.Status.ACTIVE;
=======
    @Column(name = "publishCategory", nullable = false, length = 10)
    private PublishStatus publishCategory = PublishStatus.PUBLIC;
>>>>>>> e1efffad80468f8d543451dfb16a1bada55beb12

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "myCategory", cascade = CascadeType.ALL)
    private final List<Pin> pinList = new ArrayList<>();

    @OneToMany(mappedBy = "myCategory", cascade = CascadeType.ALL)
    private final List<Store> storeList = new ArrayList<>();

    public void setMyCategoryName(String myCategoryName) {
        this.myCategoryName = myCategoryName;
    }

    public void setMyCategoryIcon(Integer myCategoryIcon) {
        this.myCategoryIcon = myCategoryIcon;
    }

    public void setMyCategoryScript(String myCategoryScript) {
        this.myCategoryScript = myCategoryScript;
    }

    public void setPublishCategory(PublishStatus publishCategory) {
        this.publishCategory = publishCategory;
    }

    public void setStatus(BaseEntity.Status status) {this.status = status;}
}
