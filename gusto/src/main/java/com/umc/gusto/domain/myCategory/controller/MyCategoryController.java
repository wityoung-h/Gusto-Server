package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryCommandService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryCommandService myCategoryCommandService;

    @GetMapping("/{nickname}")
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategory(
            @PathVariable String nickname) {
        try {
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryCommandService.getAllMyCategory(nickname);
            return ResponseEntity.ok(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            throw new RuntimeException("error");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategoryWithLocation(
            @RequestParam(name = "townName") String townName) {
        try {
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryCommandService.getAllMyCategoryWithLocation(townName);
            return ResponseEntity.ok(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            throw new RuntimeException("error");
        }
    }

    @GetMapping("/pinStores/{nickname}")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByMyCategory(
            @RequestParam(name = "myCategoryId") Long myCategoryId, @PathVariable String nickname) {
        try {
            List<MyCategoryResponse.PinByMyCategory> myStoreList = myCategoryCommandService.getAllPinByMyCategory(nickname, myCategoryId);
            return ResponseEntity.ok(myStoreList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            throw new RuntimeException("error");
        }
    }

    @GetMapping("/pinStores")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByCategoryWithLocation(
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @RequestParam(name = "townName") String townName) {
        try {
            List<MyCategoryResponse.PinByMyCategory> myCategoryList = myCategoryCommandService.getAllPinByMyCategoryWithLocation(myCategoryId,townName);
            return ResponseEntity.ok(myCategoryList);
        } catch (Exception e) {
            // Handle the exception and return a failure response
            throw new RuntimeException("error");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<String> createMyCategory(
            @RequestBody @Valid MyCategoryRequest.createMyCategory createMyCategory
    ) {
        try {
            // 여기서 createMyCategoryDTO를 사용하여 새로운 MyCategory를 생성하는 로직을 구현합니다.
            myCategoryCommandService.createMyCategory(createMyCategory);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            // 예외가 발생하면 처리하고 실패 응답을 반환합니다.
            throw new RuntimeException("MyCategory with the same name already exists and is in ACTIVE");
        }
    }



    @PatchMapping("/{myCategoryId}/update")
    public ResponseEntity<String> modifyMyCategory(
            @PathVariable Long myCategoryId,
            @RequestBody MyCategoryRequest.updateMyCategory request
    ) {
        try {
            myCategoryCommandService.modifyMyCategory(myCategoryId, request);

            return ResponseEntity.ok("success");
        } catch (Exception e) {
            // 수정 중 에러가 발생한 경우, 실패 응답을 반환
            throw new RuntimeException("error");
        }

    }

    @PatchMapping("/delete")
    public ResponseEntity<String> deleteMyCategory(@RequestParam(name = "myCategoryId") List<Long> myCategoryIds) {
        try {
            myCategoryCommandService.deleteMyCategories(myCategoryIds);

            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우, 실패 응답을 반환
            throw new RuntimeException("error");
        }
    }
}