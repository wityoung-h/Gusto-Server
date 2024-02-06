package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.response.MyCategoryResponse;
import com.umc.gusto.domain.myCategory.service.MyCategoryService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class MyCategoryController {
    private final MyCategoryService myCategoryCommandService;

    @GetMapping("/{nickname}")
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategory(
            @PathVariable String nickname) {
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryCommandService.getAllMyCategory(nickname);
            return ResponseEntity.ok().body(myCategoryList);

    }

    @GetMapping("")
    public ResponseEntity<List<MyCategoryResponse.MyCategory>> allMyCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<MyCategoryResponse.MyCategory> myCategoryList = myCategoryCommandService.getAllMyCategoryWithLocation(user, townName);
            return ResponseEntity.ok().body(myCategoryList);
    }

    @GetMapping("/pins/{nickname}")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByMyCategory(
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @PathVariable String nickname) {
            List<MyCategoryResponse.PinByMyCategory> myStoreList = myCategoryCommandService.getAllPinByMyCategory(nickname, myCategoryId);
            return ResponseEntity.ok().body(myStoreList);
    }

    @GetMapping("/pins")
    public ResponseEntity<List<MyCategoryResponse.PinByMyCategory>> allPinByCategoryWithLocation(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "myCategoryId") Long myCategoryId,
            @RequestParam(name = "townName") String townName) {
            User user = authUser.getUser();
            List<MyCategoryResponse.PinByMyCategory> myCategoryList = myCategoryCommandService.getAllPinByMyCategoryWithLocation(user, myCategoryId,townName);
            return ResponseEntity.ok().body(myCategoryList);
    }


    @PostMapping("")
    public ResponseEntity<String> createMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody MyCategoryRequest.createMyCategory createMyCategory
    ) {
            User user = authUser.getUser();
            myCategoryCommandService.createMyCategory(user, createMyCategory);

            return ResponseEntity.ok().build();

    }

    @PatchMapping("/{myCategoryId}")
    public ResponseEntity<String> modifyMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long myCategoryId,
            @RequestBody MyCategoryRequest.updateMyCategory request
    ) {
        User user = authUser.getUser();
        myCategoryCommandService.modifyMyCategory(user, myCategoryId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteMyCategory(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "myCategoryId") List<Long> myCategoryIds) {
            User user = authUser.getUser();
            myCategoryCommandService.deleteMyCategories(user, myCategoryIds);

            return ResponseEntity.ok().build();

    }
}