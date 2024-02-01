package com.umc.gusto.global.auth.model;


import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.entity.User;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OAuthAttributes {
    private String id;
    private String nickname;
    private String profileImg;
    private User.Gender gender;
    private User.Age age;

    public static OAuthAttributes of(Social.SocialType provider, String userNameAttribute, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttribute);

        if(provider == Social.SocialType.NAVER) {
            return ofNaver(response);
        }

        return null;
    }

    public static OAuthAttributes ofNaver(Map<String, Object> response) {
        OAuthAttributes oAuthAttributes = new OAuthAttributes();
        oAuthAttributes.id = (String) response.get("id");
        oAuthAttributes.nickname = (String) response.get("nickname");
        oAuthAttributes.profileImg = (String) response.get("profile_image");

        // Gender & Age-range의 경우 provider에 따라 제공 방법이 다르므로 개별 파싱
        // 여러 provider 연결 이후 개선 예정
        String providedGender = (String) response.get("gender");
        oAuthAttributes.gender = User.Gender.NONE;
        if(providedGender.equals("F"))
            oAuthAttributes.gender = User.Gender.FEMALE;
        else if(providedGender.equals("M"))
            oAuthAttributes.gender = User.Gender.MALE;

        String providedAge = (String) response.get("age");
        providedAge = providedAge.substring(0, providedAge.indexOf("-"));

        switch (Integer.valueOf(providedAge)) {
            case 10 -> oAuthAttributes.age = User.Age.TEEN;
            case 20 -> oAuthAttributes.age = User.Age.TWENTIES;
            case 30 -> oAuthAttributes.age = User.Age.THIRTIES;
            case 40 -> oAuthAttributes.age = User.Age.FOURTIES;
            case 50 -> oAuthAttributes.age = User.Age.FIFTIES;
            default -> oAuthAttributes.age = User.Age.NONE;
        }

        if(Integer.valueOf(providedAge) >= 60) {
            oAuthAttributes.age = User.Age.OLDER;
        }

        return oAuthAttributes;
    }
}
