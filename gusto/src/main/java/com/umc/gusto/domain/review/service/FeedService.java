package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.model.response.FeedDetailResponse;
import com.umc.gusto.domain.review.model.response.RandomFeedResponse;
import com.umc.gusto.domain.review.model.response.SearchFeedResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface FeedService {
    List<RandomFeedResponse> getRandomFeed(User user);
    SearchFeedResponse searchFeed(String keyword, List<Long> hashTags, Long cursorId);
    FeedDetailResponse getFeedDetail(User user, Long reviewId);
}
