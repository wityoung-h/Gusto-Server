package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    Optional<Review> findTopByStoreOrderByLikedDesc(Store store);
    Integer countByStoreAndUserNickname(Store store, String nickname);
    Integer countByStore(Store store);
}
