package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.user.entity.User;
import org.springframework.data.domain.Pageable;


public interface StoreService {
    StoreResponse.getStore getStore(User user, Long storeId);
    StoreResponse.getStoreDetail getStoreDetail(User user, Long storeId, Long reviewId, Pageable pageable);
}
