package com.umc.gusto.domain.user.controller;

import com.umc.gusto.domain.user.model.response.FollowResponse;
import com.umc.gusto.domain.user.service.UserService;
import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.domain.user.model.response.ProfileRes;
import com.umc.gusto.global.auth.model.AuthUser;
import com.umc.gusto.global.auth.model.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * 회원 가입 API
     * [POST] /users/sing-up
     * @param token
     * @param multipartFile
     * @param signUpRequest
     * @return -
     */
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestHeader("Temp-Token") String token,
                         @RequestPart(name = "profileImg", required = false) MultipartFile multipartFile,
                         @RequestPart(name = "info") SignUpRequest signUpRequest) {
        // 에러 핸들러 작업 예정으로 try-catch 작성하지 않음
        Tokens tokens = userService.createUser(token, multipartFile, signUpRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", tokens.getAccessToken());
        headers.set("refresh-token", tokens.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    /**
     * 닉네임 중복 체크 API
     * [GET] /users/check-nickname/{nickname}
     * @param nickname
     * @return -
     */
    @GetMapping("/check-nickname/{nickname}")
    public ResponseEntity checkNickname(@PathVariable("nickname")String nickname) {
        userService.checkNickname(nickname);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 닉네임 사용 확정 API
     * [POST] /users/confirm-nickname/{nickname}
     * @param nickname
     * @return -
     */
    @PostMapping("/confirm-nickname/{nickname}")
    public ResponseEntity confirmNickname(@PathVariable("nickname")String nickname) {
        userService.confirmNickname(nickname);

        return ResponseEntity.ok()
                .build();
    }

    /**
     * 먹스또 프로필 조회
     * [GET] /users/{nickname}/profile
     * @param nickname
     * @return ProfileRes
     */
    @GetMapping("/{nickname}/profile")
    public ResponseEntity<ProfileRes> retrieveProfile(@PathVariable("nickname") String nickname) {
        ProfileRes profileRes = userService.getProfile(nickname);

        return ResponseEntity.ok()
                .body(profileRes);
    }

    /**
     * 팔로우
     * [POST] /users/follow/{nickname}
     * @param nickname
     * @return -
     */
    @PostMapping("/follow/{nickname}")
    public ResponseEntity followUser(@AuthenticationPrincipal AuthUser authUser, @PathVariable("nickname") String nickname) {
        userService.followUser(authUser.getUser(), nickname);

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }


    /**
     * 언팔로우
     * [DELETE] /users/unfollow/{nickname}
     * @param nickname
     * @return -
     */
    @DeleteMapping("/unfollow/{nickname}")
    public ResponseEntity unfollow(@AuthenticationPrincipal AuthUser authUser, @PathVariable("nickname") String nickname) {
        userService.unfollowUser(authUser.getUser(), nickname);

        return ResponseEntity.status(HttpStatus.RESET_CONTENT)
                .build();
    }

    /**
     * 팔로우 리스트 조회
     * [GET] /users/following?followId={followId}
     * @param followId
     * @return List<>
     */
    @GetMapping("/following")
    public ResponseEntity<List<FollowResponse>> followList(@AuthenticationPrincipal AuthUser authUser,
                                                           @RequestParam(required = false, name = "followId") Long followId) {
        List<FollowResponse> followResponses = userService.getFollowList(authUser.getUser(), followId);

        return ResponseEntity.ok()
                .body(followResponses);
    }

    /**
     * 팔로워 리스트 조회
     * [GET] /users/follower?followId={followId}
     * @param followId
     * @return List<>
     */
    @GetMapping("/follower")
    public ResponseEntity<List<FollowResponse>> followerList(@AuthenticationPrincipal AuthUser authUser,
                                                           @RequestParam(required = false, name = "followId") Long followId) {
        List<FollowResponse> followResponses = userService.getFollwerList(authUser.getUser(), followId);

        return ResponseEntity.ok()
                .body(followResponses);
    }
}
