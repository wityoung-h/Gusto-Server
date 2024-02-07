package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.user.entity.User;

public interface StoreService {
    StoreResponse.getStore getStore(User user, Long storeId);
}
