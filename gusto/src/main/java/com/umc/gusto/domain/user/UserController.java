package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.model.request.SignUpRequest;
import com.umc.gusto.global.auth.model.Tokens;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Object> signUp(@RequestHeader("Temp-Token") String token,
                         @RequestPart(name = "profileImg", required = false) MultipartFile multipartFile,
                         @RequestPart(name = "info") SignUpRequest signUpRequest) {
        // 에러 핸들러 작업 예정으로 try-catch 작성하지 않음
        Tokens tokens = userService.createUser(token, multipartFile, signUpRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-AUTH-TOKEN", tokens.getAccessToken());
        headers.set("refresh-token", tokens.getRefreshToken());

        return ResponseEntity.ok()
                .headers(headers)
                .body("success"); // 임시 메세지
    }

    /**
     * 닉네임 중복 체크 API
     * [POST] /users/check-nickname/{nickname}
     * @param nickname
     * @return -
     */
    @PostMapping("/check-nickname/{nickname}")
    public ResponseEntity<Object> checkNickname(@PathVariable("nickname")String nickname) {
        checkNickname(nickname);

        return ResponseEntity.ok()
                .body("");
    }

    /**
     * 닉네임 사용 확정 API
     * [POST] /users/confirm-nickname/{nickname}
     * @param nickname
     * @return -
     */
    @PostMapping("/confirm-nickname/{nickname}")
    public ResponseEntity<Object> confirmNickname(@PathVariable("nickname")String nickname) {
        userService.confirmNickname(nickname);

        return ResponseEntity.ok()
                .body("");
    }
}
