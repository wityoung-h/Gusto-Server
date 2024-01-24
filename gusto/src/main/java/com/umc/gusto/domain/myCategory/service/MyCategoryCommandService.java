package com.umc.gusto.domain.myCategory.service;

import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;

import java.util.List;

public interface MyCategoryCommandService {

    List<MyCategoryResponse.MyCategory> getAllMyCategory();
}
