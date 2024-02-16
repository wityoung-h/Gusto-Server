package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.response.*;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.common.PublishStatus;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import com.umc.gusto.global.exception.customException.PrivateItemException;
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

    @Override
    public FeedDetailResponse getFeedDetail(Long reviewId) {
        //TOOD: reviewServiceImpl와 중복되는 코드
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new NotFoundException(Code.REVIEW_NOT_FOUND));
        //TODO: 후에 각 리뷰마다의 공개, 비공개를 확인해서 주는거로 수정하기
        if(!review.getUser().getPublishReview().equals(PublishStatus.PUBLIC)){
            throw new PrivateItemException(Code.NO_PUBLIC_REVIEW);
        }

        StringBuilder hashTags = new StringBuilder();
        review.getTaggingSet().stream().map(r-> r.getHashTag().getHasTagId()).forEach(o-> hashTags.append(o).append(","));
        //마지막 문자 , 제거
        hashTags.deleteCharAt(hashTags.length()-1);
        return FeedDetailResponse.of(review, hashTags.toString());
    }
}
