package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.model.request.PinRequest;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface PinService {
    void createPin(User user, Long myCategoryId, PinRequest.createPin createPin);
    void deletePin(User user, List<Long> pinId);
}
