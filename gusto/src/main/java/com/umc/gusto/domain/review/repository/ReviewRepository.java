package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUser(Store store, User user);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);


    @Query("SELECT r.img1 FROM Review r WHERE r.store.storeId = :storeId ORDER BY r.liked DESC")
    List<String> findTopReviewImageByStoreId(Long storeId);

    Optional<Page<Review>> findAllByUser(User user, PageRequest pageRequest);
    Optional<Page<Review>> findAllByUserAndReviewIdLessThan(User user, Long reviewId,PageRequest pageRequest);
    List<Review> findByUserAndVisitedAtBetween(User user, LocalDate startDate, LocalDate lastDate);

    boolean existsByUserAndReviewIdLessThan(User user, Long reviewId);
    @Query(value = "SELECT * FROM review r WHERE r.user_id <> :user ORDER BY RAND() limit 15", nativeQuery = true)
    List<Review> findRandomFeedByUser(@Param("user") UUID user); //WHERE r.user_id <> :user

}
