package com.umc.gusto.domain.review.controller;

import com.umc.gusto.domain.review.model.request.CreateReviewRequest;
import com.umc.gusto.domain.review.model.request.UpdateReviewRequest;
import com.umc.gusto.domain.review.service.ReviewService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    /**
     * 리뷰 생성
     */
    @PostMapping
    public ResponseEntity<?> createReview(@AuthenticationPrincipal AuthUser authUser, @RequestPart(name = "image", required = false) List<MultipartFile> images, @RequestPart(name = "info") @Valid CreateReviewRequest createReviewRequest){
        User user = authUser.getUser();
        reviewService.createReview(user, images, createReviewRequest);
        //TODO: 응답 형식 맞추기
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 수정
     */
    @PatchMapping("{reviewId}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId, @RequestPart(name = "image", required = false) List<MultipartFile> images, @RequestPart(name = "info") @Valid UpdateReviewRequest updateReviewRequest){
        User user = authUser.getUser();
        reviewService.validateReviewByUser(user, reviewId);
        reviewService.updateReview(reviewId, images,updateReviewRequest);
        //TODO: 응답 형식 맞추기
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId){
        User user = authUser.getUser();
        reviewService.validateReviewByUser(user, reviewId);
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 1건 조회
     */
    @GetMapping("{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId){
        return ResponseEntity.ok().body(reviewService.getReview(reviewId));
    }
}
