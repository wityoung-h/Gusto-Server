package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.response.RandomFeedResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface FeedService {
    List<RandomFeedResponse> getRandomFeed(User user);
}
