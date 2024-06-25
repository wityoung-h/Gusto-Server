package com.umc.gusto.global.auth;

import com.umc.gusto.domain.user.repository.SocialRepository;
import com.umc.gusto.domain.user.service.UserService;
import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.global.auth.model.CustomOAuth2User;
import com.umc.gusto.global.auth.model.OAuthAttributes;
import com.umc.gusto.global.exception.Code;
import com.umc.gusto.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {
    private final SocialRepository socialRepository;
    private final UserService userService;
    private final RestClient restClient = RestClient.create();
    @Value("${default.img.url}")
    private static String DEFAULT_PROFILE_IMG;
    private static final String AUTH_TYPE = "Bearer ";

    // 유저 불러오기 - 해당 유저의 security context가 저장됨
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("***********************");
        System.out.println(userRequest.getAccessToken().getTokenValue());
        System.out.println("***********************");

        // provider - string to enum으로 변환
        Social.SocialType provider = Social.SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(provider, userNameAttribute, oAuth2User.getAttributes());

        Optional<Social> socialInfo = socialRepository.findBySocialTypeAndProviderId(provider, oAuthAttributes.getId());

        Social info;

        if(socialInfo.isEmpty()) {
             info = socialRepository.save(Social.builder()
                     .socialType(provider)
                     .providerId(oAuthAttributes.getId())
                     .socialStatus(Social.SocialStatus.WAITING_SIGN_UP)
//                     .temporalToken(UUID.randomUUID())
                     .build());

             if(oAuthAttributes.getNickname() == null) {
                 oAuthAttributes.updateNickname(userService.generateRandomNickname().getNickname());
             }

             if(oAuthAttributes.getProfileImg() == null) {
                 oAuthAttributes.updateProfileImg(DEFAULT_PROFILE_IMG);
             }

        } else {
            info = socialInfo.get();
        }

        if(info.getSocialStatus() == Social.SocialStatus.DISCONNECTED) {
            // TODO: error throw
        }

        return CustomOAuth2User.builder()
                .delegate(oAuth2User)
                .oAuthAttributes(oAuthAttributes)
                .socialInfo(info)
                .build();
    }

    public void loadUserInfo(String provider, String providerId, String accessToken) {
        // TODO: ACCESS TOKEN 복호화

        String header = AUTH_TYPE + accessToken;
        String id = "";

        if(provider.equals("NAVER")) {
            Map result = restClient.get()
                    .uri("https://openapi.naver.com/v1/nid/me")
                    .header("Authorization", header)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .body(Map.class);

            Map<String, String> response = new HashMap<>((LinkedHashMap) result.get("response"));
            id = response.get("id");
        } else if(provider.equals("GOOGLE")) {
            Map result = restClient.get()
                    .uri("https://www.googleapis.com/oauth2/v2/userinfo")
                    .header("Authorization", header)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .body(Map.class);

            id = String.valueOf(result.get("id"));
        } else {
            Map result = restClient.get()
                    .uri("https://kapi.kakao.com/v2/user/me")
                    .header("Authorization", header)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                        throw new GeneralException(Code.OAUTH_FIND_ERROR);
                    }))
                    .body(Map.class);

            id = String.valueOf(result.get("id"));
        }

        if(!providerId.equals(id)) {
            throw new GeneralException(Code.UNMATCHED_AUTH_INFO);
        }
    }
}
