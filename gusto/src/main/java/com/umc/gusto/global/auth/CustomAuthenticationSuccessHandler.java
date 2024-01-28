package com.umc.gusto.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.global.auth.model.CustomOAuth2User;
import com.umc.gusto.global.auth.model.TokenDTO;
import com.umc.gusto.global.config.secret.JwtConfig;
import com.umc.gusto.global.util.RedisService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(authentication.getPrincipal() instanceof CustomOAuth2User) {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            Social socialInfo = oAuth2User.getSocialInfo();

            response.setCharacterEncoding("utf-8");

            if(socialInfo.getSocialStatus() == Social.SocialStatus.CONNECTED){
                String userUUID = String.valueOf(socialInfo.getUser().getUserid());
                TokenDTO tokens = jwtService.createToken(userUUID);
                
                // redis에 refresh token 정보를 저장
                redisService.setValuesWithTimeout(tokens.getRefreshToken(), userUUID, JwtConfig.REFRESH_TOKEN_VALID_TIME);
                
                response.setHeader("X-AUTH-TOKEN", tokens.getAccessToken());
                response.setHeader("refresh-token", tokens.getRefreshToken());
                response.getWriter();
                return;
            }

            // social temporal token 및 OAuthAttributes return
            response.setContentType("application/json");
            response.setHeader("Location", "http://{domain}/sign-in");
            response.setHeader("temp-token", String.valueOf(socialInfo.getTemporalToken()));

            // TODO: 차후 응답 코드 형태 맞춰 리팩토링할 것
            String body = """
                    { "isSuccess" : true, "code" : 302, "message" : "회원가입을 진행해야 합니다.", "result" : """
                    + objectMapper.writeValueAsString(oAuth2User.getOAuthAttributes());

            response.getWriter().write(body);
        }
    }
}
