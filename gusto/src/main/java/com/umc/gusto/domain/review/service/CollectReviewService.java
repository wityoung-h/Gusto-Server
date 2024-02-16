package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.response.CollectReviewsOfInstaResponse;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfCalResponse;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfTimelineResponse;
import com.umc.gusto.domain.user.entity.User;

import java.time.LocalDate;

public interface CollectReviewService {
//    CollectReviewsOfInstaResponse getReviewOfInstaView(User user, ReviewViewRequest reviewViewRequest);
    CollectReviewsOfInstaResponse getReviewOfInstaView(User user, Long reviewId, int size);
    CollectReviewsOfCalResponse getReviewOfCalView(User user, Long reviewId, int size, LocalDate date);
    CollectReviewsOfTimelineResponse getReviewOfTimeView(User user, Long reviewId, int size);
    CollectReviewsOfInstaResponse getOthersReview(String nickName, Long reviewId, int size);
}
