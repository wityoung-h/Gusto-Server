package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.response.RandomFeedResponse;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService{
    private final ReviewRepository reviewRepository;

    @Override
    public List<RandomFeedResponse> getRandomFeed(User user) {
//        List<Review> feedList = reviewRepository.findRandomByUser(user);
        List<Review> feedList = new ArrayList<>();
        return feedList.stream().map(RandomFeedResponse::of).collect(Collectors.toList());
    }
}
