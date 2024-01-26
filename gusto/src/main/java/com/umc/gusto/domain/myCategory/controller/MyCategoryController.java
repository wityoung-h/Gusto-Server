package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.apiPayload.ApiResponse;
import com.umc.gusto.domain.myCategory.entity.MyCategory;
import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryCommandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryCommandService myCategoryCommandService;

    @GetMapping("/")
    public ApiResponse<List<MyCategoryResponse.MyCategoryDTO>> allMyCategory(
            @RequestParam(name = "dong", required = false) String dong
    ) {
        try {
            List<MyCategoryResponse.MyCategoryDTO> myCategoryList = myCategoryCommandService.getAllMyCategory();
            return ApiResponse.onSuccess(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred", null);
        }
    }

    @GetMapping("/{myCategoryId}/myStores")
    public ApiResponse<List<MyCategoryResponse.MyStoreByMyCategoryDTO>> allMyStoreByMyCategory(
            @RequestParam(name = "dong", required = false) String dong,
            @PathVariable Long myCategoryId) {
        try {
            List<MyCategoryResponse.MyStoreByMyCategoryDTO> myStoreList = myCategoryCommandService.getAllMyStoreByMyCategory(myCategoryId);
            return ApiResponse.onSuccess(myStoreList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred", null);
        }
    }

    @PostMapping("/create")
    public ApiResponse<String> createMyCategory(
            @RequestBody @Valid MyCategoryRequest.createMyCategoryDTO createMyCategoryDTO
    ) {
        try {
            // 여기서 createMyCategoryDTO를 사용하여 새로운 MyCategory를 생성하는 로직을 구현합니다.
            myCategoryCommandService.createMyCategory(createMyCategoryDTO);

            return ApiResponse.onSuccess("SUCCESS");
        } catch (Exception e) {
            // 예외가 발생하면 처리하고 실패 응답을 반환합니다.
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred while creating MyCategory", null);
        }
    }



    @PatchMapping("/{myCategoryId}/update")
    public ApiResponse<MyCategory> modifyMyCategory(
            @PathVariable Long myCategoryId,
            @RequestBody MyCategoryRequest.updateMyCategoryDTO request
    ) {
        try {
            myCategoryCommandService.modifyMyCategory(myCategoryId, request);
        } catch (Exception e) {
            // 수정 중 에러가 발생한 경우, 실패 응답을 반환
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred while modifying the category", null);
        }

        return null;
    }

    @PatchMapping("/{myCategoryId}/delete")
    public ApiResponse<String> deleteMyCategory(@PathVariable Long myCategoryId, MyCategoryRequest.deleteMyCategoryDTO request) {
        try {
            myCategoryCommandService.deleteMyCategory(myCategoryId, request);

            return ApiResponse.onSuccess("Category deleted successfully");
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우, 실패 응답을 반환
            return ApiResponse.onFailure("ERROR_CODE", "An error occurred while deleting the category", null);
        }
    }
}