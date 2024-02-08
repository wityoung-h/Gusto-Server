package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedRepository extends JpaRepository<Liked, Long> {

}
