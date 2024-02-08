package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUser(Store store, User user);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);

    @Query("SELECT r.img1 FROM Review r WHERE r.store.storeId = :storeId ORDER BY r.liked DESC")
    Optional<String> findTopReviewImageByStoreId(Long storeId);
}
