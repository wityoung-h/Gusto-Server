package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.global.auth.model.AuthUser;

public interface ReviewService {
    void createReview(AuthUser authUser, ReviewRequest.createReviewDTO createReviewDTO);
}
