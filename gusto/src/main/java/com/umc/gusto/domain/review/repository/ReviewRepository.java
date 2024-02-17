package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.status = 'ACTIVE' AND r.user.publishReview = 'PUBLIC'AND r.store = :store ORDER BY r.liked DESC LIMIT 1")
    Optional<Review> findFirstByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.status = 'ACTIVE' AND r.user.publishReview = 'PUBLIC' ORDER BY r.liked DESC, r.reviewId DESC LIMIT 3")
    List<Review> findFirst3ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.status = 'ACTIVE' AND r.user.publishReview = 'PUBLIC' ORDER BY r.liked DESC, r.reviewId DESC LIMIT 4")
    List<Review> findFirst4ByStoreOrderByLikedDesc(Store store);
    @Query("SELECT r FROM Review r WHERE r.status = 'ACTIVE' AND r.user.publishReview = 'PUBLIC' AND r.store = :store ORDER BY r.reviewId DESC")
    List<Review> findFirstReviewsByStore(Store store, Pageable pageable);
    @Query("SELECT r FROM Review r WHERE r.status = 'ACTIVE' AND r.user.publishReview = 'PUBLIC' AND r.store = :store AND r.reviewId < :reviewId ORDER BY r.reviewId DESC")
    List<Review> findReviewsAfterIdByStore(Store store, Long reviewId, Pageable pageable);
    boolean existsByReviewIdAndUser(Long reviewId, User user);
    Integer countByStoreAndUserNickname(Store store, String nickname);      // 방문횟수는 리뷰 공개여부과 상관 X
    Integer countByStoreAndUser(Store store, User user);
    @Query("SELECT r.img1 FROM Review r WHERE r.store.storeId = :storeId ORDER BY r.liked DESC")
    List<String> findTopReviewImageByStoreId(Long storeId);

    Optional<Page<Review>> findAllByUser(User user, PageRequest pageRequest);
    Optional<Page<Review>> findAllByUserAndReviewIdLessThan(User user, Long reviewId,PageRequest pageRequest);
    List<Review> findByUserAndVisitedAtBetween(User user, LocalDate startDate, LocalDate lastDate);

    boolean existsByUserAndReviewIdLessThan(User user, Long reviewId);
    @Query(value = "SELECT * FROM review r WHERE r.user_id <> :user ORDER BY RAND() limit 15", nativeQuery = true)
    List<Review> findRandomFeedByUser(@Param("user") UUID user); //WHERE r.user_id <> :userZ

    boolean existsByStoreAndUserNickname(Store store, String nickname);

    //검색 기능
    @Query("SELECT r FROM Review r WHERE r.store.storeName like concat('%', :keyword, '%') OR r.comment like concat('%', :keyword, '%')")
    List<Review> searchByStoreContains(String keyword); //TODO: 후에 페이징 처리 하기
    @Query("SELECT t.review FROM Tagging t WHERE t.review.store.storeName like concat('%', :keyword, '%') AND t.hashTag.hasTagId = :hashTagId")
    List<Review> searchByStoreAndHashTagContains(String keyword, Long hashTagId);
    @Query("SELECT t.review FROM Tagging t WHERE t.hashTag.hasTagId = :hashTagId")
    List<Review> searchByHashTagContains(Long hashTagId);
}
