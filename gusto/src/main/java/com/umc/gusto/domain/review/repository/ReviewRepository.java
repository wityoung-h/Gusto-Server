package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUser(Store store, User user);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);
}
