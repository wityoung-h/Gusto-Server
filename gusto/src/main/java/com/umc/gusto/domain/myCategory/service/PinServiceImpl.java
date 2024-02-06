package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.entity.Pin;
import com.umc.gusto.domain.myCategory.model.request.PinRequest;
import com.umc.gusto.domain.myCategory.repository.MyCategoryRepository;
import com.umc.gusto.domain.myCategory.repository.PinRepository;
import com.umc.gusto.domain.store.entity.Store;
import com.umc.gusto.domain.store.repository.StoreRepository;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.customException.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinServiceImpl implements PinService{
    private final PinRepository pinRepository;
    private final MyCategoryRepository myCategoryRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public void createPin(User user, Long myCategoryId, PinRequest.createPin createPin) {
        MyCategory myCategory = myCategoryRepository.findByUserAndMyCategoryId(user, myCategoryId)
                .orElseThrow(() -> new NotFoundException(Code.MYCATEGORY_NOT_FOUND));
        Store store = storeRepository.findById(createPin.getStoreId())
                        .orElseThrow(() -> new NotFoundException(Code.STORE_NOT_FOUND));

        Pin pin = Pin.builder()
                .user(user)
                .myCategory(myCategory)
                .store(store)
                .build();

        pinRepository.save(pin);

    }

    @Transactional
    public void deletePin(User user, List<Long> pinIds) {
        for (Long pinId : pinIds) {
            Pin pin = pinRepository.findByUserAndPinId(user, pinId)
                    .orElseThrow(() -> new NotFoundException(Code.PIN_NOT_FOUND));

            pinRepository.delete(pin);

        }

    }

}
