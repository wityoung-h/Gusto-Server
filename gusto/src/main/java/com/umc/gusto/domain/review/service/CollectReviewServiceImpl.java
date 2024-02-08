package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.request.ReviewViewRequest;
import com.umc.gusto.domain.review.model.response.CollectReviewsOfInstaResponse;
import com.umc.gusto.domain.review.model.response.InstaViewResponse;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectReviewServiceImpl implements CollectReviewService{
    private final ReviewRepository reviewRepository;

    @Override
    public CollectReviewsOfInstaResponse getReviewOfInstaView(User user, ReviewViewRequest reviewViewRequest) {
        Sort sort = Sort.by("visitedAt").descending();
        PageRequest pageRequest = PageRequest.of(0, reviewViewRequest.getSize(), sort);

        //페이징해서 가져오기
        Page<Review> reviews = getReviews(user, reviewViewRequest.getReviewId(), pageRequest);

        //다음에 조회될 리뷰가 있는지 확인하기
        List<Review> reviewList = reviews.toList();
        Long lastReviewId = reviewList.get(reviewList.size()-1).getReviewId();
        boolean checkNext = hasNext(user, lastReviewId);

        List<InstaViewResponse> instaViewResponses = reviews.map(InstaViewResponse::of).toList();
        return CollectReviewsOfInstaResponse.of(instaViewResponses, checkNext);
    }

    public Page<Review> getReviews(User user, Long cursorId, PageRequest pageRequest){
        //최초로 조회한 경우
        if(cursorId==null){
            return reviewRepository.findAllByUser(user, pageRequest).orElseThrow(()-> new NotFoundException(Code.REVIEW_NOT_FOUND));
        }else{ //최초가 아닌 경우
            //커서 id를 기반으로 그보다 낮은 ID의 리뷰를 가져온다 => 최신 날짜가 이전의 데이터가 나타난다.
            return reviewRepository.findAllByUserAndReviewIdLessThan(user, cursorId, pageRequest).orElseThrow(()-> new NotFoundException(Code.REVIEW_NOT_FOUND));
        }
    }

    public boolean hasNext(User user, Long reviewId){
        if(reviewId==null) return false;
        return reviewRepository.existsByUserAndReviewIdLessThan(user, reviewId);
    }
}
