package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUser(Store store, User user);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);
    Optional<Page<Review>> findAllByUser(User user, PageRequest pageRequest);
    Optional<Page<Review>> findAllByUserAndReviewIdLessThan(User user, Long reviewId,PageRequest pageRequest);
    boolean existsByUserAndReviewIdLessThan(User user, Long reviewId);
}
