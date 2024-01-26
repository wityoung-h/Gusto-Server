package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.review.entity.Review;
import com.umc.gusto.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long > {
    List<Review> findByStore(Store store);
}
