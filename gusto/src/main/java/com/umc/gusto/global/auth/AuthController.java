package com.umc.gusto.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.gusto.domain.user.model.response.FirstLogInResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final ObjectMapper objectMapper;

    @GetMapping("/result/member")
    public void returnLoginResult(HttpServletResponse response,
                                  @RequestParam(name = "X-Auth-Token") String XAuthToken,
                                  @RequestParam(name = "refresh-Token") String refreshToken) {
        response.setHeader("X-Auth-Token", XAuthToken);
        response.setHeader("refresh-Token", refreshToken);
    }

    @GetMapping("/result/new-user")
    public void returnLoginResult(HttpServletResponse response,
                                  @RequestParam(name = "temp-token") String tempToken,
                                  @RequestParam(value = "nickname") String nickname,
                                  @RequestParam(value = "profileImg") String profileImg,
                                  @RequestParam(value = "gender", required = false) String gender,
                                  @RequestParam(value = "age", required = false) String age) throws IOException {
        FirstLogInResponse firstLogInResponse = FirstLogInResponse.builder()
                .nickname(nickname)
                .profileImg(profileImg)
                .gender(gender)
                .age(age)
                .build();

        response.setHeader("temp-token", tempToken);
        response.getWriter().write(objectMapper.writeValueAsString(firstLogInResponse));
    }
}
