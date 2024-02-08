package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC' AND r.store = :store ORDER BY r.liked DESC LIMIT 1")
    Optional<Review> findFirstByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC' ORDER BY r.liked DESC LIMIT 3")
    List<Review> findFirst3ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.publishReview = 'PUBLIC' ORDER BY r.liked DESC LIMIT 4")
    List<Review> findFirst4ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.store = :store ORDER BY r.reviewId DESC")
    List<Review> findFirstReviewsByStore(Store store, Pageable pageable);
    @Query("SELECT r FROM Review r WHERE r.store = :store AND r.reviewId < :reviewId ORDER BY r.reviewId DESC")
    List<Review> findReviewsAfterIdByStore(Store store, Long reviewId, Pageable pageable);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);      // 방문횟수는 리뷰 공개여부과 상관 X

}
