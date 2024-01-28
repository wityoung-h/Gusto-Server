package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;

import java.util.List;

public interface MyCategoryCommandService {

    List<MyCategoryResponse.MyCategoryDTO> getAllMyCategory(String nickname);

    List<MyCategoryResponse.PinByMyCategoryDTO> getAllPinByMyCategory(String nickname, Long myCategoryId, String dong);

    void createMyCategory(MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO);

    void modifyMyCategory(Long myCategoryId, MyCategoryRequest.updateMyCategoryDTO updateMyCategoryDTO);

    void deleteMyCategories(List<Long> myCategoryId);
}