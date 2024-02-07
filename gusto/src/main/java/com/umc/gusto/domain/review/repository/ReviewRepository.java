package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC'")
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC'")
    List<Review> findTop3ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC'")
    List<Review> findTop4ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIE'")
    List<Review> findByStoreOrderByReviewIdDesc(Store store);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);      // 방문횟수는 리뷰 공개여부과 상관 X

}
