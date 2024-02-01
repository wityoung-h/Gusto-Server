package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.HashTag;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.entity.Tagging;
import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.domain.review.repository.HashTagRepository;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.review.repository.TaggingRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.auth.model.AuthUser;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final HashTagRepository hashTagRepository;
    private final TaggingRepository taggingRepository;

    @Override
    public void createReview(AuthUser authUser, ReviewRequest.createReviewDTO createReviewDTO) {
        Store store= storeRepository.findById(createReviewDTO.getStoreId()).orElseThrow(()-> new NotFoundException(Code.STORE_NOT_FOUND));
        User user = authUser.getUser();
        //리뷰 생성
        Review review = Review.builder()
                .store(store)
                .user(user)
                .visitedAt(createReviewDTO.getVisitedAt())
                .menuName(createReviewDTO.getMenuName())
                .taste(createReviewDTO.getTaste())
                .spiciness(createReviewDTO.getSpiciness())
                .mood(createReviewDTO.getMood())
                .toilet(createReviewDTO.getToilet())
                .parking(createReviewDTO.getParking())
                .comment(createReviewDTO.getComment())
                .build();


        //리뷰와 해시태그 연결
        String[] hashTags = createReviewDTO.getHashTagId().split(",");
        for(String hashTagId : hashTags){
            HashTag hashTag = hashTagRepository.findById(Long.parseLong(hashTagId)).orElseThrow(()-> new NotFoundException(Code.HASHTAG_NOT_FOUND));
            Tagging tagging = Tagging.builder()
                    .hashTag(hashTag)
                    .review(review)
                    .build();
            review.connectHashTag(tagging);
        }

        reviewRepository.save(review);
    }
}
