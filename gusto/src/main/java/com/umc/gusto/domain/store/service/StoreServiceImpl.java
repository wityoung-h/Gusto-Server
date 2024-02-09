package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.entity.Category;
import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final PinRepository pinRepository;
    private static final int PAGE_SIZE_FIRST = 3;
    private static final int PAGE_SIZE = 6;

    @Transactional(readOnly = true)
    public StoreResponse.getStore getStore(User user, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND));
        OpeningHours openingHours = storeRepository.findOpeningHoursByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException(Code.OPENINGHOURS_NOT_FOUND));

        List<Review> top3Reviews = reviewRepository.findFirst3ByStoreOrderByLikedDesc(store);

        List<String> reviewImg = top3Reviews.stream()
                .map(Review::getImg1)
                .collect(Collectors.toList());

        boolean isPinned = pinRepository.existsByUserAndStoreStoreId(user, storeId);

        List<String> businessDays = Arrays.asList(openingHours.getBusinessDay().split(","));

        return StoreResponse.getStore.builder()
                .storeId(storeId)
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .businessDay(businessDays)
                .openedAt(openingHours.getOpenedAt())
                .closedAt(openingHours.getClosedAt())
                .contact(store.getContact())
                .reviewImg3(reviewImg)
                .pin(isPinned)
                .build();

    }

    @Transactional(readOnly = true)
    public StoreResponse.getStoreDetail getStoreDetail(User user, Long storeId, Long reviewId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND));
        Category category = storeRepository.findCategoryByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException(Code.CATEGORY_NOT_FOUND));

        List<Review> top4Reviews = reviewRepository.findFirst4ByStoreOrderByLikedDesc(store);

        List<String> reviewImg = top4Reviews.stream()
                .map(Review::getImg1)
                .collect(Collectors.toList());

        // reviews 페이징 처리 (3,6,6...)
        int pageSize;
        int pageNumber = pageable.getPageNumber();
        List<Review> reviews;

        if (reviewId != null) {
            pageSize = PAGE_SIZE_FIRST;
            reviews = reviewRepository.findReviewsAfterIdByStore(store, reviewId, PageRequest.of(pageNumber, pageSize));
        } else {
            pageSize = PAGE_SIZE;
            reviews = reviewRepository.findFirstReviewsByStore(store, PageRequest.of(pageNumber, pageSize));
        }

        List<StoreResponse.getReviews> getReviews = reviews.stream()
                .map(review -> {
                    User reviewer = review.getUser();
                    return StoreResponse.getReviews.builder()
                        .reviewId(review.getReviewId())
                        .visitedAt(review.getVisitedAt())
                        .profileImage(reviewer.getProfileImage())
                        .nickname(reviewer.getNickname())
                        .liked(review.getLiked())
                        .comment(review.getComment())
                        .img1(review.getImg1())
                        .img2(review.getImg2())
                        .img3(review.getImg3())
                        .img4(review.getImg4())
                        .build();
                })
                .toList();

        boolean isPinned = pinRepository.existsByUserAndStoreStoreId(user, storeId);

        return StoreResponse.getStoreDetail.builder()
                .storeId(storeId)
                .categoryName(category.getCategoryName())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .reviewImg4(reviewImg)
                .pin(isPinned)
                .reviews(getReviews)
                .build();
    }
}
