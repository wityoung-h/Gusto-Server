package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.model.request.PinRequest;
import com.umc.gusto.domain.user.entity.User;

public interface PinService {
    void createPin(User user, Long myCategoryId, PinRequest.createPin createPin);
}
