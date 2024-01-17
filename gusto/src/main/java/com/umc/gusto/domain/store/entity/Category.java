package com.umc.gusto.domain.store.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @Column(nullable = false, updatable = false)
    private Long categoryId;

    @Column(nullable = false, columnDefinition = "VARCHAR(15)")
    private String categoryName;
}
