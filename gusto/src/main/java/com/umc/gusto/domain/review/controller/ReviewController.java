package com.umc.gusto.domain.review.controller;

import com.umc.gusto.domain.review.model.request.CreateReviewRequest;
import com.umc.gusto.domain.review.model.request.UpdateReviewRequest;
import com.umc.gusto.domain.review.service.CollectReviewService;
import com.umc.gusto.domain.review.service.ReviewService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final CollectReviewService collectReviewService;
    /**
     * 리뷰 생성
     */
    @PostMapping
    public ResponseEntity<?> createReview(@AuthenticationPrincipal AuthUser authUser, @RequestPart(name = "image", required = false) List<MultipartFile> images, @RequestPart(name = "info") @Valid CreateReviewRequest createReviewRequest){
        User user = authUser.getUser();
        reviewService.createReview(user, images, createReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 리뷰 수정
     */
    @PatchMapping("{reviewId}")
    public ResponseEntity<?> updateReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId, @RequestPart(name = "image", required = false) List<MultipartFile> images, @RequestPart(name = "info") @Valid UpdateReviewRequest updateReviewRequest){
        User user = authUser.getUser();
        reviewService.validateReviewByUser(user, reviewId);
        reviewService.updateReview(reviewId, images,updateReviewRequest);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("{reviewId}")
    public ResponseEntity<?> deleteReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId){
        User user = authUser.getUser();
        reviewService.validateReviewByUser(user, reviewId);
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 1건 조회
     */
    @GetMapping("{reviewId}")
    public ResponseEntity<?> getReview(@PathVariable Long reviewId){
        return ResponseEntity.ok().body(reviewService.getReview(reviewId));
    }

    /**
     * 리뷰 모아보기 - 인스타 뷰
     */
    @GetMapping("/instaView")
    public ResponseEntity<?> getReviewOfInstaView(@AuthenticationPrincipal AuthUser authUser, @RequestParam(name = "reviewId", required = false) Long reviewId, @RequestParam(name = "size") int size){
        User user = authUser.getUser();
        return ResponseEntity.ok().body(collectReviewService.getReviewOfInstaView(user, reviewId, size));
    }

    /**
     * 리뷰 모아보기 - 캘린더 뷰
     */
    @GetMapping("/calView")
    public ResponseEntity<?> getReviewOfCalView(@AuthenticationPrincipal AuthUser authUser, @RequestParam(name = "reviewId", required = false) Long reviewId, @RequestParam(name = "size") int size, @RequestParam(name = "date") LocalDate date){
        User user = authUser.getUser();
        return ResponseEntity.ok().body(collectReviewService.getReviewOfCalView(user, reviewId, size, date));
    }

    /**
     * 리뷰 모아보기 - 타임라인 뷰
     */
    @GetMapping("/timelineView")
    public ResponseEntity<?> getReviewOfTimeView(@AuthenticationPrincipal AuthUser authUser, @RequestParam(name = "reviewId", required = false) Long reviewId, @RequestParam(name = "size") int size){
        User user = authUser.getUser();
        return ResponseEntity.ok().body(collectReviewService.getReviewOfTimeView(user, reviewId, size));
    }
  
    /**
     * 다른 유저의 리뷰 모아보기
     */
    @GetMapping()
    public ResponseEntity<?> getOthersReview(@RequestParam(name = "nickName")String nickName, @RequestParam(name = "reviewId", required = false) Long reviewId, @RequestParam(name = "size") int size){
        return ResponseEntity.ok().body(collectReviewService.getOthersReview(nickName, reviewId, size));
    }

    /**
     * 리뷰 좋아요
     */
    @PostMapping("/{reviewId}/like")
    public ResponseEntity<?> likeReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId){
        User user = authUser.getUser(); //TODO: 로그인한 유저가 아니면 리뷰를 할 수 없도록 예외처리 필요
        reviewService.likeReview(user, reviewId);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }

    /**
     * 리뷰 좋아요 취소
     */
    @DeleteMapping("/{reviewId}/unlike")
    public ResponseEntity<?> unLikeReview(@AuthenticationPrincipal AuthUser authUser, @PathVariable Long reviewId){
        User user = authUser.getUser(); //TODO: 로그인한 유저가 아니면 리뷰를 할 수 없도록 예외처리 필요
        reviewService.unlikeReview(user, reviewId);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
    }
}
