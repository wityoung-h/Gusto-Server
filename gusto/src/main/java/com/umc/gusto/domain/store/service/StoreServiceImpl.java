package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.review.repository.ReviewRepository;
import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final PinRepository pinRepository;
    @Transactional(readOnly = true)
    public StoreResponse.getStore getStore(User user, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND));
        OpeningHours openingHours = storeRepository.findOpeningHoursByStoreId(storeId)
                .orElseThrow(() -> new NotFoundException(Code.OPENINGHOURS_NOT_FOUND));

        List<Review> top3Reviews = reviewRepository.findTop3ByStoreOrderByLikedDesc(store);

        List<String> reviewImg = top3Reviews.stream()
                .map(Review::getImg1)
                .collect(Collectors.toList());

        boolean isPinned = pinRepository.existsByUserAndStoreStoreId(user, storeId);

        return StoreResponse.getStore.builder()
                .storeId(storeId)
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .businessDay(openingHours.getBusinessDay())
                .openedAt(openingHours.getOpenedAt())
                .closedAt(openingHours.getClosedAt())
                .contact(store.getContact())
                .reviewImg(reviewImg)
                .pin(isPinned)
                .build();

    }
}
