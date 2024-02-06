package com.umc.gusto.domain.review.repository;

import com.umc.gusto.domain.review.entity.Tagging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaggingRepository extends JpaRepository<Tagging, Long > {

}
