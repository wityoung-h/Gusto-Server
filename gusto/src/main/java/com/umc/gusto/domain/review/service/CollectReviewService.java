package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.response.CollectReviewsResponse;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfCalResponse;
import com.umc.gusto.domain.user.entity.User;

import java.time.LocalDate;

public interface CollectReviewService {
//    CollectReviewsOfInstaResponse getReviewOfInstaView(User user, ReviewViewRequest reviewViewRequest);
    CollectReviewsResponse getMyReviewOfInstaView(User user, Long reviewId, int size);
    CollectReviewsResponse getReviewOfInstaView(User user, Long reviewId, int size);
    CollectReviewsOfCalResponse getReviewOfCalView(User user, Long reviewId, int size, LocalDate date);
    CollectReviewsResponse getReviewOfTimeView(User user, Long reviewId, int size);
    CollectReviewsResponse getOthersReview(String nickName, Long reviewId, int size);
}
