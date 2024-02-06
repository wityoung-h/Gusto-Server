package com.umc.gusto.domain.store.service;

import com.umc.gusto.domain.store.entity.OpeningHours;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.model.response.StoreResponse;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public StoreResponse.getStore getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND));

        return StoreResponse.getStore.builder()
                .storeId(storeId)
                .storeName(store.getStoreName())
                .address(store.getAddress())
//                .businessDay(OpeningHours.BusinessDay)
                .contact(store.getContact())
                .build();

    }
}
