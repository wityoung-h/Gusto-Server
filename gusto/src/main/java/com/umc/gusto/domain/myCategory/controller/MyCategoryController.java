package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
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
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable String nickname) {
        User user = authUser.getUser();
        List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryService.getAllMyCategory(user, nickname);

        return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }

    @GetMapping
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryService.getAllMyCategoryWithLocation(user, townName);

            return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }

    @GetMapping("/pins/{nickname}")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByMyCategory(
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @PathVariable String nickname) {
            List<MyCategoryResponse.PinByMyCategory> myStoreList = myCategoryService.getAllPinByMyCategory(nickname, myCategoryId);

            return ResponseEntity.status(HttpStatus.OK).body(myStoreList);
    }

    @GetMapping("/pins")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<MyCategoryResponse.PinByMyCategory> myCategoryList = myCategoryService.getAllPinByMyCategoryWithLocation(user, myCategoryId,townName);

            return ResponseEntity.status(HttpStatus.OK).body(myCategoryList);
    }


    @PostMapping
    public ResponseEntity<?> createMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody MyCategoryRequest.createMyCategory createMyCategory
    ) {
            User user = authUser.getUser();
            myCategoryService.createMyCategory(user, createMyCategory);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{myCategoryId}")
    public ResponseEntity<?> modifyMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long myCategoryId,
            @RequestBody MyCategoryRequest.updateMyCategory request
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