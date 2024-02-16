package com.umc.gusto.domain.review.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.umc.gusto.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FeedDetailResponse {
    Long storeId;
    String storeName;
    String address;
    String nickName;
    String profileImage;
    List<String> images;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String menuName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String hashTags;
    Integer taste;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer spiciness;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer mood;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer toilet;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Integer parking;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String comment;
    Integer likeCnt;

    public static FeedDetailResponse of(Review review, String hashTags){
        return FeedDetailResponse.builder()
                .storeId(review.getStore().getStoreId())
                .storeName(review.getStore().getStoreName())
                .address(review.getStore().getAddress())
                .nickName(review.getUser().getNickname())
                .profileImage(review.getUser().getProfileImage())
                .images(review.getImageList())
                .menuName(review.getMenuName())
                .hashTags(hashTags)
                .taste(review.getTaste())
                .spiciness(review.getSpiciness())
                .mood(review.getMood())
                .toilet(review.getToilet())
                .parking(review.getParking())
                .comment(review.getComment())
                .likeCnt(review.getLiked())
                .build();
    }
}
