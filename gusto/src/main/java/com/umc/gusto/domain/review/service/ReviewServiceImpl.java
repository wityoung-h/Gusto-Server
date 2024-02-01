package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.HashTag;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.entity.Tagging;
import com.umc.gusto.domain.review.model.request.ReviewRequest;
import com.umc.gusto.domain.review.repository.HashTagRepository;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NoPermission;
import com.umc.gusto.global.exception.customException.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final HashTagRepository hashTagRepository;

    public void validateReviewByUser(final User user, final Long reviewId){
        if(!reviewRepository.existsByReviewIdAndUser(reviewId, user)){
            throw new NoPermission(Code.USER_NO_PERMISSION_FOR_REVIEW);
        }
    }

    @Override
    public void createReview(User user, ReviewRequest.createReviewDTO createReviewDTO) {
        Store store= storeRepository.findById(createReviewDTO.getStoreId()).orElseThrow(()-> new NotFoundException(Code.STORE_NOT_FOUND));

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
        connectHashTag(review, hashTags);

        reviewRepository.save(review);
    }

    @Override
    @Transactional
    public void updateReview(Long reviewId, ReviewRequest.updateReviewDTO updateReviewDTO) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(()->new NotFoundException(Code.REVIEW_NOT_FOUND));

        //방문일자 변경
        if(updateReviewDTO.getVisitedAt()!=null){
            review.updateVisitedAt(updateReviewDTO.getVisitedAt());
        }
        //TODO: 이미지 사진 변경

        //메뉴명 변경
        if(updateReviewDTO.getMenuName()!=null){
            review.updateMenu(updateReviewDTO.getMenuName());
        }
        //해시태그 변경
        if(updateReviewDTO.getHashTagId()!=null){
            //두가지 방법이 있음 => 1. 기존의 해시태그 다 지우고 다시 생성 / 2. 기존의 해시태그와 현재 해시태그 값을 비교해서 지우고 생성
            //TODO: 어떤게 더 빠른지 성능 측정 하기
//            List<HashTag> originalHashTag = review.getTaggingSet().stream().map(Tagging::getHashTag).toList();
            //기존 해시태그 지우기
            review.getTaggingSet().clear();
            //새로운 해시태그로 생성
            String[] changeHashTags = updateReviewDTO.getHashTagId().split(",");
            connectHashTag(review, changeHashTags);
        }
        if(updateReviewDTO.getTaste()!=null){
            review.updateTaste(updateReviewDTO.getTaste());
        }
        if(updateReviewDTO.getSpiciness()!=null){
            review.updateSpiciness(updateReviewDTO.getSpiciness());
        }
        if(updateReviewDTO.getMood()!=null){
            review.updateMood(updateReviewDTO.getMood());
        }
        if(updateReviewDTO.getToilet()!=null){
            review.updateToilet(updateReviewDTO.getToilet());
        }
        if(updateReviewDTO.getParking()!=null){
            review.updateParking(updateReviewDTO.getParking());
        }
        if(updateReviewDTO.getComment()!=null){
            review.updateComment(updateReviewDTO.getComment());
        }

        reviewRepository.save(review);
    }

    private void connectHashTag(Review review, String[] hashTags){
        for(String hashTagId : hashTags){
            HashTag hashTag = hashTagRepository.findById(Long.parseLong(hashTagId)).orElseThrow(()-> new NotFoundException(Code.HASHTAG_NOT_FOUND));
            Tagging tagging = Tagging.builder()
                    .hashTag(hashTag)
                    .review(review)
                    .build();
            review.connectHashTag(tagging);
        }
    }

}
