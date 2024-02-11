package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.ReviewCalViewRequest;
import com.umc.gusto.domain.review.model.request.ReviewViewRequest;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfInstaResponse;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfCalResponse;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfTimelineResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.UUID;

public interface CollectReviewService {
    CollectReviewsOfInstaResponse getReviewOfInstaView(User user, ReviewViewRequest reviewViewRequest);
    CollectReviewsOfCalResponse getReviewOfCalView(User user, ReviewCalViewRequest reviewCalViewRequest);
    CollectReviewsOfTimelineResponse getReviewOfTimeView(User user, ReviewViewRequest reviewViewRequest);
    CollectReviewsOfInstaResponse getOthersReview(UUID userId, ReviewViewRequest reviewViewRequest);
}
