package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.domain.review.model.response.ReviewResponse;
import com.umc.gusto.domain.user.entity.User;

public interface ReviewService {
    void validateReviewByUser(final User user, final Long reviewId);
    void createReview(User user, ReviewRequest.createReviewDTO createReviewDTO);
    void updateReview(Long reviewId, ReviewRequest.updateReviewDTO updateReviewDTO);
    void deleteReview(Long reviewId);
    ReviewResponse.ReviewDetailDTO getReview(Long reviewId);
}
