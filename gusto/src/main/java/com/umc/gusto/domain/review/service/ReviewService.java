package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.request.CreateReviewRequest;
import com.umc.gusto.domain.review.model.request.UpdateReviewRequest;
import com.umc.gusto.domain.review.model.response.ReviewResponse;
import com.umc.gusto.domain.user.entity.User;

public interface ReviewService {
    void validateReviewByUser(final User user, final Long reviewId);
    void createReview(User user, CreateReviewRequest createReviewRequest);
    void updateReview(Long reviewId, UpdateReviewRequest updateReviewRequest);
    void deleteReview(Long reviewId);
    ReviewResponse.ReviewDetailDTO getReview(Long reviewId);
}
