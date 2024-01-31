package com.umc.gusto.global.auth.model;


import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.entity.User;
import lombok.*;

import java.util.Map;
import java.util.Optional;

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
        Optional<String> providedGender = Optional.ofNullable((String) response.get("gender"));

        oAuthAttributes.gender = User.Gender.NONE;

        providedGender.ifPresent(gender -> {
            if(gender.equals("F"))
                oAuthAttributes.gender = User.Gender.FEMALE;
            else if(gender.equals("M"))
                oAuthAttributes.gender = User.Gender.MALE;
        });

        Optional<String> providedAge = Optional.ofNullable((String) response.get("age"));
        oAuthAttributes.age = User.Age.NONE;

        providedAge.ifPresent(age -> {
            switch (Integer.valueOf(age)) {
                case 0 -> oAuthAttributes.age = User.Age.NONE;
                case 10 -> oAuthAttributes.age = User.Age.TEEN;
                case 20 -> oAuthAttributes.age = User.Age.TWENTIES;
                case 30 -> oAuthAttributes.age = User.Age.THIRTIES;
                case 40 -> oAuthAttributes.age = User.Age.FOURTIES;
                case 50 -> oAuthAttributes.age = User.Age.FIFTIES;
                default -> oAuthAttributes.age = User.Age.OLDER;
            }
        });

        return oAuthAttributes;
    }
}
