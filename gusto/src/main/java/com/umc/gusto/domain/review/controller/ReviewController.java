package com.umc.gusto.domain.review.controller;

import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.domain.review.service.ReviewService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    /**
     * 리뷰 생성
     */
    @PostMapping
    public ResponseEntity<?> createReview(@AuthenticationPrincipal AuthUser authUser, @RequestPart(name = "img", required = false) MultipartFile multipartFile, @RequestBody @Valid ReviewRequest.createReviewDTO createReviewDTO){
        User user = authUser.getUser();
        reviewService.createReview(user, createReviewDTO);
        //TODO: 응답 형식 맞추기
        return ResponseEntity.ok().build();
    }

}
