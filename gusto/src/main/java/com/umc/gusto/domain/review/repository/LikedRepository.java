package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Liked;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikedRepository extends JpaRepository<Liked, Long> {
    Optional<Liked> findByUserAndReview(User user, Review review);
    boolean existsByUserAndReview(User user, Review review);

    @Modifying //DELETE 쿼리임을 명시
    @Query("DELETE FROM Liked l WHERE l.review.reviewId = :reviewId")
    void deleteByReviewId(@Param("reviewId") Long reviewId);
}
