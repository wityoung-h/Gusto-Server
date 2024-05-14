package com.umc.gusto.domain.review.service;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.model.response.*;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.domain.user.repository.UserRepository;
import com.umc.gusto.global.common.BaseEntity;
import com.umc.gusto.global.common.PublishStatus;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import com.umc.gusto.global.exception.customException.PrivateItemException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectReviewServiceImpl implements CollectReviewService{
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public CollectReviewsResponse getReviewOfInstaView(User user, Long reviewId, int size) {
        //페이징해서 가져오기
        Page<Review> reviews = pagingReview(user, reviewId, size);

        //다음에 조회될 리뷰가 있는지 확인하기
        boolean checkNext = reviews.hasNext();
        List<BasicViewResponse> basicViewResponse = reviews.map(BasicViewResponse::of).toList();
//        Long cursorId = basicViewResponse.get(basicViewResponse.size()-1).getReviewId();
        Long cursorId = null;
        if(!basicViewResponse.isEmpty()){
            cursorId = basicViewResponse.get(basicViewResponse.size()-1).getReviewId();
        }
        return CollectReviewsResponse.of(basicViewResponse, cursorId,checkNext);
    }

    @Override
    public CollectReviewsOfCalResponse getReviewOfCalView(User user, Long reviewId, int size, LocalDate date) {
        //해당 달의 첫 날짜, 마지막 날짜 구하기
        LocalDate startDate = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        List<Review> reviews = reviewRepository.findByUserAndStatusAndVisitedAtBetween(user, BaseEntity.Status.ACTIVE,startDate, lastDate);

        List<CalendarViewResponse> calendarViewResponses = reviews.stream().map(CalendarViewResponse::of).toList();
        return CollectReviewsOfCalResponse.builder().reviews(calendarViewResponses).build();
    }

    @Override
    public CollectReviewsResponse getReviewOfTimeView(User user, Long reviewId, int size) {
        //페이징해서 가져오기
        Page<Review> reviews = pagingReview(user, reviewId, size);

        //다음에 조회될 리뷰가 있는지 확인하기
        boolean checkNext = reviews.hasNext();

        List<TimelineViewResponse> timelineViewResponses = reviews.map(review -> {
                    int visitedCount = reviewRepository.countByStoreAndUser(review.getStore(), user);
                    return TimelineViewResponse.of(review, visitedCount);
                }).toList();
//        return CollectReviewsOfTimelineResponse.of(timelineViewResponses, checkNext);
//        Long cursorId = timelineViewResponses.get(timelineViewResponses.size()-1).getReviewId();
        Long cursorId = null;
        if(!timelineViewResponses.isEmpty()){
            cursorId = timelineViewResponses.get(timelineViewResponses.size()-1).getReviewId();
        }
        return CollectReviewsResponse.of(timelineViewResponses, cursorId,checkNext);
    }
  
    @Override
    public CollectReviewsResponse getOthersReview(String nickName, Long reviewId, int size) {
        User other = userRepository.findByNicknameAndMemberStatusIs(nickName, User.MemberStatus.ACTIVE).orElseThrow(()-> new NotFoundException(Code.USER_NOT_FOUND));
        //다른 유저의 공개 여부 확인
        if(!other.getPublishReview().equals(PublishStatus.PUBLIC)){
            throw new PrivateItemException(Code.NO_PUBLIC_REVIEW);
        }

        //페이징해서 가져오기
        Page<Review> reviews = pagingReview(other, reviewId, size);

        //다음에 조회될 리뷰가 있는지 확인하기
        boolean checkNext = reviews.hasNext();
        List<BasicViewResponse> basicViewResponse = reviews.map(BasicViewResponse::of).toList();
        Long cursorId = basicViewResponse.get(basicViewResponse.size()-1).getReviewId();
        return CollectReviewsResponse.of(basicViewResponse, cursorId, checkNext);
    }

    private Page<Review> pagingReview(User user, Long cursorId, int size){
        //최신순 날짜로 정렬
        Sort sort = Sort.by("visitedAt").descending().and(Sort.by("createdAt").descending());
        PageRequest pageRequest = PageRequest.of(0, size, sort);

        Page<Review> reviews;
        //최초로 조회한 경우
        if(cursorId==null){
            reviews = reviewRepository.findAllByUserAndStatus(user, BaseEntity.Status.ACTIVE, pageRequest).orElseThrow(()-> new NotFoundException(Code.REVIEW_NOT_FOUND));
        }else{ //최초가 아닌 경우
            //커서 id를 기반으로 그보다 낮은 ID의 리뷰를 가져온다 => 최신 날짜가 이전의 데이터가 나타난다.
            Review review = reviewRepository.findById(cursorId).orElseThrow(()->new NotFoundException(Code.REVIEW_NOT_FOUND));
            reviews = reviewRepository.findAllByUserAndStatusAndReviewIdLessThanAndVisitedAtLessThanEqual(user, BaseEntity.Status.ACTIVE, cursorId, review.getVisitedAt(),pageRequest).orElseThrow(()-> new NotFoundException(Code.REVIEW_NOT_FOUND));
        }

        if(reviews.isEmpty()){
            throw new NotFoundException(Code.REVIEW_NOT_FOUND);
        }
        return reviews;
    }
}
