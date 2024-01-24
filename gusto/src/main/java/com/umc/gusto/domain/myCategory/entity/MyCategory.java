package com.umc.gusto.domain.myCategory.entity;

import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.BaseTime;
import com.umc.gusto.global.common.PublishStatus;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String myCategoryName;

    @Column(nullable = false)
    private Integer myCategoryIcon;

    private String myCategoryScript;

    @Enumerated(EnumType.STRING)
    @Column(name = "publishCategory", nullable = false, columnDefinition = "VARCHAR(10) DEFAULT 'PUBLIC'")
    private PublishStatus publishCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "myCategory", cascade = CascadeType.ALL)
    private List<Pin> pinList = new ArrayList<>();

}
