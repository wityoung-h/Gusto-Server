package com.umc.gusto.global.auth;

import com.umc.gusto.domain.user.model.response.FirstLogInResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/result/member")
    public ResponseEntity returnLoginResult(@RequestHeader(name = "X-Auth-Token") String XAuthToken,
                                            @RequestHeader(name = "refresh-Token") String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Auth-Token", XAuthToken);
        headers.set("refresh-Token", refreshToken);

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @GetMapping("/result/new-user")
    public ResponseEntity<FirstLogInResponse> returnLoginResult(@RequestHeader(name = "temp-token") String tempToken,
                                                                @PathParam("nickname") String nickname,
                                                                @PathParam("profileImg") String profileImg,
                                                                @PathParam("gender") String gender,
                                                                @PathParam("age") String age) {

        FirstLogInResponse firstLogInResponse = FirstLogInResponse.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .gender(gender)
                .age(age)
                .build();

        return ResponseEntity.ok()
                .header("temp-token", tempToken)
                .body(firstLogInResponse);
    }
}
