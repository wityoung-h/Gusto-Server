package com.umc.gusto.domain.myCategory.controller;

import com.umc.gusto.domain.myCategory.model.request.CreatePinRequest;
import com.umc.gusto.domain.myCategory.service.PinService;
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
            @RequestBody CreatePinRequest createPin
    ) {
            User user = authUser.getUser();
            pinService.createPin(user, myCategoryId, createPin);

            return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 찜 취소
     * [DELETE] /myCategories/pins?pinId={pinId}&...
     */
    @DeleteMapping("/pins")
    public ResponseEntity<?> deletePin(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(name = "pinId") List<Long> pinIds) {
            User user =  authUser.getUser();
            pinService.deletePin(user, pinIds);

            return ResponseEntity.status(HttpStatus.OK).build();
    }

}
