package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.apiPayload.ApiResponse;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryCommandService myCategoryCommandService;

    @GetMapping("/")
    public ApiResponse<List<MyCategoryResponse.MyCategory>> allMyCategory() {
        try {
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryCommandService.getAllMyCategory();
            return ApiResponse.onSuccess(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred", null);
        }
    }

}