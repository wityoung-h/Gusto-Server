package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.CreateMyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.request.UpdateMyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.model.response.PinByMyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryService myCategoryService;

    @GetMapping("/{nickname}")
    public ResponseEntity<List<MyCategoryResponse>> allMyCategory(
            @PathVariable String nickname) {
            List<MyCategoryResponse> myCategoryList = myCategoryService.getAllMyCategory(nickname);

            return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }

    @GetMapping
    public ResponseEntity<List<MyCategoryResponse>> allMyCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<MyCategoryResponse> myCategoryList = myCategoryService.getAllMyCategoryWithLocation(user, townName);

            return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }

    @GetMapping("/pins/{nickname}")
    public ResponseEntity<List<PinByMyCategoryResponse>> allPinByMyCategory(
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @PathVariable String nickname) {
            List<PinByMyCategoryResponse> myStoreList = myCategoryService.getAllPinByMyCategory(nickname, myCategoryId);

            return ResponseEntity.status(HttpStatus.OK).body(myStoreList);
    }

    @GetMapping("/pins")
    public ResponseEntity<List<PinByMyCategoryResponse>> allPinByCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<PinByMyCategoryResponse> myCategoryList = myCategoryService.getAllPinByMyCategoryWithLocation(user, myCategoryId,townName);

            return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }


    @PostMapping
    public ResponseEntity<?> createMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody CreateMyCategoryRequest request
    ) {
            User user = authUser.getUser();
            myCategoryService.createMyCategory(user, request);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{myCategoryId}")
    public ResponseEntity<?> modifyMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long myCategoryId,
            @RequestBody UpdateMyCategoryRequest request
    ) {
        User user = authUser.getUser();
        myCategoryService.modifyMyCategory(user, myCategoryId, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "myCategoryId") List<Long> myCategoryIds) {
            User user = authUser.getUser();
            myCategoryService.deleteMyCategories(user, myCategoryIds);

            return ResponseEntity.status(HttpStatus.OK).build();

    }
}