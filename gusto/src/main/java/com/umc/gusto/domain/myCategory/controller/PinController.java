package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.MyCategoryRequest;
import com.umc.gusto.domain.myCategory.model.request.PinRequest;
import com.umc.gusto.domain.myCategory.service.PinService;
import com.umc.gusto.domain.user.entity.User;
import com.umc.gusto.global.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myCategories")
public class PinController {
    private final PinService pinService;

    /**
     * 찜
     * [POST] /myCategories/{myCategoryId}/pin
     */
    @PostMapping("/{myCategoryId}/pin")
    public ResponseEntity<?> createPin(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long myCategoryId,
            @RequestBody PinRequest.createPin createPin
    ) {
            User user = authUser.getUser();
            pinService.createPin(user, myCategoryId, createPin);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
