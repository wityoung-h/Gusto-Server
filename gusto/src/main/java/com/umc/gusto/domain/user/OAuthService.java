package com.umc.gusto.domain.user;

import com.umc.gusto.domain.user.entity.Social;
import com.umc.gusto.domain.user.model.OAuth2UserWithAuthority;
import com.umc.gusto.domain.user.model.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OAuthService extends DefaultOAuth2UserService {
    private final SocialRepository socialRepository;
    // 유저 불러오기 - 해당 유저의 security context가 저장됨
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // provider - string to enum으로 변환
        Social.SocialType provider = Social.SocialType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(provider, userNameAttribute, oAuth2User.getAttributes());

        Optional<Social> socialInfo = socialRepository.findBySocialTypeAndProviderId(provider, oAuthAttributes.getId());

        Set<GrantedAuthority> authoritySet = new HashSet<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        if(socialInfo.isEmpty()) {
            authority = new SimpleGrantedAuthority("ROLE_NEW");
        }

        authoritySet.add(authority);

        OAuth2UserWithAuthority oAuth2UserWithAuthority = new OAuth2UserWithAuthority(oAuth2User, authoritySet, oAuthAttributes);

        return oAuth2UserWithAuthority;
    }
}
