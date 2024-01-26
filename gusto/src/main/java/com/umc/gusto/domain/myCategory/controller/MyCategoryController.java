package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.apiPayload.ApiResponse;
import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryCommandService myCategoryCommandService;

    @GetMapping("/")
    public ApiResponse<List<MyCategoryResponse.MyCategoryDTO>> allMyCategory() {
        try {
            List<MyCategoryResponse.MyCategoryDTO> myCategoryList = myCategoryCommandService.getAllMyCategory();
            return ApiResponse.onSuccess(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred", null);
        }
    }

    @PostMapping("/create")
    public ApiResponse<MyCategory> createMyCategory(@RequestBody MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO){
            MyCategory createdCategory = myCategoryCommandService.createMyCategory(createMyCategoryDTO);
            return ApiResponse.onSuccess(createdCategory);

    }

}