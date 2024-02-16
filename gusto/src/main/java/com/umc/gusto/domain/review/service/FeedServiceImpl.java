package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.response.BasicViewResponse;
import com.umc.gusto.domain.review.model.response.RandomFeedResponse;
import com.umc.gusto.domain.review.model.response.SearchFeedResponse;
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
        List<Review> feedList = reviewRepository.findRandomFeedByUser(user.getUserId());
//        List<Review> feedList = new ArrayList<>();
        return feedList.stream().map(RandomFeedResponse::of).collect(Collectors.toList());
    }

    @Override
    public SearchFeedResponse searchFeed(String keyword, List<Long> hashTags) {
        List<Review> searchResult = new ArrayList<>();
        //맛집과 해시태그를 함께 검색하는 경우
        if(keyword!=null && hashTags!=null){
            for(Long hashTagId: hashTags){
                searchResult.addAll(reviewRepository.searchByStoreAndHashTagContains(keyword, hashTagId));
            }
        } else if(keyword!=null){ //맛집을 검색하는 경우
            searchResult = reviewRepository.searchByStoreContains(keyword);
        } else if(hashTags!=null){
            for(Long hashTagId: hashTags){
                searchResult.addAll(reviewRepository.searchByHashTagContains(hashTagId));
            }
        }

        List<BasicViewResponse> basicViewResponse = searchResult.stream().map(BasicViewResponse::of).toList();
        return SearchFeedResponse.of(basicViewResponse);
    }
}
