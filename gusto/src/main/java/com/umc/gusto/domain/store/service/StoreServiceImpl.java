package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.entity.Category;
import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.model.response.GetReviewsResponse;
import com.umc.gusto.domain.store.model.response.GetStoreDetailResponse;
import com.umc.gusto.domain.store.model.response.GetStoreResponse;
import com.umc.gusto.domain.store.repository.OpeningHoursRepository;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final PinRepository pinRepository;
    private final OpeningHoursRepository openingHoursRepository;
    private static final int PAGE_SIZE_FIRST = 3;
    private static final int PAGE_SIZE = 6;

    @Transactional(readOnly = true)
    public GetStoreResponse getStore(User user, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND));
        List<OpeningHours> openingHoursList = openingHoursRepository.findByStoreStoreId(storeId);

        Map<OpeningHours.BusinessDay, GetStoreResponse.Timing> businessDays = new LinkedHashMap<>();
        for (OpeningHours openingHours : openingHoursList) {
            GetStoreResponse.Timing timing = new GetStoreResponse.Timing(
                    openingHours.getOpenedAt(),
                    openingHours.getClosedAt()
            );
            businessDays.put(openingHours.getBusinessDay(), timing);
        }

        List<Review> top3Reviews = reviewRepository.findFirst3ByStoreOrderByLikedDesc(store);

        List<String> reviewImg = top3Reviews.stream()
                .map(Review::getImg1)
                .collect(Collectors.toList());

        boolean isPinned = pinRepository.existsByUserAndStoreStoreId(user, storeId);

        return GetStoreResponse.builder()
                .storeId(storeId)
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .businessDay(businessDays)
                .reviewImg3(reviewImg)
                .pin(isPinned)
                .build();
    }


    @Transactional(readOnly = true)
    public GetStoreDetailResponse getStoreDetail(User user, Long storeId, Long reviewId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND));
        Category category = storeRepository.findCategoryByStoreId(storeId)
                .orElseThrow(() -> new GeneralException(Code.CATEGORY_NOT_FOUND));

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

        List<GetReviewsResponse> getReviews = reviews.stream()
                .map(review -> {
                    User reviewer = review.getUser();
                    return GetReviewsResponse.builder()
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

        return GetStoreDetailResponse.builder()
                .storeId(storeId)
                .categoryName(category.getCategoryName())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .reviewImg4(reviewImg)
                .pin(isPinned)
                .reviews(getReviews)
                .build();
    }

    @Transactional(readOnly = true)
    public GetStoreResponse getStores(User user) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new GeneralException(Code.STORE_NOT_FOUND));
        List<OpeningHours> openingHoursList = openingHoursRepository.findByStoreStoreId(storeId);

        Map<OpeningHours.BusinessDay, GetStoreResponse.Timing> businessDays = new LinkedHashMap<>();
        for (OpeningHours openingHours : openingHoursList) {
            GetStoreResponse.Timing timing = new GetStoreResponse.Timing(
                    openingHours.getOpenedAt(),
                    openingHours.getClosedAt()
            );
            businessDays.put(openingHours.getBusinessDay(), timing);
        }

        List<Review> top3Reviews = reviewRepository.findFirst3ByStoreOrderByLikedDesc(store);

        List<String> reviewImg = top3Reviews.stream()
                .map(Review::getImg1)
                .collect(Collectors.toList());

        boolean isPinned = pinRepository.existsByUserAndStoreStoreId(user, storeId);

        return GetStoreResponse.builder()
                .storeId(storeId)
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .businessDay(businessDays)
                .reviewImg3(reviewImg)
                .pin(isPinned)
                .build();
    }
}
