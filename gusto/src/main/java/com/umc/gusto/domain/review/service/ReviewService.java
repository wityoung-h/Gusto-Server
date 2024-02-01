package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.domain.user.entity.User;

public interface ReviewService {
    void createReview(User user, ReviewRequest.createReviewDTO createReviewDTO);
}
