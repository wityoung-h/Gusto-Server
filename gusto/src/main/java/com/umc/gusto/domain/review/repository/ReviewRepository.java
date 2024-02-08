package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUser(Store store, User user);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);
    Optional<Page<Review>> findAllByUser(User user, PageRequest pageRequest);
    Optional<Page<Review>> findAllByUserAndReviewIdLessThan(User user, Long reviewId,PageRequest pageRequest);
//    @Query(value = "SELECT r FROM Review r where r.user=:user AND date_format(r.visitedAt, \"%Y%m\") like %:date%", nativeQuery = true)
//    List<Review> searchAllByUserAndVisitedAtStartsWith(@Param("user") User user, @Param("date") String date);

    boolean existsByUserAndReviewIdLessThan(User user, Long reviewId);
//    @Query(value = "SELECT r FROM Review r WHERE r.user NOT :user ORDER BY RAND() LIMIT 15", nativeQuery = true)
//    List<Review> findRandomByUser(@Param("user") User user);
}
