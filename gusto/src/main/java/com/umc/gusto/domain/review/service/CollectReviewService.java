package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.ReviewViewRequest;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfInstaResponse;
import com.umc.gusto.domain.user.entity.User;

public interface CollectReviewService {
    CollectReviewsOfInstaResponse getReviewOfInstaView(User user, ReviewViewRequest reviewViewRequest);
}
