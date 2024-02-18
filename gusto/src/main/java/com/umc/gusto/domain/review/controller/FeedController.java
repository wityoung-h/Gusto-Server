package com.umc.gusto.domain.review.controller;

import com.umc.gusto.domain.review.service.FeedService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feeds")
public class FeedController {
    private final FeedService feedService;

    @GetMapping
    public ResponseEntity<?> getRandomFeed(@AuthenticationPrincipal AuthUser authUser){
        User user = authUser.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getRandomFeed(user));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchFeed(@RequestParam(name = "keyword", required = false) String keyword, @RequestParam(name = "hashTags", required = false) List<Long> hashTags){
        return ResponseEntity.status(HttpStatus.OK).body(feedService.searchFeed(keyword, hashTags));
    }

    @GetMapping("{reviewId}")
    public ResponseEntity<?> getFeedDetail(@AuthenticationPrincipal AuthUser authUser, @PathVariable(name = "reviewId") Long reviewId){
        User user = authUser.getUser();
        return ResponseEntity.status(HttpStatus.OK).body(feedService.getFeedDetail(user, reviewId));
    }
}
