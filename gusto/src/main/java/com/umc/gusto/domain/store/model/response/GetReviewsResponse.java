package com.umc.gusto.domain.store.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewsResponse{         // review public 여부 확인
    Long reviewId;                       // 리뷰 id로 페이징 처리
    LocalDate visitedAt;
    String profileImage;
    String nickname;
    Integer liked;
    String comment;
    String img1;
    String img2;
    String img3;
    String img4;

}