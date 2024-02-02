package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;

import java.util.List;

public interface MyCategoryCommandService {

    List<MyCategoryResponse.MyCategory> getAllMyCategory(String nickname);

    List<MyCategoryResponse.MyCategory> getAllMyCategoryWithLocation(String townName);

    List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategory(String nickname, Long myCategoryId);

    List<MyCategoryResponse.PinByMyCategory> getAllPinByMyCategoryWithLocation(Long myCategoryId, String townName);

    void createMyCategory(MyCategoryRequest.createMyCategory createMyCategory);

    void modifyMyCategory(Long myCategoryId, MyCategoryRequest.updateMyCategory updateMyCategory);

    void deleteMyCategories(List<Long> myCategoryId);
}