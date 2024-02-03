package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.user.entity.User;

import java.util.List;

public interface MyCategoryService {

    List<MyCategoryResponse.MyCategory> getAllMyCategory(String nickname);

    List<MyCategoryResponse.MyCategory> getAllMyCategoryWithLocation(User user, String townName);

    List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategory(String nickname, Long myCategoryId);

    List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategoryWithLocation(User user, Long myCategoryId, String townName);

    void createMyCategory(User user, MyCategoryRequest.createMyCategory createMyCategory);

    void modifyMyCategory(User user,Long myCategoryId, MyCategoryRequest.updateMyCategory updateMyCategory);

    void deleteMyCategories(User user, List<Long> myCategoryId);
}